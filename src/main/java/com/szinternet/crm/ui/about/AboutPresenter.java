package com.szinternet.crm.ui.about;

import android.content.Context;

import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.base.RxPresenter;

import javax.inject.Inject;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class AboutPresenter extends RxPresenter<AboutContract.View> implements AboutContract.Presenter<AboutContract.View> {

    private NetworkApi netApi;

    @Inject
    public AboutPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
    }

}
