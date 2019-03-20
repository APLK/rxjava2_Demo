package com.szinternet.crm.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.databean.ProxyBean;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;

import java.util.List;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class ProxyListAdapter extends EasyLVAdapter<ProxyBean> {


    public ProxyListAdapter(Context context, List<ProxyBean> list) {
        super(context, list, R.layout.item_proxy_list);
    }

    @Override
    public void convert(EasyLVHolder holder, int position, ProxyBean proxyBean) {
        TextView tvGrade = holder.getView(R.id.grade);
        TextView tvCount = holder.getView(R.id.count);
        tvGrade.setText(proxyBean.getDesr());
        Drawable drawable = ContextCompat.getDrawable(mContext, proxyBean.getImageResId());
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvGrade.setCompoundDrawables(drawable, null, null, null);
        tvCount.setText(proxyBean.getCount() + "");
    }

}
