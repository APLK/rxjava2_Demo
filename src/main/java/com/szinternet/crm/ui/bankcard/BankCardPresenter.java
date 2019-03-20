package com.szinternet.crm.ui.bankcard;

import android.content.Context;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.CardMangerBean;
import com.szinternet.crm.databean.MyCardBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class BankCardPresenter extends RxPresenter<BankCardContract.View> implements BankCardContract.Presenter<BankCardContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject    //BaseRvFragment里的mPresenter建立关联
    public BankCardPresenter(Context mContext, NetworkApi netApi) {
        this.mContext = mContext;
        this.netApi = netApi;
    }


    @Override
    public void loadRecord(final int pageIndex, int pageSize) {
        netApi.myBank(CreditCardApplication.getsInstance().getToken())
                .compose(RxSchedulers.<BaseBean<MyCardBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<MyCardBean>(mView, mActivity, new HttpObserverInterface<MyCardBean>() {
                    @Override
                    public void onSuccess(BaseBean<MyCardBean> httpResult) {
                        List<CardMangerBean> list = new ArrayList<CardMangerBean>();
                        if (httpResult.getData() != null) {
                            if (httpResult.getData().rc != null && !httpResult.getData().rc.isEmpty()) {
                                for (int i = 0; i < httpResult.getData().rc.size(); i++) {
                                    list.add(new CardMangerBean(1, httpResult.getData().rc.get(i).getBank_name(), httpResult.getData().rc.get(i).getNub(),
                                            httpResult.getData().rc.get(i).getID()));
                                }
                            }
                            if (httpResult.getData().chuxu != null) {
                                list.add(new CardMangerBean(0, httpResult.getData().chuxu.getBank_name(), httpResult.getData().chuxu.getAccount(), ""));
                            }
                        }
                        mView.onSuccess(list, pageIndex == 1 ? true : false);
                    }

                    @Override
                    public void onError(String msg, int code) {
                        mView.onError(msg);
                    }
                }));
    }

}
