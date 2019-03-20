package com.szinternet.crm.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.databean.MyGradeBean;
import com.szinternet.crm.ui.grade_update.GradeUpdateActivity;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;

import java.io.Serializable;
import java.util.List;


public class MyGradeListAdapter extends EasyLVAdapter<MyGradeBean.UpClassEntity> {


    private Context context;

    public MyGradeListAdapter(Context context, List<MyGradeBean.UpClassEntity> list) {
        super(context, list, R.layout.item_grade_list);
        this.context = context;
    }

    @Override
    public void convert(EasyLVHolder holder, int position, final MyGradeBean.UpClassEntity myGradeBean) {
        TextView tvGradeName = holder.getView(R.id.grade_name);
        TextView tvMoney = holder.getView(R.id.money);
        TextView tvRate = holder.getView(R.id.rate);
        Button btUpdate = holder.getView(R.id.bt_update);
        tvGradeName.setText(myGradeBean.getGrade_name());
        int iconId = R.mipmap.menu_earnings_icon_vip;
        switch (Integer.parseInt(myGradeBean.getGrade())) {
            case 1:
                iconId = R.mipmap.menu_earnings_icon_vip;
                break;
            case 2:
                iconId = R.mipmap.proxy4;
                break;
            case 3:
                iconId = R.mipmap.menu_earnings_icon_senior;
                break;
            case 4:
                iconId = R.mipmap.menu_earnings_icon_shi;
                break;
            case 5:
                iconId = R.mipmap.menu_earnings_icon_sheng;
                break;
            case 6:
                iconId = R.mipmap.menu_earnings_icon_partner;
                break;
            case 7:
                iconId = R.mipmap.proxy1;
                break;
        }
        Drawable drawable = ContextCompat.getDrawable(mContext, iconId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvGradeName.setCompoundDrawables(drawable, null, null, null);
        tvMoney.setText("¥" + myGradeBean.getUp_money());
        tvRate.setText(String.format(context.getString(R.string.rate), myGradeBean.getRepay_rate() + "%", myGradeBean.getCollection_rate() + "%"));
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GradeUpdateActivity.class);
                intent.putExtra("grade", myGradeBean.getGrade());
                intent.putExtra("gradeName", myGradeBean.getGrade_name());
                intent.putExtra("info", (Serializable) myGradeBean.info);
                mContext.startActivity(intent);
            }
        });
    }

}

/*public class ProxyListAdapter extends RecyclerArrayAdapter<ProxyBean> {


    public ProxyListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<ProxyBean>(parent, R.layout.item_proxy_list) {
            @Override
            public void setData(final ProxyBean item) {
                super.setData(item);
                holder.setText(R.id.count, item.getCount() + "");
                ((TextView) holder.itemView.findViewById(R.id.grade)).setCompoundDrawables(ContextCompat.getDrawable(mContext, R.mipmap.proxy1), null, null, null);
            }
        };
    }

}*/
