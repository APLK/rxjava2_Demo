package com.szinternet.crm.ui.xitongnews;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.szinternet.crm.R;
import com.szinternet.crm.adapter.XitongNewsAdapter;
import com.szinternet.crm.base.BaseRVActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerBillingRecordComponent;
import com.szinternet.crm.databean.MessageBean;
import com.szinternet.crm.ui.msgdetail.MsgDetailActivity;

import java.util.List;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class XitongNewsActivity extends BaseRVActivity<MessageBean, XitongNewsPresenter> implements XitongNewsContract.View {

    @Override
    public int getLayoutId() {
        return R.layout.activity_xitong_news;
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
        initTitleBar(true, "系统消息");

        initAdapter(XitongNewsAdapter.class, true, true);
        onRefresh();
    }


    @Override
    protected void processClick(View v) {
    }


    @Override
    public void onError() {
        loaddingError();
    }

    @Override
    public void onSuccess(List<MessageBean> bean, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
        }
        mRecyclerView.setRefreshing(false);
        mAdapter.addAll(bean);
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
        MessageBean data = mAdapter.getItem(position);
        Intent intent = new Intent(this, MsgDetailActivity.class);
        intent.putExtra("data",data);
        startActivity(intent);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.loadRecord(pageIndex,  pageSize);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.loadRecord(pageIndex,  pageSize);
    }
}
