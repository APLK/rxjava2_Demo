package com.szinternet.crm.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.szinternet.crm.R;
import com.szinternet.crm.adapter.ProxyListAdapter;
import com.szinternet.crm.base.BaseMainFragment;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.MembersCountBean;
import com.szinternet.crm.databean.ProxyBean;
import com.szinternet.crm.fragment.contract.MemberCountContract;
import com.szinternet.crm.fragment.presenter.MemberCountPresenter;
import com.szinternet.crm.ui.members.MembersActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class SubordinateFragment extends BaseMainFragment<MemberCountPresenter> implements MemberCountContract.View {
    @BindView(R.id.lv_list)
    ListView mLvList;
    private int imgResIds[] = {R.mipmap.subordinate1, R.mipmap.subordinate2,
            R.mipmap.subordinate3};
    private ProxyListAdapter mProxyListAdapter;
    private ArrayList<ProxyBean> mProxyBeanList;
    private String[] mDesrs;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_proxy;
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
        mDesrs = getActivity().getResources().getStringArray(R.array.subordinate_rank_name);
        mProxyBeanList = new ArrayList<>();
        int i = 0;
        for (String str : mDesrs) {
            mProxyBeanList.add(new ProxyBean(imgResIds[i], str, 0));
            i++;
        }
        mProxyListAdapter = new ProxyListAdapter(getActivity(), mProxyBeanList);
        mLvList.setAdapter(mProxyListAdapter);
        mLvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MembersActivity.class);
                intent.putExtra("position", String.valueOf(position + 1));
                intent.putExtra("type", 1);
                intent.putExtra("name", mDesrs[position]);
                startActivity(intent);
            }
        });
        mPresenter.mychildren();
    }


    @Override
    public void onError(String msg) {

    }

    @Override
    public void onSuccess(MembersCountBean bean) {
        if (bean != null) {
            for (int i = 0; i < mProxyBeanList.size(); i++) {
                if (i == 0) {
                    mProxyBeanList.get(i).setCount(bean.one_count);
                } else if (i == 1) {
                    mProxyBeanList.get(i).setCount(bean.tow_count);
                } else {
                    mProxyBeanList.get(i).setCount(bean.three_count);
                }
            }
            mProxyListAdapter.notifyDataSetChanged();
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
    public void showLoadDialog(String loadingText) {

    }

}
