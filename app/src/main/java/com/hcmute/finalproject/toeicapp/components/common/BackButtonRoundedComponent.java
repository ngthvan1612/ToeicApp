package com.hcmute.finalproject.toeicapp.components.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hcmute.finalproject.toeicapp.R;

public class BackButtonRoundedComponent extends LinearLayout {
    private ConstraintLayout mainLayout;
    private View mainView;

    public BackButtonRoundedComponent(Context context) {
        super(context);
        this.initComponent(context);

    }

    public BackButtonRoundedComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);

    }

    public BackButtonRoundedComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr,0);


    }
    private void initComponent(Context context){
        mainView = inflate(context, R.layout.component_back_button_rounded,this);
        this.mainLayout = mainView.findViewById(R.id.component_back_button_rounded);

    }
}
