package com.fdd.mydagger2test.loginmvptest.ui;

import android.os.Bundle;
import android.view.View;

import com.fdd.mydagger2test.databinding.ActivityLoginBinding;
import com.fdd.mydagger2test.loginmvptest.LoginTestApplication;
import com.fdd.mydagger2test.loginmvptest.injection.component.DaggerLoginActivityComponent;
import com.fdd.mydagger2test.loginmvptest.injection.model.LoginModel;
import com.fdd.mydagger2test.loginmvptest.mvp.bean.LoginInfo;
import com.fdd.mydagger2test.loginmvptest.mvp.contract.LoginActivityContract;
import com.fdd.mydagger2test.loginmvptest.mvp.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginPresenter> implements LoginActivityContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DaggerLoginActivityComponent.builder()
                .appComponent(LoginTestApplication.getAppComponent())
                .loginModel(new LoginModel(this))
                .build().inject(this);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.LoginApp(binding.etUserName.getText().toString(), binding.etPsw.getText().toString());
            }
        });
    }

    @Override
    public void setLoginStatus(LoginInfo loginInfo) {
        LoginTestApplication.log().d("loginInfo: " + loginInfo);
    }
}