package com.szinternet.crm.fragment;

import android.support.v4.content.ContextCompat;
import android.view.View;

import com.szinternet.crm.R;
import com.szinternet.crm.adapter.CashRecordAdapter;
import com.szinternet.crm.base.BaseRVFragment;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerBillingRecordComponent;
import com.szinternet.crm.databean.CashRecordBean;
import com.szinternet.crm.eventbus.RefreshTypeEvent;
import com.szinternet.crm.recycle.decoration.DividerDecoration;
import com.szinternet.crm.ui.cash_record.CashContract;
import com.szinternet.crm.ui.cash_record.CashPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class ProfitRecordFragment extends BaseRVFragment<CashPresenter, CashRecordBean.DataEntity> implements CashContract.View {


    private int mType = 1;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_rank;
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
        DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor(getActivity(), R.color.common_divider_narrow), 30, 0, 0);
        itemDecoration.setDrawLastItem(false);
        mRecyclerView.addItemDecoration(itemDecoration);
        initAdapter(CashRecordAdapter.class, true, true);
        onRefresh();
    }


    @Override
    public void configViews() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void processClick(View v) {

    }


    @Override
    protected void lazyLoadData() {

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
        return String.valueOf(mType);
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
        mPresenter.loadRecord(pageIndex, pageSize);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.loadRecord(pageIndex, pageSize);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshIdsEvent(RefreshTypeEvent event) {
        mType = event.type == 0 ? 1 : 2;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册
    }
}
