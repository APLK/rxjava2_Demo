package com.szinternet.crm.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.szinternet.crm.R;
import com.szinternet.crm.databean.GlobalAttribute;
import com.szinternet.crm.ui.GuideActivity;
import com.szinternet.crm.ui.login.LoginActivity;
import com.szinternet.crm.utils.CommonUtil;

import java.util.List;


/**
 * ViewPagerAdapter
 * 引导页面适配器
 */
public class ViewPagerAdapter extends PagerAdapter {
    /**
     * 界面数据源
     */
    private List<View> views;
    /**
     * 上下文
     */
    private Activity activity;

    /**
     * 构造器
     *
     * @param views
     * @param activity 上下文
     */
    public ViewPagerAdapter(List<View> views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }

    /**
     * 销毁arg1位置的界面
     */
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(views.get(arg1));
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    // 获得当前界面数
    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    /**
     * 初始化arg1位置的界面
     *
     * @author zqgame
     */
    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(views.get(arg1), 0);

        if (arg1 == views.size() - 1) {
            View startBtn = arg0.findViewById(R.id.iv_guide);
            if (!(activity instanceof GuideActivity)) {
                startBtn.setVisibility(View.GONE);
            } else {
                startBtn.setVisibility(View.VISIBLE);
            }
            startBtn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 设置已经引导
                    setGuided();
                    goLogin();
                }
            });

        }
        return views.get(arg1);
    }

    /**
     * 跳转登录页面
     */
    private void goLogin() {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * 设置已经引导过了，下次启动不用再次引导
     */
    private void setGuided() {
        CommonUtil.set2SP(GlobalAttribute.FIRST_IN, false);
    }

    // 判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

}
