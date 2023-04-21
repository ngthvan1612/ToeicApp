package com.hcmute.finalproject.toeicapp.components.part_five;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.common.CommonHeaderComponent;

public class PartFiveComponent  extends LinearLayout {
    private CommonHeaderComponent commonHeaderComponent;
    public PartFiveComponent(Context context) {
        this(context, null);
    }

    public PartFiveComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartFiveComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);

    }
    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_part_five, this);
        commonHeaderComponent=view.findViewById(R.id.common_part_five_header_title);
        commonHeaderComponent.setTitle("Part two - Questions and response");
    }

}
