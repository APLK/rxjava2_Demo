package com.szinternet.crm.ui.bindidentity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.BindInfoBean;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.view.ClearEditText;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class BindIdentityActivity extends BaseActivity<BindIdentityPresenter> implements BindIdentityContract.View {

    @BindView(R.id.et_nickname)
    EditText mEtNickname;
    @BindView(R.id.et_real_name)
    EditText mEtRealName;
    @BindView(R.id.et_code)
    ClearEditText mEtCode;
    @BindView(R.id.bt_next)
    Button mBtNext;
    @BindView(R.id.iv_delete_code)
    ImageView mIvDeleteCode;
    private boolean mState;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_identity;
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
        initTitleBar(true, "身份认证");
        EventHelper.click(this,mIvDeleteCode, mBtNext);
    }

    @Override
    public void initDatas() {
        mEtCode.setClearDrawable(null);
        mState = getIntent().getBooleanExtra("state", false);
        if (mState) {
            mBtNext.setVisibility(View.GONE);
            mEtNickname.setKeyListener(null);
            mEtRealName.setKeyListener(null);
            mEtCode.setKeyListener(null);
            mIvDeleteCode.setVisibility(View.GONE);
            mEtNickname.setHint("default");
            mEtRealName.setHint("default");
            mEtCode.setHint("default");
            mPresenter.getBindInfo();
        } else {
            mBtNext.setVisibility(View.VISIBLE);

            mEtCode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (TextUtils.isEmpty(charSequence)) {
                        isShowX(false, mIvDeleteCode);
                    } else {
                        isShowX(true, mIvDeleteCode);
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
        Intent intent = new Intent(this, c);
        intent.putExtra("nickname", getNickname());
        intent.putExtra("realname", getRealName());
        intent.putExtra("ic", getIdentityCode());
        startActivity(intent);
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
    public void onError(String msg) {
    }


    @Override
    public void onSuccess(BindInfoBean bean) {
        if (bean != null) {
            mEtNickname.setText(bean.nickname);
            mEtRealName.setText(bean.realname);
            mEtCode.setText(bean.ic);
        } else {
            mEtNickname.setText("default");
            mEtRealName.setText("default");
            mEtCode.setText("default");
        }
    }

    @Override
    public void showLoadDialog() {
    }

    @Override
    public String getNickname() {
        return mEtNickname.getText().toString().trim();
    }

    @Override
    public String getRealName() {
        return mEtRealName.getText().toString().trim();
    }

    @Override
    public String getIdentityCode() {
        return mEtCode.getText().toString().trim().replace(" ", "");
    }


    @Override
    public void showLoadDialog(String loadingText) {
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.bt_next:
                mPresenter.bindIdentity();
                break;
            case R.id.iv_delete_code:
                mEtCode.setText("");
                break;
        }
    }
}
