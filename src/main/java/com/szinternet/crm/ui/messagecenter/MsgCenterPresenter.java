package com.szinternet.crm.ui.messagecenter;

import android.content.Context;

import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.base.RxPresenter;

import javax.inject.Inject;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MsgCenterPresenter extends RxPresenter<MsgCenterContract.View> implements MsgCenterContract.Presenter<MsgCenterContract.View> {

    private NetworkApi netApi;

    @Inject
    public MsgCenterPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
    }
}
