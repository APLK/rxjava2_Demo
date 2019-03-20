package com.szinternet.crm.ui.billingrecord;

import android.content.Context;

import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.Car;
import com.szinternet.crm.utils.LogUtils;

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
public class BillingRecordPresenter extends RxPresenter<BillingRecordContract.View> implements BillingRecordContract.Presenter<BillingRecordContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject    //BaseRvFragment里的mPresenter建立关联
    public BillingRecordPresenter(Context mContext, NetworkApi netApi) {
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
            public void accept(Boolean isLogin) throws Exception {
                LogUtils.i("1", "isLogin=" + isLogin);
                List<Car> cars = new ArrayList<>();
                if (pageIndex < 2) {
                    cars.add(new Car("奥迪", "http://www.eovas-logo.net/uploadfile/2016/1208/20161208052954750.jpg", "2017-12-1" + pageIndex, "http://img.sootuu.com/vector/2006-4/2006420105425345.jpg"));
                    cars.add(new Car("阿尔法罗密欧", "http://pic34.photophoto.cn/20150226/0007019997634366_b.jpg", "2017-12-1" + pageIndex, "http://img.sootuu.com/vector/2006-4/2006420105425345.jpg"));
                    cars.add(new Car("阿斯顿马丁", "http://pic.dbw.cn/0/01/96/49/1964963_892590.jpg", "2017-12-1" + pageIndex, "http://img.sootuu.com/vector/2006-4/2006420105425345.jpg"));
                    cars.add(new Car("ALPINA", "http://www.ioffer.com/img/item/587/931/077/o_bLdValpina-logo-decals-emblem-1pcs-7-4-cm-no-157.jpg", "2017-12-1" + pageIndex, "http://img.sootuu.com/vector/2006-4/2006420105425345.jpg"));
                    cars.add(new Car("安凯客车", "http://img5.imgtn.bdimg.com/it/u=10827124,3714635719&fm=26&gp=0.jpg", "2017-12-1" + pageIndex, "http://img.sootuu.com/vector/2006-4/2006420105425345.jpg"));
                }
                mView.onSuccess(cars, pageIndex == 1 ? true : false);

            }
        });
    }


}
