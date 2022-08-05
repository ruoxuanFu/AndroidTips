package com.fdd.constraintlayouttest;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;

import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CircularRevealHelper extends ConstraintHelper {
    public CircularRevealHelper(Context context) {
        super(context);
    }

    public CircularRevealHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularRevealHelper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void updatePostLayout(ConstraintLayout container) {
        super.updatePostLayout(container);
        View[] views = getViews(container);
        for (View view : views) {
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(view, view.getWidth() / 2,
                    view.getHeight() / 2, 0f,
                    Math.max(view.getWidth() / 2, view.getHeight() / 2));
            circularReveal.setDuration(3000);
            circularReveal.start();
        }
    }
}
