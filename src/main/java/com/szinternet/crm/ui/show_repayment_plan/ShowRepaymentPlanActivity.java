package com.szinternet.crm.ui.show_repayment_plan;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.szinternet.crm.R;
import com.szinternet.crm.adapter.ShowRepaymentPlanListAdapter;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.RepaymentPlanBean;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.utils.LogUtils;
import com.szinternet.crm.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 * 还款记录固定11条  消费记录 固定22调,一共33条记录
 * 消费总金额=还款总金额+手续费
 * 还款第一笔 直接加上手续费
 * 还款金额在平均值(+-)money*0.008的基础上上下浮动的,
 * 时间是按照:
 * 如果时间间隔小于11天就先还时间间隔天数,多的未还笔数就直接从第一天依次重新在添加的
 * 如果时间间隔大于11天就随机从间隔天数取11天不重复的日期还款的
 * <p>
 * 5<=结束时间-开始时间<=最后还款日-账单日
 * <p>
 * 生成的json数据格式
 * {
 * "data": [
 * {
 * "type": "1",
 * "times": "1",
 * "money": "20",
 * "create_time": "2018-01-19"
 * },
 * {
 * "type": "1",
 * "times": "2",
 * "money": "20",
 * "create_time": "2018-01-19"
 * },
 * {
 * "type": "1",
 * "times": "3",
 * "money": "20",
 * "create_time": "2018-01-19"
 * }
 * ]
 * }
 */
public class ShowRepaymentPlanActivity extends BaseActivity<ShowPlanPresenter> implements ShowPlanContract.View {

    @BindView(R.id.lv_list)
    ListView mLvList;
    @BindView(R.id.bt_submit)
    Button mBtSubmit;
    private ShowRepaymentPlanListAdapter mPlanListAdapter;
    private String startTime;
    private String endTime;
    private float serverchange;
    private float repaymentMoney;
    private String mJsonString;
    private String mID;

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
        startTime = getIntent().getStringExtra("startTime");
        mID = getIntent().getStringExtra("ID");
        endTime = getIntent().getStringExtra("endTime");
        repaymentMoney = getIntent().getFloatExtra("repaymentMoney", 0);
        serverchange = getIntent().getFloatExtra("serverchange", 0);
        mPresenter.createRepaymentPlan(repaymentMoney, serverchange, 11, startTime, endTime);
    }


    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "预览还款计划");
        EventHelper.click(this, mBtSubmit);
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                if (!TextUtils.isEmpty(mJsonString)) {
                    mPresenter.addPay(mID, startTime, endTime, repaymentMoney + "", serverchange + "", mJsonString);
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void start2Activity(Class c) {
    }

    @Override
    public void showLoadDialog() {
        super.showDialog();
        mBtSubmit.setEnabled(false);
    }

    @Override
    public void showLoadDialog(String loadingText) {
        super.showDialog(loadingText);
    }

    @Override
    public void onError(String msg) {
        mBtSubmit.setEnabled(true);
    }

    @Override
    public void onSuccess() {
        mBtSubmit.setEnabled(true);
        finish();
    }

    @Override
    public void onPlanSuccess(List<RepaymentPlanBean.DataEntity> list) {
        dismissDialog();
        LogUtils.i("DataEntity=" + list.size());
        if (list == null || list.isEmpty() || list.size() != 33) {
            ToastUtils.showToast("生成还款计划预览失败");
        } else {
            mBtSubmit.setVisibility(View.VISIBLE);
            mPlanListAdapter = new ShowRepaymentPlanListAdapter(this, list);
            mLvList.setAdapter(mPlanListAdapter);
            RepaymentPlanBean repaymentPlanBean = new RepaymentPlanBean();
            repaymentPlanBean.data = list;
            //生成json
            Gson g = new Gson();
            mJsonString = g.toJson(repaymentPlanBean);
            LogUtils.i("1", "jsonString=" + mJsonString);
        }
    }

}
