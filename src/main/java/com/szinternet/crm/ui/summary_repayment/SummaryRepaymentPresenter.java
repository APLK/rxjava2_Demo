package com.szinternet.crm.ui.summary_repayment;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.szinternet.crm.adapter.BasePagerAdapter;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.base.BaseFragment;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.fragment.SummaryRepaymentFragment;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class SummaryRepaymentPresenter extends RxPresenter<SummaryRepaymentContract.View> implements SummaryRepaymentContract.Presenter<SummaryRepaymentContract.View> {

    private Context mContext;
    private NetworkApi netApi;
    private ViewPager mViewPager;
    private ArrayList<BaseFragment> mFragmentList;
    private BasePagerAdapter mBasePagerAdapter;


    @Inject
    public SummaryRepaymentPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }


    @Override
    public void initAdapter(ViewPager viewPager, FragmentManager fragmentManager) {
        mViewPager = viewPager;
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new SummaryRepaymentFragment());
        mFragmentList.add(new SummaryRepaymentFragment());
        mFragmentList.add(new SummaryRepaymentFragment());
        mFragmentList.add(new SummaryRepaymentFragment());
        mBasePagerAdapter = new BasePagerAdapter(fragmentManager, mFragmentList);
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
    public BaseFragment getFragment(int index) {
        setType(index);
        return mFragmentList.get(index);
    }

    public void setType(int index) {
        int type = 3;
        switch (index) {
            case 0:
                type = 3;
                break;
            case 1:
                type = 0;
                break;
            case 2:
                type = 1;
                break;
            case 3:
                type = 2;
                break;
        }
        ((SummaryRepaymentFragment) mFragmentList.get(index)).setType(type);
    }

}
