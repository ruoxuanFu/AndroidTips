package com.fdd.constraintlayouttest;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import com.fdd.constraintlayouttest.databinding.ActivityBarrierBinding;

/**
 * 很多时候我们都会遇到控件的宽高值随着其包含的数据的多少而改变的情况，而此时如果有多个控件之间是相互约束的话，就比较难来设定各个控件间的约束关系了，而 Barrier（屏障）就是用于解决这种情况。Barrier 和
 * GuideLine 一样是一个虚拟的 View，对界面是不可见的，只是用于辅助布局
 *
 * Barrier 可以使用的属性有：
 *
 * 1.barrierDirection：用于设置 Barrier 的位置，属性值有：bottom、top、start、end、left、right
 * 2.constraint_referenced_ids：用于设置 Barrier 所引用的控件的 ID，可同时设置多个
 * 3.barrierAllowsGoneWidgets：默认为 true，当 Barrier 所引用的控件为 Gone 时，则 Barrier 的创建行为是在已 Gone 的控件已解析的位置上进行创建。如果设置为 false，则不会将
 * Gone 的控件考虑在内
 */
public class BarrierActivity extends AppCompatActivity {

    ActivityBarrierBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBarrierBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.tvText1.setText(editable);
            }
        });
    }
}