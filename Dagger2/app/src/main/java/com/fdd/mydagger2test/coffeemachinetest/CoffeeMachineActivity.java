package com.fdd.mydagger2test.coffeemachinetest;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.fdd.mydagger2test.R;

import javax.inject.Inject;

public class CoffeeMachineActivity extends AppCompatActivity {

    @Inject
    CoffeeMachine coffeeMachine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_machine);

        DaggerSimpleMakerComponent.builder().simpleMakerModule(new SimpleMakerModule())
                .build().inject(this);

        String makeCoffee = coffeeMachine.makeCoffee();
        Log.d("fmsg", "makeCoffee: " + makeCoffee);
    }
}