package com.hcmute.finalproject.toeicapp.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;

public class QuestionSentenceComponent extends LinearLayout {
    private static final int TYPE_QUESTION_CONTENT = 1;
    private static final int TYPE_QUESTION_TRANSCRIPT = 3;
    private TextView txtQuestionDescription;
    private WebView webViewQuestionSentenceDescription;
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
        View view = inflate(context, R.layout.component_question_sentence, this);
        webViewQuestionSentenceDescription = findViewById(R.id.component_question_sentence_description_webview);
        txtQuestionDescription = findViewById(R.id.component_answer_selection_title);
    }

    public void setTitle(int typeTitle) {
        if (typeTitle == TYPE_QUESTION_CONTENT) {
            txtQuestionDescription.setText("Question");
        } else if (typeTitle == TYPE_QUESTION_TRANSCRIPT) {
            txtQuestionDescription.setText("Transcript");
        }
    }

    public void setShowTranscript(boolean isShowTranscript) {
        if (!isShowTranscript) {
            this.setVisibility(View.GONE);
            this.setLayoutParams(new LayoutParams(0, 0));
        }
        else {
            this.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams layoutParams =this.getLayoutParams();
            layoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height= ViewGroup.LayoutParams.WRAP_CONTENT;
            this.setLayoutParams(layoutParams);
        }
    }
    public void setQuestionDescription(String data) {
        //webViewQuestionSentenceDescription.loadUrl(url);
        webViewQuestionSentenceDescription.loadData(data, "text/html; charset=utf-8", "UTF-8");

        WebSettings settings = webViewQuestionSentenceDescription.getSettings();


        settings.setDomStorageEnabled(false);
    }
}
