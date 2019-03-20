package com.szinternet.crm.ui.member_detail;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.MemberDetailBean;
import com.yuyh.easyadapter.glide.GlideCircleTransform;

import butterknife.BindView;

import static com.szinternet.crm.Url.baseUrl;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MemberDetailActivity extends BaseActivity<MemberDetailPresenter> implements MemberDetailContract.View {


    @BindView(R.id.img)
    ImageView mImg;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_grade)
    TextView mTvGrade;
    @BindView(R.id.tv_profit)
    TextView mTvProfit;
    @BindView(R.id.tv_rer)
    TextView mTvRer;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_trade_money)
    TextView mTvTradeMoney;
    @BindView(R.id.tv_divide_profits)
    TextView mTvDivideProfits;
    @BindView(R.id.tv_member_count)
    TextView mTvMemberCount;
    private String mID;
    private RequestOptions mRequestOptions;

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_detail;
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
        mID = getIntent().getStringExtra("id");
        mPresenter.memberInfo();
        if (CreditCardApplication.getsInstance().account.real.equalsIgnoreCase("have")) {
            mTvState.setText("已认证");
        } else {
            mTvState.setText("未认证");
        }
    }

    @Override
    public void configViews() {
        mRequestOptions = new RequestOptions();
        mRequestOptions.placeholder(R.mipmap.default_head);
        mRequestOptions.error(R.mipmap.default_head);
        mRequestOptions.transform(new GlideCircleTransform(this));

        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "成员详情");
    }


    @Override
    protected void processClick(View v) {
    }


    @Override
    public void start2Activity(Class c) {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public String getID() {
        return mID;
    }

    @Override
    public void onSuccess(MemberDetailBean httpResult) {
        if (httpResult != null) {
            Glide.with(mContext).load(baseUrl + httpResult.img_url).apply(mRequestOptions).into(mImg);
            mTvPhone.setText("手机号:" + httpResult.username);
            mTvGrade.setText("代理级别:" + httpResult.grade);
            mTvProfit.setText("分润贡献: ¥ " + httpResult.fenrun);
            mTvTime.setText(httpResult.create_time);
            mTvRer.setText(httpResult.parent_id);
            mTvDivideProfits.setText("¥ " + httpResult.fenrun_mon);
           /* mTvState.setText(httpResult.fenrun_mon);
            mTvTradeMoney.setText("¥ "+httpResult.create_time);
            mTvMemberCount.setText(httpResult.create_time);*/
        }
    }

    @Override
    public void showLoadDialog() {
        super.showDialog();
    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

}
