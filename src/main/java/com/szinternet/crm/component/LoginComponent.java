package com.szinternet.crm.component;

import com.szinternet.crm.ui.forgetpwd.ForgetPwdActivity;
import com.szinternet.crm.ui.login.LoginActivity;
import com.szinternet.crm.ui.register.RegisterActivity;
import com.szinternet.crm.ui.splash.SplashActivity;

import dagger.Component;
@PerActivityScoped
@Component(dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(SplashActivity activity);
    void inject(LoginActivity activity);
    void inject(RegisterActivity activity);
    void inject(ForgetPwdActivity activity);
}