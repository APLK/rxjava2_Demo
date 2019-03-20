package com.szinternet.crm.fragment.presenter;

import android.content.Context;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.SpendRecordBean;
import com.szinternet.crm.databean.TradeLogBean;
import com.szinternet.crm.fragment.contract.RepaymentRecordContract;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class RepaymentRecordPresenter extends RxPresenter<RepaymentRecordContract.View> implements RepaymentRecordContract.Presenter<RepaymentRecordContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject    //BaseRvFragment里的mPresenter建立关联
    public RepaymentRecordPresenter(Context mContext, NetworkApi netApi) {
        this.mContext = mContext;
        this.netApi = netApi;
    }


    @Override
    public void loadRecord(final int pageIndex, int pageSize) {

        netApi.planList(CreditCardApplication.getsInstance().getToken(), mView.getType(), String.valueOf(pageIndex), String.valueOf(pageSize))
                .compose(RxSchedulers.<BaseBean<SpendRecordBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<SpendRecordBean>(mView, mActivity, new HttpObserverInterface<SpendRecordBean>() {
                    @Override
                    public void onSuccess(BaseBean<SpendRecordBean> httpResult) {
                        mView.onSuccess(httpResult.getData().data, pageIndex == 1 ? true : false);
                    }

                    @Override
                    public void onError(String msg, int code) {
                        mView.onError();
                    }
                }));
    }

    @Override
    public void loadTradeRecord() {

         netApi.defrayLog(CreditCardApplication.getsInstance().getToken())
                .compose(RxSchedulers.<BaseBean<TradeLogBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<TradeLogBean>(mView, mActivity, new HttpObserverInterface<TradeLogBean>() {
                    @Override
                    public void onSuccess(BaseBean<TradeLogBean> httpResult) {
                        mView.onTradeLogSuccess(httpResult.getData().data);
                    }

                    @Override
                    public void onError(String msg, int code) {
                    }
                }));
    }

}
