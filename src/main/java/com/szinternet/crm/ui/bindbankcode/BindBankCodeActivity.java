package com.szinternet.crm.ui.bindbankcode;

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
import com.szinternet.crm.databean.AllBankBean;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.utils.ToastUtils;
import com.szinternet.crm.view.ClearEditText;
import com.szinternet.crm.view.PopSpinnerView;
import com.szinternet.crm.view.TelEditText;

import java.util.List;

import butterknife.BindView;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class BindBankCodeActivity extends BaseActivity<BindBankCodePresenter> implements BindBankCodeContract.View {

    @BindView(R.id.et_real_name)
    EditText mEtRealName;
    @BindView(R.id.et_card)
    ClearEditText mEtCard;
    @BindView(R.id.iv_delete_card)
    ImageView mIvDeleteCard;
    @BindView(R.id.et_bank)
    PopSpinnerView mEtBank;
    @BindView(R.id.et_bank_branch)
    EditText mEtBankBranch;
    @BindView(R.id.et_phonenum)
    TelEditText mEtPhonenum;
    @BindView(R.id.iv_delete_account)
    ImageView mIvDeleteAccount;
    @BindView(R.id.et_phone_ver)
    EditText mEtPhoneVer;
    @BindView(R.id.tv_get_ver)
    TextView mTvGetVer;
    @BindView(R.id.bt_next)
    Button mBtNext;
    private String idCardType = "-1";
    private String mNickname = "";
    private String mRealname = "";
    private String mIc = "";
    private String banName = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_bankcode;
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
        initTitleBar(true, "银行卡认证");
        EventHelper.click(this, mIvDeleteCard, mIvDeleteAccount, mTvGetVer, mEtBank, mBtNext);
    }

    @Override
    public void initDatas() {
        mNickname = getIntent().getStringExtra("nickname");
        mRealname = getIntent().getStringExtra("realname");
        mIc = getIntent().getStringExtra("ic");
        mEtCard.setClearDrawable(null);
        mEtCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    isShowX(false, mIvDeleteCard);
                } else {
                    isShowX(true, mIvDeleteCard);
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
    }

    private void initPopSpinnerData(final List<AllBankBean.DataEntity> list) {
        list.add(0, new AllBankBean.DataEntity("请选择银行", "-1"));
        mEtBank.setTextSize(14);
        mEtBank.init(list.size(), new PopSpinnerView.NameFilter() {

            @Override
            public String filter(int position) {
                return list.get(position).bank_name;
            }
        });

        mEtBank.setOnItemChecked(new PopSpinnerView.OnItemChecked() {
            @Override
            public void onChecked(int type) {
                idCardType = list.get(type).ids;
                banName = list.get(type).bank_name;
            }
        });

       /* getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                getWindow().getDecorView().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mEtBank.setWidth(mEtBank.getWidth());
                    }
                }, 300);
            }
        });*/
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        mEtBank.setWidth(mEtBank.getWidth());
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void start2Activity(Class c) {

    }


    @Override
    public void onError(String msg) {
        mBtNext.setEnabled(true);
        mEtBank.setEnabled(true);
        if (!TextUtils.isEmpty(msg))
            ToastUtils.showLongToast(msg);
    }

    @Override
    public void onSuccess() {
        mBtNext.setEnabled(true);
        mEtBank.setEnabled(true);
    }

    @Override
    public void onBankListSuccess(List<AllBankBean.DataEntity> list) {
        mEtBank.setEnabled(true);
        mEtBank.showPop(this);
        //        621035 5030100660429
        if (list != null && !list.isEmpty()) {
            //银行卡列表
            initPopSpinnerData(list);
        }
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
    public String getVer() {
        return mEtPhoneVer.getText().toString().trim().replace(" ", "");
    }

    @Override
    public String getBankName() {
        return banName;
    }

    @Override
    public String getBankType() {
        return Integer.parseInt(idCardType) == -1 ? "" : idCardType;
    }

    @Override
    public String getBankBranch() {
        return mEtBankBranch.getText().toString().trim().replace(" ", "");
    }

    @Override
    public TextView getSendCodeView() {
        return mTvGetVer;
    }

    @Override
    public String getPhone() {
        return mEtPhonenum.getText().toString().trim().replace(" ", "");
    }

    @Override
    public String getNickname() {
        return mNickname;
    }

    @Override
    public String getRealname() {
        return mRealname;
    }

    @Override
    public String getIc() {
        return mIc;
    }

    @Override
    public void showLoadDialog(String loadingText) {
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.bt_next:
                mPresenter.bindBankCode();
                break;
            case R.id.et_bank:
                mEtBank.dissmissPop();
                mEtBank.setEnabled(false);
                mPresenter.getAllBankList();
                break;
            case R.id.iv_delete_card:
                mEtCard.setText("");
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
