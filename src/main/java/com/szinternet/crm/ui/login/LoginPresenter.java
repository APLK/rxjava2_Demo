package com.szinternet.crm.ui.login;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.GlobalAttribute;
import com.szinternet.crm.databean.LoginResultBean;
import com.szinternet.crm.dialog.AlertDialog;
import com.szinternet.crm.ui.main.MainActivity;
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
public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter<LoginContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject
    public LoginPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }

    @Override
    public void login() {
        if (!checkParamIsVail()) {
            return;
        }
        mView.showLoadDialog();
        Map<String, RequestBody> params = new HashMap<>();
        params.put("username", netApi.convertToRequestBody(mView.getUserName()));
        params.put("password", netApi.convertToRequestBody(mView.getPassword()));
        params.put("client", netApi.convertToRequestBody("0"));//android传0
       netApi.login(params)
                .compose(RxSchedulers.<BaseBean<LoginResultBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<LoginResultBean>(mView, mActivity, new HttpObserverInterface<LoginResultBean>() {
                    @Override
                    public void onSuccess(BaseBean<LoginResultBean> httpResult) {
                        //保存账户信息
                        CommonUtil.set2SP(GlobalAttribute.LOGIN_NAME, mView.getUserName());
                        CommonUtil.set2SP(GlobalAttribute.LOGIN_PASSWORD, DESUtils.encode(mView.getPassword(), DES_KEY));
                        CreditCardApplication.getsInstance().account = httpResult.getData();
                        CreditCardApplication.getsInstance().setToken(httpResult.getData().token);
                        mView.onSuccess();
                        mView.start2Activity(MainActivity.class);
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
        if (TextUtils.isEmpty(mView.getUserName())) {
            ToastUtils.showToast("手机号不能为空");
            return false;
        }


        if (!CommonUtil.checkPhone(mView.getUserName())) {
            ToastUtils.showToast("手机号格式错误");
            return false;
        }

        if (TextUtils.isEmpty(mView.getPassword())) {
            ToastUtils.showToast("密码不能为空");
            return false;
        }


        if (!CommonUtil.checkPassWord(mView.getPassword())) {
            ToastUtils.showToast("密码格式错误");
            return false;
        }

        return true;
    }


    @Override
    public void checkIsClient(String client, BaseActivity activity) {
        if(!TextUtils.isEmpty(client)){
            //获取用户状态 如果不为空 则用户被禁用 弹框提示
            new AlertDialog(activity).builder().setTitle("温馨提示")
                    .setMsg("您的token已失效,请重新登录!")
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).show();
        }
    }


}
