package com.szinternet.crm.ui.xitongnews;

import com.szinternet.crm.base.BaseContract;
import com.szinternet.crm.databean.MessageBean;

import java.util.List;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public interface XitongNewsContract {

    /**
     * 视图层需要实现的接口
     */
    interface View extends BaseContract.BaseView {
        void onError();

        void onSuccess(List<MessageBean> bean, boolean isRefresh);

        void start2Activity(Class c);

        void showLoadDialog();
    }

    /**
     * 视图层和数据层需要的交互
     */
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void loadRecord(int pageIndex, int pageSize);


    }
}
