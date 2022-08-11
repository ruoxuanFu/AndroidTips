package com.fdd.mydagger2test;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fdd.mydagger2test.componentdependence.ComponentDependenceTestActivity;
import com.fdd.mydagger2test.databinding.ActivityMainBinding;
import com.fdd.mydagger2test.loginmvptest.ui.LoginActivity;
import com.fdd.mydagger2test.mpvtest.MvpTestActivity;
import com.fdd.mydagger2test.qualifiertest.QualifierTestActivity;
import com.fdd.mydagger2test.subcomponent.SubComponentActivity;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnMvpTest.setOnClickListener(view -> startActivity(new Intent(this, MvpTestActivity.class)));
        binding.btnQualifierTest.setOnClickListener(view -> startActivity(new Intent(this,
                QualifierTestActivity.class)));
        binding.btnComponentDependenceTest.setOnClickListener(view -> startActivity(new Intent(this,
                ComponentDependenceTestActivity.class)));
        binding.btnSubComponent.setOnClickListener(view -> startActivity(new Intent(this, SubComponentActivity.class)));
        binding.btnLoginMvpTest.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));
    }
}