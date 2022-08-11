package com.fdd.mydagger2test.loginmvptest.mvp.view;

import com.fdd.mydagger2test.loginmvptest.mvp.bean.LoginInfo;

public interface LoginActivityView extends IBaseView {

    void showLoginInfo(LoginInfo info);
}
