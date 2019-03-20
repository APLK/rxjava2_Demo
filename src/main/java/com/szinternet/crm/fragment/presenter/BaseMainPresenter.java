package com.szinternet.crm.fragment.presenter;

import android.content.Context;

import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.fragment.contract.BaseMainContract;

import javax.inject.Inject;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class BaseMainPresenter extends RxPresenter<BaseMainContract.View> implements BaseMainContract.Presenter<BaseMainContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject
    public BaseMainPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }


}
