package com.szinternet.crm.ui.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.szinternet.crm.AppManager;
import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerLoginComponent;
import com.szinternet.crm.ui.forgetpwd.ForgetPwdActivity;
import com.szinternet.crm.ui.register.RegisterActivity;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.view.ClearEditText;
import com.szinternet.crm.view.TelEditText;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.tv_account_login)
    TextView mTvAccountLogin;
    @BindView(R.id.et_login_phone)
    TelEditText mEtLoginPhone;
    @BindView(R.id.iv_delete_account)
    ImageView mIvDeleteAccount;
    @BindView(R.id.et_login_password)
    ClearEditText mEtLoginPassword;
    @BindView(R.id.iv_delete_pwd)
    ImageView mIvDeletePwd;
    @BindView(R.id.bt_login)
    Button mBtLogin;
    @BindView(R.id.tv_login_forget_user)
    TextView mTvLoginForgetUser;
    @BindView(R.id.tv_login_new_user)
    TextView mTvLoginNewUser;

    private long mExitTime;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
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
        EventHelper.click(this, mIvDeleteAccount, mIvDeletePwd, mBtLogin, mTvLoginNewUser, mTvLoginForgetUser);
        //检查intent中client的状态,如果存在则表示由其他界面跳转过去的需要去登录,此时弹出dialog
        mPresenter.checkIsClient(getIntent().getStringExtra("client"), this);
    }

    @Override
    public void initDatas() {
        mEtLoginPassword.setClearDrawable(null);
        mEtLoginPhone.addTextChangedListener(new TextWatcher() {
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

        mEtLoginPassword.addTextChangedListener(new TextWatcher() {
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
        mBtLogin.setEnabled(true);
    }

    @Override
    public void onSuccess() {
        mBtLogin.setEnabled(true);
    }

    @Override
    public void showLoadDialog() {
        mBtLogin.setEnabled(false);
        super.showDialog("正在登录中...");
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
        return mEtLoginPhone.getText().toString().trim().replace(" ", "");
    }

    @Override
    public String getPassword() {
        return mEtLoginPassword.getText().toString().trim().replace(" ", "");
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_new_user:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.bt_login:
                mPresenter.login();
                break;
            case R.id.iv_delete_account:
                mEtLoginPhone.setText("");
                break;
            case R.id.iv_delete_pwd:
                mEtLoginPassword.setText("");
                mEtLoginPhone.setSelection(0, 1);
                break;
            case R.id.tv_login_forget_user:
                //                                start2Activity(MainActivity.class);
                startActivity(new Intent(this, ForgetPwdActivity.class));
                break;
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, R.string.exit_tip, Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                AppManager.getAppManager().AppExit(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
