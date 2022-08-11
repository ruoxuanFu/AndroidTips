package com.fdd.mydagger2test.loginmvptest.util;

import android.util.Log;

import javax.inject.Inject;

public class LogUtil {

    private String tag;

    @Inject
    public LogUtil(String logTag) {
        this.tag = logTag;
    }

    public void w(Object paramObject) {
        Log.w(this.tag, paramObject.toString());
    }

    public void d(Object paramObject) {
        Log.d(this.tag, paramObject.toString());
    }

    public void i(Object paramObject) {
        Log.i(this.tag, paramObject.toString());

    }

    public void e(Object paramObject) {
        Log.e(this.tag, paramObject.toString());
    }

    public void v(Object paramObject) {
        Log.v(this.tag, paramObject.toString());

    }
}
