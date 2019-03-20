package com.szinternet.crm.api;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Retrofit2 Cookie拦截器。用于保存和设置Cookies
 */
public final class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
//        Request original = chain.request();
        Request.Builder builder = chain.request().newBuilder();
        Request requst = builder.addHeader("Content-type", "application/json").build();
        return chain.proceed(requst);
//        return chain.proceed(original);
    }
}
