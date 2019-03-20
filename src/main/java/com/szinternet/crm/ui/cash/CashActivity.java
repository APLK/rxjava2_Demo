package com.szinternet.crm.ui.cash;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.FrCashIndexBean;
import com.szinternet.crm.ui.cash_record.CashRecordActivity;
import com.szinternet.crm.ui.divide_profits_record.DivideProfitsRecordActivity;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.utils.ToastUtils;
import com.szinternet.crm.view.MoneyTextWatcher;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class CashActivity extends BaseActivity<CashPresenter> implements CashContract.View {

    @BindView(R.id.bank_view)
    RelativeLayout mBankView;
    @BindView(R.id.btn_review_image)
    TextView mBtnReviewImage;
    @BindView(R.id.icon_bank)
    ImageView mIconBank;
    @BindView(R.id.tv_bank_name)
    TextView mTvBankName;
    @BindView(R.id.tv_number)
    TextView mTvNumber;
    @BindView(R.id.et_money)
    EditText mEtMoney;
    @BindView(R.id.bt_cash)
    Button mBtCash;
    @BindView(R.id.income_record)
    TextView mIncomeRecord;
    private FrCashIndexBean mItem;
    private int mType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cash;
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
        mItem = (FrCashIndexBean) getIntent().getSerializableExtra("item");
        mType = getIntent().getIntExtra("type", 0);
        if (mItem != null) {
            mTvBankName.setText(mItem.bank_name);
            mTvNumber.setText(mItem.account);
        }
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        setTitleRightText("提现记录", 0);
        EventHelper.click(this, mBtnReviewImage);
        initTitleBar(true, "提现");
        EventHelper.click(this, mBtCash, mBtnReviewImage, mIncomeRecord, mBankView);

        //默认两位小数
        mEtMoney.addTextChangedListener(new MoneyTextWatcher(mEtMoney));
        //测试3位小数
        //        mEtMoney.addTextChangedListener(new MoneyTextWatcher(mEtMoney).setDigits(3));
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
           /* case R.id.bank_view:
                PayWayFragment payDetailFragment = new PayWayFragment();
                payDetailFragment.show(getSupportFragmentManager(), "payWayFragment");
                break;*/
            case R.id.bt_cash:
                if (TextUtils.isEmpty(getMoney())) {
                    ToastUtils.showToast("请输入提现金额");
                } else {
                    mPresenter.cash();
                }
                break;
            case R.id.income_record:
                if (mType == 0) {
                    start2Activity(DivideProfitsRecordActivity.class);
                } else {
                    Intent intent = new Intent(this, CashRecordActivity.class);
                    intent.putExtra("promoteDetail", true);
                    startActivity(intent);
                }
                break;
            case R.id.btn_review_image:
                start2Activity(CashRecordActivity.class);
                break;
            default:
                break;
        }
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
    public void onSuccess() {

    }

    @Override
    public String getMoney() {
        return mEtMoney.getText().toString().replace(" ", "").trim();
    }

}
