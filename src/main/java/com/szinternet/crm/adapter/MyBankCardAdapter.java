package com.szinternet.crm.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.szinternet.crm.R;
import com.szinternet.crm.databean.CardMangerBean;
import com.szinternet.crm.recycle.BaseViewHolder;
import com.szinternet.crm.recycle.RecyclerArrayAdapter;


public class MyBankCardAdapter extends RecyclerArrayAdapter<CardMangerBean> {

    private Context mContext;

    public MyBankCardAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(final ViewGroup parent, int viewType) {
        return new BaseViewHolder<CardMangerBean>(parent, R.layout.item_card) {
            @Override
            public void setData(final CardMangerBean item) {
                super.setData(item);
                holder.setText(R.id.tv_bank_type, item.getType() == 0 ? "储蓄卡" : "信用卡");
                holder.setText(R.id.tv_bank_name, TextUtils.isEmpty(item.getBank_name()) ? "undefined" : item.getBank_name());
                holder.setText(R.id.tv_bank_code, item.getAccount());
            }
        };
    }

}
