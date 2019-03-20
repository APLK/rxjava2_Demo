package com.szinternet.crm.ui.rate;

import android.content.Context;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.MyRateBean;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class RatePresenter extends RxPresenter<RateContract.View> implements RateContract.Presenter<RateContract.View> {

    private NetworkApi netApi;

    @Inject
    public RatePresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
    }

    @Override
    public void myRate() {
        mView.showLoadDialog();
       netApi.myRate(CreditCardApplication.getsInstance().getToken())
                .compose(RxSchedulers.<BaseBean<MyRateBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<MyRateBean>(mView, mActivity, new HttpObserverInterface<MyRateBean>() {
                    @Override
                    public void onSuccess(BaseBean<MyRateBean> httpResult) {
                        mView.onSuccess(httpResult.getData());
                    }

                    @Override
                    public void onError(String msg, int code) {
                    }
                }));
    }
}
