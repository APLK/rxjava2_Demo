package com.szinternet.crm.fragment.presenter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.adapter.BasePagerAdapter;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.BaseFragment;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.FrCashIndexBean;
import com.szinternet.crm.databean.MyGainBean;
import com.szinternet.crm.databean.RankOneBean;
import com.szinternet.crm.fragment.FragmentFactory;
import com.szinternet.crm.fragment.contract.ProfitContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class ProfitPresenter extends RxPresenter<ProfitContract.View> implements ProfitContract.Presenter<ProfitContract.View> {

    private Context mContext;
    private NetworkApi netApi;
    private ViewPager mViewPager;
    private ArrayList<BaseFragment> mFragmentList;
    private BasePagerAdapter mBasePagerAdapter;
    private FragmentFactory mFragmentFactory;


    @Inject
    public ProfitPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }


    @Override
    public void initAdapter(ViewPager viewPager, FragmentManager fragmentManager, List<String> list) {
        mViewPager = viewPager;
        mFragmentFactory = new FragmentFactory();
        mFragmentList = new ArrayList<>();
        mFragmentList.add(mFragmentFactory.getInstance().getProxyFragment());
        mFragmentList.add(mFragmentFactory.getInstance().getSubordinateFragment());
        mBasePagerAdapter = new BasePagerAdapter(fragmentManager, mFragmentList, list);
        viewPager.setAdapter(mBasePagerAdapter);
        mView.initAdapter(mBasePagerAdapter);

    }

    @Override
    public void setCurrentShowingPage(int index) {
        if (index <= mFragmentList.size()) {
            mViewPager.setCurrentItem(index);
        }
    }

    @Override
    public BaseFragment getFragment(int index) {
        return mFragmentList.get(index);
    }

    @Override
    public void frcashIndex() {
        netApi.frcashIndex(CreditCardApplication.getsInstance().getToken())
                .compose(RxSchedulers.<BaseBean<FrCashIndexBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<FrCashIndexBean>(mView, mActivity, new HttpObserverInterface<FrCashIndexBean>() {
                    @Override
                    public void onSuccess(BaseBean<FrCashIndexBean> httpResult) {
                        mView.onCashSuccess(httpResult.getData());
                    }

                    @Override
                    public void onError(String msg, int code) {
                        if (code != 500)
                            mView.go2Bind();
                    }
                }));
    }

    @Override
    public void rankOne() {
        netApi.rankOne()
                .compose(RxSchedulers.<BaseBean<RankOneBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<RankOneBean>(mView, mActivity, new HttpObserverInterface<RankOneBean>() {
                    @Override
                    public void onSuccess(BaseBean<RankOneBean> httpResult) {
                        mView.onRankOneSuccess(httpResult.getData());
                    }

                    @Override
                    public void onError(String msg, int code) {

                    }
                }));
    }

    @Override
    public void getMyGain() {
        netApi.myGain(CreditCardApplication.getsInstance().getToken())
                .compose(RxSchedulers.<BaseBean<MyGainBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<MyGainBean>(mView, mActivity, new HttpObserverInterface<MyGainBean>() {
                    @Override
                    public void onSuccess(BaseBean<MyGainBean> httpResult) {
                        mView.onMyGainSuccess(httpResult.getData());
                    }

                    @Override
                    public void onError(String msg, int code) {

                    }
                }));
    }

}
