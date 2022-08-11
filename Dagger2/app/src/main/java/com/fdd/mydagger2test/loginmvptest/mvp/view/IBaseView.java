package com.fdd.mydagger2test.loginmvptest.mvp.view;

import android.content.Context;

public interface IBaseView {
    Context getContext();

    void showLoading();

    void hideLoading();

    void showToast(String value);
}
