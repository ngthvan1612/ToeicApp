package com.hcmute.finalproject.toeicapp.components.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;

public class HeaderComponent extends LinearLayout {
    public HeaderComponent(Context context) {
        this(context, null);
    }

    public HeaderComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);
    }

    public HeaderComponent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_common_header, this);
    }
}
