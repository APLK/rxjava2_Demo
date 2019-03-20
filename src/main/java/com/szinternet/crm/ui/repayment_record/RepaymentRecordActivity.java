package com.szinternet.crm.ui.repayment_record;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.szinternet.crm.R;
import com.szinternet.crm.adapter.RepaymentRecordAdapter;
import com.szinternet.crm.base.BaseRVActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.SpendRecordBean;
import com.szinternet.crm.databean.TradeLogBean;
import com.szinternet.crm.fragment.contract.RepaymentRecordContract;
import com.szinternet.crm.fragment.presenter.RepaymentRecordPresenter;
import com.szinternet.crm.recycle.decoration.DividerDecoration;
import com.szinternet.crm.ui.repayment_plan_info.RepaymentPlanInfoActivity;

import java.util.List;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class RepaymentRecordActivity extends BaseRVActivity<SpendRecordBean.DataEntity, RepaymentRecordPresenter> implements RepaymentRecordContract.View {

    @Override
    public int getLayoutId() {
        return R.layout.activity_billing_record;
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
        initTitleBar(true, "还款记录");

        DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), 30, 0, 0);
        itemDecoration.setDrawLastItem(false);
        mRecyclerView.addItemDecoration(itemDecoration);
        initAdapter(RepaymentRecordAdapter.class, true, true);
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
        if (isRefresh) {
            mAdapter.clear();
        }
        mRecyclerView.setRefreshing(false);
        mAdapter.addAll(list);
    }

    @Override
    public void onTradeLogSuccess(List<TradeLogBean.DataEntity> list) {

    }


    @Override
    public void start2Activity(Class c) {
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
        Intent intent = new Intent(this, RepaymentPlanInfoActivity.class);
        intent.putExtra("id", mAdapter.getItem(position).ids);
        startActivity(intent);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.loadRecord(pageIndex, pageSize);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.loadRecord(pageIndex, pageSize);
    }
}
