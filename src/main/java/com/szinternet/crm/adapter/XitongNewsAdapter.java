package com.szinternet.crm.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.szinternet.crm.R;
import com.szinternet.crm.databean.MessageBean;
import com.szinternet.crm.recycle.BaseViewHolder;
import com.szinternet.crm.recycle.RecyclerArrayAdapter;
import com.szinternet.crm.utils.FormatUtils;

import java.util.Date;


/**
 * 账单记录adapter
 */
public class XitongNewsAdapter extends RecyclerArrayAdapter<MessageBean> {

    private Context mContext;

    public XitongNewsAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<MessageBean>(parent, R.layout.item_xt_news) {
            @Override
            public void setData(final MessageBean item) {
                super.setData(item);
                holder.setText(R.id.msg_title, item.getTitle());
                holder.setText(R.id.msg_content, item.getContent());
                holder.setText(R.id.time, FormatUtils.date2String(new Date()));
            }
        };
    }

}
