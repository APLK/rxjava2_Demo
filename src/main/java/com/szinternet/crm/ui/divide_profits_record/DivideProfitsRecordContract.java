package com.szinternet.crm.ui.divide_profits_record;

import android.support.v4.app.FragmentManager;

import com.szinternet.crm.adapter.BasePagerAdapter;
import com.szinternet.crm.base.BaseContract;
import com.szinternet.crm.base.BaseFragment;
import com.szinternet.crm.view.NoScrollViewPager;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public interface DivideProfitsRecordContract {
    /**
     * 视图层需要实现的接口
     */
    interface View extends BaseContract.BaseView {

        void onError(String msg);

        void onSuccess();

        void showLoadDialog();

        BaseFragment getFragment(int index);

        void initAdapter(BasePagerAdapter basePagerAdapter);
    }

    /**
     * 视图层和数据层需要的交互
     */
    interface Presenter<T> extends BaseContract.BasePresenter<T> {//dagger2接口,降低类之间的耦合度

        void initAdapter(NoScrollViewPager viewPager, FragmentManager fragmentManager);

        void setCurrentShowingPage(int index);

        BaseFragment getFragment(int index);
    }
}
