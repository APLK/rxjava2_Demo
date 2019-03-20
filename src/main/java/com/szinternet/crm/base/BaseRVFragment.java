package com.szinternet.crm.base;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import com.szinternet.crm.R;
import com.szinternet.crm.recycle.EasyRecyclerView;
import com.szinternet.crm.recycle.RecyclerArrayAdapter;
import com.szinternet.crm.recycle.decoration.DividerDecoration;
import com.szinternet.crm.recycle.swipe.SwipeRefreshLayout;
import com.szinternet.crm.utils.NetworkUtils;

import java.lang.reflect.Constructor;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * BaseRvFragment,绑定dagger2
 */
public abstract class BaseRVFragment<T1 extends BaseContract.BasePresenter, T2> extends BaseFragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener {

    @Inject
    protected T1 mPresenter;//表明mPresenter是需要注入到BaseRVFragment中,BaseRVFragment依赖于mPresenter

    @BindView(R.id.recyclerview)
    protected EasyRecyclerView mRecyclerView;
    protected RecyclerArrayAdapter<T2> mAdapter;

    protected int pageIndex = 1;
    protected int pageSize = 10;

    /**
     * [此方法不可再重写]
     */
    @Override
    public void attachView() {
        if (mPresenter != null)
            mPresenter.attachView(this, mActivity);
    }

    protected void initAdapter(boolean refreshable, boolean loadmoreable) {
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getSupportActivity()));
            DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor(mActivity, R.color.common_divider_narrow), 1, 0, 0);
            itemDecoration.setDrawLastItem(false);
            mRecyclerView.addItemDecoration(itemDecoration);

            mRecyclerView.setAdapterWithProgress(mAdapter);
        }

        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(this);
            mAdapter.setError(R.layout.common_error_view, new RecyclerArrayAdapter.OnErrorListener() {
                @Override
                public void onErrorShow() {
                    mAdapter.resumeMore();
                }

                @Override
                public void onErrorClick() {
                    if (!NetworkUtils.isConnected(getActivity())) {
                        loaddingError();
                        return;
                    }
                    mAdapter.resumeMore();
                }
            });
            if (loadmoreable) {
                mAdapter.setMore(R.layout.common_more_view, this);
                mAdapter.setNoMore(R.layout.common_nomore_view);
            }
            if (refreshable && mRecyclerView != null) {
                mRecyclerView.setRefreshListener(this);
            }

        }
    }

    protected void initAdapter(Class<? extends RecyclerArrayAdapter<T2>> clazz, boolean refreshable, boolean loadmoreable) {
        mAdapter = (RecyclerArrayAdapter<T2>) createInstance(clazz);
        initAdapter(refreshable, loadmoreable);
    }

    public Object createInstance(Class<?> cls) {
        Object obj;
        try {
            Constructor c1 = cls.getDeclaredConstructor(Context.class);
            c1.setAccessible(true);
            obj = c1.newInstance(mActivity);
        } catch (Exception e) {
            obj = null;
        }
        return obj;
    }

    @Override
    public void onLoadMore() {
        pageIndex++;
       /* if (!NetworkUtils.isConnected(getActivity())) {
            loaddingError();
//            mAdapter.pauseMore();
            return;
        }*/
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
       /* if (!NetworkUtils.isConnected(getActivity())) {
//            mAdapter.pauseMore();
            loaddingError();
            return;
        }*/
    }

    protected void loaddingError() {
        if (mAdapter.getCount() < 1) { // 说明缓存也没有加载，那就显示errorview，如果有缓存，即使刷新失败也不显示error
            mAdapter.clear();
        }
        mAdapter.pauseMore();
        mRecyclerView.setRefreshing(false);
        //        mRecyclerView.showTipViewAndDelayClose("似乎没有网络哦");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null)
            mPresenter.detachView();
    }
}
