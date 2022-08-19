package com.fdd.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerView2 extends RecyclerView {

    protected int newScrollState = -1;

    protected RecyclerView.OnScrollListener onScrollListener;

    public void setOnMyScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public MyRecyclerView2(@NonNull Context context) {
        this(context, null);
    }

    public MyRecyclerView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        addOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                newScrollState = newState;
                if (newScrollState > 0) {
                    if (onScrollListener != null) {
                        onScrollListener.onScrollStateChanged(recyclerView, newState);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (newScrollState > 0) {
                    if (onScrollListener != null) {
                        onScrollListener.onScrolled(recyclerView, dx, dy);
                    }
                }
            }
        });
    }

}
