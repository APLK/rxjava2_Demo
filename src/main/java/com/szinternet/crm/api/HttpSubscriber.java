package com.szinternet.crm.api;


import android.content.Intent;

import com.szinternet.crm.AppManager;
import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.BaseContract;
import com.szinternet.crm.ui.login.LoginActivity;
import com.szinternet.crm.utils.LogUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class HttpSubscriber<T> implements Observer<BaseBean<T>> {

    private BaseContract.BaseView mIView;
    private BaseActivity mBaseActivity;
    private HttpObserverInterface mInterface;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public HttpSubscriber(BaseContract.BaseView IView, BaseActivity baseActivity, HttpObserverInterface<T> observerInterface) {
        mIView = IView;
        mBaseActivity = baseActivity;
        mInterface = observerInterface;
    }

    public HttpSubscriber(BaseContract.BaseView IView, BaseActivity baseActivity) {
        mIView = IView;
        mBaseActivity = baseActivity;
    }


    @Override
    public void onError(Throwable e) {
        LogUtils.e(mBaseActivity.getClass().getName() + " -> " + e.getMessage());
        String msg = mBaseActivity.getString(R.string.service_timeout);
        if (e != null) {
            if (e.toString().contains("SocketTimeoutException")) {
                mBaseActivity.toastNoNot(msg);
            } else {
                msg = mBaseActivity.getString(R.string.service_error);
                mBaseActivity.toastNoNot(msg);
            }
            e.printStackTrace();
        } else {
            mBaseActivity.toastNoNot(msg);
        }
        if (mInterface != null) {
            mInterface.onError(msg, 500);
        }
        mIView.dismissDialog();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        mCompositeDisposable.add(d);
    }

    @Override
    public void onNext(BaseBean<T> result) {
        if (result != null) {
            if (result.getCode() == 1 || result.getCode() == 200 || result.getCode() == 2) {//请求成功
                if (mInterface != null) {
                    mInterface.onSuccess(result);
                }
            } else if (result.getCode() == 0) {//请求失败
                if (mInterface != null) {
                    mInterface.onError(result.getMessage(), result.getCode());
                }
            } else if (result.getCode() == 20) {
                if (!(AppManager.getAppManager().currentActivity() instanceof LoginActivity)) {
                    //token超时
                    AppManager.getAppManager().finishAllActivity();
                    Intent intent = new Intent(mBaseActivity, LoginActivity.class);
                    intent.putExtra("client", "login");
                    mBaseActivity.startActivity(intent);

                }
            }
            mIView.showMessage(result.getMessage());
        } else {
            if (mInterface != null) {
                mInterface.onError("服务器异常,请稍后重试!", 500);
            }
        }
        mIView.dismissDialog();
    }

}
