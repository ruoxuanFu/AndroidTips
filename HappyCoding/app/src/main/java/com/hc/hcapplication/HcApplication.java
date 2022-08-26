package com.hc.hcapplication;

import android.app.Application;

import com.hc.lib_common.network.RetrofitManager;

/**
 * @author furuoxuan
 */
public class HcApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitManager.getInstance().init("https://ssittest.sflep.com/");
    }
}
