package com.szinternet.crm.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.adapter.HomeHeadAdapter;
import com.szinternet.crm.base.BaseMainFragment;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.HomeHeadBean;
import com.szinternet.crm.databean.MyRepaymentCardBean;
import com.szinternet.crm.databean.SpendRecordBean;
import com.szinternet.crm.eventbus.RefreshEvent;
import com.szinternet.crm.fragment.contract.RepaymentCardContract;
import com.szinternet.crm.fragment.presenter.RepaymentCardPresenter;
import com.szinternet.crm.marqueeview.MarqueeView;
import com.szinternet.crm.ui.code.CodeGenerateActivity;
import com.szinternet.crm.ui.creditcard_collect.CreditCardCollectActivity;
import com.szinternet.crm.ui.grade.MyGradeActivity;
import com.szinternet.crm.ui.guide.NoviceGuideActivity;
import com.szinternet.crm.ui.insurance.InsuranceActivity;
import com.szinternet.crm.ui.summary_repayment.SummaryRepaymentActivity;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.view.CustomGridView;
import com.szinternet.crm.view.DotImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MainFragment extends BaseMainFragment<RepaymentCardPresenter> implements RepaymentCardContract.View {

    @BindView(R.id.iv_speaker)
    ImageView mIvSpeaker;
    @BindView(R.id.marqueeView)
    MarqueeView mMarqueeView;
    @BindView(R.id.iv_message)
    DotImageView mIvMessage;
    @BindView(R.id.view_marqueeView)
    RelativeLayout mViewMarqueeView;
    @BindView(R.id.gridview)
    CustomGridView mGridview;
    @BindView(R.id.img_bank_logo)
    ImageView mImgBankLogo;
    @BindView(R.id.tv_bank_name)
    TextView mTvBankName;
    @BindView(R.id.tv_start)
    TextView mTvStart;
    @BindView(R.id.tv_credit_line)
    TextView mTvCreditLine;
    @BindView(R.id.tv_state_date)
    TextView mTvStateDate;
    @BindView(R.id.tv_repay_date)
    TextView mTvRepayDate;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.img_start)
    ImageView mImgStart;
    @BindView(R.id.tv_plan_status)
    TextView mTvPlanStatus;
    @BindView(R.id.banner_guide_content)
    BGABanner mBannerGuideContent;
    @BindView(R.id.tv_collect)
    TextView mTvCollect;
    @BindView(R.id.tv_repayment)
    TextView mTvRepayment;
    private HomeHeadAdapter homeHeadAdapter;
    private int imgResIds[] = {R.mipmap.main_reduce_rate, R.mipmap.main_plan,
            R.mipmap.main_share, R.mipmap.main_help, R.mipmap.main_insurance};
    private String[] mDesrs;
    private List<String> mInfo;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
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
        mPresenter.loadPlanRecord(1);
       /* mRequestOptions = new RequestOptions();
        mRequestOptions.placeholder(R.mipmap.no_photo);
        mRequestOptions.error(R.mipmap.no_photo);
        mRequestOptions.centerCrop();
        mRequestOptions.transform(new CenterCrop());
        mRequestOptions.dontAnimate();
        mBannerGuideContent.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(getActivity()).load(model).apply(mRequestOptions).into(itemView);
            }
        });

        mBannerGuideContent.setData(Arrays.asList("http://img.ivsky.com/img/tupian/pre/201709/17/zhenyeshu.jpg",
                "http://img.ivsky.com/img/tupian/pre/201709/17/zhenyeshu-002.jpg",
                "http://img.ivsky.com/img/tupian/pre/201709/17/zhenyeshu-005.jpg"),
                Arrays.asList("", "", ""));

        mBannerGuideContent.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                Toast.makeText(banner.getContext(), "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });*/
    }


    @Override
    public void configViews() {

    }

    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.iv_message:
//                start2Activity(MsgCenterActivity.class);
                break;
            case R.id.tv_collect:
                start2Activity(CreditCardCollectActivity.class);
                break;
            case R.id.tv_repayment:
                EventBus.getDefault().post(new RefreshEvent());
                break;
            case R.id.img_start:
                EventBus.getDefault().post(new RefreshEvent());
                break;
            default:
                break;
        }
    }

    @Override
    protected void lazyLoadData() {
        mIvMessage.setVisibility(View.GONE);
        EventHelper.click(this, mIvMessage, mTvCollect, mTvRepayment, mImgStart);
        mInfo = new ArrayList<>();
        mInfo.add("温馨提示:恭喜您,成功登录此应用!");
        mInfo.add("部分功能暂未开放,敬请期待!");
        mInfo.add("谢谢您的支持,我们将马上推出更符合您口味的应用功能~");
        mInfo.add("好消息,新版APP马上就要上线啦,敬请期待哦!");
        mInfo.add("您的头像已通过系统审核!");
        mInfo.add("应用又推出新活动了,快来围观吧,参与分享送好礼,礼品不断,惊喜不断...");
//        mMarqueeView.startWithList(mInfo);
        // 在代码里设置自己的动画
        mMarqueeView.startWithList(mInfo, R.anim.anim_bottom_in, R.anim.anim_top_out);

        homeHeadAdapter = new HomeHeadAdapter(getActivity());
        mDesrs = getActivity().getResources().getStringArray(R.array.main_grid_name);
        ArrayList<HomeHeadBean> homeHeadBeans = new ArrayList<HomeHeadBean>();
        int i = 0;
        for (String str : mDesrs) {
            homeHeadBeans.add(new HomeHeadBean(imgResIds[i], str));
            i++;
        }
        homeHeadAdapter.setList(homeHeadBeans);
        mGridview.setAdapter(homeHeadAdapter);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        start2Activity(MyGradeActivity.class);
                        break;
                    case 1:
                        start2Activity(SummaryRepaymentActivity.class);
                        break;
                    case 2:
                        start2Activity(CodeGenerateActivity.class);
                        break;
                    case 3:
                        start2Activity(NoviceGuideActivity.class);
                        break;
                    case 4:
                        start2Activity(InsuranceActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });
    }


    @Override
    public void onError() {

    }

    @Override
    public void onSuccess(List<MyRepaymentCardBean.DataEntity> list) {

    }

    @Override
    public void onPlanSuccess(List<SpendRecordBean.DataEntity> list) {
        if (list != null && !list.isEmpty()) {
            mTvBankName.setText(list.get(0).bank_name + "(" + list.get(0).nub + ")");
            mTvStateDate.setText(list.get(0).s_time);
            mTvRepayDate.setText(list.get(0).e_time);
            if (!TextUtils.isEmpty(list.get(0).type)) {
                switch (Integer.parseInt(list.get(0).type)) {
                    case 0:
                        mTvPlanStatus.setText("还款中");
                        mTvStart.setText("还款中");
                        break;
                    case 1:
                        mTvPlanStatus.setText("已完成");
                        mTvStart.setText("已完成");
                        break;
                    case 2:
                        mTvPlanStatus.setText("失败");
                        mTvStart.setText("失败");
                        break;
                }
            }
            mTvCreditLine.setText(list.get(0).pay_money);
        }
    }

    @Override
    public void start2Activity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        startActivity(intent);
    }


    @Override
    public void showLoadDialog() {

    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

}
