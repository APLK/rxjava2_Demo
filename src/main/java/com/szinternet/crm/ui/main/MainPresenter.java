package com.szinternet.crm.ui.main;

import android.content.Context;
import android.os.Environment;
import android.support.v4.app.FragmentManager;

import com.szinternet.crm.adapter.BasePagerAdapter;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.BaseFragment;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.VersionConfigBean;
import com.szinternet.crm.fragment.FragmentFactory;
import com.szinternet.crm.ui.login.LoginActivity;
import com.szinternet.crm.update.BgUpdate;
import com.szinternet.crm.utils.CommonUtil;
import com.szinternet.crm.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter<MainContract.View> {
    private List<BaseFragment> mFragmentList;
    private BasePagerAdapter mBasePagerAdapter;

    private NoScrollViewPager mNoScrollViewPager;

    private Context mContext;
    private NetworkApi netApi;
    private FragmentFactory mFragmentFactory;


    @Inject    //BaseRvFragment里的mPresenter建立关联
    public MainPresenter(Context mContext, NetworkApi netApi) {
        this.mContext = mContext;
        this.netApi = netApi;
    }


    @Override
    public void initAdapter(NoScrollViewPager viewPager, FragmentManager fragmentManager) {
        mNoScrollViewPager = viewPager;
        mFragmentFactory = new FragmentFactory();
        mFragmentList = new ArrayList<>();
        mFragmentList.add(mFragmentFactory.getInstance().getMainFragment());
        mFragmentList.add(mFragmentFactory.getInstance().getRepaymentFragment());
        mFragmentList.add(mFragmentFactory.getInstance().getShareFragment());
        mFragmentList.add(mFragmentFactory.getInstance().getProfitFragment());
        mFragmentList.add(mFragmentFactory.getInstance().getMineFragment());
        mBasePagerAdapter = new BasePagerAdapter(fragmentManager, mFragmentList);
        viewPager.setAdapter(mBasePagerAdapter);
        mView.initAdapter(mBasePagerAdapter);
    }

    @Override
    public void setCurrentShowingPage(int index) {
        if (index <= mFragmentList.size()) {
            mNoScrollViewPager.setCurrentItem(index, false);
        }
    }

    @Override
    public void checkNewVersion() {
        Map<String, RequestBody> params = new HashMap<>();
        params.put("clientType", netApi.convertToRequestBody("1"));
       netApi.checkVersion(params)
                .compose(RxSchedulers.<BaseBean<VersionConfigBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<VersionConfigBean>(mView, mActivity, new HttpObserverInterface<VersionConfigBean>() {
                    @Override
                    public void onSuccess(BaseBean<VersionConfigBean> httpResult) {
                        mView.onSuccess();
                    }

                    @Override
                    public void onError(String msg, int code) {
                        mView.onError(msg);
                    }
                }));
    }
/*
    @Override
    public void checkUserState() {
        Map<String, RequestBody>
                params = new HashMap<>();
        params.put("token", netApi.convertToRequestBody(TOKEN));
        params.put("friendId", netApi.convertToRequestBody("236"));

        netApi.checkUserState(params).compose(RxSchedulers.applySchedulers())
                .map(new Func1() {
                    @Override
                    public Object call(Object temp) {
                        LogUtils.e("e", "检测用户状态 : checkUserState -> " + JsonUtil.parseBeanToJson(temp));
                        return temp;
                    }
                }).subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean httpResult) {
                LogUtils.e("1", httpResult.getCode() + ",=" + httpResult.getMessage());
                if (httpResult.getCode() == 200) {
                    onSuccess(0, httpResult);
                } else {
                    onError(0, httpResult);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                LogUtils.e("用户检验错误信息：", throwable.toString());
            }
        });
    }*/

    @Override
    public void downLoadApp() {
        final String url = "https://pro-app-qn.fir.im/3c87c508fcdca4b705e89800a6d6081361be034b.apk?attname=BGAUpdateDemo_v1.0.1_debug.apk_1.0.1.apk&e=1513743719&token=LOvmia8oXF4xnLh0IdH05XMYpH6ENHNpARlmPc-T:wm7E0Hp3y6LP26kiyEao-i4xWc4=";
        BgUpdate.updateForDialog(mContext, url, Environment.getExternalStorageDirectory() + "/xiaohui.apk");
    }


    @Override
    public void onError(int type, BaseBean tHttpResult) {
        CommonUtil.clear();
        mView.start2Activity(LoginActivity.class);
    }

    @Override
    public void onSuccess(int type, BaseBean tHttpResult) {

    }

    @Override
    public BaseFragment getFragment(int index) {
        return mFragmentList.get(index);
    }
}
