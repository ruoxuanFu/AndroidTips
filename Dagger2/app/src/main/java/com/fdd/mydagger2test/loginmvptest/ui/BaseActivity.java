package com.fdd.mydagger2test.loginmvptest.ui;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.fdd.mydagger2test.loginmvptest.LoginTestApplication;
import com.fdd.mydagger2test.loginmvptest.mvp.presenter.BasePresenter;
import com.fdd.mydagger2test.loginmvptest.mvp.view.IBaseView;

import javax.inject.Inject;

public class BaseActivity<VB extends ViewBinding, P extends BasePresenter> extends AppCompatActivity
        implements IBaseView {

    @Inject
    protected P mPresenter;

    protected VB binding;

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showLoading() {
        LoginTestApplication.log().d("showLoading");
    }

    @Override
    public void hideLoading() {
        LoginTestApplication.log().d("hideLoading");
    }

    @Override
    public void showToast(String value) {
        LoginTestApplication.log().d("showToast:" + value);
    }
}
