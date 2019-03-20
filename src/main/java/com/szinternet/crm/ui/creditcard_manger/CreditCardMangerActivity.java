package com.szinternet.crm.ui.creditcard_manger;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.MyRepaymentCardBean;
import com.szinternet.crm.dialog.ActionSheetDialog;
import com.szinternet.crm.dialog.AlertDialog;
import com.szinternet.crm.ui.bindcreditcard.BindCreditCardActivity;
import com.szinternet.crm.ui.card_manger.CardMangerContract;
import com.szinternet.crm.ui.card_manger.CardMangerPresenter;
import com.szinternet.crm.ui.repayment_record.RepaymentRecordActivity;
import com.szinternet.crm.utils.CommonUtil;
import com.szinternet.crm.utils.EventHelper;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class CreditCardMangerActivity extends BaseActivity<CardMangerPresenter> implements CardMangerContract.View {


    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.tv_limit)
    TextView mTvLimit;
    @BindView(R.id.tv_repayment_day)
    TextView mTvRepaymentDay;
    @BindView(R.id.tv_last_repayment)
    TextView mTvLastRepayment;
    @BindView(R.id.tv_repayment_info)
    TextView mTvRepaymentInfo;
    @BindView(R.id.tv_edit_info)
    TextView mTvEditInfo;
    @BindView(R.id.tv_unbind_card)
    TextView mTvUnbindCard;
    @BindView(R.id.tv_bank_name)
    TextView mTvBankName;
    private MyRepaymentCardBean.DataEntity mData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_creditcard_manger;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initDatas() {
        mData = (MyRepaymentCardBean.DataEntity) getIntent().getSerializableExtra("data");
        if (mData != null) {
            mTvBankName.setText(mData.bank_name +
                    mData.nub.substring(mData.nub.length() - 4, mData.nub.length()));
            mName.setText(String.format(getString(R.string.card_admin),
                    CommonUtil.userNameReplaceWithStar(mData.user)));
            mTvLimit.setText(String.format(getString(R.string.money_limit),
                    CommonUtil.userNameReplaceWithStar(mData.limit_money)));
            mTvRepaymentDay.setText(String.format(getString(R.string.repayment_day),
                    CommonUtil.userNameReplaceWithStar(mData.bill_day)));
            mTvLastRepayment.setText(String.format(getString(R.string.last_repayment),
                    CommonUtil.userNameReplaceWithStar(mData.end_time)));
        }
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "信用卡管理");
        EventHelper.click(this, mTvRepaymentInfo, mTvEditInfo, mTvUnbindCard);
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.tv_repayment_info:
                start2Activity(RepaymentRecordActivity.class);
                break;
            case R.id.tv_edit_info:
                if (mData.status == 0) {//待还款
                    Intent intent = new Intent(this, BindCreditCardActivity.class);
                    intent.putExtra("data", mData);
                    startActivityForResult(intent, 100);
                } else {
                    showEditDialog();
                }
                break;
            case R.id.tv_unbind_card:
                showUnbindDialog();
                break;
            default:
                break;
        }
    }

    private void showEditDialog() {
        new AlertDialog(CreditCardMangerActivity.this).builder().setTitle("提示")
                .setMsg("当前卡号任务计划正在进行中,不允许修改")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }

    private void showUnbindDialog() {
        new ActionSheetDialog(CreditCardMangerActivity.this)
                .builder()
                .setTitle("以后使用还款无忧可以继续绑定")
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("确定解除绑定", ActionSheetDialog.SheetItemColor.Red,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                if (mData != null) {
                                    mPresenter.deleterc(mData.ids);
                                }
                            }
                        }).show();
    }


    @Override
    public void start2Activity(Class c) {
        startActivity(new Intent(this, c));
    }

    @Override
    public void showLoadDialog() {

    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onSuccess(String msg) {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {//信用卡修改
            //更新信息
            //TODO
        }
    }
}
