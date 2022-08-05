package com.fdd.constraintlayouttest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.TransitionManager;

import com.fdd.constraintlayouttest.databinding.ActivityConstraintSetBinding;
import com.fdd.constraintlayouttest.databinding.ActivityConstraintSetSmallBinding;

/**
 * ConstraintSet 可以理解为 ConstraintLayout 对其所有子控件的约束规则的集合。在不同的交互规则下，我们可能需要改变 ConstraintLayout
 * 内的所有子控件的约束条件，即子控件的位置需要做一个大调整，ConstraintSet 就用于实现平滑地改变子控件的位置
 * <p>
 * 例如，我们需要在不同的场景下使用两种不同的布局形式，先定义好这两种布局文件，其中子 View 的 Id 必须保持一致，View 的约束条件则可以随意设置。然后在代码中通过 ConstraintSet
 * 来加载这两个布局文件的约束规则，apply 给 ConstraintLayout 后即可平滑地切换两种布局效果
 * <p>
 * ConstraintHelper，在 updatePostLayout方法中遍历其引用的所有控件，然后对每个控件应用 CircularReveal 动画。updatePostLayout方法会在执行 onLayout 之前被调用
 */
public class ConstraintSetActivity extends AppCompatActivity {

    private ConstraintSet normal = new ConstraintSet();
    private ConstraintSet small = new ConstraintSet();

    ActivityConstraintSetBinding normalBinding;
    ActivityConstraintSetSmallBinding smallBinding;

    private boolean isSmall = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        normalBinding = ActivityConstraintSetBinding.inflate(getLayoutInflater());
        setContentView(normalBinding.getRoot());
        normal.clone(normalBinding.getRoot());

        smallBinding = ActivityConstraintSetSmallBinding.inflate(getLayoutInflater());
        small.clone(smallBinding.getRoot());

        normalBinding.content.setOnScrollListener((l, t, oldl, oldt) -> {
            if (t > 200) {
                if (isSmall) {
                    return;
                }
                toggleMode();
            } else {
                if (isSmall) {
                    toggleMode();
                }
            }
        });
    }

    private void toggleMode() {
        TransitionManager.beginDelayedTransition(normalBinding.getRoot());
        isSmall = !isSmall;
        if (isSmall) {
            small.applyTo(normalBinding.getRoot());
        } else {
            normal.applyTo(normalBinding.getRoot());
        }
    }
}