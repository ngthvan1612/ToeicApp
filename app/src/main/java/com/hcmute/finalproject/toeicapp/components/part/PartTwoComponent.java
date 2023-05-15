package com.hcmute.finalproject.toeicapp.components.part;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.common.CommonHeaderComponent;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;

public class PartTwoComponent extends ToeicPartComponentBase {
    private CommonHeaderComponent commonHeaderComponent;
    public PartTwoComponent(Context context) {
        this(context, null);
    }

    public PartTwoComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartTwoComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);

    }
    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_part_two, this);
        commonHeaderComponent=view.findViewById(R.id.common_part_two_header_title);
        commonHeaderComponent.setTitle("Part two - Questions and response");
    }

    @Override
    public void loadQuestionGroup(ToeicQuestionGroup toeicQuestionGroup) {

    }

    @Override
    public void showExplain() {

    }

    @Override
    public String getAnswer() {
        return null;
    }

    @Override
    public String getSelectedChoice() {
        return null;
    }
}
