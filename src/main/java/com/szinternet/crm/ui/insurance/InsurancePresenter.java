package com.szinternet.crm.ui.insurance;

import android.content.Context;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.InsuranceInfoBean;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class InsurancePresenter extends RxPresenter<InsuranceContract.View> implements InsuranceContract.Presenter<InsuranceContract.View> {

    private NetworkApi netApi;

    @Inject
    public InsurancePresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
    }

    @Override
    public void myInsurance() {
        mView.showLoadDialog();
        netApi.myInsurance(CreditCardApplication.getsInstance().getToken())
                .compose(RxSchedulers.<BaseBean<InsuranceInfoBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<InsuranceInfoBean>(mView, mActivity, new HttpObserverInterface<InsuranceInfoBean>() {
                    @Override
                    public void onSuccess(BaseBean<InsuranceInfoBean> httpResult) {
                        mView.onSuccess(httpResult.getData());
                    }

                    @Override
                    public void onError(String msg, int code) {
                    }
                }));
    }
}
