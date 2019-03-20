package com.szinternet.crm.ui.spend_record_list;

import android.content.Context;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.RepaymentPeriodsBean;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class SpendRecordListPresenter extends RxPresenter<SpendRecordListContract.View> implements SpendRecordListContract.Presenter<SpendRecordListContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject    //BaseRvFragment里的mPresenter建立关联
    public SpendRecordListPresenter(Context mContext, NetworkApi netApi) {
        this.mContext = mContext;
        this.netApi = netApi;
    }


    @Override
    public void loadRecord(String id, String type) {
        netApi.planPeriodsList(CreditCardApplication.getsInstance().getToken(),id,type)
                .compose(RxSchedulers.<BaseBean<RepaymentPeriodsBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<RepaymentPeriodsBean>(mView, mActivity, new HttpObserverInterface<RepaymentPeriodsBean>() {
                    @Override
                    public void onSuccess(BaseBean<RepaymentPeriodsBean> httpResult) {
                        mView.onSuccess(httpResult.getData());
                    }

                    @Override
                    public void onError(String msg, int code) {
                    }
                }));

    }
}
