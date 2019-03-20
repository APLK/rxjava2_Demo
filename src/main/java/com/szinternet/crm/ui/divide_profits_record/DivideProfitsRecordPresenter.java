package com.szinternet.crm.ui.divide_profits_record;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.szinternet.crm.adapter.BasePagerAdapter;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.base.BaseFragment;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.eventbus.RefreshTypeEvent;
import com.szinternet.crm.fragment.ProfitRecordFragment;
import com.szinternet.crm.view.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class DivideProfitsRecordPresenter extends RxPresenter<DivideProfitsRecordContract.View> implements DivideProfitsRecordContract.Presenter<DivideProfitsRecordContract.View> {

    private Context mContext;
    private NetworkApi netApi;
    private NoScrollViewPager mViewPager;
    private ArrayList<BaseFragment> mFragmentList;
    private BasePagerAdapter mBasePagerAdapter;


    @Inject
    public DivideProfitsRecordPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }


    @Override
    public void initAdapter(NoScrollViewPager viewPager, FragmentManager fragmentManager) {
        mViewPager = viewPager;
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new ProfitRecordFragment());
        mFragmentList.add(new ProfitRecordFragment());
        mBasePagerAdapter = new BasePagerAdapter(fragmentManager, mFragmentList);
        viewPager.setAdapter(mBasePagerAdapter);
        mView.initAdapter(mBasePagerAdapter);

    }

    @Override
    public void setCurrentShowingPage(int index) {
        if (index <= mFragmentList.size()) {
            mViewPager.setCurrentItem(index);
            EventBus.getDefault().post(new RefreshTypeEvent(index));
        }
    }

    @Override
    public BaseFragment getFragment(int index) {
        return mFragmentList.get(index);
    }

}
