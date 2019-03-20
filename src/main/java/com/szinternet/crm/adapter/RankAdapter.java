package com.szinternet.crm.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.databean.RankBean;
import com.szinternet.crm.recycle.BaseViewHolder;
import com.szinternet.crm.recycle.RecyclerArrayAdapter;

import static com.szinternet.crm.Url.baseUrl;


public class RankAdapter extends RecyclerArrayAdapter<RankBean.DataEntity> {

    private Context mContext;

    public RankAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(final ViewGroup parent, int viewType) {
        return new BaseViewHolder<RankBean.DataEntity>(parent, R.layout.item_rank) {
            @Override
            public void setData(final RankBean.DataEntity item) {
                //                super.setData(item);
                TextView introduceCount = (TextView) holder.getItemView().findViewById(R.id.introduce_count);
                TextView iconSmall = (TextView) holder.getItemView().findViewById(R.id.icon_small);
                TextView iconRigth = (TextView) holder.getItemView().findViewById(R.id.icon_grade);
                ImageView iconBig = (ImageView) holder.getItemView().findViewById(R.id.icon_big);
                ImageView rankBg = (ImageView) holder.getItemView().findViewById(R.id.rank_bg);
                if (!TextUtils.isEmpty(item.username)) {
                    iconSmall.setVisibility(View.VISIBLE);
                    iconRigth.setVisibility(View.VISIBLE);
                    iconBig.setVisibility(View.VISIBLE);
                    holder.setText(R.id.phone, item.username);
                    holder.setCircleImageUrl(R.id.icon_big, baseUrl + item.img_url, R.mipmap.default_head);
                    introduceCount.setText(Html.fromHtml(String.format(mContext.getString(R.string.introduce_count), item.people)));
                    Drawable drawable;
                    switch (holder.getAdapterPosition()) {
                        case 1:
                            drawable = ContextCompat.getDrawable(mContext, R.mipmap.menu_earnings_icon_two);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            iconSmall.setText("");
                            rankBg.setVisibility(View.VISIBLE);
                            rankBg.setImageResource(R.mipmap.icon_second);
                            break;
                        case 2:
                            drawable = ContextCompat.getDrawable(mContext, R.mipmap.menu_earnings_icon_three);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            iconSmall.setText("");
                            rankBg.setVisibility(View.VISIBLE);
                            rankBg.setImageResource(R.mipmap.icon_third);
                            break;
                        default:
                            drawable = null;
                            iconSmall.setText("NO." + (holder.getAdapterPosition() + 1));
                            rankBg.setVisibility(View.GONE);
                            break;
                    }
                    iconSmall.setCompoundDrawables(drawable, null, null, null);
                    iconRigth.setText(item.grade);
                    /*switch (Integer.parseInt(item.grade)) {
                        case 0:
                            iconRigth.setImageResource(R.mipmap.rank_vip);
                            break;
                        case 1:
                            iconRigth.setImageResource(R.mipmap.rank_shiji);
                            break;
                        case 2:
                            iconRigth.setImageResource(R.mipmap.rank_shengji);
                            break;
                        case 3:
                            iconRigth.setImageResource(R.mipmap.rank_hehuo);
                            break;
                        default:
                            iconRigth.setImageResource(R.mipmap.rank_putong);
                            break;
                    }*/
                } else {
                    holder.setText(R.id.phone, "暂无数据");
                    introduceCount.setText("暂无数据");
                    iconSmall.setVisibility(View.INVISIBLE);
                    iconRigth.setVisibility(View.INVISIBLE);
                    iconBig.setVisibility(View.INVISIBLE);
                }
            }


        };
    }

}
