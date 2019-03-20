package com.szinternet.crm.ui.members;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.szinternet.crm.R;
import com.szinternet.crm.adapter.MembersAdapter;
import com.szinternet.crm.base.BaseRVActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.MembersBean;
import com.szinternet.crm.recycle.decoration.DividerDecoration;
import com.szinternet.crm.ui.member_detail.MemberDetailActivity;

import java.util.List;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MembersActivity extends BaseRVActivity<MembersBean.DataEntity, MembersPresenter> implements MembersContract.View {


    private String mPosition = "0";
    private int mType = 0;
    private String mName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_xitong_news;
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
        mPosition = getIntent().getStringExtra("position");
        mType = getIntent().getIntExtra("type", 0);
        mName = getIntent().getStringExtra("name");
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, mName);
        DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), 20, 0, 0);
        itemDecoration.setDrawLastItem(false);
        mRecyclerView.addItemDecoration(itemDecoration);
        initAdapter(MembersAdapter.class, true, false);
        onRefresh();
    }

    @Override
    public void configViews() {

    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {

        }
    }


    @Override
    public void onError() {
        loaddingError();
    }

    @Override
    public String getType() {
        return mPosition;
    }

    @Override
    public void onSuccess(List<MembersBean.DataEntity> list) {
        mAdapter.clear();
        mRecyclerView.setRefreshing(false);
        mAdapter.addAll(list);
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
    public void onItemClick(int position) {
        Intent intent = new Intent(this, MemberDetailActivity.class);
        intent.putExtra("id", mAdapter.getItem(position).ids);
        startActivity(intent);
    }


    @Override
    public void onRefresh() {
        super.onRefresh();
        if (mType == 0) {
            mPresenter.loadProxyRecord();
        } else {
            mPresenter.loadSubordinateRecord();
        }
    }

}
