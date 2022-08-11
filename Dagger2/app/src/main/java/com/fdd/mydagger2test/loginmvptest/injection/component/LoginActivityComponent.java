package com.fdd.mydagger2test.loginmvptest.injection.component;

import com.fdd.mydagger2test.loginmvptest.injection.model.LoginModel;
import com.fdd.mydagger2test.loginmvptest.injection.scope.ActivityScope;
import com.fdd.mydagger2test.loginmvptest.ui.LoginActivity;

import dagger.Component;

@ActivityScope
@Component(modules = LoginModel.class, dependencies = AppComponent.class)
public interface LoginActivityComponent {
    void inject(LoginActivity loginActivity);
}
