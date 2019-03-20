package com.szinternet.crm.ui.register;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;

import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.RegisterResultBean;
import com.szinternet.crm.ui.login.LoginActivity;
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
public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter<RegisterContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject
    public RegisterPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }

    @Override
    public void register() {
        if (!checkParamIsVail()) {
            return;
        }
        mView.showLoadDialog();
        Map<String, RequestBody> params = new HashMap<>();
        params.put("username", netApi.convertToRequestBody(mView.getUserName()));
        params.put("password", netApi.convertToRequestBody(mView.getPassword()));
        params.put("code", netApi.convertToRequestBody(mView.getVer()));
        params.put("rer", netApi.convertToRequestBody(TextUtils.isEmpty(mView.getIntroducter()) ? "" : mView.getIntroducter()));
         netApi.register(params)
                .compose(RxSchedulers.<BaseBean<RegisterResultBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<RegisterResultBean>(mView, mActivity, new HttpObserverInterface<RegisterResultBean>() {
                    @Override
                    public void onSuccess(BaseBean<RegisterResultBean> httpResult) {
                        mView.onSuccess();
                        mView.start2Activity(LoginActivity.class);
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
        if (TextUtils.isEmpty(mView.getUserName())) {
            ToastUtils.showToast("手机号不能为空");
            return;
        }
        if (!CommonUtil.checkPhone(mView.getUserName())) {
            ToastUtils.showToast("手机号格式错误");
            return;
        }
        netApi.sendCode(mView.getUserName())
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

    private void changeSendCodeBtn() {
        //开始1分钟倒计时
        //每一秒执行一次Task
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
            ToastUtils.showToast("手机号不能为空");
            return false;
        }


        if (!CommonUtil.checkPhone(mView.getUserName())) {
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

        if (TextUtils.isEmpty(mView.getPassword())) {
            ToastUtils.showToast("密码不能为空");
            return false;
        }


        if (!CommonUtil.checkPassWord(mView.getPassword())) {
            ToastUtils.showToast("密码格式错误");
            return false;
        }

        if (!TextUtils.isEmpty(mView.getIntroducter()) && !CommonUtil.checkPhone(mView.getIntroducter())) {
            ToastUtils.showToast("推荐人手机号格式错误");
            return false;
        }

        return true;
    }

}
