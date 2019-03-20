package com.szinternet.crm.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.szinternet.crm.R;
import com.szinternet.crm.databean.Car;
import com.szinternet.crm.recycle.BaseViewHolder;
import com.szinternet.crm.recycle.RecyclerArrayAdapter;


/**
 * 账单记录adapter
 */
public class BillingRecordAdapter extends RecyclerArrayAdapter<Car> {

    private Context mContext;

    public BillingRecordAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(final ViewGroup parent, int viewType) {
        return new BaseViewHolder<Car>(parent, R.layout.item_record) {
            @Override
            public void setData(final Car item) {
                super.setData(item);
                holder.setText(R.id.person_name, item.getName());
                holder.setText(R.id.person_sign, item.getLogo());
//                Glide.with(mContext).load(item.getLogo()).into((ImageView) holder.getView(R.id.person_face));
                setCircleImageUrl(R.id.person_face, item.getLogo(), R.mipmap.default_head);
            }
        };
    }

}
