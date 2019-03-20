package com.szinternet.crm.ui.rate;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.MyRateBean;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class RateActivity extends BaseActivity<RatePresenter> implements RateContract.View {


    @BindView(R.id.repayment_rate)
    TextView mRepaymentRate;
    @BindView(R.id.collect_money_rate)
    TextView mCollectMoneyRate;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rate;
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
        mPresenter.myRate();
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "我的费率");
    }


    @Override
    protected void processClick(View v) {
    }


    @Override
    public void start2Activity(Class c) {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onSuccess(MyRateBean httpResult) {
        mRepaymentRate.setText(httpResult.huai);
        mCollectMoneyRate.setText(httpResult.shou);
    }

    @Override
    public void showLoadDialog() {
        super.showDialog();
    }

    @Override
    public void showLoadDialog(String loadingText) {

    }
}
