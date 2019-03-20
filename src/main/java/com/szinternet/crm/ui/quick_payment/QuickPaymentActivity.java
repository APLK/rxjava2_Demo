package com.szinternet.crm.ui.quick_payment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.CollectInfoBean;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.utils.ToastUtils;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class QuickPaymentActivity extends BaseActivity<QuickPaymentPresenter> implements QuickPaymentContract.View {


    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_card_code)
    TextView mTvCardCode;
    @BindView(R.id.et_bind_ver)
    EditText mEtBindVer;
    @BindView(R.id.tv_get_ver)
    TextView mTvGetVer;
    @BindView(R.id.bt_sure)
    Button mBtSure;
    private String mCreditID;
    private String mMoney;
    private String mBankID;
    private CollectInfoBean collectInfoBean;
    private String mBankCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_quick_payment;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "快捷支付");
        EventHelper.click(this, mBtSure, mTvGetVer);
    }

    @Override
    public void initDatas() {
        mCreditID = getIntent().getStringExtra("creditID");
        mBankID = getIntent().getStringExtra("bankID");
        mBankCode = getIntent().getStringExtra("bankCode");
        mMoney = getIntent().getStringExtra("money");
        mTvMoney.setText("¥ " + mMoney);
        if (CreditCardApplication.getsInstance().account != null) {
            mTvName.setText(CreditCardApplication.getsInstance().account.username);
        }
        mTvCardCode.setText(mBankCode);
    }


    @Override
    public void start2Activity(Class c) {
    }


    @Override
    public void onError(String msg) {
        mBtSure.setEnabled(true);
        super.dismissDialog();
        if (!TextUtils.isEmpty(msg))
            ToastUtils.showLongToast(msg);
    }

    @Override
    public void onSuccess() {
        mBtSure.setEnabled(true);
        finish();
    }

    @Override
    public void onCollectInfoSuccess(CollectInfoBean bean) {
        collectInfoBean = bean;
    }

    @Override
    public void showLoadDialog() {
        mBtSure.setEnabled(false);
        super.showDialog("正在提交信息...");
    }

    @Override
    public String getVer() {
        return mEtBindVer.getText().toString().replace(" ", "").trim();
    }

    @Override
    public TextView getSendCodeView() {
        return mTvGetVer;
    }


    @Override
    public void showLoadDialog(String loadingText) {
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_ver:
                mPresenter.getVerCode(mBankID, mCreditID, mMoney);
                break;
            case R.id.bt_sure:
                if (collectInfoBean != null) {
                    mPresenter.cashPay(mCreditID, mCreditID, mMoney, collectInfoBean.pay_order, collectInfoBean.order);
                } else {
                    ToastUtils.showToast("请先获取验证码");
                }
                break;
        }
    }
}
