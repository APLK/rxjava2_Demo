package com.szinternet.crm.ui.bindidentity;

import android.widget.ImageView;

import com.szinternet.crm.base.BaseContract;
import com.szinternet.crm.databean.BindInfoBean;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public interface BindIdentityContract {
    /**
     * 视图层需要实现的接口
     */
    interface View extends BaseContract.BaseView {

        void onError(String msg);

        void onSuccess(BindInfoBean bean);

        void isShowX(boolean isShow, ImageView imageView);

        void showLoadDialog();

        String getNickname();

        String getRealName();

        String getIdentityCode();

    }

    /**
     * 视图层和数据层需要的交互
     */
    interface Presenter<T> extends BaseContract.BasePresenter<T> {//dagger2接口,降低类之间的耦合度

        void bindIdentity();

        boolean checkParamIsVail();

        void getBindInfo();

    }
}
