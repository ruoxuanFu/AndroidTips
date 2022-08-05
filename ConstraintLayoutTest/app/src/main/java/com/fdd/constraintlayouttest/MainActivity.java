package com.fdd.constraintlayouttest;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fdd.constraintlayouttest.databinding.ActivityMainBinding;

/**
 * @author furuoxuan
 */
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnConstraintCircle.setOnClickListener(view ->
                startActivity(new Intent(this, ConstraintCircleActivity.class))
        );

        binding.btnBarrier.setOnClickListener(view ->
                startActivity(new Intent(this, BarrierActivity.class))
        );

        binding.btnFlow.setOnClickListener(view ->
                startActivity(new Intent(this, FlowActivity.class))
        );

        binding.btnConstraintSet.setOnClickListener(view ->
                startActivity(new Intent(this, ConstraintSetActivity.class))
        );

        binding.btnImageFilterView.setOnClickListener(view ->
                startActivity(new Intent(this, ImageFilterViewActivity.class))
        );
    }
}