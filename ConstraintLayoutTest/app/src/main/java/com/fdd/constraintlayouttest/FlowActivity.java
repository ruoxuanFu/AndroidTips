package com.fdd.constraintlayouttest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Flow;

import com.fdd.constraintlayouttest.databinding.ActivityFlowBinding;

public class FlowActivity extends AppCompatActivity {

    ActivityFlowBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnNone.setOnClickListener(view -> {
            binding.tvWrapMode.setText("wrapMode: none");
            binding.flow.setWrapMode(Flow.WRAP_NONE);
        });

        binding.btnChain.setOnClickListener(view -> {
            binding.tvWrapMode.setText("wrapMode: chain");
            binding.flow.setWrapMode(Flow.WRAP_CHAIN);
        });

        binding.btnAligned.setOnClickListener(view -> {
            binding.tvWrapMode.setText("wrapMode: aligned");
            binding.flow.setWrapMode(Flow.WRAP_ALIGNED);
        });
    }
}