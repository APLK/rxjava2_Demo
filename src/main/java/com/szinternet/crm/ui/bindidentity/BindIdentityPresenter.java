package com.szinternet.crm.ui.bindidentity;

import android.content.Context;
import android.text.TextUtils;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.BindInfoBean;
import com.szinternet.crm.ui.bindbankcode.BindBankCodeActivity;
import com.szinternet.crm.utils.CommonUtil;
import com.szinternet.crm.utils.ToastUtils;

import javax.inject.Inject;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class BindIdentityPresenter extends RxPresenter<BindIdentityContract.View> implements BindIdentityContract.Presenter<BindIdentityContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject
    public BindIdentityPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }

    @Override
    public void bindIdentity() {
        if (!checkParamIsVail()) {
            return;
        }
        mView.start2Activity(BindBankCodeActivity.class);
    }

    @Override
    public void getBindInfo() {
        mView.showLoadDialog();
        netApi.bindInfo(CreditCardApplication.getsInstance().getToken())
                .compose(RxSchedulers.<BaseBean<BindInfoBean>>applySchedulers(RxSchedulers.IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<BindInfoBean>(mView, mActivity, new HttpObserverInterface<BindInfoBean>() {
                    @Override
                    public void onSuccess(BaseBean<BindInfoBean> httpResult) {
                        mView.onSuccess(httpResult.getData());
                    }

                    @Override
                    public void onError(String msg, int code) {
                    }
                }));
    }


    @Override
    public boolean checkParamIsVail() {
        if (TextUtils.isEmpty(mView.getNickname())) {
            ToastUtils.showToast("昵称不能未空");
            return false;
        }

        if (TextUtils.isEmpty(mView.getRealName())) {
            ToastUtils.showToast("姓名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mView.getIdentityCode())) {
            ToastUtils.showToast("身份证号码不能为空");
            return false;
        }

        if (!CommonUtil.sada(mView.getIdentityCode())) {
            ToastUtils.showToast("身份证号格式错误");
            return false;
        }

        return true;
    }

}
