package com.szinternet.crm.ui.webview;

import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.fragment.contract.BaseMainContract;
import com.szinternet.crm.fragment.presenter.BaseMainPresenter;

import butterknife.BindView;

import static com.szinternet.crm.R.id.webView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class WebviewActivity extends BaseActivity<BaseMainPresenter> implements BaseMainContract.View {


    @BindView(webView)
    WebView mWebView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
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
        //加载通行证协议内容
        mWebView.loadUrl("file:///android_asset/pingan.html");
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "权益说明");
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
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
