package com.fdd.constraintlayouttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * 圆形定位用于将两个 View 以角度和距离这两个维度来进行定位，以两个 View 的中心点作为定位点
 *
 * 1.app:layout_constraintCircle - 目标 View 的 ID
 * 2.app:layout_constraintCircleAngle - 对齐的角度
 * 3.app:layout_constraintCircleRadius - 与目标 View 之间的距离（顺时针方向，0~360度）
 */
public class ConstraintCircleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_circle);
    }
}