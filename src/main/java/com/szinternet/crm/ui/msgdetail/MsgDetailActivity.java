package com.szinternet.crm.ui.msgdetail;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerBillingRecordComponent;
import com.szinternet.crm.databean.MessageBean;
import com.szinternet.crm.fragment.contract.BaseMainContract;
import com.szinternet.crm.fragment.presenter.BaseMainPresenter;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MsgDetailActivity extends BaseActivity<BaseMainPresenter> implements BaseMainContract.View {


    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.content)
    TextView mContent;
    private MessageBean mData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_msg_detail;
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
        mData = (MessageBean) getIntent().getSerializableExtra("data");
        mTitle.setText(mData.getTitle());
        mContent.setText(mData.getContent());
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "消息详情");
    }


    @Override
    protected void processClick(View v) {
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

}
