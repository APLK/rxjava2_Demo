package com.szinternet.crm.ui.quick_payment;

import android.widget.TextView;

import com.szinternet.crm.base.BaseContract;
import com.szinternet.crm.databean.CollectInfoBean;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public interface QuickPaymentContract {
    /**
     * 视图层需要实现的接口
     */
    interface View extends BaseContract.BaseView {

        void onError(String msg);

        void onSuccess();

        void onCollectInfoSuccess(CollectInfoBean bean);

        void showLoadDialog();

        String getVer();

        TextView getSendCodeView();
    }

    /**
     * 视图层和数据层需要的交互
     */
    interface Presenter<T> extends BaseContract.BasePresenter<T> {//dagger2接口,降低类之间的耦合度

        void getVerCode(String user_bank_id,
                        String rc_bank_id, String money);

        void cashPay(String rc_bank_id, String user_bank_id, String money,
                     String pay_order, String order);

        boolean checkParamIsVail(String user_bank_id,
                                 String rc_bank_id, String money,
                                 String pay_order, String order,int type);
    }
}
