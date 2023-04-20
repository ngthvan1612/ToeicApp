package com.hcmute.finalproject.toeicapp.components.part_four;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.common.CommonHeaderComponent;

public class PartFourComponent  extends LinearLayout {
    private CommonHeaderComponent commonHeaderComponent;
    public PartFourComponent(Context context) {
        this(context, null);
    }

    public PartFourComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartFourComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);

    }
    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_part_four, this);
        commonHeaderComponent=view.findViewById(R.id.common_part_four_header_title);
        commonHeaderComponent.setTitle("Part four - Short talks");
    }

}