package com.szinternet.crm.ui.grade;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.adapter.MyGradeListAdapter;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.MyGradeBean;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MyGradeActivity extends BaseActivity<MyGradePresenter> implements MyGradeContract.View {


    @BindView(R.id.lv_list)
    ListView mLvList;
    private MyGradeListAdapter mMyGradeListAdapter;
    private View mHeadView;
    private TextView myGrade;

    @Override
    public int getLayoutId() {
        return R.layout.activity_grade;
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
        mHeadView = LayoutInflater.from(this).inflate(R.layout.header_grade, null);
        myGrade = (TextView) mHeadView.findViewById(R.id.my_grade);
        mLvList.addHeaderView(mHeadView);
        mPresenter.myRate();
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "我的等级");
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }


    @Override
    public void start2Activity(Class c) {
        startActivity(new Intent(this, c));
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onSuccess(MyGradeBean httpResult) {
        super.dismissDialog();
        if (httpResult != null && httpResult.upClass != null && !httpResult.upClass.isEmpty()) {
            initGradeData(httpResult);
        }
    }

    private void initGradeData(MyGradeBean httpResult) {
        mMyGradeListAdapter = new MyGradeListAdapter(this, httpResult.upClass);
        mLvList.setAdapter(mMyGradeListAdapter);
        Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.menu_me_myrank_member);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        myGrade.setCompoundDrawables(null, drawable, null, null);
        myGrade.setText(String.format(getString(R.string.my_grade), httpResult.myGrade));
    }

    @Override
    public void showLoadDialog() {
        super.showDialog();
    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

}
