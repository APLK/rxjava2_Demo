package com.szinternet.crm.ui.cash_record;

import android.content.Context;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.CashRecordBean;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class CashPresenter extends RxPresenter<CashContract.View> implements CashContract.Presenter<CashContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject    //BaseRvFragment里的mPresenter建立关联
    public CashPresenter(Context mContext, NetworkApi netApi) {
        this.mContext = mContext;
        this.netApi = netApi;
    }


    @Override
    public void loadRecord(final int pageIndex, int pageSize) {
        netApi.gainList(CreditCardApplication.getsInstance().getToken(), String.valueOf(pageIndex), String.valueOf(pageSize))
                .compose(RxSchedulers.<BaseBean<CashRecordBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<CashRecordBean>(mView, mActivity, new HttpObserverInterface<CashRecordBean>() {
                    @Override
                    public void onSuccess(BaseBean<CashRecordBean> httpResult) {
                        mView.onSuccess(httpResult.getData().data, pageIndex == 1 ? true : false);
                    }

                    @Override
                    public void onError(String msg, int code) {
                        mView.onError();
                    }
                }));
    }

    @Override
    public void loadCashRecord(final int pageIndex, int pageSize) {
        netApi.cashList(CreditCardApplication.getsInstance().getToken(), mView.getType(), String.valueOf(pageIndex), String.valueOf(pageSize))
                .compose(RxSchedulers.<BaseBean<CashRecordBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<CashRecordBean>(mView, mActivity, new HttpObserverInterface<CashRecordBean>() {
                    @Override
                    public void onSuccess(BaseBean<CashRecordBean> httpResult) {
                        mView.onSuccess(httpResult.getData().data, pageIndex == 1 ? true : false);
                    }

                    @Override
                    public void onError(String msg, int code) {
                    }
                }));
    }

    @Override
    public void loadPromoteRecord(final int pageIndex, int pageSize) {
        netApi.spList(CreditCardApplication.getsInstance().getToken(), String.valueOf(pageIndex), String.valueOf(pageSize))
                .compose(RxSchedulers.<BaseBean<CashRecordBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<CashRecordBean>(mView, mActivity, new HttpObserverInterface<CashRecordBean>() {
                    @Override
                    public void onSuccess(BaseBean<CashRecordBean> httpResult) {
                        mView.onSuccess(httpResult.getData().data, pageIndex == 1 ? true : false);
                    }

                    @Override
                    public void onError(String msg, int code) {
                    }
                }));
    }

}
