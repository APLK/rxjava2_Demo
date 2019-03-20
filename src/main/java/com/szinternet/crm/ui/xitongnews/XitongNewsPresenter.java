package com.szinternet.crm.ui.xitongnews;

import android.content.Context;

import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.MessageBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class XitongNewsPresenter extends RxPresenter<XitongNewsContract.View> implements XitongNewsContract.Presenter<XitongNewsContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject    //BaseRvFragment里的mPresenter建立关联
    public XitongNewsPresenter(Context mContext, NetworkApi netApi) {
        this.mContext = mContext;
        this.netApi = netApi;
    }


    @Override
    public void loadRecord(final int pageIndex, int pageSize) {
        Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).flatMap(new Function<Long, ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> apply(Long aLong) throws Exception {
                return Observable.just(true);
            }

        }).subscribe(new Consumer<Boolean>() {

            @Override
            public void accept(Boolean aBoolean) throws Exception {
                List<MessageBean> msgBean = new ArrayList<>();
                if (pageIndex < 3) {
                    msgBean.add(new MessageBean("刷卡", "刷卡成功"));
                    msgBean.add(new MessageBean("代刷", "代刷刷卡成功"));
                    msgBean.add(new MessageBean("消费", "消费刷卡成功"));
                    msgBean.add(new MessageBean("购物", "购物刷卡成功"));
                    msgBean.add(new MessageBean("餐饮", "餐饮刷卡成功"));
                }
                mView.onSuccess(msgBean, pageIndex == 1 ? true : false);
            }
        });
    }


}
