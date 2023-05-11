package com.hcmute.finalproject.toeicapp.components.part_six;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.QuestionSentenceComponent;
import com.hcmute.finalproject.toeicapp.components.common.CommonHeaderComponent;

public class PartSixComponent  extends LinearLayout {
    private CommonHeaderComponent commonHeaderComponent;
    private QuestionSentenceComponent questionSentenceComponent;
    public PartSixComponent(Context context) {
        this(context, null);
    }

    public PartSixComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartSixComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);

    }
    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_part_six, this);
        commonHeaderComponent=view.findViewById(R.id.common_part_six_header_title);
        commonHeaderComponent.setTitle("Part six - Text completion");


    }

}
