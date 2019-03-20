package com.szinternet.crm.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseMainFragment;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.dialog.ActionSheetDialog;
import com.szinternet.crm.dialog.AlertDialog;
import com.szinternet.crm.fragment.contract.MeContract;
import com.szinternet.crm.fragment.presenter.MePresenter;
import com.szinternet.crm.ui.add_weixin.AddWeiXinActivity;
import com.szinternet.crm.ui.bankcard.BankCardActivity;
import com.szinternet.crm.ui.bindidentity.BindIdentityActivity;
import com.szinternet.crm.ui.grade.MyGradeActivity;
import com.szinternet.crm.ui.guide.NoviceGuideActivity;
import com.szinternet.crm.ui.login.LoginActivity;
import com.szinternet.crm.ui.myinfo.MyinfoActivity;
import com.szinternet.crm.ui.rate.RateActivity;
import com.szinternet.crm.ui.setting.SettingActivity;
import com.szinternet.crm.utils.CommonUtil;
import com.szinternet.crm.utils.EventHelper;
import com.yuyh.easyadapter.glide.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.szinternet.crm.Url.baseUrl;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MineFragment extends BaseMainFragment<MePresenter> implements MeContract.View {
    @BindView(R.id.toolbar)
    LinearLayout mToolbar;
    @BindView(R.id.ivHeader)
    ImageView mIvHeader;
    @BindView(R.id.nickname)
    TextView mNickname;
    @BindView(R.id.phone)
    TextView mPhone;
    @BindView(R.id.bind_state)
    TextView mBindState;
    @BindView(R.id.bind_phone)
    LinearLayout mBindPhone;
    @BindView(R.id.tv_phonenum)
    TextView mTvPhonenum;
    @BindView(R.id.introducer_view)
    RelativeLayout mIntroducerView;
    @BindView(R.id.tv_msg)
    TextView mTvMsg;
    @BindView(R.id.message_view)
    RelativeLayout mMessageView;
    @BindView(R.id.rate_view)
    RelativeLayout mRateView;
    @BindView(R.id.tv_grade)
    TextView mTvGrade;
    @BindView(R.id.grade_view)
    RelativeLayout mGradeView;
    @BindView(R.id.tv_bind_state)
    TextView mTvBindState;
    @BindView(R.id.bind_view)
    RelativeLayout mBindView;
    @BindView(R.id.manger_view)
    RelativeLayout mMangerView;
    @BindView(R.id.guide_view)
    RelativeLayout mGuideView;
    @BindView(R.id.contact_view)
    RelativeLayout mContactView;
    @BindView(R.id.btn_review_image)
    TextView mBtnReviewImage;
    @BindView(R.id.myinfo_view)
    RelativeLayout mMyinfoView;
    private String phone = "";
    private RequestOptions mRequestOptions;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mine;
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

    }


    @Override
    public void configViews() {
        mMessageView.setVisibility(View.GONE);
        setToolbar(mToolbar);
        initTitleBar(false, R.string.mine);
        setTitleRightText("设置", 0);
        EventHelper.click(this, mBtnReviewImage, mBindState, mIntroducerView,
                mMessageView, mRateView, mGradeView, mBindView, mMangerView, mGuideView,
                mContactView, mMyinfoView);
    }

    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.myinfo_view:
                start2Activity(MyinfoActivity.class);
                break;
            case R.id.bind_state:
                if (CreditCardApplication.getsInstance().account == null) {//未登录
                    start2Activity(LoginActivity.class);
                } else {
                    go2BindIdentity();
                }
                break;
            case R.id.introducer_view:
                break;
            case R.id.message_view:
