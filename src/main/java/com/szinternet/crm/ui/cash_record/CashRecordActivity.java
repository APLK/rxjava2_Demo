package com.szinternet.crm.ui.cash_record;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.szinternet.crm.R;
import com.szinternet.crm.adapter.CashRecordAdapter;
import com.szinternet.crm.base.BaseRVActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerBillingRecordComponent;
import com.szinternet.crm.databean.CashRecordBean;
import com.szinternet.crm.recycle.decoration.DividerDecoration;

import java.util.List;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class CashRecordActivity extends BaseRVActivity<CashRecordBean.DataEntity, CashPresenter> implements CashContract.View {


    private boolean isPromoteDetail = false;

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
        isPromoteDetail = getIntent().getBooleanExtra("promoteDetail", false);
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, isPromoteDetail ? "推广收益详情" : "提现记录");
        DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), 30, 0, 0);
        itemDecoration.setDrawLastItem(false);
        mRecyclerView.addItemDecoration(itemDecoration);
        initAdapter(CashRecordAdapter.class, true, true);
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
    public void onSuccess(List<CashRecordBean.DataEntity> list, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
        }
        mRecyclerView.setRefreshing(false);
        mAdapter.addAll(list);
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

    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isPromoteDetail) {
            mPresenter.loadPromoteRecord(pageIndex, pageSize);
        } else {
            mPresenter.loadRecord(pageIndex, pageSize);
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if (isPromoteDetail) {
            mPresenter.loadPromoteRecord(pageIndex, pageSize);
        } else {
            mPresenter.loadRecord(pageIndex, pageSize);
        }
    }

}
