package com.szinternet.crm.ui.creditcard_collect;

import android.content.Context;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.AllBankBean;
import com.szinternet.crm.databean.CreditCollectInfoBean;

import javax.inject.Inject;


import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class CreditCardCollectPresenter extends RxPresenter<CreditCardCollectContract.View> implements CreditCardCollectContract.Presenter<CreditCardCollectContract.View> {
    private NetworkApi netApi;

    @Inject
    public CreditCardCollectPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
    }

    @Override
    public void loadCreditList() {
        mView.showLoadDialog();
        netApi.defrayList(CreditCardApplication.getsInstance().getToken())
                .compose(RxSchedulers.<BaseBean<AllBankBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<AllBankBean>(mView, mActivity, new HttpObserverInterface<AllBankBean>() {
                    @Override
                    public void onSuccess(BaseBean<AllBankBean> httpResult) {
                        mView.onSuccess(httpResult.getData().data);
                    }

                    @Override
                    public void onError(String msg, int code) {
                    }
                }));
    }

    @Override
    public void loadCreditInfo() {
        mView.showLoadDialog();
        netApi.income(CreditCardApplication.getsInstance().getToken())
                .compose(RxSchedulers.<BaseBean<CreditCollectInfoBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<CreditCollectInfoBean>(mView, mActivity, new HttpObserverInterface<CreditCollectInfoBean>() {
                    @Override
                    public void onSuccess(BaseBean<CreditCollectInfoBean> httpResult) {
                        mView.onInfoSuccess(httpResult.getData());
                    }

                    @Override
                    public void onError(String msg, int code) {
                    }
                }));
    }
}
