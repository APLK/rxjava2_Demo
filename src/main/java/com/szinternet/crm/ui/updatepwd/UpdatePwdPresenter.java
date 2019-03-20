package com.szinternet.crm.ui.updatepwd;

import android.content.Context;
import android.text.TextUtils;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.GlobalAttribute;
import com.szinternet.crm.utils.CommonUtil;
import com.szinternet.crm.utils.DESUtils;
import com.szinternet.crm.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;

import static com.szinternet.crm.Constant.DES_KEY;
import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class UpdatePwdPresenter extends RxPresenter<UpdatePwdContract.View> implements UpdatePwdContract.Presenter<UpdatePwdContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject
    public UpdatePwdPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }

    @Override
    public void updatePwd() {
        if (!checkParamIsVail()) {
            return;
        }
        mView.showLoadDialog();
        Map<String, RequestBody> params = new HashMap<>();
        params.put("token", netApi.convertToRequestBody(CreditCardApplication.getsInstance().getToken()));
        params.put("password", netApi.convertToRequestBody(mView.getOldPassword()));
        params.put("newpassword", netApi.convertToRequestBody(mView.getSurePassword()));
        params.put("newpasswords", netApi.convertToRequestBody(mView.getSurePassword()));
        netApi.updatePwd(params)
                .compose(RxSchedulers.<BaseBean<BaseBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<BaseBean>(mView, mActivity, new HttpObserverInterface<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean<BaseBean> httpResult) {
                        CommonUtil.set2SP(GlobalAttribute.LOGIN_PASSWORD, DESUtils.encode(mView.getSurePassword(), DES_KEY));
                        mView.onSuccess();
                        finishSelf();
                    }

                    @Override
                    public void onError(String msg, int code) {
                        mView.onError(msg);
                    }
                }));
    }


    @Override
    public boolean checkParamIsVail() {
        if (TextUtils.isEmpty(mView.getOldPassword())) {
            ToastUtils.showToast("原密码不能为空");
            return false;
        }

        if (!CommonUtil.checkPassWord(mView.getOldPassword())) {
            ToastUtils.showToast("原密码格式错误");
            return false;
        }
        if (TextUtils.isEmpty(mView.getNewPassword())) {
            ToastUtils.showToast("新密码不能为空");
            return false;
        }

        if (!CommonUtil.checkPassWord(mView.getNewPassword())) {
            ToastUtils.showToast("新密码格式错误");
            return false;
        }
        if (TextUtils.isEmpty(mView.getSurePassword())) {
            ToastUtils.showToast("请确认密码");
            return false;
        }

        if (!mView.getNewPassword().equals(mView.getSurePassword())) {
            ToastUtils.showToast("两次密码输入不一致");
            return false;
        }

        return true;
    }

}
