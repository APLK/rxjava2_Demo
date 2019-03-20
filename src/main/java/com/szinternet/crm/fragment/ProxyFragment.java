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
public class ProxyFragment extends BaseMainFragment<MemberCountPresenter> implements MemberCountContract.View {
    @BindView(R.id.lv_list)
    ListView mLvList;
    private int imgResIds[] = {R.mipmap.menu_earnings_icon_vip, R.mipmap.proxy4,
            R.mipmap.menu_earnings_icon_senior,
            R.mipmap.menu_earnings_icon_shi, R.mipmap.menu_earnings_icon_sheng,
            R.mipmap.menu_earnings_icon_partner, R.mipmap.proxy1};
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
        mDesrs = getActivity().getResources().getStringArray(R.array.proxy_rank_name);
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
                intent.putExtra("type", 0);
                intent.putExtra("name", mDesrs[position]);
                startActivity(intent);
            }
        });
        mPresenter.myagents();
    }


    @Override
    public void start2Activity(Class c) {
        startActivity(new Intent(getActivity(), c));
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onSuccess(MembersCountBean bean) {
        if (bean != null) {
            for (int i = 0; i < mProxyBeanList.size(); i++) {
                switch (i) {
                    case 0:
                        mProxyBeanList.get(i).setCount(bean.s1);
                        break;
                    case 1:
                        mProxyBeanList.get(i).setCount(bean.s2);
                        break;
                    case 2:
                        mProxyBeanList.get(i).setCount(bean.s3);
                        break;
                    case 3:
                        mProxyBeanList.get(i).setCount(bean.s4);
                        break;
                    case 4:
                        mProxyBeanList.get(i).setCount(bean.s5);
                        break;
                    case 5:
                        mProxyBeanList.get(i).setCount(bean.s6);
                        break;
                    case 6:
                        mProxyBeanList.get(i).setCount(bean.s7);
                        break;
                    default:
                        mProxyBeanList.get(i).setCount(0);
                        break;
                }
            }
            mProxyListAdapter.notifyDataSetChanged();
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
