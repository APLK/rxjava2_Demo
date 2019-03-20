package com.szinternet.crm.fragment.presenter;

import android.content.Context;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.MyRepaymentCardBean;
import com.szinternet.crm.databean.SpendRecordBean;
import com.szinternet.crm.fragment.contract.RepaymentCardContract;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class RepaymentCardPresenter extends RxPresenter<RepaymentCardContract.View> implements RepaymentCardContract.Presenter<RepaymentCardContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject    //BaseRvFragment里的mPresenter建立关联
    public RepaymentCardPresenter(Context mContext, NetworkApi netApi) {
        this.mContext = mContext;
        this.netApi = netApi;
    }


    @Override
    public void loadCardList() {
        mView.showLoadDialog();
        netApi.repaymentList(CreditCardApplication.getsInstance().getToken())
                .compose(RxSchedulers.<BaseBean<MyRepaymentCardBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<MyRepaymentCardBean>(mView, mActivity, new HttpObserverInterface<MyRepaymentCardBean>() {
                    @Override
                    public void onSuccess(BaseBean<MyRepaymentCardBean> httpResult) {
                        mView.onSuccess(httpResult.getData().data);
                    }

                    @Override
                    public void onError(String msg, int code) {
                        mView.onError();
                    }
                }));
    }


    @Override
    public void loadPlanRecord(int limit) {
        mView.showLoadDialog();
        netApi.planList(CreditCardApplication.getsInstance().getToken(), "3", String.valueOf(1), String.valueOf(2))
                .compose(RxSchedulers.<BaseBean<SpendRecordBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<SpendRecordBean>(mView, mActivity, new HttpObserverInterface<SpendRecordBean>() {
                    @Override
                    public void onSuccess(BaseBean<SpendRecordBean> httpResult) {
                        mView.onPlanSuccess(httpResult.getData().data);
                    }

                    @Override
                    public void onError(String msg, int code) {
                    }
                }));
    }
}
