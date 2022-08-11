package com.fdd.mydagger2test.loginmvptest.injection.model;

import com.fdd.mydagger2test.loginmvptest.LoginTestApplication;
import com.fdd.mydagger2test.loginmvptest.injection.scope.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private LoginTestApplication application;

    public AppModule(LoginTestApplication application) {
        this.application = application;
    }

    @AppScope
    @Provides
    LoginTestApplication providerMyApplication() {
        return application;
    }
}
