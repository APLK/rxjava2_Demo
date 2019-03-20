package com.szinternet.crm.ui.register;

import android.content.Intent;
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
import com.szinternet.crm.component.DaggerLoginComponent;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.utils.ToastUtils;
import com.szinternet.crm.view.ClearEditText;
import com.szinternet.crm.view.TelEditText;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.tv_account_register)
    TextView mTvAccountRegister;
    @BindView(R.id.et_register_phone)
    TelEditText mEtRegisterPhone;
    @BindView(R.id.iv_delete_account)
    ImageView mIvDeleteAccount;
    @BindView(R.id.et_register_ver)
    EditText mEtRegisterVer;
    @BindView(R.id.tv_get_ver)
    TextView mTvGetVer;
    @BindView(R.id.et_register_password)
    ClearEditText mEtRegisterPassword;
    @BindView(R.id.iv_delete_pwd)
    ImageView mIvDeletePwd;
    @BindView(R.id.et_register_intruder)
    TelEditText mEtRegisterIntruder;
    @BindView(R.id.iv_delete_intruder)
    ImageView mIvDeleteIntruder;
    @BindView(R.id.bt_register)
    Button mBtRegister;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {
        EventHelper.click(this, mBtRegister, mIvDeleteAccount, mIvDeleteIntruder, mIvDeletePwd, mTvGetVer);
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "注册");
    }

    @Override
    public void initDatas() {
        mEtRegisterPassword.setClearDrawable(null);
        mEtRegisterPhone.addTextChangedListener(new TextWatcher() {
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

        mEtRegisterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    isShowX(false, mIvDeletePwd);
                } else {
                    isShowX(true, mIvDeletePwd);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mEtRegisterIntruder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    isShowX(false, mIvDeleteIntruder);
                } else {
                    isShowX(true, mIvDeleteIntruder);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void start2Activity(Class c) {
        Intent intent = new Intent(this, c);
        intent.putExtra("fragmentIndex", 0);
        startActivity(intent);
        finish();
    }


    @Override
    public void onError(String msg) {
        mBtRegister.setEnabled(true);
        super.dismissDialog();
        if (!TextUtils.isEmpty(msg))
            ToastUtils.showLongToast(msg);
    }

    @Override
    public void onSuccess() {
        mBtRegister.setEnabled(true);
        super.dismissDialog();
    }

    @Override
    public void showLoadDialog() {
        mBtRegister.setEnabled(false);
        super.showDialog("注册资料提交中...");
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
    public void showLoadDialog(String loadingText) {
    }


    @Override
    public String getUserName() {
        return mEtRegisterPhone.getText().toString().trim().replace(" ", "");
    }

    @Override
    public String getPassword() {
        return mEtRegisterPassword.getText().toString().trim().replace(" ", "");
    }

    @Override
    public String getVer() {
        return mEtRegisterVer.getText().toString().trim().replace(" ", "");
    }

    @Override
    public String getIntroducter() {
        return mEtRegisterIntruder.getText().toString().trim().replace(" ", "");
    }

    @Override
    public TextView getSendCodeView() {
        return mTvGetVer;
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register:
                mPresenter.register();
                break;
            case R.id.iv_delete_account:
                mEtRegisterPhone.setText("");
                break;
            case R.id.iv_delete_pwd:
                mEtRegisterPassword.setText("");
                break;
            case R.id.iv_delete_intruder:
                mEtRegisterIntruder.setText("");
                break;
            case R.id.tv_get_ver:
                mPresenter.getVerCode();
                break;
        }
    }

}
