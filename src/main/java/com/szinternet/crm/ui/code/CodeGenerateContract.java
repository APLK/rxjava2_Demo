package com.szinternet.crm.ui.code;

import android.graphics.Bitmap;

import com.szinternet.crm.base.BaseContract;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public interface CodeGenerateContract {
    /**
     * 视图层需要实现的接口
     */
    interface View extends BaseContract.BaseView {

        void onError(String msg);

        void onSuccess(Bitmap bitmap);

        void showLoadDialog();

        void onSuccess(String url);

    }

    /**
     * 视图层和数据层需要的交互
     */
    interface Presenter<T> extends BaseContract.BasePresenter<T> {//dagger2接口,降低类之间的耦合度
        void getShareImgURL();
    }
}
