package com.szinternet.crm.ui.bindbankcode;

import android.widget.ImageView;
import android.widget.TextView;

import com.szinternet.crm.base.BaseContract;
import com.szinternet.crm.databean.AllBankBean;

import java.util.List;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public interface BindBankCodeContract {
    /**
     * 视图层需要实现的接口
     */
    interface View extends BaseContract.BaseView {

        void onError(String msg);

        void onSuccess();

        void onBankListSuccess(List<AllBankBean.DataEntity> list);

        void showLoadDialog();

        void isShowX(boolean isShow, ImageView imageView);

        String getUserName();

        String getBankCode();

        String getVer();

        String getBankName();

        String getBankType();

        String getBankBranch();

        String getNickname();

        String getRealname();

        String getIc();

        TextView getSendCodeView();

        String getPhone();
    }

    /**
     * 视图层和数据层需要的交互
     */
    interface Presenter<T> extends BaseContract.BasePresenter<T> {//dagger2接口,降低类之间的耦合度

        void bindBankCode();

        void getVerCode();

        void getAllBankList();

        boolean checkParamIsVail();

    }
}
