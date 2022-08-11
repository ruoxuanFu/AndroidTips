package com.fdd.mydagger2test.mpvtest;

public interface MvpTestContract {

    interface BasePresenter {

        void loadData();
    }

    interface BaseView {

        void showLoading(boolean cancelAble);

        void dismissLoading();

        void showToast(String msg);
    }
}
