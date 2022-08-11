package com.fdd.mydagger2test.loginmvptest.mvp.presenter;

import android.text.TextUtils;

import com.fdd.mydagger2test.loginmvptest.LoginTestApplication;
import com.fdd.mydagger2test.loginmvptest.mvp.bean.LoginInfo;
import com.fdd.mydagger2test.loginmvptest.mvp.contract.LoginActivityContract;
import com.fdd.mydagger2test.loginmvptest.mvp.model.ILoginActivityModel;

import javax.inject.Inject;

public class LoginPresenter extends BasePresenter<ILoginActivityModel, LoginActivityContract.View>
        implements LoginActivityContract.Presenter {

    private ILoginActivityModel model;

    @Inject
    public LoginPresenter(ILoginActivityModel model, LoginActivityContract.View view) {
        super(model, view);
        this.model = model;
    }

    @Override
    public void LoginApp(String userName, String psw) {

        model.LoginApp(userName, psw, () -> {
            LoginInfo loginInfo = new LoginInfo();
            if (TextUtils.equals(userName, psw)) {
                loginInfo.setSuccess(true);
                loginInfo.setId(100);
                loginInfo.setName("张三");
            } else {
                loginInfo.setSuccess(false);
            }
            mView.setLoginStatus(loginInfo);
        });

    }

    @Override
    public void getLoginStatus(int id) {

    }

    @Override
    public void showActivityName() {
        LoginTestApplication.log().w("showActivityName : LoginActivity");
    }
}
