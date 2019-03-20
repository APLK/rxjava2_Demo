package com.szinternet.crm.ui.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerLoginComponent;
import com.szinternet.crm.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        //状态栏透明显示
        //        setTranslateBar();
        // android隐藏底部虚拟键NavigationBar实现全屏
        //        ButterKnife.findById(this, R.id.splash_layout).setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        verifyStoragePermissions();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     */
    public void verifyStoragePermissions() {
        List<String> permissionsNeeded = new ArrayList<String>();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (permissionsNeeded.size() > 0) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[permissionsNeeded.size()]), 100);
        } else {
            mPresenter.checkIsFirstIn(SplashActivity.this);
        }
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
                    mPresenter.checkIsFirstIn(SplashActivity.this);
                } else {
                    Toast.makeText(CreditCardApplication.getsInstance(), R.string.permissions_denied, Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            }
        }
    }

    @Override
    protected void processClick(View v) {

    }


    @Override
    public void readyGoLogin() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onSuccess() {
        start2Activity(MainActivity.class);
    }


    @Override
    public void onBackPressed() {
    }


    @Override
    public void start2Activity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoadDialog() {

    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

}
