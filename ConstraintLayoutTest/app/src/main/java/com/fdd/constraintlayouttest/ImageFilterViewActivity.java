package com.fdd.constraintlayouttest;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.fdd.constraintlayouttest.databinding.ActivityImageFilterViewBinding;

public class ImageFilterViewActivity extends AppCompatActivity {

    ActivityImageFilterViewBinding binding;
    private float percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageFilterViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    percentage = (float) (progress / 100.0);
                    setImageFilter();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        setImageFilter();
    }

    private void setImageFilter() {
        binding.altSrc.setCrossfade(percentage);
        binding.crossfade.setCrossfade(percentage);
        binding.saturation.setSaturation(percentage * 2);
        binding.brightness.setBrightness(percentage * 2);
        binding.warmth.setWarmth(percentage * 2);
        binding.contrast.setContrast(percentage * 2);
        binding.round.setRound(percentage * 50);
        binding.roundPercent.setRoundPercent(percentage);
    }
}