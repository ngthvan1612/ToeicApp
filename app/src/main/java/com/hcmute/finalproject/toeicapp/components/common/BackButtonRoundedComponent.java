package com.hcmute.finalproject.toeicapp.components.common;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;

public class BackButtonRoundedComponent extends LinearLayout {
    private Context context;
    ImageView backBtn;


    public BackButtonRoundedComponent(Context context) {
        this(context,null);
        this.context = context;
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
        this.backBtn = view.findViewById(R.id.component_back_button_rounded_btn);
        if (this.isInEditMode()) {
            return;
        }
    }
    public void handleBackToHomepage(Intent intent) {
        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getContext().startActivity(intent);
            }
        });

    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);

        if (backBtn != null) {
            backBtn.setOnClickListener(l);
        }
    }
}
