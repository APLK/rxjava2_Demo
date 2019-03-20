package com.szinternet.crm.ui.code;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.R;
import com.szinternet.crm.UmengListener;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.utils.EventHelper;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class CodeGenerateActivity extends BaseActivity<CodeGeneratePresenter> implements CodeGenerateContract.View {
    @BindView(R.id.iv_english)
    ImageView mIvEnglish;
    @BindView(R.id.bt_share)
    Button mBtShare;
    private UmengListener mUmengListener;
    private String imgURL = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_code_generate;
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
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, R.string.code_generate);

        EventHelper.click(this, mBtShare);
        mPresenter.getShareImgURL();
    }

    @Override
    public void configViews() {
        mUmengListener = new UmengListener(this);//分享
    }

    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.bt_share:
                checkPer();
                break;
            default:
                break;
        }
    }

    private void checkPer() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissionsNeeded = new ArrayList<String>();
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);

            }
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.CALL_PHONE);
            }
            //            if (ContextCompat.checkSelfPermission(getActivity(),
            //                    Manifest.permission.READ_LOGS)
            //                    != PackageManager.PERMISSION_GRANTED) {
            //                LogUtils.i("1", "checkPer13="+Manifest.permission.READ_LOGS);
            //                permissionsNeeded.add(Manifest.permission.READ_LOGS);
            //            }
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
            }
            //            if (!Settings.canDrawOverlays(this)) {
            //                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            //                        Uri.parse("package:" + getPackageName()));
            //                startActivityForResult(intent, 10);
            //            }
            //            if (ContextCompat.checkSelfPermission(this,
            //                    Manifest.permission.SYSTEM_ALERT_WINDOW)
            //                    != PackageManager.PERMISSION_GRANTED) {
            //                LogUtils.i("1", "checkPer13="+Manifest.permission.SYSTEM_ALERT_WINDOW);
            //                permissionsNeeded.add(Manifest.permission.SYSTEM_ALERT_WINDOW);
            //            }
            //            if (ContextCompat.checkSelfPermission(this,
            //                    Manifest.permission.GET_ACCOUNTS)
            //                    != PackageManager.PERMISSION_GRANTED) {
            //                LogUtils.i("1", "checkPer13="+Manifest.permission.GET_ACCOUNTS);
            //                permissionsNeeded.add(Manifest.permission.GET_ACCOUNTS);
            //            }
            //            if (ContextCompat.checkSelfPermission(this,
            //                    Manifest.permission.SET_DEBUG_APP)
            //                    != PackageManager.PERMISSION_GRANTED) {
            //                LogUtils.i("1", "checkPer13="+Manifest.permission.SET_DEBUG_APP);
            //                permissionsNeeded.add(Manifest.permission.SET_DEBUG_APP);
            //            }
            //            if (ContextCompat.checkSelfPermission(this,
            //                    Manifest.permission.WRITE_APN_SETTINGS)
            //                    != PackageManager.PERMISSION_GRANTED) {
            //                LogUtils.i("1", "checkPer13="+Manifest.permission.WRITE_APN_SETTINGS);
            //                permissionsNeeded.add(Manifest.permission.WRITE_APN_SETTINGS);
            //            }
            if (permissionsNeeded.size() > 0) {
                ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[permissionsNeeded.size()]), 123);
            } else {
                share();
            }
        } else {
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
        boolean hasPermiss = true;
        switch (requestCode) {
            case 123: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            hasPermiss = false;
                            break;
                        }
                    }
                }
                if (hasPermiss) {
                    share();
                } else {
                    Toast.makeText(CreditCardApplication.getsInstance(), R.string.permissions_denied, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void share() {
        String imgShareURL = TextUtils.isEmpty(imgURL) ? "undefined" : imgURL;
        mUmengListener.shareUmeng(getString(R.string.app_name), imgShareURL, "【"+getString(R.string.app_name)+"】手机app是建立在手机完成付款的基础上," +
                "不用带卡,即可完成全额账单的还款操作智能还款,实时美化账单");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onError(String msg) {

    }

    @Override
    public void onSuccess(Bitmap bitmap) {
        super.dismissDialog();
        mIvEnglish.setImageBitmap(bitmap);
        mBtShare.setEnabled(true);
    }

    @Override
    public void start2Activity(Class c) {

    }

    @Override
    public void showLoadDialog() {
        mBtShare.setEnabled(false);
        super.showDialog("正在生成二维码...");
    }

    @Override
    public void onSuccess(String url) {
        imgURL = url;
    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

}