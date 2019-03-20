package com.szinternet.crm.ui.creditcard_collect;

import com.szinternet.crm.base.BaseContract;
import com.szinternet.crm.databean.AllBankBean;
import com.szinternet.crm.databean.CreditCollectInfoBean;

import java.util.List;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public interface CreditCardCollectContract {
    //RecommendContract是个接口，View是他的内部接口，这里看做View接口即可
    interface View extends BaseContract.BaseView {
        void onError();

        void onSuccess(List<AllBankBean.DataEntity> list);

        void onInfoSuccess(CreditCollectInfoBean bean);

        void showLoadDialog();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {//dagger2接口,降低类之间的耦合度

        void loadCreditList();

        void loadCreditInfo();
    }
}
