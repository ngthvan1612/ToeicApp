package com.hcmute.finalproject.toeicapp.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;

public class QuestionSentenceComponent  extends LinearLayout {
    private TextView txtTitle, txtQuestionDescription;
    public QuestionSentenceComponent(Context context) {
        this(context,null);
    }

    public QuestionSentenceComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QuestionSentenceComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);
    }

    public QuestionSentenceComponent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_question_sentence,this);
        this.txtQuestionDescription = view.findViewById(R.id.component_question_sentence_description);
        txtQuestionDescription.setText("We have ______ your fax order for a case of hanging file folders. We have in stock the style folders you requested, but you did not specify a color on the order form. We currently carry the Pro Stock Hanging File Folder in black, navy blue, light gree, and orange.");
    }
}
