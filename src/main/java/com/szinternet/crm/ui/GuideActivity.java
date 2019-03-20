package com.szinternet.crm.ui;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.szinternet.crm.R;
import com.szinternet.crm.adapter.ViewPagerAdapter;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private List<View> views;
    private ViewPagerAdapter vpAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initDatas() {
        //状态栏透明显示
        //        setTranslateBar();
        // android隐藏底部虚拟键NavigationBar实现全屏
        //        ButterKnife.findById(this, R.id.guide_layout).setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        // 初始化引导图片列表
        views.add(inflater.inflate(R.layout.guide_item, null));
        views.add(inflater.inflate(R.layout.guide_item1, null));
        views.add(inflater.inflate(R.layout.guide_item2, null));

        // 初始化Adapter
        vpAdapter = new ViewPagerAdapter(views, this);

        mViewpager.setAdapter(vpAdapter);
        // 绑定回调
        mViewpager.setOnPageChangeListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public void configViews() {

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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}