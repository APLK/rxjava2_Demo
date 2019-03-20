package com.szinternet.crm.module;


import com.szinternet.crm.api.HeaderInterceptor;
import com.szinternet.crm.api.LoggingInterceptor;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.utils.LogUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 * module类,通过appcomponent与inject建立联系
 * 提供OkHttpClient网络对象
 */
@Module
public class CreditCardApiModule {

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient() {

        LoggingInterceptor logging = new LoggingInterceptor(new MyLog());
        logging.setLevel(LoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(15 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(15 * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true) // 失败重发
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(logging);//特别注意!!!下载大文件时千万不要打开此行代码,否则会导致OOM
        return builder.build();
    }

    @Singleton
    @Provides
    protected NetworkApi provideNetworkService(OkHttpClient okHttpClient) {
        return NetworkApi.getInstance(okHttpClient);
    }

    public static class MyLog implements LoggingInterceptor.Logger {
        @Override
        public void log(int type, String message) {
            if (type == LoggingInterceptor.IMPORTMSGTYPE) {
                LogUtils.i("oklog: " + message);
            } else {
                LogUtils.d("oklog: " + message);
            }
        }
    }

}