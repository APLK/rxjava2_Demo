package com.szinternet.crm;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.view.View;

import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerAppComponent;
import com.szinternet.crm.databean.LoginResultBean;
import com.szinternet.crm.dialog.AlertDialog;
import com.szinternet.crm.module.AppModule;
import com.szinternet.crm.module.CreditCardApiModule;
import com.szinternet.crm.utils.AppUtils;
import com.szinternet.crm.utils.CrashHandler;
import com.szinternet.crm.utils.SharedPreferencesUtil;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class CreditCardApplication extends Application {

    private static CreditCardApplication sInstance;
    public LoginResultBean account;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 应用单例，方便在其它类访问
     */
    public String token = "";
    private AppComponent appComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        this.sInstance = this;
        initCompoent();//初始化注解类

        AppUtils.init(this);

        //崩溃日志初始化
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        //初始化prefs
        initPrefs();
        //初始化umeng
        initUmeng();

        //内存检测
       /* if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);*/
    }

    private void initUmeng() {
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx5dc7561a075585ae", "eeec8b30040a10be62214977bb366d13");
        PlatformConfig.setQQZone("1106718766", "HM1BZYGTFo93TLou");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    //    private RefWatcher refWatcher;

    /**
     * 内存检测
     * //     * @param context
     *
     * @return
     */
    //    public static RefWatcher getRefWatcher(Context context) {
    //        CreditCardApplication application = (CreditCardApplication) context.getApplicationContext();
    //        return application.refWatcher;
    //    }
    public static CreditCardApplication getsInstance() {
        return sInstance;
    }

    private void initCompoent() {
        appComponent = DaggerAppComponent.builder()
                .creditCardApiModule(new CreditCardApiModule())
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    /**
     * 初始化SharedPreference
     */
    protected void initPrefs() {
        SharedPreferencesUtil.init(getApplicationContext(), getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
    }

    /**
     * 显示退出应用的对话框
     *
     * @param activity
     */
    public void showExitAppDialog(final Activity activity) {
        new AlertDialog(activity).builder().setTitle(getString(R.string.alert_exit_title))
                .setMsg( getString(R.string.alert_exit_content))
                .setPositiveButton("残忍退出", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppManager.getAppManager().AppExit(activity);
                    }
                }).setNegativeButton("否", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }

}
