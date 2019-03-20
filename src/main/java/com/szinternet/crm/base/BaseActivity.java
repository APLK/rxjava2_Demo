package com.szinternet.crm.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.szinternet.crm.AppManager;
import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.R;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.utils.InputUtils;
import com.szinternet.crm.utils.StatusBarCompat;
import com.szinternet.crm.utils.ToastUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 *
 * @param <T>
 */
public abstract class BaseActivity<T extends RxPresenter> extends AppCompatActivity implements View.OnClickListener, BaseContract.BaseView {
    @Inject
    public T mPresenter;
    protected Context mContext;
    //    private CustomDialog dialog;//进度条
    private ConnectivityManager manager;

    /***
     * 顶部title
     ***/
    protected LinearLayout toolbar;
    private View mToolbarBack;
    private KProgressHUD hud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        setupActivityComponent(CreditCardApplication.getsInstance().getAppComponent());
        if (mPresenter != null)
            mPresenter.attachView(this, this);
        initDialog();
        initDatas();
        configViews();
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void setTranslateBar() {
        StatusBarCompat.translucentStatusBar(this);
    }


    @Override
    protected void onDestroy() {
        dismissDialog();
        if (mPresenter != null)
            mPresenter.detachView();
        InputUtils.close(this);
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }


    public abstract int getLayoutId();

    protected abstract void setupActivityComponent(AppComponent appComponent);

    public abstract void initDatas();

    /**
     * 对各种控件进行设置、适配、填充数据
     */
    public abstract void configViews();

    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    // dialog
    protected void initDialog() {
        hud = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
    }

    public void showDialog() {
        if (hud == null) {
            initDialog();
        }
        try {
            hud.setLabel("加载中").setCancellable(false).show();
        } catch (Exception e) {

        }
    }

    public void showDialog(String loadingText) {
        if (hud == null) {
            initDialog();
        }
        hud.setLabel(loadingText).setCancellable(false).show();
    }

    public void dismissDialog() {
        if (hud == null) {
            initDialog();
        }
        hud.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected <T extends View> T getViewId(int resourcesId) {
        return (T) findViewById(resourcesId);
    }

    /**
     * 初始化标题栏(没有下拉图片)
     *
     * @param titleStrResId
     */
    public void initTitleBar(boolean isBack, int titleStrResId) {
        String titleStr = null;
        if (titleStrResId != 0) {
            titleStr = getString(titleStrResId);
        }
        initTitleBar(isBack, titleStr);
    }

    /**
     * 初始化标题栏
     *
     * @param isBack   true 表示可能回退
     * @param titleStr
     */
    public void initTitleBar(boolean isBack, String titleStr) {
        if (toolbar == null)
            return;
        if (mToolbarBack == null) {
            mToolbarBack = getViewId(R.id.btnBack);
        }
        TextView textView = getViewId(R.id.tv_top_title);
        if (!TextUtils.isEmpty(titleStr)) {
            textView.setText(titleStr);
        }
        if (isBack) {
            mToolbarBack.setVisibility(View.VISIBLE);
            mToolbarBack.setOnClickListener(this);
        } else {
            mToolbarBack.setVisibility(View.GONE);
        }
    }

    /**
     * title右侧文字
     *
     * @param str :文字内容
     */
    protected void setTitleRightText(String str, int resourcesId) {
        TextView textView = getViewId(R.id.btn_review_image);
        textView.setVisibility(View.VISIBLE);
        textView.setText(str);
        //        if (!TextUtils.isEmpty(str)) {
        if (resourcesId != 0) {
            textView.setCompoundDrawablePadding(5);
            Drawable image = getResources().getDrawable(resourcesId);
            image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());//非常重要，必须设置，否则图片不会显示
            textView.setCompoundDrawables(null, null, image, null);
        } else {
            textView.setCompoundDrawables(null, null, null, null);
        }
        //        }
    }

    /**
     * title右侧文字
     *
     * @param str :文字内容
     */
    protected void setTitleRightText(String str, int color, int resourcesId) {
        TextView textView = getViewId(R.id.btn_review_image);
        textView.setVisibility(View.VISIBLE);
        textView.setText(str);
        textView.setTextColor(color);
        //        if (!TextUtils.isEmpty(str)) {
        if (resourcesId != 0) {
            textView.setCompoundDrawablePadding(5);
            Drawable image = getResources().getDrawable(resourcesId);
            image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());//非常重要，必须设置，否则图片不会显示
            textView.setCompoundDrawables(null, null, image, null);
        } else {
            textView.setCompoundDrawables(null, null, null, null);
        }
        //        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                //                super.finish();
                break;
            default:
                processClick(v);
                break;
        }
    }

    /**
     * BaseActivity没有处理的点击事件，在此方法处理
     */
    protected abstract void processClick(View v);

    public void showMessage(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            ToastUtils.showToast(msg);
        }
    }

    /**
     * 显示错误页面信息
     *
     * @param msg            错误信息
     * @param erroricon      错误图标
     * @param prompt         错误提示
     * @param titleIsVisible 是否显示标题栏
     * @param titleText      标题栏文字
     */
    public void onError(String msg, int erroricon, String prompt, boolean titleIsVisible, String titleText) {
        setContentView(R.layout.common_empty_view);
    }


    /**
     * 检测网络是否连接
     *
     * @return ture 为连接 false 为未连接
     */
    protected boolean checkNetworkState() {
        boolean flag = false;
        //得到网络连接信息
        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }

    /**
     * 网络未连接 或者 服务器错误的处理方法
     *
     * @param message 服务器错误的消息
     */
    public void toastNoNot(String message) {
        if (checkNetworkState()) {
            ToastUtils.showToast(message);
        } else {
            ToastUtils.showToast(getString(R.string.no_net));
        }
    }

    /**
     * 网络未连接 或者 服务器错误的处理方法
     *
     * @param error   网络未连接时候的消息
     * @param message 服务器错误的消息
     */
    public void toastNoNot(String error, String message) {
        if (checkNetworkState()) {
            ToastUtils.showToast(message);
        } else {
            ToastUtils.showToast(error);
        }
    }

}
