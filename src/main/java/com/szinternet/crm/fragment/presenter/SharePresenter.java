package com.szinternet.crm.fragment.presenter;

import android.content.Context;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.ShareDownUrlBean;
import com.szinternet.crm.fragment.contract.ShareContract;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class SharePresenter extends RxPresenter<ShareContract.View> implements ShareContract.Presenter<ShareContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject
    public SharePresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }


    @Override
    public void getShareDownURl() {
        mView.showLoadDialog();
       netApi.shareDownUrl(CreditCardApplication.getsInstance().getToken())
                .compose(RxSchedulers.<BaseBean<ShareDownUrlBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<ShareDownUrlBean>(mView, mActivity, new HttpObserverInterface<ShareDownUrlBean>() {
                    @Override
                    public void onSuccess(BaseBean<ShareDownUrlBean> httpResult) {
                        mView.onSuccess(httpResult.getData().url);
                    }

                    @Override
                    public void onError(String msg, int code) {
                    }

                }));
    }
}
