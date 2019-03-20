package com.szinternet.crm.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.szinternet.crm.R;
import com.szinternet.crm.databean.CashRecordBean;
import com.szinternet.crm.recycle.BaseViewHolder;
import com.szinternet.crm.recycle.RecyclerArrayAdapter;
import com.szinternet.crm.utils.FormatUtils;

import java.util.Date;


public class CashRecordAdapter extends RecyclerArrayAdapter<CashRecordBean.DataEntity> {

    private Context mContext;

    public CashRecordAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(final ViewGroup parent, int viewType) {
        return new BaseViewHolder<CashRecordBean.DataEntity>(parent, R.layout.item_cash) {
            @Override
            public void setData(final CashRecordBean.DataEntity item) {
                super.setData(item);
                holder.setText(R.id.tv_money, "¥ " + item.money);
                holder.setText(R.id.tv_time, FormatUtils.date2String(new Date()));
            }
        };
    }

}
