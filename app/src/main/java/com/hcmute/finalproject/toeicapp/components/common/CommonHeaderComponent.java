package com.hcmute.finalproject.toeicapp.components.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;

public class CommonHeaderComponent extends LinearLayout {
    private TextView txtTitle;
    public CommonHeaderComponent(Context context) {
        this(context, null);
    }

    public CommonHeaderComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonHeaderComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);
    }

    public CommonHeaderComponent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_common_header, this);
        this.txtTitle = view.findViewById(R.id.component_common_header_title);
    }
    public void setTitle(String title) {
        this.txtTitle.setText(title);
    }
}
