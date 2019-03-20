package com.szinternet.crm.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.szinternet.crm.R;
import com.szinternet.crm.databean.TradeLogBean;
import com.szinternet.crm.recycle.BaseViewHolder;
import com.szinternet.crm.recycle.RecyclerArrayAdapter;


public class TradeRecordAdapter extends RecyclerArrayAdapter<TradeLogBean.DataEntity> {

    private Context mContext;

    public TradeRecordAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(final ViewGroup parent, int viewType) {
        return new BaseViewHolder<TradeLogBean.DataEntity>(parent, R.layout.item_repayment) {
            @Override
            public void setData(final TradeLogBean.DataEntity item) {
                super.setData(item);
                holder.setText(R.id.tv_spend, item.money + "");
                holder.setText(R.id.tv_time, item.create_time);
                holder.setText(R.id.tv_code, item.pay_order);
            }
        };
    }

}
