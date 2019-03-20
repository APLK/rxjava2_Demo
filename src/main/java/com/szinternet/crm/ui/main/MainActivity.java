package com.szinternet.crm.ui.main;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.R;
import com.szinternet.crm.adapter.BasePagerAdapter;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.base.BaseFragment;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.GlobalAttribute;
import com.szinternet.crm.eventbus.RefreshEvent;
import com.szinternet.crm.utils.CommonUtil;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.utils.ToastUtils;
import com.szinternet.crm.view.NoScrollViewPager;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.vp_container_main)
    NoScrollViewPager mVpContainerMain;
    @BindView(R.id.rb_home_main)
    RadioButton mRbHomeMain;
    @BindView(R.id.rb_home_card)
    RadioButton mRbHomeCard;
    @BindView(R.id.rb_home_pay_record)
    RadioButton mRbHomePayRecord;
    @BindView(R.id.rb_home_me)
    RadioButton mRbHomeMe;
    @BindView(R.id.btn_main_share)
    ImageView mBtnMainShare;
    @BindView(R.id.tab)
    RadioGroup mTab;
    private FragmentManager fm;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
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
        //        showDialog();
        fm = getSupportFragmentManager();
        mPresenter.initAdapter(mVpContainerMain, fm);
        EventHelper.click(this, mRbHomeMain, mRbHomeCard, mRbHomePayRecord, mRbHomeMe, mBtnMainShare);
        mVpContainerMain.setOffscreenPageLimit(1);

        EventBus.getDefault().register(this);//注册eventBus,用于接收消息

        //        mPresenter.checkNewVersion();
    }

    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.rb_home_main:
                mPresenter.setCurrentShowingPage(0);
                break;
            case R.id.rb_home_card:
                mPresenter.setCurrentShowingPage(1);
                break;
            case R.id.rb_home_pay_record:
                mPresenter.setCurrentShowingPage(3);
                break;
            case R.id.rb_home_me:
                mPresenter.setCurrentShowingPage(4);
                break;
            case R.id.btn_main_share:
                mTab.clearCheck();
                mPresenter.setCurrentShowingPage(2);
                break;
        }
    }


    @Override
    public void initAdapter(BasePagerAdapter basePagerAdapter) {
        mVpContainerMain.setAdapter(basePagerAdapter);
    }

    @Override
    public void onError(String msg) {
        if (!TextUtils.isEmpty(msg))
            ToastUtils.showLongToast(msg);
       /* mDialogUtil = new DialogUtil(this);
        mDialogUtil.updataDlg("立即更新", new DialogInterface() {
            @Override
            public void sure(Object object) {
                LogUtils.i("1", "sure");
                mDialogUtil.toastDlgdismiss();
                mPresenter.downLoadApp();
            }

            @Override
            public void cancel(Object object) {
                LogUtils.i("1", "cancel");
            }
        }, "2.0", "1.优化了一些继承设置<b>2.修复了一些bug<b>3.增加了一些功能<b>4.内存优化,细节优化");*/
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public String getUserPhone() {
        return (String) CommonUtil.get4SP(GlobalAttribute.LOGIN_NAME, "");
    }

    @Override
    public void start2Activity(Class c) {

    }

    @Override
    public BaseFragment getFragment(int index) {
        return mPresenter.getFragment(index);
    }


    @Override
    public void showLoadDialog() {

    }

    @Override
    public void showLoadDialog(String loadingText) {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            CreditCardApplication.getsInstance().showExitAppDialog(this);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        //        mPresenter.checkUserState();
        mTab.clearCheck();
        int fragmentIndex = getIntent().getIntExtra("fragmentIndex", -1);
        if (fragmentIndex != -1) {
            mVpContainerMain.setCurrentItem(fragmentIndex);
        }
        if (mVpContainerMain.getCurrentItem() == 1) {
            mRbHomeCard.setChecked(true);
            mPresenter.setCurrentShowingPage(1);
        } else if (mVpContainerMain.getCurrentItem() == 0) {
            mRbHomeMain.setChecked(true);
            mPresenter.setCurrentShowingPage(0);
        } else if (mVpContainerMain.getCurrentItem() == 2) {
            mPresenter.setCurrentShowingPage(2);
        } else if (mVpContainerMain.getCurrentItem() == 3) {
            mRbHomePayRecord.setChecked(true);
            mPresenter.setCurrentShowingPage(3);
        } else if (mVpContainerMain.getCurrentItem() == 4) {
            mRbHomeMe.setChecked(true);
            mPresenter.setCurrentShowingPage(4);
        }
        getIntent().removeExtra("fragmentIndex");
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);// must store the new intent unless getIntent() will
        // return the old one
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        //        ((MineFragment) getFragment(4)).onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册
    }

    /**
     * EventBus接收传递的消息(线程间数据的传递)
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshEvent(RefreshEvent event) {
        mRbHomeCard.setChecked(true);
        mPresenter.setCurrentShowingPage(1);
    }

}
