package com.szinternet.crm.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.szinternet.crm.R;
import com.szinternet.crm.databean.MembersBean;
import com.szinternet.crm.recycle.BaseViewHolder;
import com.szinternet.crm.recycle.RecyclerArrayAdapter;


public class MembersAdapter extends RecyclerArrayAdapter<MembersBean.DataEntity> {

    private Context mContext;

    public MembersAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(final ViewGroup parent, int viewType) {
        return new BaseViewHolder<MembersBean.DataEntity>(parent, R.layout.item_member) {
            @Override
            public void setData(final MembersBean.DataEntity item) {
                super.setData(item);
                holder.setCircleImageUrl(R.id.img, item.img_url, R.mipmap.default_head);
                holder.setText(R.id.tv_phone, "手机号:" + item.username);
                holder.setText(R.id.tv_profit, "分润贡献:" + item.fenrun);
            }

        };

    }
}
