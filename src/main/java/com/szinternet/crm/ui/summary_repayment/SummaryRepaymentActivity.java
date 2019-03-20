package com.szinternet.crm.ui.summary_repayment;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.szinternet.crm.R;
import com.szinternet.crm.adapter.BasePagerAdapter;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.base.BaseFragment;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.view.SegmentTabLayout;
import com.szinternet.crm.view.listener.OnTabSelectListener;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class SummaryRepaymentActivity extends BaseActivity<SummaryRepaymentPresenter> implements SummaryRepaymentContract.View {

    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.tl_1)
    SegmentTabLayout mTl1;
    private FragmentManager fm;

    private String[] mTitles = {"全部还款", "还款中", "已完成", "还款失败"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_summary_repayment;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }


    @Override
    public void initDatas() {


    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "还款汇总");

        fm = getSupportFragmentManager();
        mTl1.setTabData(mTitles);
        mTl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mPresenter.setType(position);
                mPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPresenter.setType(position);
                mTl1.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPager.setCurrentItem(1);
        mPresenter.initAdapter(mPager, fm);
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
        }
    }


    @Override
    public void start2Activity(Class c) {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void showLoadDialog() {

    }

    @Override
    public BaseFragment getFragment(int index) {
        return mPresenter.getFragment(index);
    }

    @Override
    public void initAdapter(BasePagerAdapter basePagerAdapter) {
        mPager.setAdapter(basePagerAdapter);
    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

}
