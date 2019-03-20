package com.szinternet.crm.ui.bindbankcode;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;

import com.szinternet.crm.AppManager;
import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.AllBankBean;
import com.szinternet.crm.ui.bindidentity.BindIdentityActivity;
import com.szinternet.crm.utils.CommonUtil;
import com.szinternet.crm.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.RequestBody;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class BindBankCodePresenter extends RxPresenter<BindBankCodeContract.View> implements BindBankCodeContract.Presenter<BindBankCodeContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject
    public BindBankCodePresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }

    @Override
    public void bindBankCode() {
        if (!checkParamIsVail()) {
            return;
        }
        mView.showLoadDialog();
        Map<String, RequestBody> params = new HashMap<>();
        params.put("token", netApi.convertToRequestBody(CreditCardApplication.getsInstance().getToken()));
        params.put("realname", netApi.convertToRequestBody(mView.getUserName()));
        params.put("account", netApi.convertToRequestBody(mView.getBankCode()));
        params.put("bank_id", netApi.convertToRequestBody(mView.getBankType()));
        params.put("countname", netApi.convertToRequestBody(mView.getBankBranch()));
        params.put("phone", netApi.convertToRequestBody(mView.getPhone()));
        params.put("code", netApi.convertToRequestBody(mView.getVer()));
        params.put("nickname", netApi.convertToRequestBody(mView.getNickname()));
        params.put("bank_name", netApi.convertToRequestBody(mView.getBankName()));
        params.put("ic", netApi.convertToRequestBody(mView.getIc()));
        netApi.bindBankCode(params)
                .compose(RxSchedulers.<BaseBean<BaseBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<BaseBean>(mView, mActivity, new HttpObserverInterface<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean<BaseBean> httpResult) {
                        mView.onSuccess();
                        //销毁前一个身份绑定的界面
                        AppManager.getAppManager().finishActivity(BindIdentityActivity.class);
                        finishSelf();
                    }

                    @Override
                    public void onError(String msg, int code) {
                        mView.onError(msg);
                    }
                }));
    }


    @Override
    public void getVerCode() {
        if (TextUtils.isEmpty(mView.getPhone())) {
            ToastUtils.showToast("手机号不能为空");
            return;
        }
        if (!CommonUtil.checkPhone(mView.getPhone())) {
            ToastUtils.showToast("手机号格式错误");
            return;
        }
        netApi.sendRealNameCode(CreditCardApplication.getsInstance().getToken(), mView.getPhone())
                .compose(RxSchedulers.<BaseBean<BaseBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<BaseBean>(mView, mActivity, new HttpObserverInterface<BaseBean>() {

                    @Override
                    public void onSuccess(BaseBean<BaseBean> httpResult) {
                        changeSendCodeBtn();
                    }

                    @Override
                    public void onError(String msg, int code) {

                    }
                }));
    }

    @Override
    public void getAllBankList() {
        netApi.getAllBankList(CreditCardApplication.getsInstance().getToken())
                .compose(RxSchedulers.<BaseBean<AllBankBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<AllBankBean>(mView, mActivity, new HttpObserverInterface<AllBankBean>() {

                    @Override
                    public void onSuccess(BaseBean<AllBankBean> httpResult) {
                        mView.onBankListSuccess(httpResult.getData().data);
                    }

                    @Override
                    public void onError(String msg, int code) {

                    }
                }));
    }

    private void changeSendCodeBtn() {
        //开始1分钟倒计时
        //每一秒执行一次Task
        final int count = 60;
        Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                .take(count + 1) //设置循环11次
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - (aLong + 1);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (mView.getSendCodeView() != null) {
                            if (aLong > 0) {
                                mView.getSendCodeView().setEnabled(false);//在发送数据的时候设置为不能点击
                                mView.getSendCodeView().setTextColor(Color.GRAY);//背景色设为灰色
                                mView.getSendCodeView().setText(aLong + "秒");
                            } else {
                                mView.getSendCodeView().setText("获取验证码");//数据发送完后设置为原来的文字
                                mView.getSendCodeView().setTextColor(Color.parseColor("#FF375BF1"));//数据发送完后设置为原来背景色
                            }
                        }
                    }

                });

    }

    @Override
    public boolean checkParamIsVail() {

        if (TextUtils.isEmpty(mView.getUserName())) {
            ToastUtils.showToast("姓名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mView.getBankCode())) {
            ToastUtils.showToast("储蓄卡卡号不能为空");
            return false;
        }

        if (!CommonUtil.isBankNumber(mView.getBankCode())) {
            ToastUtils.showToast("储蓄卡卡号格式错误");
            return false;
        }
        if (TextUtils.isEmpty(mView.getBankType())) {
            ToastUtils.showToast("请选择开户银行");
            return false;
        }
        if (TextUtils.isEmpty(mView.getUserName())) {
            ToastUtils.showToast("预留手机号不能为空");
            return false;
        }

        if (!CommonUtil.checkPhone(mView.getPhone())) {
            ToastUtils.showToast("手机号格式错误");
            return false;
        }

        if (TextUtils.isEmpty(mView.getVer())) {
            ToastUtils.showToast("验证码不能为空");
            return false;
        }
        if (!CommonUtil.checkVerificationCode(mView.getVer())) {
            ToastUtils.showToast("验证码格式不正确");
            return false;
        }
        return true;
    }

}
