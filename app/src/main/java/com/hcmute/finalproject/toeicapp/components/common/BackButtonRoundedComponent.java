package com.hcmute.finalproject.toeicapp.components.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hcmute.finalproject.toeicapp.R;

public class BackButtonRoundedComponent extends LinearLayout {

    public BackButtonRoundedComponent(Context context) {
        this(context, null);
    }

    public BackButtonRoundedComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackButtonRoundedComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);
    }

    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_back_button_rounded, this);
    }
}
