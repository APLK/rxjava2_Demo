package com.szinternet.crm.base;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public interface BaseContract {

    interface BasePresenter<T> {

        void attachView(T view, BaseActivity context);

        void detachView();
    }

    interface BaseView {

        void start2Activity(Class c);

        void showMessage(String msg);

        void showLoadDialog();

        void showLoadDialog(String loadingText);

        void dismissDialog();

    }
}
