package com.szinternet.crm.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.szinternet.crm.R;
import com.szinternet.crm.databean.SpendRecordBean;
import com.szinternet.crm.recycle.BaseViewHolder;
import com.szinternet.crm.recycle.RecyclerArrayAdapter;
import com.szinternet.crm.utils.CommonUtil;


/**
 * 账单记录adapter
 */
public class RepaymentRecordAdapter extends RecyclerArrayAdapter<SpendRecordBean.DataEntity> {

    private Context mContext;

    public RepaymentRecordAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(final ViewGroup parent, int viewType) {
        return new BaseViewHolder<SpendRecordBean.DataEntity>(parent, R.layout.item_my_plan) {
            @Override
            public void setData(final SpendRecordBean.DataEntity dataEntity) {
                super.setData(dataEntity);
                holder.setText(R.id.tv_name, dataEntity.bank_name +
                        dataEntity.nub.substring(dataEntity.nub.length() - 4, dataEntity.nub.length()));
                if (!TextUtils.isEmpty(dataEntity.type)) {
                    switch (Integer.parseInt(dataEntity.type)) {
                        case 0:
                            holder.setText(R.id.tv_state, "还款中");
                            break;
                        case 1:
                            holder.setText(R.id.tv_state, "已完成");
                            break;
                        case 2:
                            holder.setText(R.id.tv_state, "失败");
                            break;
                    }
                }
                holder.setText(R.id.tv_time,String.format(mContext.getString(R.string.plan_time),
                        CommonUtil.userNameReplaceWithStar(dataEntity.e_time)));
                holder.setText(R.id.tv_money,String.format(mContext.getString(R.string.plan_money),
                        CommonUtil.userNameReplaceWithStar(dataEntity.pay_money)));
                holder.setText(R.id.tv_repayment_day,String.format(mContext.getString(R.string.plan_count),
                        CommonUtil.userNameReplaceWithStar(dataEntity.e_time)));
                holder.setText(R.id.tv_charge,String.format(mContext.getString(R.string.plan_server),
                        CommonUtil.userNameReplaceWithStar(dataEntity.pay_fee)));
            }
        };
    }

}
