package com.szinternet.crm.ui.repayment_plan_info;

import android.content.Context;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.PlanDetailInfoBean;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class PlanInfoPresenter extends RxPresenter<PlanInfoContract.View> implements PlanInfoContract.Presenter<PlanInfoContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject
    public PlanInfoPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }

    @Override
    public void getPlanInfo(String id) {
        mView.showLoadDialog();
        netApi.planDetailInfo(CreditCardApplication.getsInstance().getToken(), id)
                .compose(RxSchedulers.<BaseBean<PlanDetailInfoBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<PlanDetailInfoBean>(mView, mActivity, new HttpObserverInterface<PlanDetailInfoBean>() {
                    @Override
                    public void onSuccess(BaseBean<PlanDetailInfoBean> httpResult) {
                        mView.onSuccess(httpResult.getData());
                    }

                    @Override
                    public void onError(String msg, int code) {
                        mView.onError(msg);
                    }
                }));
    }
}
