package com.fdd.mydagger2test.componentdependence;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.fdd.mydagger2test.R;

import javax.inject.Inject;

public class ComponentDependenceTestActivity extends AppCompatActivity {

    @PotModule.RosePot
    @Inject
    Pot rosePot;

    @PotModule.LilyPot
    @Inject
    Pot lilyPot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_dependence_test);
        DaggerPotFlowerComponent.builder().potComponent(
                DaggerPotComponent.builder().flowerComponent(
                        DaggerFlowerComponent.builder().flowerModule(new FlowerModule()).build()
                ).build()
        ).build().inject(this);

        String show1 = rosePot.show();
        Log.d("fmsg", "show1 whisper is " + show1);

        String show2 = lilyPot.show();
        Log.d("fmsg", "show2 whisper is " + show2);

    }
}