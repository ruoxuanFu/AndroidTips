package com.fdd.mydagger2test.loginmvptest.mvp.model;

import com.fdd.mydagger2test.loginmvptest.util.DelayUtil;

public interface ILoginActivityModel extends IBaseModel {

    void isLogined(int id, DelayUtil.IdelayTodoCallback callback);

    void LoginApp(String userName, String psw, DelayUtil.IdelayTodoCallback callback);
}
