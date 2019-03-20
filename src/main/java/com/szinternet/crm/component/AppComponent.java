package com.szinternet.crm.component;

import android.content.Context;

import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.module.AppModule;
import com.szinternet.crm.module.CreditCardApiModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 * AppComponent是一个注解类，用@Module注解标注，主要用来提供依赖
 * AppComponent是连接@Module(AppModule,CreditCardApiModule)和@Inject的桥梁
 */
@Singleton
@Component(modules = {AppModule.class, CreditCardApiModule.class})
public interface AppComponent {

    Context getContext();

    NetworkApi getNetworkApi();

}