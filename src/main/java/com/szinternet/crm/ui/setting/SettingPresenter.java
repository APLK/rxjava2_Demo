package com.szinternet.crm.ui.setting;

import android.content.Context;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.GlobalAttribute;
import com.szinternet.crm.utils.CommonUtil;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class SettingPresenter extends RxPresenter<SettingContract.View> implements SettingContract.Presenter<SettingContract.View> {

    private NetworkApi netApi;

    @Inject
    public SettingPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
    }

    @Override
    public void logout() {
        mView.showLoadDialog();
         netApi.logout(CreditCardApplication.getsInstance().getToken())
                .compose(RxSchedulers.<BaseBean<BaseBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<BaseBean>(mView, mActivity, new HttpObserverInterface<BaseBean>() {

                    @Override
                    public void onSuccess(BaseBean<BaseBean> httpResult) {
                        //清除账户信息
                        CreditCardApplication.getsInstance().setToken("");
                        CreditCardApplication.getsInstance().account = null;
                        //保存账户信息
                        CommonUtil.set2SP(GlobalAttribute.LOGIN_NAME, "");
                        CommonUtil.set2SP(GlobalAttribute.LOGIN_PASSWORD, "");
                        mView.onSuccess();
                    }

                    @Override
                    public void onError(String msg, int code) {

                    }
                }));
    }
}
