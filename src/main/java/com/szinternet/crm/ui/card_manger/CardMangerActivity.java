package com.szinternet.crm.ui.card_manger;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerBillingRecordComponent;
import com.szinternet.crm.databean.CardMangerBean;
import com.szinternet.crm.dialog.ActionSheetDialog;
import com.szinternet.crm.dialog.AlertDialog;
import com.szinternet.crm.eventbus.RefreshIdsEvent;
import com.szinternet.crm.utils.EventHelper;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class CardMangerActivity extends BaseActivity<CardMangerPresenter> implements CardMangerContract.View {


    @BindView(R.id.tv_card)
    TextView mTvCard;
    @BindView(R.id.tv_unbind_card)
    TextView mTvUnbindCard;
    private CardMangerBean mItem;

    @Override
    public int getLayoutId() {
        return R.layout.activity_card_manger;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBillingRecordComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }


    @Override
    public void initDatas() {
        mItem = (CardMangerBean) getIntent().getSerializableExtra("item");
        mTvCard.setText(mItem.getType() == 0 ? "储蓄卡" : "信用卡" + "(尾号" + mItem.getAccount().substring(mItem.getAccount().length() - 4, mItem.getAccount().length()) + ")");
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "管理");
        EventHelper.click(this, mTvUnbindCard);
    }

    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.tv_unbind_card:
                if (mItem.getType() == 0) {
                    new AlertDialog(CardMangerActivity.this).builder().setTitle("提示")
                            .setMsg("储蓄卡不能解绑!")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            }).show();
                } else {
                    new ActionSheetDialog(CardMangerActivity.this)
                            .builder()
                            .setTitle("以后使用还款无忧可以继续绑定")
                            .setCancelable(false)
                            .setCanceledOnTouchOutside(false)
                            .addSheetItem("确定解除绑定", ActionSheetDialog.SheetItemColor.Red,
                                    new ActionSheetDialog.OnSheetItemClickListener() {
                                        @Override
                                        public void onClick(int which) {
                                            mPresenter.deleterc(mItem.getID());
                                        }
                                    }).show();
                }

            default:
                break;
        }
    }


    @Override
    public void start2Activity(Class c) {

    }

    @Override
    public void showLoadDialog() {
        mTvUnbindCard.setEnabled(false);
        super.showDialog("正在提交...");
    }

    @Override
    public void showLoadDialog(String loadingText) {
    }

    @Override
    public void onError(String msg) {
        mTvUnbindCard.setEnabled(true);
    }

    @Override
    public void onSuccess(String msg) {
        showMessage(msg);
        EventBus.getDefault().post(new RefreshIdsEvent(mItem.getID()));
        finish();
    }
}