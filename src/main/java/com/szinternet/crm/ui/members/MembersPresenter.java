package com.szinternet.crm.ui.members;

import android.content.Context;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.MembersBean;
import com.szinternet.crm.utils.LogUtils;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MembersPresenter extends RxPresenter<MembersContract.View> implements MembersContract.Presenter<MembersContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject    //BaseRvFragment里的mPresenter建立关联
    public MembersPresenter(Context mContext, NetworkApi netApi) {
        this.mContext = mContext;
        this.netApi = netApi;
    }


    @Override
    public void loadProxyRecord() {
       /* Subscription subscribe = Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).flatMap(new Func1<Long, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(Long aLong) {
                return Observable.just(true);
            }
        }).subscribe(new Action1<Boolean>() {

            @Override
            public void call(Boolean isLogin) {
                List<MembersBean.DataEntity> rankBean = new ArrayList<>();
                if (pageIndex < 3) {
                    rankBean.add(new MembersBean.DataEntity("http://www.eovas-logo.net/uploadfile/2016/1208/20161208052954750.jpg", "1", 80, "18888888881"));
                    rankBean.add(new MembersBean.DataEntity("http://www.eovas-logo.net/uploadfile/2016/1208/20161208052954750.jpg", "1", 10, "18888888882"));
                    rankBean.add(new MembersBean.DataEntity("http://www.eovas-logo.net/uploadfile/2016/1208/20161208052954750.jpg", "1", 20, "18888888883"));
                    rankBean.add(new MembersBean.DataEntity("http://www.eovas-logo.net/uploadfile/2016/1208/20161208052954750.jpg", "1", 0, "18888888884"));
                    rankBean.add(new MembersBean.DataEntity("http://www.eovas-logo.net/uploadfile/2016/1208/20161208052954750.jpg", "1", 12, "18888888885"));
                    rankBean.add(new MembersBean.DataEntity("http://www.eovas-logo.net/uploadfile/2016/1208/20161208052954750.jpg", "1", 15, "18888888886"));
                }
                mView.onSuccess(rankBean, pageIndex == 1 ? true : false);
            }
        });
        addSubscrebe(subscribe);*/
        mView.showLoadDialog();
        netApi.agentsList(CreditCardApplication.getsInstance().getToken(), mView.getType())
                .compose(RxSchedulers.<BaseBean<MembersBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<MembersBean>(mView, mActivity, new HttpObserverInterface<MembersBean>() {
                    @Override
                    public void onSuccess(BaseBean<MembersBean> httpResult) {
                        mView.onSuccess(httpResult.getData().data);
                    }

                    @Override
                    public void onError(String msg, int code) {
                        LogUtils.i("1", "loadProxyRecord");
                        mView.onError();
                    }
                }));
    }

    @Override
    public void loadSubordinateRecord() {
        mView.showLoadDialog();
        netApi.childrensList(CreditCardApplication.getsInstance().getToken(), mView.getType())
                .compose(RxSchedulers.<BaseBean<MembersBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<MembersBean>(mView, mActivity, new HttpObserverInterface<MembersBean>() {
                    @Override
                    public void onSuccess(BaseBean<MembersBean> httpResult) {
                        mView.onSuccess(httpResult.getData().data);
                    }

                    @Override
                    public void onError(String msg, int code) {
                        LogUtils.i("1", "loadSubordinateRecord");
                        mView.onError();
                    }
                }));
    }
}
