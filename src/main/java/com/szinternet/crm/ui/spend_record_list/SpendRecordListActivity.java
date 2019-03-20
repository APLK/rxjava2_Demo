package com.szinternet.crm.ui.spend_record_list;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.szinternet.crm.R;
import com.szinternet.crm.adapter.SpendRecordAdapter;
import com.szinternet.crm.base.BaseRVActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerBillingRecordComponent;
import com.szinternet.crm.databean.RepaymentPeriodsBean;
import com.szinternet.crm.recycle.decoration.DividerDecoration;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class SpendRecordListActivity extends BaseRVActivity<RepaymentPeriodsBean.DataEntity, SpendRecordListPresenter> implements SpendRecordListContract.View {

    private String mID;
    private String mType;

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
        mID = getIntent().getStringExtra("id");
        mType = getIntent().getStringExtra("type");
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, R.string.billing_record);

        DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), 40, 0, 0);
        itemDecoration.setDrawLastItem(false);
        mRecyclerView.addItemDecoration(itemDecoration);

        initAdapter(SpendRecordAdapter.class, true, false);
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
    public void onSuccess(RepaymentPeriodsBean bean) {
        mAdapter.clear();
        mRecyclerView.setRefreshing(false);
        mAdapter.addAll(bean.data);
    }


    @Override
    public void start2Activity(Class c) {

    }

    @Override
    public void showLoadDialog() {

    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);// must store the new intent unless getIntent() will
        // return the old one
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.loadRecord(mID, mType);
    }

}
