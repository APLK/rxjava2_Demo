package com.szinternet.crm.fragment;

import android.content.Intent;
import android.view.View;

import com.szinternet.crm.R;
import com.szinternet.crm.adapter.RepaymentRecordAdapter;
import com.szinternet.crm.base.BaseRVFragment;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.SpendRecordBean;
import com.szinternet.crm.databean.TradeLogBean;
import com.szinternet.crm.fragment.contract.RepaymentRecordContract;
import com.szinternet.crm.fragment.presenter.RepaymentRecordPresenter;
import com.szinternet.crm.ui.repayment_plan_info.RepaymentPlanInfoActivity;

import java.util.List;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class SummaryRepaymentFragment extends BaseRVFragment<RepaymentRecordPresenter, SpendRecordBean.DataEntity> implements RepaymentRecordContract.View {


    private int mType = 3;

    public void setType(int type) {
        mType = type;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_rank;
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
        /*final NormalDecoration decoration = new NormalDecoration() {
            @Override
            public String getHeaderName(int pos) {
                return "2018-1-3";
                //                return mAdapter.getAllData().get(pos).getType() + "哪一天";
            }
        };

        decoration.setOnHeaderClickListener(new NormalDecoration.OnHeaderClickListener() {
            @Override
            public void headerClick(int pos) {
                Toast.makeText(getActivity(), "点击到头部" + mAdapter.getAllData().get(pos).getType(), Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.addItemDecoration(decoration);*/
    }


    @Override
    public void configViews() {
        initAdapter(RepaymentRecordAdapter.class, true, true);
    }

    @Override
    protected void processClick(View v) {

    }


    @Override
    protected void lazyLoadData() {
        onRefresh();
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
        start2Activity(new Intent(getActivity(), c));
    }

    @Override
    public String getType() {
        return String.valueOf(mType);
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
        Intent intent = new Intent(getActivity(), RepaymentPlanInfoActivity.class);
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
