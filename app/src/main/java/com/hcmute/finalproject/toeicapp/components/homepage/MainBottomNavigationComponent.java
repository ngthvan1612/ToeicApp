package com.hcmute.finalproject.toeicapp.components.homepage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;

public class MainBottomNavigationComponent extends LinearLayout {
    public MainBottomNavigationComponent(Context context) {
        this(context, null);
    }

    public MainBottomNavigationComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainBottomNavigationComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);
    }

    private void initComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_main_bottom_navigation, this);

        if (this.isInEditMode()) {
            return;
        }
    }
}
