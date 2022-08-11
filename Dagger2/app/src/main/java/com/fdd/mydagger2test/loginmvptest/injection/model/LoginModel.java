package com.fdd.mydagger2test.loginmvptest.injection.model;

import com.fdd.mydagger2test.loginmvptest.injection.scope.ActivityScope;
import com.fdd.mydagger2test.loginmvptest.mvp.contract.LoginActivityContract;
import com.fdd.mydagger2test.loginmvptest.mvp.model.ILoginActivityModel;
import com.fdd.mydagger2test.loginmvptest.mvp.model.LoginActivityModel;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModel {

    private LoginActivityContract.View view;

    public LoginModel(LoginActivityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    LoginActivityContract.View provideLoginActivityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ILoginActivityModel provideLoginActivityModel(LoginActivityModel loginActivityModel){
        return loginActivityModel;
    }
}
