package com.fdd.mydagger2test.loginmvptest.mvp.presenter;

import com.fdd.mydagger2test.loginmvptest.mvp.model.IBaseModel;
import com.fdd.mydagger2test.loginmvptest.mvp.view.IBaseView;

public abstract class BasePresenter<M extends IBaseModel, V extends IBaseView> implements IBasePresenter {

    public M mModel;
    public V mView;

    public BasePresenter(M model, V view) {
        mModel = model;
        mView = view;
    }
}
