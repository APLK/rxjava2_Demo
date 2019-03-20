package com.szinternet.crm.ui.billingrecord;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szinternet.crm.R;
import com.szinternet.crm.adapter.BillingRecordAdapter;
import com.szinternet.crm.base.BaseRVActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerBillingRecordComponent;
import com.szinternet.crm.databean.Car;
import com.szinternet.crm.datepicker.ConvertUtils;
import com.szinternet.crm.datepicker.DatePicker;
import com.szinternet.crm.recycle.decoration.NormalDecoration;
import com.szinternet.crm.utils.EventHelper;

import java.util.List;

import butterknife.BindView;

import static com.szinternet.crm.utils.ToastUtils.showToast;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class BillingRecordActivity extends BaseRVActivity<Car, BillingRecordPresenter> implements BillingRecordContract.View {

    @BindView(R.id.btn_review_image)
    TextView mBtnReviewImage;

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
        setTitleRightText("", R.mipmap.date_select);
        initTitleBar(true, R.string.billing_record);
        EventHelper.click(this, mBtnReviewImage);

        final NormalDecoration decoration = new NormalDecoration() {
            @Override
            public String getHeaderName(int pos) {
                return mAdapter.getAllData().get(pos).getHeaderName();
            }
        };

        decoration.setOnHeaderClickListener(new NormalDecoration.OnHeaderClickListener() {
            @Override
            public void headerClick(int pos) {
                Toast.makeText(BillingRecordActivity.this, "点击到头部" + mAdapter.getAllData().get(pos).getHeaderName(), Toast.LENGTH_SHORT).show();
            }
        });


        mRecyclerView.addItemDecoration(decoration);
        initAdapter(BillingRecordAdapter.class, true, true);
        onRefresh();
    }

    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.btn_review_image:
                onYearMonthDayPicker();
                break;
            default:
                break;
        }
    }

    public void onYearMonthDayPicker() {
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        picker.setRangeEnd(2111, 1, 11);
        picker.setRangeStart(2016, 8, 29);
        picker.setSelectedItem(2050, 10, 14);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                showToast(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    @Override
    public void onError() {
        loaddingError();
    }

    @Override
    public void onSuccess(List<Car> cars, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
        }
        mRecyclerView.setRefreshing(false);
        mAdapter.addAll(cars);
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
