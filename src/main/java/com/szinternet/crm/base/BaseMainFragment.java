package com.szinternet.crm.base;

import com.szinternet.crm.utils.LogUtils;

import javax.inject.Inject;



/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 * BaseRvFragment,绑定dagger2
 */
public abstract class BaseMainFragment<T extends RxPresenter> extends BaseFragment {

    @Inject
    protected T mPresenter;//表明mPresenter是需要注入到BaseRVFragment中,BaseRVFragment依赖于mPresenter

    /**
     * [此方法不可再重写]
     */
    @Override
    public void attachView() {
        LogUtils.i("1","attachView="+mPresenter.toString());
        if (mPresenter != null)
            mPresenter.attachView(this,mActivity);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null)
            mPresenter.detachView();
    }
}
