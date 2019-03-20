package com.szinternet.crm.api;


import com.szinternet.crm.base.BaseBean;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 *
 * @param <T>
 */
public interface HttpObserverInterface<T> {
    void onSuccess(BaseBean<T> httpResult);

    void onError(String msg, int code);
}
