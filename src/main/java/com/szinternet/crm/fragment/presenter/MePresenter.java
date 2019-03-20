package com.szinternet.crm.fragment.presenter;

import android.content.Context;

import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.ServerPhoneBean;
import com.szinternet.crm.fragment.contract.MeContract;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MePresenter extends RxPresenter<MeContract.View> implements MeContract.Presenter<MeContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject
    public MePresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }

    @Override
    public void downLoadApp() {
        //        final String url = "https://pro-app-qn.fir.im/3c87c508fcdca4b705e89800a6d6081361be034b.apk?attname=BGAUpdateDemo_v1.0.1_debug.apk_1.0.1.apk&e=1513743719&token=LOvmia8oXF4xnLh0IdH05XMYpH6ENHNpARlmPc-T:wm7E0Hp3y6LP26kiyEao-i4xWc4=";
        //        BgUpdate.updateForDialog(mContext, url, Environment.getExternalStorageDirectory() + "/xiaohui.apk");
    }

    @Override
    public void getPhone() {
        mView.showLoadDialog();
        netApi.webphone()
                .compose(RxSchedulers.<BaseBean<ServerPhoneBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<ServerPhoneBean>(mView, mActivity, new HttpObserverInterface<ServerPhoneBean>() {
                    @Override
                    public void onSuccess(BaseBean<ServerPhoneBean> httpResult) {
                        mView.onSuccess(httpResult.getData().web_phone);
                    }

                    @Override
                    public void onError(String msg, int code) {
                        mView.onError(msg);
                    }

                }));
    }

}
