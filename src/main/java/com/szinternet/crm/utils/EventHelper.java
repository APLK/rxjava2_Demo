package com.szinternet.crm.utils;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class EventHelper {

    public static void click(Activity activity, View... views) {
        if (!(activity instanceof View.OnClickListener))
            return;
        if (views == null || views.length == 0)
            return;
        for (View v : views) {
            v.setOnClickListener((View.OnClickListener) activity);
        }
    }

    public static void click(Fragment fragment, View... views) {
        if (!(fragment instanceof View.OnClickListener))
            return;
        if (views == null || views.length == 0)
            return;
        for (View v : views) {
            v.setOnClickListener((View.OnClickListener) fragment);
        }
    }

    public static void setNavigationItemSelected(Activity activity, View... views) {
        if (!(activity instanceof NavigationView.OnNavigationItemSelectedListener))
            return;
        if (views == null || views.length == 0)
            return;
        for (View v : views)
            ((NavigationView) v).setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener)
                    activity);
    }
}
