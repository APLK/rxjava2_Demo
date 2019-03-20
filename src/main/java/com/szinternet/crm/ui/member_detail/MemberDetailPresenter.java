package com.szinternet.crm.ui.member_detail;

import android.content.Context;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.MemberDetailBean;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MemberDetailPresenter extends RxPresenter<MemberDetailContract.View> implements MemberDetailContract.Presenter<MemberDetailContract.View> {

    private NetworkApi netApi;

    @Inject
    public MemberDetailPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
    }

    @Override
    public void memberInfo() {
        mView.showLoadDialog();
      netApi.childInfo(CreditCardApplication.getsInstance().getToken(), mView.getID())
                .compose(RxSchedulers.<BaseBean<MemberDetailBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<MemberDetailBean>(mView, mActivity, new HttpObserverInterface<MemberDetailBean>() {
                    @Override
                    public void onSuccess(BaseBean<MemberDetailBean> httpResult) {
                        mView.onSuccess(httpResult.getData());
                    }

                    @Override
                    public void onError(String msg, int code) {
                    }
                }));
    }
}
