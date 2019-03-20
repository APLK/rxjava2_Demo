package com.szinternet.crm.base;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 * 基于Rx的Presenter封装,控制订阅的生命周期
 * unsubscribe() 这个方法很重要，
 * 因为在 subscribe() 之后， Observable 会持有 Subscriber 的引用，
 * 这个引用如果不能及时被释放，将有内存泄露的风险。
 */
public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {
    private CompositeDisposable mCompositeDisposable;

    protected T mView;
    public BaseActivity mActivity;

    protected void unSubscribe() {
        if (null != mCompositeDisposable) {
            mCompositeDisposable.dispose();
        }
    }

    protected void addSubscrebe(Disposable disposable) {
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void attachView(T view, BaseActivity context) {
        this.mView = view;
        this.mActivity = context;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }

    protected void finishSelf() {
        mActivity.finish();
    }

}
