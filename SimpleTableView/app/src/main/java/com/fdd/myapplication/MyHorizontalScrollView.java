package com.fdd.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHorizontalScrollView extends HorizontalScrollView {

    private float startX;
    private float startY;
    private float endX;
    private float endY;

    public MyHorizontalScrollView(Context context) {
        super(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                endX = (int) ev.getRawX();
                endY = (int) ev.getRawY();
                // 左右滑动
                if (Math.abs(endX - startX) > Math.abs(endY - startY)) {
                    return true;
                } else {
                    return super.onInterceptTouchEvent(ev);
                }
            default:
        }
        return super.onInterceptTouchEvent(ev);
    }
}
