package com.fdd.motionlayouttest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fdd.motionlayouttest.databinding.ActivityConstraintsetBinding;

public class ConstraintSetActivity extends AppCompatActivity {

    ActivityConstraintsetBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConstraintsetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}