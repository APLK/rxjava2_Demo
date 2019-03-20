package com.szinternet.crm.ui.rank;

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
import com.szinternet.crm.view.PagerSlidingTabStrip;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class RankActivity extends BaseActivity<RankPresenter> implements RankContract.View {

    @BindView(R.id.tabs)
    PagerSlidingTabStrip mTabs;
    @BindView(R.id.pager)
    ViewPager mPager;
    private FragmentManager fm;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rank;
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
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "龙虎榜");
        ArrayList<String> list = new ArrayList<>();
        list.add("日榜");
        list.add("周榜");
        list.add("总榜");


        fm = getSupportFragmentManager();
        mPresenter.initAdapter(mPager, fm, list);

        mTabs.setViewPager(mPager);
    }

    @Override
    public void configViews() {
    }

    @Override
    protected void processClick(View v) {
    }

    @Override
    public void initAdapter(BasePagerAdapter basePagerAdapter) {
        mPager.setAdapter(basePagerAdapter);
    }

    @Override
    public void onError(String msg) {
    }

    @Override
    public void onSuccess() {

    }


    @Override
    public void start2Activity(Class c) {

    }

    @Override
    public BaseFragment getFragment(int index) {
        return mPresenter.getFragment(index);
    }


    @Override
    public void showLoadDialog() {

    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

}
