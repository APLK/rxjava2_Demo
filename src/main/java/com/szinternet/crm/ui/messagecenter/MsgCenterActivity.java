package com.szinternet.crm.ui.messagecenter;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.ui.billingrecord.BillingRecordActivity;
import com.szinternet.crm.ui.xitongnews.XitongNewsActivity;
import com.szinternet.crm.utils.EventHelper;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MsgCenterActivity extends BaseActivity<MsgCenterPresenter> implements MsgCenterContract.View {


    @BindView(R.id.tv_xt_news)
    TextView mTvXtNews;
    @BindView(R.id.tv_xt_time)
    TextView mTvXtTime;
    @BindView(R.id.xt_view)
    RelativeLayout mXtView;
    @BindView(R.id.tv_share_news)
    TextView mTvShareNews;
    @BindView(R.id.tv_share_time)
    TextView mTvShareTime;
    @BindView(R.id.share_view)
    RelativeLayout mShareView;
    @BindView(R.id.tv_tg_news)
    TextView mTvTgNews;
    @BindView(R.id.tv_tg_time)
    TextView mTvTgTime;
    @BindView(R.id.tg_view)
    RelativeLayout mTgView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_msg_center;
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
        initTitleBar(true, "消息中心");
        EventHelper.click(this, mXtView, mShareView, mTgView);
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.xt_view:
                start2Activity(XitongNewsActivity.class);
                break;
            case R.id.share_view:
                start2Activity(BillingRecordActivity.class);
                break;
            case R.id.tg_view:
                start2Activity(BillingRecordActivity.class);
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
    public void showLoadDialog() {

    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

}
