package com.fdd.mydagger2test.loginmvptest.injection.model;

import com.fdd.mydagger2test.loginmvptest.injection.scope.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class LogUtilModel {

    private String logTag;

    public LogUtilModel(String logTag) {
        this.logTag = logTag;
    }

    @AppScope
    @Provides
    String provideLogTag() {
        return this.logTag;
    }
}
