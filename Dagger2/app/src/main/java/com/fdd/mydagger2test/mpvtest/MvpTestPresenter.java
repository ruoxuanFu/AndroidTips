package com.fdd.mydagger2test.mpvtest;

import javax.inject.Inject;

public class MvpTestPresenter implements MvpTestContract.BasePresenter {

    private MvpTestContract.BaseView view;

    @Inject
    public MvpTestPresenter(MvpTestContract.BaseView view) {
        this.view = view;
    }

    @Override
    public void loadData() {
        view.showLoading(false);
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                view.dismissLoading();
                view.showToast("success");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
