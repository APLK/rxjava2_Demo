package com.szinternet.crm.ui.quick_payment;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.CollectInfoBean;
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
public class QuickPaymentPresenter extends RxPresenter<QuickPaymentContract.View> implements QuickPaymentContract.Presenter<QuickPaymentContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject
    public QuickPaymentPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }

    @Override
    public void cashPay(String rc_bank_id, String user_bank_id, String money,
                        String pay_order, String order) {
        checkParamIsVail(user_bank_id, rc_bank_id, money, pay_order, order, 0);
        mView.showLoadDialog();
        /*bank_id
                bank_name*/
        Map<String, RequestBody> params = new HashMap<>();
        params.put("token", netApi.convertToRequestBody(CreditCardApplication.getsInstance().getToken()));
        params.put("code", netApi.convertToRequestBody(mView.getVer()));
        params.put("rc_bank_id", netApi.convertToRequestBody(rc_bank_id));
        params.put("user_bank_id", netApi.convertToRequestBody(user_bank_id));
        params.put("money", netApi.convertToRequestBody(money));
        params.put("pay_order", netApi.convertToRequestBody(pay_order));
        params.put("order", netApi.convertToRequestBody(order));
         netApi.cashPay(params)
                .compose(RxSchedulers.<BaseBean<BaseBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<BaseBean>(mView, mActivity, new HttpObserverInterface<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean<BaseBean> httpResult) {
                        mView.onSuccess();
                    }

                    @Override
                    public void onError(String msg, int code) {
                    }
                }));
    }

    @Override
    public void getVerCode(String user_bank_id,
                           String rc_bank_id, String money) {
        checkParamIsVail(user_bank_id, rc_bank_id, money, "", "", 1);
      netApi.sendCollectCode(CreditCardApplication.getsInstance().getToken(), user_bank_id,
                rc_bank_id, money)
                .compose(RxSchedulers.<BaseBean<CollectInfoBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<CollectInfoBean>(mView, mActivity, new HttpObserverInterface<CollectInfoBean>() {

                    @Override
                    public void onSuccess(BaseBean<CollectInfoBean> httpResult) {
                        mView.onCollectInfoSuccess(httpResult.getData());
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
    public boolean checkParamIsVail(String user_bank_id,
                                    String rc_bank_id, String money,
                                    String pay_order, String order, int type) {
        if (TextUtils.isEmpty(user_bank_id)) {
            ToastUtils.showToast("银行卡号错误,请返回修改资料");
            return false;
        }


        if (TextUtils.isEmpty(rc_bank_id)) {
            ToastUtils.showToast("信用卡卡号错误,请返回修改资料");
            return false;
        }

        if (TextUtils.isEmpty(money)) {
            ToastUtils.showToast("还款金额不能为0,请返回修改资料");
            return false;
        }
        if (type == 0) {
            if (TextUtils.isEmpty(pay_order) || TextUtils.isEmpty(order)) {
                ToastUtils.showToast("订单号有误,请重新获取验证码");
                return false;
            }
            if (TextUtils.isEmpty(mView.getVer())) {
                ToastUtils.showToast("请输入验证码");
                return false;
            }
        }

        return true;
    }

}
