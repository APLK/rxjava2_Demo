package com.szinternet.crm.fragment.presenter;

import android.content.Context;

import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.RankBean;
import com.szinternet.crm.fragment.contract.RankContract;

import javax.inject.Inject;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class RankPresenter extends RxPresenter<RankContract.View> implements RankContract.Presenter<RankContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject    //BaseRvFragment里的mPresenter建立关联
    public RankPresenter(Context mContext, NetworkApi netApi) {
        this.mContext = mContext;
        this.netApi = netApi;
    }


    @Override
    public void loadRecord() {
      /*  Subscription subscribe = Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).flatMap(new Func1<Long, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(Long aLong) {
                return Observable.just(true);
            }
        }).subscribe(new Action1<Boolean>() {

            @Override
            public void call(Boolean isLogin) {
                LogUtils.i("1", "isLogin=" + isLogin);
                List<RankBean> rankBean = new ArrayList<>();
                if (pageIndex < 3) {
                    rankBean.add(new RankBean("http://www.eovas-logo.net/uploadfile/2016/1208/20161208052954750.jpg", "18888888881", 10, 0, 1));
                    rankBean.add(new RankBean("http://pic34.photophoto.cn/20150226/0007019997634366_b.jpg", "18888888882", 10, 2, 2));
                    rankBean.add(new RankBean("http://img5.imgtn.bdimg.com/it/u=10827124,3714635719&fm=26&gp=0.jpg", "18888888883", 13, 1, 3));
                    rankBean.add(new RankBean("http://www.eovas-logo.net/uploadfile/2016/1208/20161208052954750.jpg", "18888888884", 16, 3, 4));
                    rankBean.add(new RankBean("http://pic34.photophoto.cn/20150226/0007019997634366_b.jpg", "18888888885", 10, 4, 5));
                    rankBean.add(new RankBean("http://img5.imgtn.bdimg.com/it/u=10827124,3714635719&fm=26&gp=0.jpg", "18888888886", 11, 2, 6));
                    rankBean.add(new RankBean("http://www.eovas-logo.net/uploadfile/2016/1208/20161208052954750.jpg", "18888888887", 17, 1, 7));
                    rankBean.add(new RankBean("http://pic34.photophoto.cn/20150226/0007019997634366_b.jpg", "18888888888", 19, 4, 8));
                    rankBean.add(new RankBean("http://img5.imgtn.bdimg.com/it/u=10827124,3714635719&fm=26&gp=0.jpg", "18888888889", 1, 3, 9));
                }
                mView.onSuccess(rankBean, pageIndex == 1 ? true : false);
            }
        });
        addSubscrebe(subscribe);*/
         netApi.rankList(mView.getType())
                .compose(RxSchedulers.<BaseBean<RankBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<RankBean>(mView, mActivity, new HttpObserverInterface<RankBean>() {
                    @Override
                    public void onSuccess(BaseBean<RankBean> httpResult) {
                        mView.onSuccess(httpResult.getData().data);
                    }

                    @Override
                    public void onError(String msg, int code) {
                        mView.onError();
                    }
                }));
    }


}
