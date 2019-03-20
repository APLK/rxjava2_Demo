package com.szinternet.crm.ui.rank;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.szinternet.crm.adapter.BasePagerAdapter;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.BaseContract;
import com.szinternet.crm.base.BaseFragment;

import java.util.List;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public interface RankContract {

    /**
     * 视图层需要实现的接口
     */
    interface View extends BaseContract.BaseView{
        void initAdapter(BasePagerAdapter basePagerAdapter);

        void onError(String msg);

        void onSuccess();

        void start2Activity(Class c);

        BaseFragment getFragment(int index);
        void showLoadDialog();
    }

    /**
     * 视图层和数据层需要的交互
     */
    interface Presenter<T> extends BaseContract.BasePresenter<T> {


        void initAdapter(ViewPager viewPager, FragmentManager fragmentManager, List<String> list);

        void setCurrentShowingPage(int index);

        void onError(int type, BaseBean tHttpResult);

        void onSuccess(int type, BaseBean tHttpResult);

        BaseFragment getFragment(int index);
    }
}
