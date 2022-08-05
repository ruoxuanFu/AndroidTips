package com.fdd.motionlayouttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionScene;

import android.os.Bundle;

import com.fdd.motionlayouttest.databinding.ActivityOnSwipeBinding;

public class OnSwipeActivity extends AppCompatActivity {

    ActivityOnSwipeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnSwipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}