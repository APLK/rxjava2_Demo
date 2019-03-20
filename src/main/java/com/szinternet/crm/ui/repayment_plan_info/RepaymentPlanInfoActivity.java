package com.szinternet.crm.ui.repayment_plan_info;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.PlanDetailInfoBean;
import com.szinternet.crm.ui.spend_record_list.SpendRecordListActivity;
import com.szinternet.crm.utils.EventHelper;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class RepaymentPlanInfoActivity extends BaseActivity<PlanInfoPresenter> implements PlanInfoContract.View {


//    @BindView(R.id.tv_all_time)
//    TextView mTvAllTime;
//    @BindView(R.id.tv_bond)
//    TextView mTvBond;
    @BindView(R.id.tv_application_number)
    TextView mTvApplicationNumber;
    @BindView(R.id.tv_service_charge)
    TextView mTvServiceCharge;
    @BindView(R.id.tv_repaymented)
    TextView mTvRepaymented;
    @BindView(R.id.tv_repayment_no)
    TextView mTvRepaymentNo;
    @BindView(R.id.tv_spend_record)
    TextView mTvSpendRecord;
    @BindView(R.id.tv_plan_time)
    TextView mTvPlanTime;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.pay_money)
    TextView mPayMoney;
    private String mID;
    private int mType = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_repayment_plan_info;
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
        mID = getIntent().getStringExtra("id");
        mPresenter.getPlanInfo(mID);
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "还款计划");
        EventHelper.click(this, mTvRepaymentNo, mTvRepaymented, mTvSpendRecord);
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.tv_repaymented:
                mType = 1;
                start2Activity(SpendRecordListActivity.class);
                break;
            case R.id.tv_repayment_no:
                mType = 0;
                start2Activity(SpendRecordListActivity.class);
                break;
            case R.id.tv_spend_record:
                start2Activity(SpendRecordListActivity.class);
                break;
            default:
                break;
        }
    }


    @Override
    public void start2Activity(Class c) {
        Intent intent = new Intent(this, c);
        intent.putExtra("id", mID);
        intent.putExtra("type", String.valueOf(mType));
        startActivity(intent);
    }

    @Override
    public void showLoadDialog() {
        super.showDialog();
    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onSuccess(PlanDetailInfoBean bean) {
        if (bean != null) {
            mPayMoney.setText(bean.pay_money);
            mTvApplicationNumber.setText(bean.pay_order);
//            mTvAllTime.setVisibility(View.GONE);
//            mTvBond.setVisibility(View.GONE);
            mTvServiceCharge.setText(bean.pay_fee);
            mTvRepaymented.setText(bean.have_repay_no + "期");
            mTvRepaymentNo.setText(bean.repay_no + "期");
            mTvSpendRecord.setText(bean.pay_mub + "笔");
            mTvPlanTime.setText(bean.create_time);
            if (!TextUtils.isEmpty(bean.type))
                mTvState.setText(Integer.parseInt(bean.type) == 0 ? "还款中" : "已完成");
        }
    }


    @Override
    public void isShowX(boolean isShow, ImageView imageView) {

    }

}
