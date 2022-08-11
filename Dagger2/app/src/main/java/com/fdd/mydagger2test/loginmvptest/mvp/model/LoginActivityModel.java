package com.fdd.mydagger2test.loginmvptest.mvp.model;

import com.fdd.mydagger2test.loginmvptest.LoginTestApplication;
import com.fdd.mydagger2test.loginmvptest.util.DelayUtil;

import javax.inject.Inject;

public class LoginActivityModel implements ILoginActivityModel {

    private final DelayUtil delayUtil;

    @Inject
    public LoginActivityModel(DelayUtil delayUtil) {
        this.delayUtil = delayUtil;
    }

    @Override
    public void isLogined(int id, DelayUtil.IdelayTodoCallback callback) {
        LoginTestApplication.log().d("Wating... :");
        LoginTestApplication.log().d("Login id :" + id);
        delayUtil.delayTodo(2000, callback);
    }

    @Override
    public void LoginApp(String userName, String psw, DelayUtil.IdelayTodoCallback callback) {
        LoginTestApplication.log().d("Logining... :");
        LoginTestApplication.log().d("Login user name :" + userName);
        LoginTestApplication.log().d("Login user psw :" + psw);
        delayUtil.delayTodo(2000, callback);
    }
}
