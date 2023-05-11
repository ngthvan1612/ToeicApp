package com.hcmute.finalproject.toeicapp.components.part_one;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;

public class PartOneTestListComponent extends LinearLayout {
    public PartOneTestListComponent(Context context) {
        this(context,null);
        this.initComponent(context);
    }

    public PartOneTestListComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PartOneTestListComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PartOneTestListComponent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initComponent(Context context) {
        View view = inflate(context, R.layout.component_test_list,this);
    }

}
