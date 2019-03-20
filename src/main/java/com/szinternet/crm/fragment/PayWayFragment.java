package com.szinternet.crm.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.szinternet.crm.R;
import com.szinternet.crm.adapter.BankListAdapter;
import com.szinternet.crm.databean.AllBankBean;
import com.szinternet.crm.ui.bindcreditcard.BindCreditCardActivity;
import com.szinternet.crm.view.ScrollviewListView;

import java.util.ArrayList;
import java.util.List;

public class PayWayFragment extends DialogFragment {

    private static PayWayFragment sInstance;
    private ScrollviewListView list;
    private BankListAdapter mBankListAdapter;
    private ArrayList<AllBankBean.DataEntity> mData;
    private ClickItemListener listener;

    public static PayWayFragment newInstance(List<AllBankBean.DataEntity> data) {
        sInstance = new PayWayFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) data);
        sInstance.setArguments(b);
        return sInstance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mData = getArguments().getParcelableArrayList("data");
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_pay_way);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 2 / 5;
        window.setAttributes(lp);
        dialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog.findViewById(R.id.add_new_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BindCreditCardActivity.class));
                dismiss();
            }
        });
       /* mMyCardList = new ArrayList<>();
        mMyCardList.add(new CardMangerBean(0, "中国建设银行", "8857", ""));
        mMyCardList.add(new CardMangerBean(0, "中国农业银行", "8858", ""));
        mMyCardList.add(new CardMangerBean(0, "中国银行", "8859", ""));
        mMyCardList.add(new CardMangerBean(0, "中国工商银行", "8851", ""));
        mMyCardList.add(new CardMangerBean(0, "中国邮政储蓄银行", "8852", ""));
        mMyCardList.add(new CardMangerBean(0, "中国建设银行", "8853", ""));
        mMyCardList.add(new CardMangerBean(0, "浦东开发银行", "8854", ""));
        mMyCardList.add(new CardMangerBean(0, "农村信用合作社", "8855", ""));*/
        list = (ScrollviewListView) dialog.findViewById(R.id.lv_bank);
        mBankListAdapter = new BankListAdapter(getActivity(), mData);
        list.setAdapter(mBankListAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener!=null){
                    listener.click((AllBankBean.DataEntity) mBankListAdapter.getItem(position));
                }
                dismiss();
            }
        });
        return dialog;
    }


    //    public void setData(List<AllBankBean.DataEntity> data) {
    //        this.cardList = data;
    //        mBankListAdapter.notifyDataSetChanged();
    //    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sInstance = null;
    }

    public interface ClickItemListener{
        void click(AllBankBean.DataEntity bean);
    }

    public void setClickItemListener(ClickItemListener listener){
        this.listener=listener;
    }

}
