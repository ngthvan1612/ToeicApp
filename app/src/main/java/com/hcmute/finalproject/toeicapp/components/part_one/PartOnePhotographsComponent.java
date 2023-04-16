package com.hcmute.finalproject.toeicapp.components.part_one;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;

public class PartOnePhotographsComponent extends LinearLayout {
    public PartOnePhotographsComponent(Context context) {
        this(context, null);
    }

    public PartOnePhotographsComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);

    }

    public PartOnePhotographsComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);

    }
    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_part_one_photographs, this);
    }
}
