package com.fdd.motionlayouttest;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fdd.motionlayouttest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tv1.setOnClickListener(view -> {
            startActivity(new Intent(this, ConstraintSetActivity.class));
        });

        binding.tv2.setOnClickListener(view -> {
            startActivity(new Intent(this, OnSwipeActivity.class));
        });
    }
}