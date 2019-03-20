package com.szinternet.crm.ui.insurance;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.InsuranceInfoBean;
import com.szinternet.crm.ui.webview.WebviewActivity;
import com.szinternet.crm.utils.EventHelper;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class InsuranceActivity extends BaseActivity<InsurancePresenter> implements InsuranceContract.View {
    @BindView(R.id.imageView2)
    ImageView mImageView2;
    @BindView(R.id.tv_guarantee_object)
    TextView mTvGuaranteeObject;
    @BindView(R.id.tv_application_number)
    TextView mTvApplicationNumber;
    @BindView(R.id.tv_policy_number)
    TextView mTvPolicyNumber;
    @BindView(R.id.tv_real_name)
    TextView mTvRealName;
    @BindView(R.id.tv_begin_time)
    TextView mTvBeginTime;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.tv_guaranteed_amount)
    TextView mTvGuaranteedAmount;
    @BindView(R.id.tv_insurance_amount)
    TextView mTvInsuranceAmount;
    @BindView(R.id.tv_hotline)
    TextView mTvHotline;
    @BindView(R.id.btn_insurance)
    TextView mBtnInsurance;

    @Override
    public int getLayoutId() {
        return R.layout.activity_insurance_clause;
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
        mPresenter.myInsurance();
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "平安保险");
        EventHelper.click(this, mBtnInsurance);
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insurance:
                start2Activity(WebviewActivity.class);
                break;
            default:
                break;
        }
    }


    @Override
    public void start2Activity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoadDialog() {
        super.showDialog();
    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

    @Override
    public void onSuccess(InsuranceInfoBean bean) {
        if (bean != null) {
            mTvGuaranteeObject.setText(bean.in_ob);
            mTvApplicationNumber.setText(bean.in_order);
            mTvPolicyNumber.setText(bean.in_orders);
            mTvRealName.setText(bean.in_er);
            mTvBeginTime.setText(bean.in_stime);
            mTvEndTime.setText(bean.in_etime);
            mTvGuaranteedAmount.setText(bean.in_money);
            mTvInsuranceAmount.setText(bean.in_moneys);
            mTvHotline.setText(bean.in_phone);
        }
    }
}
