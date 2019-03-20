package com.szinternet.crm.fragment;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.szinternet.crm.R;
import com.szinternet.crm.adapter.RankAdapter;
import com.szinternet.crm.base.BaseRVFragment;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.RankBean;
import com.szinternet.crm.fragment.contract.RankContract;
import com.szinternet.crm.fragment.presenter.RankPresenter;
import com.szinternet.crm.recycle.RecyclerArrayAdapter;
import com.yuyh.easyadapter.glide.GlideCircleTransform;

import java.util.List;

import static com.szinternet.crm.Url.baseUrl;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class DayRankingFragment extends BaseRVFragment<RankPresenter, RankBean.DataEntity> implements RankContract.View {

    private RequestOptions mRequestOptions;
    private RecyclerArrayAdapter.ItemView itemView;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_rank;
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
        mRequestOptions = new RequestOptions();
        mRequestOptions.placeholder(R.mipmap.default_head);
        mRequestOptions.error(R.mipmap.default_head);
        mRequestOptions.transform(new GlideCircleTransform(getActivity()));
        initAdapter(RankAdapter.class, true, false);
        onRefresh();

    }

    private void addHeader(final RankBean.DataEntity dataEntity) {
        itemView = new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.item_rank_header, parent, false);
                return headerView;
            }

            @Override
            public void onBindView(View headerView) {
                ImageView bigImg = (ImageView) headerView.findViewById(R.id.icon_big);
                TextView gradeImg = (TextView) headerView.findViewById(R.id.icon_grade);
                TextView phone = (TextView) headerView.findViewById(R.id.phone);
                Glide.with(getActivity()).load(baseUrl + dataEntity.img_url).apply(mRequestOptions).into(bigImg);
                TextView introduceCount = (TextView) headerView.findViewById(R.id.introduce_count);
                phone.setText(dataEntity.username);
                introduceCount.setText(Html.fromHtml(String.format(getActivity().getString(R.string.introduce_count), dataEntity.people)));
                TextView iconSmall = (TextView) headerView.findViewById(R.id.icon_small);
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.mipmap.menu_earnings_icon_one);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                iconSmall.setText("");
                iconSmall.setCompoundDrawables(drawable, null, null, null);
                gradeImg.setText(dataEntity.grade);
            }
        };
        mAdapter.addHeader(itemView);
    }


    @Override
    public void configViews() {

    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    public void onError() {
        loaddingError();
    }

    @Override
    public String getType() {
        return "week";
    }

    @Override
    public void onSuccess(List<RankBean.DataEntity> list) {
        if (itemView != null)
            mAdapter.removeHeader(itemView);
        mAdapter.clear();
        if (list != null && !list.isEmpty()) {
            addHeader((list.get(0)));
            list.remove(0);
        }
        mRecyclerView.setRefreshing(false);
        mAdapter.addAll(list);
    }


    @Override
    public void start2Activity(Class c) {

    }

    @Override
    public void showLoadDialog() {

    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

    @Override
    public void onItemClick(int position) {

    }


    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.loadRecord();
    }
}
