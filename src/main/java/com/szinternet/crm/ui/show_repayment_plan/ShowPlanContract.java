package com.szinternet.crm.ui.show_repayment_plan;

import com.szinternet.crm.base.BaseContract;
import com.szinternet.crm.databean.RepaymentPlanBean;

import java.util.List;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public interface ShowPlanContract {
    /**
     * 视图层需要实现的接口
     */
    interface View extends BaseContract.BaseView {

        void onError(String msg);

        void onSuccess();

        void onPlanSuccess(List<RepaymentPlanBean.DataEntity> list);

        void showLoadDialog(String msg);

    }

    /**
     * 视图层和数据层需要的交互
     */
    interface Presenter<T> extends BaseContract.BasePresenter<T> {//dagger2接口,降低类之间的耦合度

        void addPay(String id, String s_time, String e_time, String pay_money, String pay_fee, String pay_list);

        void createRepaymentPlan(float epaymentMoney, float serverchange, int count, String startTime, String endTime);
    }
}
