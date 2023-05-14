package com.hcmute.finalproject.toeicapp.components.learnvocab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.entities.ToeicStorage;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabulary;
import com.hcmute.finalproject.toeicapp.services.storage.StorageConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LearnVocabularyQuestionComponent extends LinearLayout {
    public static final int MODE_QUESTION = 0;
    public static final int MODE_CORRECT_ANSWER = 1;
    public static final int MODE_WRONG_ANSWER = 2;

    private OnQuestionContainerListener onQuestionContainerListener;

    private TextView txtQuestionContent, txtCorrectAnswerText, txtWrongAnswerText, txtWrongAnswerRightAnswerText,
        txtWrongAnswerRightAnswerContent;
    private ImageView imgQuestion;
    private AppCompatButton btnNext;
    private AppCompatEditText txtInputAnswer;

    private int viewMode;

    public LearnVocabularyQuestionComponent(Context context) {
        this(context, null);
    }

    public LearnVocabularyQuestionComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setOnQuestionContainerListener(OnQuestionContainerListener onQuestionContainerListener) {
        this.onQuestionContainerListener = onQuestionContainerListener;
    }

    public OnQuestionContainerListener getOnQuestionContainerListener() {
        return onQuestionContainerListener;
    }

    public LearnVocabularyQuestionComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.initComponent(context, attrs, defStyleAttr);
    }

    public int getViewMode() {
        return viewMode;
    }

    public void setViewMode(int viewMode) {
        this.viewMode = viewMode;
        switch (viewMode) {
            case MODE_QUESTION:
                this.txtCorrectAnswerText.setVisibility(GONE);
                this.txtWrongAnswerText.setVisibility(GONE);
                this.txtWrongAnswerRightAnswerContent.setVisibility(GONE);
                this.txtWrongAnswerRightAnswerText.setVisibility(GONE);
                this.btnNext.setVisibility(GONE);
                break;
            case MODE_CORRECT_ANSWER:
                this.txtCorrectAnswerText.setVisibility(VISIBLE);
                this.txtWrongAnswerText.setVisibility(GONE);
                this.txtWrongAnswerRightAnswerContent.setVisibility(GONE);
                this.txtWrongAnswerRightAnswerText.setVisibility(GONE);
                this.btnNext.setVisibility(VISIBLE);
                break;
            case MODE_WRONG_ANSWER:
                this.txtCorrectAnswerText.setVisibility(GONE);
                this.txtWrongAnswerText.setVisibility(VISIBLE);
                this.txtWrongAnswerRightAnswerContent.setVisibility(VISIBLE);
                this.txtWrongAnswerRightAnswerText.setVisibility(VISIBLE);
                this.btnNext.setVisibility(VISIBLE);
                break;
        }
    }

    public void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_learn_vocabulary_question, this);

        this.txtInputAnswer = view.findViewById(R.id.component_learn_vocabulary_question_input_answer);
        this.txtQuestionContent = view.findViewById(R.id.component_learn_vocabulary_question_question_content);
        this.txtCorrectAnswerText = view.findViewById(R.id.component_learn_vocabulary_question_correct_txt);
        this.txtWrongAnswerText = view.findViewById(R.id.component_learn_vocabulary_question_wrong_txt);
        this.txtWrongAnswerRightAnswerText = view.findViewById(R.id.component_learn_vocabulary_question_wrong_right_answer);
        this.txtWrongAnswerRightAnswerContent = view.findViewById(R.id.component_learn_vocabulary_question_wrong_right_answer_txt);
        this.imgQuestion = view.findViewById(R.id.component_learn_vocabulary_question_img);
        this.btnNext = view.findViewById(R.id.component_learn_vocabulary_question_btn_next);

        if (this.isInEditMode()) {
            return;
        }

        setViewMode(MODE_QUESTION);

        this.btnNext.setOnClickListener(v -> {
            if (this.onQuestionContainerListener != null) {
                this.onQuestionContainerListener.onNextQuestion();
            }
        });

        this.txtInputAnswer.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                if (this.onQuestionContainerListener != null) {
                    if (this.onQuestionContainerListener.onCheckAnswer(this.txtInputAnswer.getText().toString()))
                        setViewMode(MODE_CORRECT_ANSWER);
                    else
                        setViewMode(MODE_WRONG_ANSWER);
                }
                this.txtInputAnswer.setText("");
                return false;
            }
            return false;
        });
    }

    public void loadVocabulary(ToeicVocabulary vocabulary) {
        this.txtQuestionContent.setText(vocabulary.getVietnamese());
        this.txtWrongAnswerRightAnswerContent.setText(vocabulary.getEnglish());

        if (vocabulary.getImagePath() != null) {
            final File vocabRoot = StorageConfiguration.getVocabularyDataDirectory(getContext());
            final File file = new File(vocabRoot, vocabulary.getImagePath());
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            this.imgQuestion.setImageBitmap(bitmap);
        }
    }

    public interface OnQuestionContainerListener {
        void onNextQuestion();
        boolean onCheckAnswer(String userAnswer);
    }
}
