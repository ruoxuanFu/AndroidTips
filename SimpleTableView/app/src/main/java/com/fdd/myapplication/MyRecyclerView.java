package com.fdd.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerView extends MyRecyclerView2 {
    private float mDownX;
    private float mDownY;

    public MyRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = e.getRawX();
                mDownY = e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float rawX = e.getRawX();
                float rawY = e.getRawY();
                float dx = Math.abs(mDownX - rawX);
                float dy = Math.abs(mDownY - rawY);
                if (dy > 5 && dx > 5 && dx > dy) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            default:
        }
        return super.dispatchTouchEvent(e);
    }

}
