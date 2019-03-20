package com.szinternet.crm.adapter;

import android.content.Context;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.databean.RepaymentPlanBean;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;

import java.util.List;


public class ShowRepaymentPlanListAdapter extends EasyLVAdapter<RepaymentPlanBean.DataEntity> {


    private Context context;

    public ShowRepaymentPlanListAdapter(Context context, List<RepaymentPlanBean.DataEntity> list) {
        super(context, list, R.layout.item_cash);
        this.context = context;
    }

    @Override
    public void convert(EasyLVHolder holder, int position, final RepaymentPlanBean.DataEntity bean) {
        TextView tvMoney = holder.getView(R.id.tv_money);
        TextView tvTime = holder.getView(R.id.tv_time);
        TextView tvType = holder.getView(R.id.tv_type);
        if (bean.type == 1) {
            tvType.setText("还款");
        } else {
            tvType.setText("消费");
        }
        tvMoney.setText("¥ " + bean.money);
        tvTime.setText(bean.create_time);
    }

}

