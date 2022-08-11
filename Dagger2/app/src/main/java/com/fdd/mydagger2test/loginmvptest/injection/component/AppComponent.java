package com.fdd.mydagger2test.loginmvptest.injection.component;

import com.fdd.mydagger2test.loginmvptest.LoginTestApplication;
import com.fdd.mydagger2test.loginmvptest.injection.model.AppModule;
import com.fdd.mydagger2test.loginmvptest.injection.model.LogUtilModel;
import com.fdd.mydagger2test.loginmvptest.injection.scope.AppScope;
import com.fdd.mydagger2test.loginmvptest.util.DelayUtil;
import com.fdd.mydagger2test.loginmvptest.util.LogUtil;

import dagger.Component;

@AppScope
@Component(modules = {AppModule.class, LogUtilModel.class})
public interface AppComponent {

    LoginTestApplication getApplication();

    LogUtil getLog();

    DelayUtil getDelay();
}
