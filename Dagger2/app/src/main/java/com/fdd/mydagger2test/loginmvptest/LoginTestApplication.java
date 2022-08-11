package com.fdd.mydagger2test.loginmvptest;

import android.app.Application;

import com.fdd.mydagger2test.loginmvptest.injection.component.AppComponent;
import com.fdd.mydagger2test.loginmvptest.injection.component.DaggerAppComponent;
import com.fdd.mydagger2test.loginmvptest.injection.model.AppModule;
import com.fdd.mydagger2test.loginmvptest.injection.model.LogUtilModel;
import com.fdd.mydagger2test.loginmvptest.util.LogUtil;

public class LoginTestApplication extends Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .logUtilModel(new LogUtilModel("fmsg"))
                .build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    public static LogUtil log() {
        return sAppComponent.getLog();
    }

}
