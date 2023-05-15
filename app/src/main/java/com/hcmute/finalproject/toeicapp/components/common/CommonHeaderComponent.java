package com.hcmute.finalproject.toeicapp.components.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.activities.HomeActivity;

public class CommonHeaderComponent extends LinearLayout {
    private TextView txtTitle;
    private BackButtonRoundedComponent btnBack;
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
        btnBack = findViewById(R.id.component_common_header_back_button);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)context).onBackPressed();
            }
        });
    }
    public void setTitle(String title) {
        this.txtTitle.setText(title);
    }
}
