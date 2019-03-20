package com.szinternet.crm.ui.creditcard_collect;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.AllBankBean;
import com.szinternet.crm.databean.CreditCollectInfoBean;
import com.szinternet.crm.fragment.PayWayFragment;
import com.szinternet.crm.ui.bindcreditcard.BindCreditCardActivity;
import com.szinternet.crm.ui.bindidentity.BindIdentityActivity;
import com.szinternet.crm.ui.grade.MyGradeActivity;
import com.szinternet.crm.ui.quick_payment.QuickPaymentActivity;
import com.szinternet.crm.ui.trade_log.TradeRecordActivity;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.utils.KeyboardUtil;
import com.szinternet.crm.utils.ToastUtils;
import com.szinternet.crm.view.MoneyTextWatcher;

import java.util.List;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class CreditCardCollectActivity extends BaseActivity<CreditCardCollectPresenter> implements CreditCardCollectContract.View {

    @BindView(R.id.add_creditcard)
    RelativeLayout mAddCreditcard;
    @BindView(R.id.tv_collect_card)
    TextView mTvCollectCard;
    @BindView(R.id.collect_card)
    RelativeLayout mCollectCard;
    @BindView(R.id.et_money)
    EditText mEtMoney;
    @BindView(R.id.tv_rate)
    TextView mTvRate;
    @BindView(R.id.tv_update)
    TextView mTvUpdate;
    @BindView(R.id.tv_bank)
    TextView mTvBank;
    @BindView(R.id.iv_enter)
    ImageView mIvEnter;
    @BindView(R.id.tv_credit)
    TextView mTvCredit;
    @BindView(R.id.btn_review_image)
    TextView mBtnReviewImage;
    private KeyboardUtil keyboardUtil;
    private PayWayFragment mPayDetailFragment;
    private CreditCollectInfoBean bean;
    private String creditID;
    private String bankID;
    private String bankCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_creditcard_collect;
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
        keyboardUtil = new KeyboardUtil(CreditCardCollectActivity.this, CreditCardCollectActivity.this, mEtMoney);
        init();
        mPresenter.loadCreditInfo();
    }

    public void init() {
        mEtMoney.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int numberType = mEtMoney.getInputType();
                mEtMoney.setInputType(InputType.TYPE_NULL);
                keyboardUtil.showKeyboard();
                mEtMoney.setInputType(numberType);
                return true;
            }
        });
        keyboardUtil.setOnEnterListener(new KeyboardUtil.EnterListener() {
            @Override
            public void enter() {
                if (TextUtils.isEmpty(creditID) || TextUtils.isEmpty(bankID)) {
                    ToastUtils.showToast("请先添加信用卡或银行卡");
                } else {
                    if (TextUtils.isEmpty(mEtMoney.getText().toString()) || Float.parseFloat(mEtMoney.getText().toString()) <= 0) {
                        ToastUtils.showToast("请先输入收款金额!");
                    } else {
                        Intent intent = new Intent(CreditCardCollectActivity.this, QuickPaymentActivity.class);
                        intent.putExtra("creditID", creditID);
                        intent.putExtra("bankCode", bankCode);
                        intent.putExtra("bankID", bankID);
                        intent.putExtra("money", mEtMoney.getText().toString());
                        startActivity(intent);
                    }
                }
            }
        });
        //默认两位小数
        mEtMoney.addTextChangedListener(new MoneyTextWatcher(mEtMoney));
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "信用卡收款");
        setTitleRightText("交易记录", 0);
        EventHelper.click(this, mAddCreditcard, mCollectCard, mTvUpdate, mBtnReviewImage);
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.add_creditcard:
                if (bean == null || TextUtils.isEmpty(bean.getRc_bank_name())) {
                    go2BindCredit();
                } else {
                    mAddCreditcard.setEnabled(false);
                    mPresenter.loadCreditList();
                }
                break;
            case R.id.collect_card:
                if (bean == null || TextUtils.isEmpty(bean.getBank_name())) {
                    start2Activity(BindIdentityActivity.class);
                }
                break;
            case R.id.tv_update:
                start2Activity(MyGradeActivity.class);
                break;
            case R.id.btn_review_image:
                start2Activity(TradeRecordActivity.class);
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
        super.showDialog();
    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

    @Override
    public void onError() {
        mAddCreditcard.setEnabled(true);
    }

    @Override
    public void onSuccess(List<AllBankBean.DataEntity> list) {
        mAddCreditcard.setEnabled(true);
        if (list != null && !list.isEmpty()) {
            mPayDetailFragment = PayWayFragment.newInstance(list);
            mPayDetailFragment.show(getSupportFragmentManager(), "payWayFragment");
            mPayDetailFragment.setClickItemListener(new PayWayFragment.ClickItemListener() {
                @Override
                public void click(AllBankBean.DataEntity bean) {
                    creditID = bean.ids;
                    mTvCredit.setCompoundDrawables(null, null, null, null);
                    mTvCredit.setText(bean.bank_name + "(" + bean.nub + ")");
                }
            });
        } else {
            go2BindCredit();
        }
    }

    private void go2BindCredit() {
        Intent intent = new Intent(this, BindCreditCardActivity.class);
        intent.putExtra("type", 1);
        startActivity(intent);
    }

    @Override
    public void onInfoSuccess(CreditCollectInfoBean bean) {
        this.bean = bean;
        if (bean != null) {
            if (!TextUtils.isEmpty(bean.getRc_bank_name())) {
                creditID = bean.getRc_bank_id();
                mTvCredit.setCompoundDrawables(null, null, null, null);
                mTvCredit.setText(bean.getRc_bank_name() + "(" + bean.getNub() + ")");
            } else {
                Drawable drawable = ContextCompat.getDrawable(mContext, R.mipmap.add_card);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mTvCredit.setText("添加信用卡");
                mTvCredit.setCompoundDrawables(drawable, null, null, null);
            }
            if (!TextUtils.isEmpty(bean.getBank_name())) {
                bankID = bean.getUser_bank_id();
                bankCode = bean.getAccount();
                mTvBank.setCompoundDrawables(null, null, null, null);
                mTvBank.setText(bean.getBank_name() + "(" + bean.getAccount() + ")");
                mIvEnter.setVisibility(View.GONE);
            } else {
                mIvEnter.setVisibility(View.VISIBLE);
                Drawable drawable = ContextCompat.getDrawable(mContext, R.mipmap.add_card);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mTvBank.setText("添加储蓄卡");
                mTvBank.setCompoundDrawables(drawable, null, null, null);
            }
            if (!TextUtils.isEmpty(bean.getGrade())) {
                mTvUpdate.setText(String.format(getString(R.string.update_rate), bean.getGrade(), bean.getUp_rate()));
            }
            mTvRate.setText(String.format(getString(R.string.my_rate), bean.getRate()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadCreditInfo();
    }

}
