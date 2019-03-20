package com.szinternet.crm.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 * module类,通过appcomponent与inject建立联系
 * 提供context
 */
@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return context;
    }
}