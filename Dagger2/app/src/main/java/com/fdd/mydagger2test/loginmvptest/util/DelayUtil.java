package com.fdd.mydagger2test.loginmvptest.util;

import com.fdd.mydagger2test.loginmvptest.LoginTestApplication;

import javax.inject.Inject;

/**
 * 模拟网络请求
 */
public class DelayUtil {

    @Inject
    public DelayUtil() {
    }

    public void delayTodo(long millisecond, IdelayTodoCallback callback) {
        if (millisecond < 0) {
            return;
        }

        new Thread(() -> {
            LoginTestApplication.log().w("delay to do start");
            try {
                Thread.sleep(millisecond);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LoginTestApplication.log().w("delay to do end");
            if (callback != null) {
                callback.finish();
            }
        }).start();
    }

    public interface IdelayTodoCallback {
        void finish();
    }
}
