package com.fdd.mydagger2test.loginmvptest.mvp.contract;

import com.fdd.mydagger2test.loginmvptest.mvp.bean.LoginInfo;
import com.fdd.mydagger2test.loginmvptest.mvp.presenter.IBasePresenter;
import com.fdd.mydagger2test.loginmvptest.mvp.view.IBaseView;

public class LoginActivityContract {

    public interface View extends IBaseView {

        void setLoginStatus(LoginInfo loginInfo);
    }

    public interface Presenter extends IBasePresenter {
        void LoginApp(String userName, String psw);

        void getLoginStatus(int id);
    }
}
