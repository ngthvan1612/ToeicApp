package com.hcmute.finalproject.toeicapp.components.part_three;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.common.CommonHeaderComponent;

public class PartThreeComponent  extends LinearLayout {
    private CommonHeaderComponent commonHeaderComponent;
    public PartThreeComponent(Context context) {
        this(context, null);
    }

    public PartThreeComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartThreeComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);

    }
    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_part_three, this);
        commonHeaderComponent=view.findViewById(R.id.common_part_three_header_title);
        commonHeaderComponent.setTitle("Part three - Conversations");
    }

}