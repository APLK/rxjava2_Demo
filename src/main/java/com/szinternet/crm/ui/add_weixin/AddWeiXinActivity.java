package com.szinternet.crm.ui.add_weixin;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.fragment.contract.BaseMainContract;
import com.szinternet.crm.fragment.presenter.BaseMainPresenter;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class AddWeiXinActivity extends BaseActivity<BaseMainPresenter> implements BaseMainContract.View {


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_weixin;
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
        initTitleBar(true, "客服微信");
    }


    @Override
    protected void processClick(View v) {
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
