package com.fdd.mydagger2test.subcomponent;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.fdd.mydagger2test.R;

import javax.inject.Inject;

public class SubComponentActivity extends AppCompatActivity {

    @PotModule.RosePot
    @Inject
    Pot rosePot;

    @PotModule.LilyPot
    @Inject
    Pot lilyPot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_component);

        DaggerFlowerComponent.create()
                .plus(new PotModule())
                .plus()
                .inject(this);

        String show1 = rosePot.show();
        Log.d("fmsg", "SubComponentActivity show1 whisper is " + show1);

        String show2 = lilyPot.show();
        Log.d("fmsg", "SubComponentActivity show2 whisper is " + show2);

    }
}