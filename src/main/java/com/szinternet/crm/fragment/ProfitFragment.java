package com.szinternet.crm.fragment;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.szinternet.crm.R;
import com.szinternet.crm.adapter.BasePagerAdapter;
import com.szinternet.crm.base.BaseFragment;
import com.szinternet.crm.base.BaseMainFragment;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.FrCashIndexBean;
import com.szinternet.crm.databean.MyGainBean;
import com.szinternet.crm.databean.RankOneBean;
import com.szinternet.crm.dialog.AlertDialog;
import com.szinternet.crm.fragment.contract.ProfitContract;
import com.szinternet.crm.fragment.presenter.ProfitPresenter;
import com.szinternet.crm.ui.bindidentity.BindIdentityActivity;
import com.szinternet.crm.ui.cash.CashActivity;
import com.szinternet.crm.ui.rank.RankActivity;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.view.PagerSlidingTabStrip;
import com.yuyh.easyadapter.glide.GlideCircleTransform;

import java.util.ArrayList;

import butterknife.BindView;

import static com.szinternet.crm.Url.baseUrl;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class ProfitFragment extends BaseMainFragment<ProfitPresenter> implements ProfitContract.View {
    @BindView(R.id.toolbar)
    LinearLayout mToolbar;
    @BindView(R.id.profit)
    TextView mProfit;
    @BindView(R.id.divide_profits)
    TextView mDivideProfits;
    @BindView(R.id.promote_profits)
    TextView mPromoteProfits;
    @BindView(R.id.rank1)
    ImageView mRank1;
    @BindView(R.id.rank2)
    ImageView mRank2;
    @BindView(R.id.rank3)
    ImageView mRank3;
    @BindView(R.id.rank_view)
    RelativeLayout mRankView;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip mTabs;
    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.tv_divide_profits)
    TextView mTvDivideProfits;
    @BindView(R.id.tv_promote_profits)
    TextView mTvPromoteProfits;
    private FragmentManager fm;
    private int mType = 0;
    private RequestOptions mRequestOptions;
    private ArrayList<String> mList;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_profit;
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
        mList = new ArrayList<>();
        mList.add("我的代理商");
        mList.add("我的下级");

        /**
         * 此处需要特别注意:在fragment嵌套的子fragment页面中如果使用getActivity().getSupportFragmentManager()
         * 来回切换页面会出现数据丢失(页面显示空白)的现象,所以必须使用
         * getChildFragmentManager();
         * 不能将初始化viewpager放在懒加载中,不然也会出现空白界面的情况
         * 原因:第二次加载的时候重复调用了onCreateView()这个方法，重新new了一个FragmentPagerAdapter导致子fragment不显示
         */
        fm = this.getChildFragmentManager();
        mPresenter.initAdapter(mPager, fm, mList);
        mTabs.setViewPager(mPager);
    }


    @Override
    public void configViews() {
        mRequestOptions = new RequestOptions();
        mRequestOptions.placeholder(R.mipmap.default_head);
        mRequestOptions.error(R.mipmap.default_head);
        mRequestOptions.transform(new GlideCircleTransform(getActivity()));
    }

    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.rank_view:
                start2Activity(RankActivity.class);
                break;
            case R.id.divide_profits:
                mType = 0;
                mPresenter.frcashIndex();
                break;
            case R.id.promote_profits:
                mType = 1;
                mPresenter.frcashIndex();
                break;
            default:
                break;
        }
    }

    @Override
    protected void lazyLoadData() {
        setToolbar(mToolbar);
        initTitleBar(false, R.string.my_profit);
        EventHelper.click(this, mRankView, mDivideProfits, mPromoteProfits);

        mPresenter.getMyGain();
        mPresenter.rankOne();
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void go2Bind() {
        new AlertDialog(getActivity()).builder().setTitle("提示")
                .setMsg("您当前并未实名认证(绑定银行卡),是否实名认证?\n")
                .setPositiveButton("是", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        start2Activity(BindIdentityActivity.class);
                    }
                }).setNegativeButton("否", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onMyGainSuccess(MyGainBean bean) {
        if (bean != null) {
            mProfit.setText(String.valueOf(bean.all_money));
            mTvDivideProfits.setText("分润利益:" + bean.fenrun);
            mTvPromoteProfits.setText("推广利益:" + bean.tuiguang);
        }
    }

    @Override
    public void onCashSuccess(FrCashIndexBean bean) {
        Intent intent = new Intent(getActivity(), CashActivity.class);
        intent.putExtra("item", bean);
        intent.putExtra("type", mType);
        startActivity(intent);
    }

    @Override
    public void onRankOneSuccess(RankOneBean bean) {
        if (bean != null) {
            if (!TextUtils.isEmpty(bean.week)) {
                mRequestOptions.placeholder(R.mipmap.menu_earnings_icon_one);
                mRequestOptions.error(R.mipmap.menu_earnings_icon_one);
                Glide.with(getActivity()).load(baseUrl + bean.week).apply(mRequestOptions).into(mRank1);
            }
            if (!TextUtils.isEmpty(bean.day)) {
                mRequestOptions.placeholder(R.mipmap.menu_earnings_icon_two);
                mRequestOptions.error(R.mipmap.menu_earnings_icon_two);
                Glide.with(getActivity()).load(baseUrl + bean.day).apply(mRequestOptions).into(mRank2);
            }
            if (!TextUtils.isEmpty(bean.mon)) {
                mRequestOptions.placeholder(R.mipmap.menu_earnings_icon_three);
                mRequestOptions.error(R.mipmap.menu_earnings_icon_three);
                Glide.with(getActivity()).load(baseUrl + bean.mon).apply(mRequestOptions).into(mRank3);
            }
        }
    }

    @Override
    public void start2Activity(Class c) {
        startActivity(new Intent(getActivity(), c));
    }

    @Override
    public void showLoadDialog() {
        super.showDialog();
    }

    @Override
    public BaseFragment getFragment(int index) {
        return mPresenter.getFragment(index);
    }

    @Override
    public void initAdapter(BasePagerAdapter basePagerAdapter) {
        mPager.setAdapter(basePagerAdapter);
    }

    @Override
    public void showLoadDialog(String loadingText) {

    }
}
