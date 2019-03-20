package com.szinternet.crm.ui.splash;

import android.content.Context;

import com.szinternet.crm.base.BaseContract;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public interface SplashContract {
    //RecommendContract是个接口，View是他的内部接口，这里看做View接口即可
    interface View extends BaseContract.BaseView {
        void readyGoLogin();

        void onError(String msg);

        void onSuccess();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {//dagger2接口,降低类之间的耦合度

        void checkIsFirstIn(Context context);

    }
}
