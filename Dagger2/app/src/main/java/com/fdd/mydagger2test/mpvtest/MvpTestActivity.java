package com.fdd.mydagger2test.mpvtest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.fdd.mydagger2test.databinding.ActivityMvpTestBinding;

import javax.inject.Inject;

public class MvpTestActivity extends AppCompatActivity implements MvpTestContract.BaseView {

    @Inject
    MvpTestPresenter mvpTestPresenter;

    private MvpTestModule mvpTestModule;

    ActivityMvpTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMvpTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mvpTestModule = new MvpTestModule(this);

        DaggerMvpTestComponent.builder()
                .mvpTestModule(mvpTestModule)
                .build()
                .inject(this);

        binding.btnLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mvpTestPresenter.loadData();
            }
        });
    }

    @Override
    public void showLoading(boolean cancelAble) {
        Log.d("fmsg", "showLoading===> cancelAble:" + cancelAble);
    }

    @Override
    public void dismissLoading() {
        Log.d("fmsg", "dismissLoading===>");
    }

    @Override
    public void showToast(String msg) {
        Log.d("fmsg", "showToast===> msg:" + msg);
    }
}