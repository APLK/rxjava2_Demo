package com.szinternet.crm.ui.bankcard;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.szinternet.crm.R;
import com.szinternet.crm.adapter.MyBankCardAdapter;
import com.szinternet.crm.base.BaseRVActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.CardMangerBean;
import com.szinternet.crm.eventbus.RefreshIdsEvent;
import com.szinternet.crm.recycle.decoration.DividerDecoration;
import com.szinternet.crm.ui.card_manger.CardMangerActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class BankCardActivity extends BaseRVActivity<CardMangerBean, BankCardPresenter> implements BankCardContract.View {


    private int position = -1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bankcard;
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
        DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor(this, R.color.common_h1), 30, 0, 0);
        itemDecoration.setDrawLastItem(false);
        mRecyclerView.addItemDecoration(itemDecoration);
        initAdapter(MyBankCardAdapter.class, true, false);
        onRefresh();
    }

    @Override
    public void configViews() {
        EventBus.getDefault().register(this);
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "银行卡");
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }


    @Override
    public void onError(String msg) {
        loaddingError();
    }

    @Override
    public void onSuccess(List<CardMangerBean> list, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
        }
        mRecyclerView.setRefreshing(false);
        mAdapter.addAll(list);
    }

    @Override
    public void start2Activity(Class c, CardMangerBean item) {
        Intent intent = new Intent(this, c);
        intent.putExtra("item", item);
        startActivity(intent);
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
        this.position = position;
        start2Activity(CardMangerActivity.class, mAdapter.getItem(position));
    }


    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.loadRecord(pageIndex, pageSize);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshIdsEvent(RefreshIdsEvent event) {
        if (position >= 0) {
            mAdapter.remove(position);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册
    }
}
