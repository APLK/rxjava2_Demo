package com.szinternet.crm.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.databean.AllBankBean;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;

import java.util.List;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class BankListAdapter extends EasyLVAdapter<AllBankBean.DataEntity> {


    public BankListAdapter(Context context, List<AllBankBean.DataEntity> list) {
        super(context, list, R.layout.item_bank);
    }

    @Override
    public void convert(EasyLVHolder holder, int position, AllBankBean.DataEntity bean) {
        TextView tvBank = holder.getView(R.id.bank);
        tvBank.setText(bean.bank_name + "(" + bean.nub.substring(bean.nub.length() - 4, bean.nub.length()) + ")");
        Drawable drawable = ContextCompat.getDrawable(mContext, R.mipmap.icon_jianshe);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvBank.setCompoundDrawables(drawable, null, null, null);
    }

}
