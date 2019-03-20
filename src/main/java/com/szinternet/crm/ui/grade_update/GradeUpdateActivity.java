package com.szinternet.crm.ui.grade_update;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.MyGradeBean;
import com.szinternet.crm.databean.UpclassBean;
import com.szinternet.crm.ui.pay.PayActivity;
import com.szinternet.crm.utils.EventHelper;

import java.util.List;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class GradeUpdateActivity extends BaseActivity<GradeUpdatePresenter> implements GradeUpdateContract.View {


    @BindView(R.id.repayment)
    ImageView mRepayment;
    @BindView(R.id.repayment_rate)
    TextView mRepaymentRate;
    @BindView(R.id.collect_money)
    ImageView mCollectMoney;
    @BindView(R.id.collect_money_rate)
    TextView mCollectMoneyRate;
    @BindView(R.id.content)
    TextView mContent;
    @BindView(R.id.bt_update)
    Button mBtUpdate;
    private String mGradeName = "";
    private List<MyGradeBean.InfoEntity> mInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_grade_update;
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
        String grade = getIntent().getStringExtra("grade");
        mGradeName = getIntent().getStringExtra("gradeName");
        mInfo = (List<MyGradeBean.InfoEntity>) getIntent().getSerializableExtra("info");
        StringBuilder content = new StringBuilder();
        if (mInfo != null && !mInfo.isEmpty()) {
            for (int i = 0; i < mInfo.size(); i++) {
                content.append(mInfo.get(i).msg).append("\n\n");
            }
        }
        mContent.setText(content);
        mPresenter.upclass(grade);
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "立即升级");
        EventHelper.click(this, mBtUpdate);
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.bt_update:
                start2Activity(PayActivity.class);
                break;
            default:
                break;
        }
    }


    @Override
    public void start2Activity(Class c) {
        startActivity(new Intent(this, c));
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onSuccess(UpclassBean httpResult) {
        if (httpResult != null) {
            initUpclassData(httpResult);
        }
    }

    private void initUpclassData(UpclassBean httpResult) {
        mRepaymentRate.setText(String.format(getString(R.string.update_repayment_rate), httpResult.huai));
        mCollectMoneyRate.setText(String.format(getString(R.string.update_collect_rate), httpResult.shou));
        mBtUpdate.setText(String.format(getString(R.string.update_btn_content), mGradeName, httpResult.upMoney));
    }

    @Override
    public void showLoadDialog() {

    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

}
