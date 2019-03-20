package com.szinternet.crm.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.R;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.utils.InputUtils;
import com.szinternet.crm.utils.LogUtils;
import com.szinternet.crm.utils.ToastUtils;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements View.OnClickListener, BaseContract.BaseView {
    //    @Inject
    //    public T mPresenter;
    protected ViewGroup parentView;
    protected LayoutInflater inflater;

    //是否可见状态
    private boolean isVisible;
    //View已经初始化完成
    private boolean isPrepared;
    //是否第一次加载完
    private boolean isFirstLoad = true;
    private ConnectivityManager manager;
    private KProgressHUD hud;
    public BaseActivity mActivity;
    private Unbinder mBindKnife;
    private View mToolbarBack;
    //    private Context mContext;

    public abstract
    @LayoutRes
    int getLayoutResId();

    protected abstract void setupActivityComponent(AppComponent appComponent);

    protected LinearLayout mToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        isFirstLoad = true;
        parentView = (ViewGroup) inflater.inflate(getLayoutResId(), container, false);
        mBindKnife = ButterKnife.bind(this, parentView);
        this.inflater = inflater;
        //        mActivity = getSupportActivity();
        //        mContext = mActivity;
        //绑定View
        setupActivityComponent(CreditCardApplication.getsInstance().getAppComponent());
        attachView();
        initDialog();
        isPrepared = true;
        //        if (mPresenter != null) {
        //            //绑定Presenter
        //            mPresenter.attachView(this, mActivity);
        //        }
        //初始化事件和获取数据, 在此方法中获取数据不是懒加载模式
        initDatas();
        configViews();
        //在此方法中获取数据为懒加载模式,如不需要懒加载,请在initEventAndData获取数据,GankFragment有使用实例
        lazyLoad();
        //初始化弹框
        return parentView;
    }


    protected <T extends View> T getViewId(int resourcesId) {
        if (parentView != null) {
            return (T) parentView.findViewById(resourcesId);
        }
        return null;
    }

    public FragmentActivity getSupportActivity() {
        return super.getActivity();
    }


    public abstract void attachView();

    public abstract void initDatas();

    /**
     * 对各种控件进行设置、适配、填充数据
     */
    public abstract void configViews();

    @Override
    public void onAttach(Activity activity) {
        this.mActivity = (BaseActivity) activity;
        super.onAttach(activity);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBindKnife.unbind();
        //页面销毁,恢复标记
        isPrepared = false;
        isFirstLoad = true;
        mToolbar = null;
        mToolbarBack = null;
        //        if (mPresenter != null)
        //            mPresenter.detachView();
    }

    protected LayoutInflater getLayoutInflater() {
        return inflater;
    }

    protected View getParentView() {
        return parentView;
    }

    protected void initDialog() {
        hud = KProgressHUD.create(getActivity()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
    }

    public void showDialog() {
        if (hud == null) {
            initDialog();
        }
        try {
            hud.setLabel("加载中...").setCancellable(false).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                processClick(v);
                break;
        }
    }

    protected abstract void processClick(View v);

    //    setUserVisibleHint是在onCreateView之前调用的
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    protected void lazyLoad() {
//        LogUtils.i("1","lazyLoad0="+this);
//        LogUtils.i("1", "lazyLoad=" + isPrepared + ",isVisible=" + isVisible + ",isFirstLoad=" + isFirstLoad);
        if (!isPrepared || !isVisible || !isFirstLoad)
            return;
        isFirstLoad = false;
        lazyLoadData();
    }

    protected abstract void lazyLoadData();

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

    public void setToolbar(LinearLayout toolbar) {
        mToolbar = toolbar;
    }

    /**
     * 初始化标题栏
     *
     * @param isBack   true 表示可能回退
     * @param titleStr
     */
    public void initTitleBar(boolean isBack, String titleStr) {
        if (mToolbar == null)
            return;
        if (mToolbarBack == null) {
            mToolbarBack = getViewId(R.id.btnBack);
        }
        TextView textView = getViewId(R.id.tv_top_title);
        if (mToolbarBack == null) {
            return;
        }
        if (textView == null) {
            return;
        }
        if (!TextUtils.isEmpty(titleStr)) {
            textView.setText(titleStr);
        }
        if (isBack) {
            mToolbarBack.setVisibility(View.VISIBLE);
            mToolbarBack.setOnClickListener(this);
        } else {
            LogUtils.i("1", "configViews11");
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
        if (textView == null) {
            return;
        }
        textView.setVisibility(View.VISIBLE);
        textView.setText(str);
        if (!TextUtils.isEmpty(str)) {
            if (resourcesId != 0) {
                textView.setCompoundDrawablePadding(5);
                Drawable image = getResources().getDrawable(resourcesId);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());//非常重要，必须设置，否则图片不会显示
                textView.setCompoundDrawables(null, null, image, null);
            } else {
                textView.setCompoundDrawables(null, null, null, null);
            }
        }
    }

    /**
     * 加载当前登录的用户
     *
     * @param imageView
     */
    public static void loadSelfHeadImage(ImageView imageView) {
        if (TextUtils.isEmpty(CreditCardApplication.getsInstance().token)) {
            //            loadHeadImage(USER_IMGURL,QiquanApplication.account.user.userImage, imageView);
        } else {//游客登录
            imageView.setImageResource(R.mipmap.default_head);
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
        parentView.removeAllViews();
        View views = inflater.inflate(R.layout.common_empty_view, null);
        parentView.addView(views);
    }

    /**
     * 显示成功页面
     */
    public void onSuccess(int layoutId) {
        parentView.removeAllViews();
        View views = inflater.inflate(layoutId, null);
        parentView.addView(views);
    }


    /**
     * 检测网络是否连接
     *
     * @return ture 为连接 false 为未连接
     */
    public boolean checkNetworkState() {
        boolean flag = false;
        //得到网络连接信息
        manager = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
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
    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i("1", "getCompressPath0=" + requestCode);
    }*/

    public void showMessage(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            ToastUtils.showToast(msg);
        }
    }

    public void setChecked(int id, boolean checked) {
        View view = this.parentView.findViewById(id);
        if (view != null) {
            if (view instanceof CheckBox) {
                ((CheckBox) view).setChecked(checked);
            } else if (view instanceof Switch) {
                ((Switch) view).setChecked(checked);
            }
        }
    }

    public boolean getChecked(int id) {
        View view = this.parentView.findViewById(id);
        if (view != null) {
            if (view instanceof CheckBox) {
                return ((CheckBox) view).isChecked();
            } else if (view instanceof Switch) {
                return ((Switch) view).isChecked();
            }
        }
        return false;
    }

    public void start2Activity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        InputUtils.close(mActivity);
        //内存检测
        //        RefWatcher refWatcher = CreditCardApplication.getRefWatcher(getActivity());
        //        refWatcher.watch(this);
    }
}