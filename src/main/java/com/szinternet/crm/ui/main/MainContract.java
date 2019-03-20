package com.szinternet.crm.ui.main;

import android.support.v4.app.FragmentManager;

import com.szinternet.crm.adapter.BasePagerAdapter;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.BaseContract;
import com.szinternet.crm.base.BaseFragment;
import com.szinternet.crm.view.NoScrollViewPager;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public interface MainContract {

    /**
     * 视图层需要实现的接口
     */
    interface View extends BaseContract.BaseView {
        void initAdapter(BasePagerAdapter basePagerAdapter);

        void onError(String msg);

        void onSuccess();

        String getUserPhone();

        void start2Activity(Class c);

        BaseFragment getFragment(int index);

        void showLoadDialog();
    }

    /**
     * 视图层和数据层需要的交互
     */
    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void initAdapter(NoScrollViewPager viewPager, FragmentManager fragmentManager);

        void setCurrentShowingPage(int index);

        void checkNewVersion();

        //        void checkUserState();

        void downLoadApp();

        void onError(int type, BaseBean tHttpResult);

        void onSuccess(int type, BaseBean tHttpResult);

        BaseFragment getFragment(int index);
    }
}
