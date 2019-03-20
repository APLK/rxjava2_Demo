package com.szinternet.crm.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
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
import com.szinternet.crm.UmengListener;
import com.szinternet.crm.base.BaseMainFragment;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.fragment.contract.ShareContract;
import com.szinternet.crm.fragment.presenter.SharePresenter;
import com.szinternet.crm.ui.code.CodeGenerateActivity;
import com.szinternet.crm.ui.grade.MyGradeActivity;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.utils.LogUtils;
import com.yuyh.easyadapter.glide.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.szinternet.crm.Url.baseUrl;

/**
 * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 *
 * @描述 分享界面
 */
public class ShareFragment extends BaseMainFragment<SharePresenter> implements ShareContract.View {
    @BindView(R.id.toolbar)
    LinearLayout mToolbar;
    @BindView(R.id.upgrade)
    TextView mUpgrade;
    @BindView(R.id.share1)
    RelativeLayout mShare1;
    @BindView(R.id.share2)
    RelativeLayout mShare2;
    @BindView(R.id.share3)
    RelativeLayout mShare3;
    @BindView(R.id.iv_img)
    ImageView mIvImg;
    private UmengListener mUmengListener;
    private RequestOptions mRequestOptions;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_share;
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
        if (CreditCardApplication.getsInstance().account != null) {
            mRequestOptions = new RequestOptions();
            mRequestOptions.placeholder(R.mipmap.default_head);
            mRequestOptions.error(R.mipmap.default_head);
            mRequestOptions.transform(new GlideCircleTransform(getActivity()));
            Glide.with(getActivity()).load(baseUrl + CreditCardApplication.getsInstance().account.img_url).apply(mRequestOptions).into(mIvImg);
        }
    }


    public void configViews() {
        mUmengListener = new UmengListener(getActivity());//分享
        EventHelper.click(this, mShare1, mShare3, mUpgrade);
        setToolbar(mToolbar);
        mShare2.setVisibility(View.GONE);
        initTitleBar(false, R.string.my_share);
    }

    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.share1:
                start2Activity(CodeGenerateActivity.class);
                break;
            case R.id.share3:
                LogUtils.i("1", "checkPer0");
                checkPer();
                break;
            case R.id.upgrade:
                start2Activity(MyGradeActivity.class);
                break;
        }
    }

    private void checkPer() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissionsNeeded = new ArrayList<String>();
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                LogUtils.i("1", "checkPer13=" + Manifest.permission.WRITE_EXTERNAL_STORAGE);
                permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                LogUtils.i("1", "checkPer13=" + Manifest.permission.ACCESS_FINE_LOCATION);
                permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);

            }
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                LogUtils.i("1", "checkPer13=" + Manifest.permission.CALL_PHONE);
                permissionsNeeded.add(Manifest.permission.CALL_PHONE);
            }
            //            if (ContextCompat.checkSelfPermission(getActivity(),
            //                    Manifest.permission.READ_LOGS)
            //                    != PackageManager.PERMISSION_GRANTED) {
            //                LogUtils.i("1", "checkPer13="+Manifest.permission.READ_LOGS);
            //                permissionsNeeded.add(Manifest.permission.READ_LOGS);
            //            }
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                LogUtils.i("1", "checkPer13=" + Manifest.permission.READ_PHONE_STATE);
                permissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
            }
//            if (!Settings.canDrawOverlays(getActivity())) {
            //                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            //                        Uri.parse("package:" + getPackageName()));
            //                startActivityForResult(intent, 10);
            //            }
            //            if (ContextCompat.checkSelfPermission(getActivity(),
            //                    Manifest.permission.SYSTEM_ALERT_WINDOW)
            //                    != PackageManager.PERMISSION_GRANTED) {
            //                LogUtils.i("1", "checkPer13="+Manifest.permission.SYSTEM_ALERT_WINDOW);
            //                permissionsNeeded.add(Manifest.permission.SYSTEM_ALERT_WINDOW);
            //            }
            //            if (ContextCompat.checkSelfPermission(getActivity(),
            //                    Manifest.permission.GET_ACCOUNTS)
            //                    != PackageManager.PERMISSION_GRANTED) {
            //                LogUtils.i("1", "checkPer13="+Manifest.permission.GET_ACCOUNTS);
            //                permissionsNeeded.add(Manifest.permission.GET_ACCOUNTS);
            //            }
            //            if (ContextCompat.checkSelfPermission(getActivity(),
            //                    Manifest.permission.SET_DEBUG_APP)
            //                    != PackageManager.PERMISSION_GRANTED) {
            //                LogUtils.i("1", "checkPer13="+Manifest.permission.SET_DEBUG_APP);
            //                permissionsNeeded.add(Manifest.permission.SET_DEBUG_APP);
            //            }
            //            if (ContextCompat.checkSelfPermission(getActivity(),
            //                    Manifest.permission.WRITE_APN_SETTINGS)
            //                    != PackageManager.PERMISSION_GRANTED) {
            //                LogUtils.i("1", "checkPer13="+Manifest.permission.WRITE_APN_SETTINGS);
            //                permissionsNeeded.add(Manifest.permission.WRITE_APN_SETTINGS);
            //            }
            if (permissionsNeeded.size() > 0) {
                requestPermissions(permissionsNeeded.toArray(new String[permissionsNeeded.size()]), 123);
            } else {
                share();
            }
            LogUtils.i("1", "checkPer1");
            //            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            //            //ActivityCompat.requestPermissions(getActivity(),
            //            //        new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            //            //以下是直接使用Fragment的requestPermissions方法
            //            requestPermissions(mPermissionList, 123);
            //            LogUtils.i("1", "checkPer12");
        } else {
            LogUtils.i("1", "checkPer2");
            share();
        }
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(getActivity())) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
                    Toast.makeText(getActivity(), R.string.permissions_denied, Toast.LENGTH_SHORT);
                }
            }
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        LogUtils.i("1", "checkPer11");
        boolean hasPermiss = true;
        switch (requestCode) {
            case 123: {
                LogUtils.i("1", "checkPer13=" + grantResults.length);
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        LogUtils.i("1", "checkPer3=" + i + ",s=" + grantResults[i]);
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            hasPermiss = false;
                            LogUtils.i("1", "checkPer3");
                            break;
                        }
                    }
                }
                if (hasPermiss) {
                    LogUtils.i("1", "checkPer4");
                    share();
                } else {
                    Toast.makeText(CreditCardApplication.getsInstance(), R.string.permissions_denied, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void share() {
        mPresenter.getShareDownURl();
    }


    @Override
    protected void lazyLoadData() {

    }


    @Override
    public void start2Activity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        startActivity(intent);
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onSuccess(String url) {
        String downUrl = TextUtils.isEmpty(url) ? "undefined" : url;
        mUmengListener.shareUmeng(getString(R.string.app_name), downUrl, "【"+getString(R.string.app_name)+"】手机app是建立在手机完成付款的基础上," +
                "不用带卡,即可完成全额账单的还款操作智能还款,实时美化账单");
    }

    @Override
    public void showLoadDialog() {
        super.showDialog();
    }

    @Override
    public void showLoadDialog(String loadingText) {

    }


}
