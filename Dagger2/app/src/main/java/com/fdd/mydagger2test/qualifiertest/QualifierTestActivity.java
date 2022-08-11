package com.fdd.mydagger2test.qualifiertest;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.fdd.mydagger2test.R;

import javax.inject.Inject;

public class QualifierTestActivity extends AppCompatActivity {

    Flower rose;

    Flower lily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualifier_test);

        DaggerFlowerComponent.builder().flowerModule(new FlowerModule()).build().inject(this);

        String whisperRose = rose.whisper();
        Log.d("fmsg", "Rose whisper is " + whisperRose);
        String whisperLily = lily.whisper();
        Log.d("fmsg", "Lily whisper is " + whisperLily);
    }

    @Inject
    public void setRose(@FlowerModule.RoseFlower Flower flower) {
        rose = flower;
    }

    @Inject
    public void setLily(@FlowerModule.LilyFlower Flower flower) {
        lily = flower;
    }
}
