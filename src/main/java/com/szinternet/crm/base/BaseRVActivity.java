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

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 *
 * @param <T1>
 * @param <T2>
 */
public abstract class BaseRVActivity<T1, T2 extends RxPresenter> extends BaseActivity<T2> implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener {
    @BindView(R.id.recyclerview)
    protected EasyRecyclerView mRecyclerView;
    protected RecyclerArrayAdapter<T1> mAdapter;

    protected int pageIndex = 1;
    protected int pageSize = 10;

    protected void initAdapter(boolean refreshable, boolean loadmoreable) {
        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(this);
            mAdapter.setError(R.layout.common_error_view, new RecyclerArrayAdapter.OnErrorListener() {
                @Override
                public void onErrorShow() {
                    mAdapter.resumeMore();
                }

                @Override
                public void onErrorClick() {
                    if (!NetworkUtils.isConnected(getApplicationContext())) {
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
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            DividerDecoration itemDecoration = new DividerDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), 1, 0, 0);
            itemDecoration.setDrawLastItem(false);
            mRecyclerView.addItemDecoration(itemDecoration);

            mRecyclerView.setAdapterWithProgress(mAdapter);
        }
    }

    protected void initAdapter(Class<? extends RecyclerArrayAdapter<T1>> clazz, boolean refreshable, boolean loadmoreable) {
        mAdapter = (RecyclerArrayAdapter) createInstance(clazz);
        initAdapter(refreshable, loadmoreable);
    }

    public Object createInstance(Class<?> cls) {
        Object obj;
        try {
            Constructor c1 = cls.getDeclaredConstructor(new Class[]{Context.class});
            c1.setAccessible(true);
            obj = c1.newInstance(new Object[]{mContext});
        } catch (Exception e) {
            obj = null;
        }
        return obj;
    }

    @Override
    public void onLoadMore() {
        pageIndex++;
       /* if (!NetworkUtils.isConnected(getApplicationContext())) {
            //            mAdapter.pauseMore();
            loaddingError();
            return;
        }*/
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
      /*  if (!NetworkUtils.isConnected(getApplicationContext())) {
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
    }
}
