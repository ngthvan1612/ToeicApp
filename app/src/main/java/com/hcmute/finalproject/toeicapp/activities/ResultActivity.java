package com.hcmute.finalproject.toeicapp.activities;

import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmute.finalproject.toeicapp.R;

public class ResultActivity extends GradientActivity {
    public final static int MODE_GOOD = 1;
    public final static int MODE_BAD = 2;

    public final static String INTENT_NUMBER_OF_CORRECT_ANSWERS = "num-of-questions";
    public final static String INTENT_TOTAL_QUESTIONS = "total-questions";

    private int numberOfCorrectAnswers;
    private int totalQuestions;

    private int viewMode;
    ImageView imageRender;
    TextView textRender, txtResult;
    AppCompatButton btnShowAnswers, btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle = getIntent().getExtras();
        viewMode = bundle.getInt("score");

        Intent intent = getIntent();
        this.numberOfCorrectAnswers = intent.getIntExtra(ResultActivity.INTENT_NUMBER_OF_CORRECT_ANSWERS, 0);
        this.totalQuestions = intent.getIntExtra(ResultActivity.INTENT_TOTAL_QUESTIONS, 0);

        this.initView();

        setViewMode(viewMode);
    }

    public void initView(){
        imageRender = (ImageView) findViewById(R.id.activity_result_image);
        textRender = (TextView) findViewById(R.id.activity_result_text);
        btnShowAnswers = (AppCompatButton) findViewById(R.id.activity_result_button_show_answers);
        btnContinue = (AppCompatButton) findViewById(R.id.activity_result_button_continue);
        txtResult = findViewById(R.id.activity_result_text_result);

        txtResult.setText("Result " + this.numberOfCorrectAnswers + "/" + this.totalQuestions);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnShowAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    public int getViewMode() {
        return viewMode;
    }

    public void setViewMode(int viewMode) {

        this.viewMode = viewMode;
        if (viewMode == MODE_GOOD) {
            imageRender.setImageResource(R.drawable.activity_result_image_happy);
            textRender.setText("Good job!");
        }
        else if (viewMode == MODE_BAD) {
            imageRender.setImageResource(R.drawable.activity_result_image_cheer_up);
            textRender.setText("You need to try harder");
        }
    }
}
