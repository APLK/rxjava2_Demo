package com.szinternet.crm.ui.cash;

import android.content.Context;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class CashPresenter extends RxPresenter<CashContract.View> implements CashContract.Presenter<CashContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject
    public CashPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }


    @Override
    public void cash() {
        mView.showLoadDialog();
        netApi.frcash(CreditCardApplication.getsInstance().getToken(), mView.getMoney())
                .compose(RxSchedulers.<BaseBean<BaseBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<BaseBean>(mView, mActivity, new HttpObserverInterface<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean<BaseBean> httpResult) {
                    }

                    @Override
                    public void onError(String msg, int code) {
                    }
                }));
    }

}
