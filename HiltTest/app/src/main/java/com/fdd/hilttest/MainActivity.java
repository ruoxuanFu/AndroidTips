package com.fdd.hilttest;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.bean.DateType;
import com.fdd.hilttest.databinding.ActivityMainBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    AnalyticsAdapter analytics;

    @Inject
    AlertDialogAdapter alertDialog;

    @CustomDialogModel.ErrorDialog
    @Inject
    AlertDialog.Builder errorDialogBuilder;

    @CustomDialogModel.ConfirmDialog
    @Inject
    AlertDialog.Builder conformDialogBuilder;

    @Inject
    AnnexBeanAdapter annexBeanAdapter;

    @DatePickDialogModel.NormalDialog
    @Inject
    DatePickDialog normalDialog;

    @Inject
    DatePickAdapter datePickAdapter;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        analytics.getService().analyticsMethods();

        binding.btn1.setOnClickListener(view -> {
            alertDialog.getDialogBuilder().show();
        });

        binding.btn2.setOnClickListener(view -> {
            errorDialogBuilder.setNegativeButton("确定", null);
            errorDialogBuilder.show();
        });

        binding.btn3.setOnClickListener(view -> {
            conformDialogBuilder.show();
        });

        binding.btn4.setOnClickListener(view -> {
            Log.d("fmsg", annexBeanAdapter.getBean().toString());
        });

        binding.btn5.setOnClickListener(view -> {
            normalDialog.show();
        });

        binding.btn6.setOnClickListener(view -> {
            datePickAdapter.setDateType(DateType.TYPE_ALL)
                    .setYearLimit(10).setYearLimit("123123123").show();
        });
    }
}