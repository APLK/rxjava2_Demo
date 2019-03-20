package com.szinternet.crm.ui.divide_profits_record;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.szinternet.crm.R;
import com.szinternet.crm.adapter.BasePagerAdapter;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.base.BaseFragment;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerBillingRecordComponent;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.view.NoScrollViewPager;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class DivideProfitsRecordActivity extends BaseActivity<DivideProfitsRecordPresenter> implements DivideProfitsRecordContract.View {

    @BindView(R.id.pager)
    NoScrollViewPager mPager;
    @BindView(R.id.repayment_profit)
    Button mRepaymentProfit;
    @BindView(R.id.collect_profit)
    Button mCollectProfit;
    private FragmentManager fm;

    @Override
    public int getLayoutId() {
        return R.layout.activity_divide_profits;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBillingRecordComponent.builder()
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
        initTitleBar(true, "分润收益");
        EventHelper.click(this, mRepaymentProfit, mCollectProfit);

        fm = getSupportFragmentManager();
        mPresenter.initAdapter(mPager, fm);

        mPager.setOffscreenPageLimit(2);
        mRepaymentProfit.setSelected(true);
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.repayment_profit:
                mRepaymentProfit.setSelected(true);
                mCollectProfit.setSelected(false);
                mPresenter.setCurrentShowingPage(0);
                break;
            case R.id.collect_profit:
                mRepaymentProfit.setSelected(false);
                mCollectProfit.setSelected(true);
                mPresenter.setCurrentShowingPage(1);
                break;
            default:
                break;
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
