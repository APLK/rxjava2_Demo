package com.szinternet.crm.fragment.presenter;

import android.content.Context;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.MembersCountBean;
import com.szinternet.crm.fragment.contract.MemberCountContract;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MemberCountPresenter extends RxPresenter<MemberCountContract.View> implements MemberCountContract.Presenter<MemberCountContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject
    public MemberCountPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }


    @Override
    public void myagents() {
        netApi.myagents(CreditCardApplication.getsInstance().getToken())
                .compose(RxSchedulers.<BaseBean<MembersCountBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<MembersCountBean>(mView, mActivity, new HttpObserverInterface<MembersCountBean>() {
                    @Override
                    public void onSuccess(BaseBean<MembersCountBean> httpResult) {
                        mView.onSuccess(httpResult.getData());
                    }

                    @Override
                    public void onError(String msg, int code) {

                    }
                }));
    }

    @Override
    public void mychildren() {
        netApi.mychildren(CreditCardApplication.getsInstance().getToken())
                .compose(RxSchedulers.<BaseBean<MembersCountBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<MembersCountBean>(mView, mActivity, new HttpObserverInterface<MembersCountBean>() {
                    @Override
                    public void onSuccess(BaseBean<MembersCountBean> httpResult) {
                        mView.onSuccess(httpResult.getData());
                    }

                    @Override
                    public void onError(String msg, int code) {

                    }
                }));
    }

}
