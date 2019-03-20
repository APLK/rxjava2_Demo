package com.szinternet.crm.ui.rank;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.szinternet.crm.adapter.BasePagerAdapter;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.BaseFragment;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.fragment.FragmentFactory;
import com.szinternet.crm.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class RankPresenter extends RxPresenter<RankContract.View> implements RankContract.Presenter<RankContract.View> {
    private List<BaseFragment> mFragmentList;
    private BasePagerAdapter mBasePagerAdapter;

    private ViewPager mViewPager;

    private Context mContext;
    private NetworkApi netApi;
    private FragmentFactory mFragmentFactory;


    @Inject    //BaseRvFragment里的mPresenter建立关联
    public RankPresenter(Context mContext, NetworkApi netApi) {
        this.mContext = mContext;
        this.netApi = netApi;
    }


    @Override
    public void initAdapter(ViewPager viewPager, FragmentManager fragmentManager, List<String> list) {
        mViewPager = viewPager;
        mFragmentFactory = new FragmentFactory();
        mFragmentList = new ArrayList<>();
        mFragmentList.add(mFragmentFactory.getInstance().getWeeklyRankingFragment());
        mFragmentList.add(mFragmentFactory.getInstance().getDayRankingFragment());
        mFragmentList.add(mFragmentFactory.getInstance().getAllRankingFragment());
        mBasePagerAdapter = new BasePagerAdapter(fragmentManager, mFragmentList, list);
        viewPager.setAdapter(mBasePagerAdapter);
        mView.initAdapter(mBasePagerAdapter);

    }

    @Override
    public void setCurrentShowingPage(int index) {
        if (index <= mFragmentList.size()) {
            mViewPager.setCurrentItem(index);
        }
    }


    @Override
    public void onError(int type, BaseBean tHttpResult) {
        mView.start2Activity(LoginActivity.class);
    }

    @Override
    public void onSuccess(int type, BaseBean tHttpResult) {

    }

    @Override
    public BaseFragment getFragment(int index) {
        return mFragmentList.get(index);
    }
}
