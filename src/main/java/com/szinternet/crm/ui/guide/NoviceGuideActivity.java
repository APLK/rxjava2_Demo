package com.szinternet.crm.ui.guide;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.fragment.contract.BaseMainContract;
import com.szinternet.crm.fragment.presenter.BaseMainPresenter;
import com.szinternet.crm.ui.operationsguide.OperationGuideActivity;
import com.szinternet.crm.utils.EventHelper;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class NoviceGuideActivity extends BaseActivity<BaseMainPresenter> implements BaseMainContract.View {

    @BindView(R.id.repayment_view)
    RelativeLayout mRepaymentView;
    @BindView(R.id.collect_view)
    RelativeLayout mCollectView;
    private int guideType=0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_novice_guide;
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
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "新手指引");
        EventHelper.click(this, mRepaymentView, mCollectView);
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.repayment_view:
                guideType=0;
                start2Activity(OperationGuideActivity.class);
                break;
            case R.id.collect_view:
                guideType=1;
                start2Activity(OperationGuideActivity.class);
                break;
            default:
                break;
        }
    }


    @Override
    public void start2Activity(Class c) {
        Intent intent = new Intent(this, c);
        intent.putExtra("guideType",guideType);
        startActivity(intent);
    }

    @Override
    public void showLoadDialog() {

    }

    @Override
    public void showLoadDialog(String loadingText) {

    }
}
