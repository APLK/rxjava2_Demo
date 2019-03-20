package com.szinternet.crm.ui.setting;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.szinternet.crm.AppManager;
import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.dialog.AlertDialog;
import com.szinternet.crm.ui.about.AboutActivity;
import com.szinternet.crm.ui.login.LoginActivity;
import com.szinternet.crm.ui.updatepwd.UpdatePwdActivity;
import com.szinternet.crm.utils.EventHelper;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View {

    @BindView(R.id.updatepwd_view)
    RelativeLayout mUpdatepwdView;
    @BindView(R.id.about_view)
    RelativeLayout mAboutView;
    @BindView(R.id.logout)
    Button mLogout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
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
        initTitleBar(true, "设置");
        EventHelper.click(this, mAboutView, mUpdatepwdView, mLogout);
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.updatepwd_view:
                start2Activity(UpdatePwdActivity.class);
                break;
            case R.id.about_view:
                start2Activity(AboutActivity.class);
                break;
            case R.id.logout:
                new AlertDialog(this).builder().setTitle("提示")
                        .setMsg("您确定要退出当前账号?\n")
                        .setPositiveButton("残忍退出", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPresenter.logout();
                            }
                        }).setNegativeButton("否", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
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
    public void onSuccess() {
        AppManager.getAppManager().finishAllActivity();
        start2Activity(LoginActivity.class);
    }

    @Override
    public void showLoadDialog() {

    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

}
