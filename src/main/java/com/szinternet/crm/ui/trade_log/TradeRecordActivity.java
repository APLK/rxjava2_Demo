package com.szinternet.crm.ui.trade_log;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.szinternet.crm.R;
import com.szinternet.crm.adapter.TradeRecordAdapter;
import com.szinternet.crm.base.BaseRVActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerBillingRecordComponent;
import com.szinternet.crm.databean.SpendRecordBean;
import com.szinternet.crm.databean.TradeLogBean;
import com.szinternet.crm.fragment.contract.RepaymentRecordContract;
import com.szinternet.crm.fragment.presenter.RepaymentRecordPresenter;
import com.szinternet.crm.recycle.decoration.DividerDecoration;

import java.util.List;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class TradeRecordActivity extends BaseRVActivity<TradeLogBean.DataEntity, RepaymentRecordPresenter> implements RepaymentRecordContract.View {

    @Override
    public int getLayoutId() {
        return R.layout.activity_billing_record;
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
        initTitleBar(true, "交易记录");

        DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), 30, 0, 0);
        itemDecoration.setDrawLastItem(false);
        mRecyclerView.addItemDecoration(itemDecoration);
        initAdapter(TradeRecordAdapter.class, true, false);
        onRefresh();
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }


    @Override
    public void onError() {
        loaddingError();
    }

    @Override
    public void onSuccess(List<SpendRecordBean.DataEntity> list, boolean isRefresh) {

    }

    @Override
    public void onTradeLogSuccess(List<TradeLogBean.DataEntity> list) {
        mAdapter.clear();
        mRecyclerView.setRefreshing(false);
        mAdapter.addAll(list);
    }


    @Override
    public void start2Activity(Class c) {
        startActivity(new Intent(this, c));
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public void showLoadDialog() {
        super.showDialog();
    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.loadTradeRecord();
    }
}
