package com.szinternet.crm.ui.updatepwd;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.view.ClearEditText;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class UpdatePwdActivity extends BaseActivity<UpdatePwdPresenter> implements UpdatePwdContract.View {

    @BindView(R.id.et_old_password)
    ClearEditText mEtOldPassword;
    @BindView(R.id.et_password)
    ClearEditText mEtPassword;
    @BindView(R.id.iv_delete_pwd)
    ImageView mIvDeletePwd;
    @BindView(R.id.et_sure_password)
    ClearEditText mEtSurePassword;
    @BindView(R.id.iv_delete_sure_pwd)
    ImageView mIvDeleteSurePwd;
    @BindView(R.id.bt_update)
    Button mBtUpdate;
    @BindView(R.id.iv_delete_old_pwd)
    ImageView mIvDeleteOldPwd;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_pwd;
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
        EventHelper.click(this, mBtUpdate, mEtOldPassword, mEtSurePassword, mIvDeletePwd, mIvDeleteOldPwd, mIvDeleteSurePwd);
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "修改密码");
    }

    @Override
    public void initDatas() {
        mEtOldPassword.setClearDrawable(null);
        mEtPassword.setClearDrawable(null);
        mEtSurePassword.setClearDrawable(null);
        mEtOldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    isShowX(false, mIvDeleteOldPwd);
                } else {
                    isShowX(true, mIvDeleteOldPwd);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEtPassword.addTextChangedListener(new TextWatcher() {
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
        mEtSurePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    isShowX(false, mIvDeleteSurePwd);
                } else {
                    isShowX(true, mIvDeleteSurePwd);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void start2Activity(Class c) {
    }


    @Override
    public void onError(String msg) {
        mBtUpdate.setEnabled(true);
    }

    @Override
    public void onSuccess() {
        mBtUpdate.setEnabled(true);
    }

    @Override
    public void showLoadDialog() {
        mBtUpdate.setEnabled(false);
        super.showDialog("正在修改密码...");
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
    public String getNewPassword() {
        return mEtPassword.getText().toString().trim();
    }


    @Override
    public String getSurePassword() {
        return mEtSurePassword.getText().toString().trim().replace(" ", "");
    }

    @Override
    public String getOldPassword() {
        return mEtOldPassword.getText().toString().trim().replace(" ", "");
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.bt_update:
                mPresenter.updatePwd();
                break;
            case R.id.iv_delete_old_pwd:
                mEtOldPassword.setText("");
                break;
            case R.id.iv_delete_pwd:
                mEtPassword.setText("");
                break;
            case R.id.iv_delete_sure_pwd:
                mEtSurePassword.setText("");
                break;
        }
    }

}
