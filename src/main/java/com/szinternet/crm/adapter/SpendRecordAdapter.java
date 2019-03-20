package com.szinternet.crm.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.szinternet.crm.R;
import com.szinternet.crm.databean.RepaymentPeriodsBean;
import com.szinternet.crm.recycle.BaseViewHolder;
import com.szinternet.crm.recycle.RecyclerArrayAdapter;


public class SpendRecordAdapter extends RecyclerArrayAdapter<RepaymentPeriodsBean.DataEntity> {

    private Context mContext;

    public SpendRecordAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(final ViewGroup parent, int viewType) {
        return new BaseViewHolder<RepaymentPeriodsBean.DataEntity>(parent, R.layout.item_repayment) {
            @Override
            public void setData(final RepaymentPeriodsBean.DataEntity item) {
                super.setData(item);
                holder.setText(R.id.tv_spend, item.money + "");
                holder.setText(R.id.tv_count, "第" + item.times + "期");
                holder.setText(R.id.tv_time, item.pay_time);
                holder.setText(R.id.tv_code, item.pay_order);
//                holder.setText(R.id.tv_time, FormatUtils.date2String(new Date()));
//                holder.setText(R.id.tv_type, item.getType() == 0 ? "自动还款" : "手动还款");
//                holder.setText(R.id.tv_code, item.getCode());
            }
        };
    }

}
