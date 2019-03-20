package com.szinternet.crm.ui.card_manger;

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
public class CardMangerPresenter extends RxPresenter<CardMangerContract.View> implements CardMangerContract.Presenter<CardMangerContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject    //BaseRvFragment里的mPresenter建立关联
    public CardMangerPresenter(Context mContext, NetworkApi netApi) {
        this.mContext = mContext;
        this.netApi = netApi;
    }


    @Override
    public void deleterc(String id) {
        mView.showLoadDialog();
        netApi.deleterc(CreditCardApplication.getsInstance().getToken(), id)
                .compose(RxSchedulers.<BaseBean<BaseBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<BaseBean>(mView, mActivity, new HttpObserverInterface<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean<BaseBean> httpResult) {
                        mView.onSuccess(httpResult.getMessage());
                    }

                    @Override
                    public void onError(String msg, int code) {
                        mView.onError(msg);
                    }
                }));
    }
}
