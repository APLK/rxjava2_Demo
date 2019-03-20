package com.szinternet.crm.ui.bindcreditcard;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.MyRepaymentCardBean;
import com.szinternet.crm.utils.CommonUtil;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.utils.ToastUtils;
import com.szinternet.crm.view.ClearEditText;
import com.szinternet.crm.view.TelEditText;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class BindCreditCardActivity extends BaseActivity<BindCreditCardPresenter> implements BindCreditCardContract.View {

    @BindView(R.id.et_real_name)
    EditText mEtRealName;
    @BindView(R.id.textView4)
    TextView mTextView4;
    @BindView(R.id.et_card)
    ClearEditText mEtCard;
    @BindView(R.id.iv_delete_card)
    ImageView mIvDeleteCard;
    @BindView(R.id.et_CVN)
    ClearEditText mEtCVN;
    @BindView(R.id.iv_delete_CVN)
    ImageView mIvDeleteCVN;
    @BindView(R.id.et_time)
    ClearEditText mEtTime;
    @BindView(R.id.iv_delete_time)
    ImageView mIvDeleteTime;
    @BindView(R.id.et_quota)
    ClearEditText mEtQuota;
    @BindView(R.id.iv_delete_quota)
    ImageView mIvDeleteQuota;
    @BindView(R.id.et_statement_date)
    ClearEditText mEtStatementDate;
    @BindView(R.id.iv_delete_statement_date)
    ImageView mIvDeleteStatementDate;
    @BindView(R.id.et_repayment_date)
    ClearEditText mEtRepaymentDate;
    @BindView(R.id.iv_delete_repayment_date)
    ImageView mIvDeleteRepaymentDate;
    @BindView(R.id.et_phonenum)
    TelEditText mEtPhonenum;
    @BindView(R.id.iv_delete_account)
    ImageView mIvDeleteAccount;
    @BindView(R.id.bt_next)
    Button mBtNext;
    @BindView(R.id.tv_get_ver)
    TextView mTvGetVer;
    @BindView(R.id.et_bind_ver)
    EditText mEtBindVer;
    @BindView(R.id.tv_bank_view)
    TextView mTvBankView;
    @BindView(R.id.repayment_view)
    LinearLayout mRepaymentView;
    private MyRepaymentCardBean.DataEntity mData;
    private int mType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_creditcard;
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
        initTitleBar(true, "绑定信用卡");
        EventHelper.click(this, mIvDeleteCard, mIvDeleteAccount, mTvGetVer, mBtNext);
    }

    @Override
    public void initDatas() {
        mData = (MyRepaymentCardBean.DataEntity) getIntent().getSerializableExtra("data");
        mEtCard.setClearDrawable(null);
        mEtCVN.setClearDrawable(null);
        mEtTime.setClearDrawable(null);
        if (mData != null) {
            mEtRealName.setKeyListener(null);
            mEtCard.setKeyListener(null);
            mEtCVN.setKeyListener(null);
            mIvDeleteCard.setVisibility(View.GONE);
            mIvDeleteCVN.setVisibility(View.GONE);
            mIvDeleteAccount.setVisibility(View.GONE);
            mIvDeleteCard.setVisibility(View.GONE);
            mEtRealName.setText(mData.user);
            mEtCVN.setText(mData.cvn);
            mEtCard.setText(mData.nub);

            mEtTime.setHint(mData.end_time);
            mEtQuota.setHint(mData.limit_money);
            mEtStatementDate.setHint(mData.bill_day);
            mEtRepaymentDate.setHint(mData.end_remoney);
        } else {
            mType = getIntent().getIntExtra("type", 0);
            if (mType == 1) {
                mRepaymentView.setVisibility(View.GONE);
            } else {
                mRepaymentView.setVisibility(View.VISIBLE);
                mEtQuota.setClearDrawable(null);
                mEtStatementDate.setClearDrawable(null);
                mEtRepaymentDate.setClearDrawable(null);
                EventHelper.click(this,mIvDeleteTime,mIvDeleteCVN,mIvDeleteQuota,mIvDeleteStatementDate,mIvDeleteRepaymentDate);
                mEtQuota.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (TextUtils.isEmpty(charSequence)) {
                            isShowX(false, mIvDeleteQuota);
                        } else {
                            isShowX(true, mIvDeleteQuota);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                mEtStatementDate.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (TextUtils.isEmpty(charSequence)) {
                            isShowX(false, mIvDeleteStatementDate);
                        } else {
                            isShowX(true, mIvDeleteStatementDate);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                mEtRepaymentDate.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (TextUtils.isEmpty(charSequence)) {
                            isShowX(false, mIvDeleteRepaymentDate);
                        } else {
                            isShowX(true, mIvDeleteRepaymentDate);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }

            mEtCard.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (TextUtils.isEmpty(charSequence)) {
                        isShowX(false, mIvDeleteCard);
                        mTvBankView.setVisibility(View.GONE);
                    } else {
                        isShowX(true, mIvDeleteCard);
                        if (CommonUtil.isValidCard(getBankCode())) {
                            mTvBankView.setVisibility(View.VISIBLE);
                        } else {
                            mTvBankView.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            mEtPhonenum.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (TextUtils.isEmpty(charSequence)) {
                        isShowX(false, mIvDeleteAccount);
                    } else {
                        isShowX(true, mIvDeleteAccount);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            mEtCVN.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (TextUtils.isEmpty(charSequence)) {
                        isShowX(false, mIvDeleteCVN);
                    } else {
                        isShowX(true, mIvDeleteCVN);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            mEtTime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (TextUtils.isEmpty(charSequence)) {
                        isShowX(false, mIvDeleteTime);
                    } else {
                        isShowX(true, mIvDeleteTime);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

    }


    @Override
    public void start2Activity(Class c) {
    }


    @Override
    public void onError(String msg) {
        mBtNext.setEnabled(true);
        super.dismissDialog();
        if (!TextUtils.isEmpty(msg))
            ToastUtils.showLongToast(msg);
    }

    @Override
    public void onSuccess() {
        mBtNext.setEnabled(true);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showLoadDialog() {
        mBtNext.setEnabled(false);
        super.showDialog("正在提交信息...");
    }

    @Override
    public void isShowX(boolean isShow, ImageView imageView) {
        if (!isShow) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public String getUserName() {
        return mEtRealName.getText().toString().trim().replace(" ", "");
    }

    @Override
    public String getBankCode() {
        return mEtCard.getText().toString().trim().replace(" ", "");
    }

    @Override
    public String getCVN2() {
        return mEtCVN.getText().toString().trim().replace(" ", "");
    }

    @Override
    public String getDealTime() {
        return mEtTime.getText().toString().trim().replace(" ", "");
    }

    @Override
    public String getQuota() {
        return mEtQuota.getText().toString().trim().replace(" ", "");
    }

    @Override
    public String getStatementDate() {
        return mEtStatementDate.getText().toString().trim().replace(" ", "");
    }

    @Override
    public String getRepaymentDate() {
        return mEtRepaymentDate.getText().toString().trim().replace(" ", "");
    }


    @Override
    public String getPhone() {
        return mEtPhonenum.getText().toString().trim().replace(" ", "");
    }

    @Override
    public String getVer() {
        return mEtBindVer.getText().toString().trim().replace(" ", "");
    }

    @Override
    public int getType() {
        return mType;
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
            case R.id.bt_next:
                if (mData != null) {
                    mPresenter.updaterc(mData.ids);
                } else {
                    if (mType == 1) {
                        mPresenter.bindDefrayrcCredit();
                    } else {
                        mPresenter.bindBankCode();
                    }
                }
                break;
            case R.id.iv_delete_card:
                mEtCard.setText("");
                break;
            case R.id.iv_delete_CVN:
                mEtCVN.setText("");
                break;
            case R.id.iv_delete_time:
                mEtTime.setText("");
                break;
            case R.id.iv_delete_quota:
                mEtQuota.setText("");
                break;
            case R.id.iv_delete_statement_date:
                mEtStatementDate.setText("");
                break;
            case R.id.iv_delete_repayment_date:
                mEtRepaymentDate.setText("");
                break;
            case R.id.iv_delete_account:
                mEtPhonenum.setText("");
                break;
            case R.id.tv_get_ver:
                mPresenter.getVerCode();
                break;
        }
    }

}
