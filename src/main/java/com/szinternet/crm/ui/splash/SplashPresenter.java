package com.szinternet.crm.ui.splash;

import android.content.Context;
import android.text.TextUtils;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.GlobalAttribute;
import com.szinternet.crm.databean.LoginResultBean;
import com.szinternet.crm.ui.GuideActivity;
import com.szinternet.crm.ui.login.LoginActivity;
import com.szinternet.crm.ui.main.MainActivity;
import com.szinternet.crm.utils.CommonUtil;
import com.szinternet.crm.utils.DESUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.RequestBody;

import static com.szinternet.crm.Constant.DES_KEY;
import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class SplashPresenter extends RxPresenter<SplashContract.View> implements SplashContract.Presenter<SplashContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject
    public SplashPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }

    public void login() {
        String userPhone = (String) CommonUtil.get4SP(GlobalAttribute.LOGIN_NAME, "");
        String password = (String) CommonUtil.get4SP(GlobalAttribute.LOGIN_PASSWORD, "");
        Map<String, RequestBody> params = new HashMap<>();
        params.put("username", netApi.convertToRequestBody(userPhone));
        params.put("password", netApi.convertToRequestBody(DESUtils.decode(password, DES_KEY)));
        params.put("client", netApi.convertToRequestBody("0"));//android传0
        netApi.login(params)
                .compose(RxSchedulers.<BaseBean<LoginResultBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<LoginResultBean>(mView, mActivity, new HttpObserverInterface<LoginResultBean>() {
                    @Override
                    public void onSuccess(BaseBean<LoginResultBean> httpResult) {
                        CreditCardApplication.getsInstance().account = httpResult.getData();
                        CreditCardApplication.getsInstance().setToken(httpResult.getData().token);
                        mView.onSuccess();
                    }

                    @Override
                    public void onError(String msg, int code) {
                        mView.start2Activity(LoginActivity.class);
                    }
                }));
    }

    /**
     * 检测是否第一次启动
     */
    @Override
    public void checkIsFirstIn(Context context) {
        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                boolean isFirstIn = (Boolean) CommonUtil.get4SP(GlobalAttribute.FIRST_IN, true);
                if (!e.isDisposed()) {
                    e.onNext(isFirstIn);
                    e.onComplete();
                }
            }
        }).subscribe(new Observer<Boolean>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    mView.start2Activity(GuideActivity.class);
                } else {
                    jump2Activity();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 决定跳转的界面
     */
    public void jump2Activity() {
        Observable.timer(3, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).flatMap(new Function<Long, ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> apply(Long aLong) throws Exception {
                String userPhone = (String) CommonUtil.get4SP(GlobalAttribute.LOGIN_NAME, "");
                String password = (String) CommonUtil.get4SP(GlobalAttribute.LOGIN_PASSWORD, "");
                if (TextUtils.isEmpty(userPhone) || TextUtils.isEmpty(password)) {
                    return Observable.just(false);
                } else {
                    return Observable.just(true);
                }
            }
        }).subscribe(new Consumer<Boolean>() {

            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    login();
                } else {
                    //                            mView.start2Activity(LoginActivity.class);
                    mView.start2Activity(MainActivity.class);
                }

            }
        });
    }
}
