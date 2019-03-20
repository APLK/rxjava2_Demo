package com.szinternet.crm.ui.grade_update;

import android.content.Context;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.UpclassBean;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class GradeUpdatePresenter extends RxPresenter<GradeUpdateContract.View> implements GradeUpdateContract.Presenter<GradeUpdateContract.View> {

    private NetworkApi netApi;

    @Inject
    public GradeUpdatePresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
    }

    @Override
    public void upclass(String grade) {
        mView.showLoadDialog();
       netApi.upclass(CreditCardApplication.getsInstance().getToken(),grade)
                .compose(RxSchedulers.<BaseBean<UpclassBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<UpclassBean>(mView, mActivity, new HttpObserverInterface<UpclassBean>() {
                    @Override
                    public void onSuccess(BaseBean<UpclassBean> httpResult) {
                        mView.onSuccess(httpResult.getData());
                    }

                    @Override
                    public void onError(String msg, int code) {
                        mView.onError(msg);
                    }
                }));
    }
}
