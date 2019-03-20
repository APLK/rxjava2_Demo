package com.szinternet.crm.update;

import com.szinternet.crm.api.NetworkApiServeice;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class NetWork {

    private static NetworkApiServeice downloadApi;

    public static NetworkApiServeice getApi(ProgressListener listener) {
        if (downloadApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://your.api.url/")
                    .client(addProgressResponseListener(listener))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            downloadApi = retrofit.create(NetworkApiServeice.class);
        }
        return downloadApi;
    }

    /**
     * 包装OkHttpClient，用于下载文件的回调
     *
     * @param progressListener 进度回调接口
     * @return 包装后的OkHttpClient
     */
    public static OkHttpClient addProgressResponseListener(final ProgressListener progressListener) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        //增加拦截器
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拦截
                Response originalResponse = chain.proceed(chain.request());
                //包装响应体并返回
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                        .build();
            }
        });
        return client.build();
    }

}