//                start2Activity(MsgCenterActivity.class);
                break;
            case R.id.btn_review_image:
                start2Activity(SettingActivity.class);
                break;
            case R.id.rate_view:
                start2Activity(RateActivity.class);
                break;
            case R.id.grade_view:
                start2Activity(MyGradeActivity.class);
                break;
            case R.id.guide_view:
                start2Activity(NoviceGuideActivity.class);
                break;
            case R.id.bind_view:
                if (CreditCardApplication.getsInstance().account == null) {//未登录
                    start2Activity(LoginActivity.class);
                } else {
                    go2BindIdentity();
                }
                break;
            case R.id.manger_view:
                start2Activity(BankCardActivity.class);
                break;
            case R.id.contact_view:
                mPresenter.getPhone();
                break;
        }
    }

    private void go2BindIdentity() {
        if (CreditCardApplication.getsInstance().account.real.equalsIgnoreCase("have")) {
            Intent intent = new Intent(getActivity(), BindIdentityActivity.class);
            intent.putExtra("state", true);
            startActivity(intent);//需要携带信息过去
        } else {
            start2Activity(BindIdentityActivity.class);
        }
    }

    private void showContactDialog() {
        new ActionSheetDialog(getActivity())
                .builder()
                .setTitle("请选择操作")
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("客服微信", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
//                                ToastUtils.showToast("敬请期待");
                                start2Activity(AddWeiXinActivity.class);
                            }
                        })
                .addSheetItem("客服热线: " + phone, ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                new AlertDialog(getActivity()).builder().setTitle("提示")
                                        .setMsg("是否拨打 " + phone + "?")
                                        .setPositiveButton("拨打", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                List<String> permissionsNeeded = new ArrayList<String>();
                                                if (ContextCompat.checkSelfPermission(getActivity(),
                                                        Manifest.permission.CALL_PHONE)
                                                        != PackageManager.PERMISSION_GRANTED) {
                                                    permissionsNeeded.add(Manifest.permission.CALL_PHONE);
                                                }
                                                if (permissionsNeeded.size() > 0) {
                                                    ActivityCompat.requestPermissions(getActivity(), permissionsNeeded.toArray(new String[permissionsNeeded.size()]), 100);
                                                } else {
                                                    go2Call();
                                                }
                                            }
                                        }).setNegativeButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();

                            }
                        }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        boolean hasPermiss = true;
        switch (requestCode) {
            case 100: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            hasPermiss = false;
                            break;
                        }
                    }
                }
                if (hasPermiss) {
                    go2Call();
                } else {
                    Toast.makeText(CreditCardApplication.getsInstance(), R.string.permissions_denied, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void go2Call() {
        CommonUtil.skipPhone(getActivity(), phone);
    }

    @Override
    protected void lazyLoadData() {
        if (CreditCardApplication.getsInstance().account == null) {//未登录
            mBindState.setText("未登录");
        } else {
            if (CreditCardApplication.getsInstance().account.real.equalsIgnoreCase("have")) {
                mTvBindState.setText("已认证");
                mBindState.setText("已认证");
            } else {
                mTvBindState.setText("未认证");
                mBindState.setText("未认证");
            }
            mTvGrade.setText(CreditCardApplication.getsInstance().account.grade);
            mNickname.setText(TextUtils.isEmpty(CreditCardApplication.getsInstance()
                    .account.nickname) ? "暂无昵称" : CreditCardApplication.getsInstance()
                    .account.nickname);
            mPhone.setText(CreditCardApplication.getsInstance().account.username);
            if (TextUtils.isEmpty(CreditCardApplication.getsInstance().account.parent_id) ||
                    "0".equals(CreditCardApplication.getsInstance().account.parent_id)) {
                mTvPhonenum.setText("暂无数据");
            } else {
                mTvPhonenum.setText(CreditCardApplication.getsInstance().account.parent_id);
            }

            mRequestOptions = new RequestOptions();
            mRequestOptions.placeholder(R.mipmap.default_head);
            mRequestOptions.error(R.mipmap.default_head);
            mRequestOptions.transform(new GlideRoundTransform(getActivity()));
            Glide.with(getActivity()).load(baseUrl + CreditCardApplication.getsInstance().account.img_url).apply(mRequestOptions).into(mIvHeader);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        lazyLoadData();
    }

    @Override
    public void onError(String msg) {
        mContactView.setEnabled(true);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onSuccess(String phone) {
        mContactView.setEnabled(true);
        this.phone = phone == null ? "" : phone;
        showContactDialog();
    }

    @Override
    public void start2Activity(Class c) {
        startActivity(new Intent(getActivity(), c));
    }

    @Override
    public void showLoadDialog() {
        mContactView.setEnabled(false);
        super.showDialog("加载中...");
    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

}
