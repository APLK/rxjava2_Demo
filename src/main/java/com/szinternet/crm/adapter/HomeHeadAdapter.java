package com.szinternet.crm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.databean.HomeHeadBean;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class HomeHeadAdapter extends ArrayBaseAdapter<HomeHeadBean> {
    public HomeHeadAdapter(Activity context) {
        super(context);
    }

    @Override
    public int initLayoutRes() {
        return R.layout.item_home_head;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, ViewHolder holder, HomeHeadBean homeHeadBean) {
        ImageView imageView = holder.obtainView(convertView, R.id.headIv);
        imageView.setImageResource(homeHeadBean.getImageResId());
        TextView textView=holder.obtainView(convertView,R.id.textView);
        textView.setText(homeHeadBean.getDesr());
        return convertView;
    }
}
