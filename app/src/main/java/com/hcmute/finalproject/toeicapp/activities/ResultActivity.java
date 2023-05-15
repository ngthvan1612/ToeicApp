package com.hcmute.finalproject.toeicapp.activities;

import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicRate;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicResult;

public class ResultActivity extends GradientActivity {
    public final static int MODE_GOOD = 1;
    public final static int MODE_BAD = 2;
    private int viewMode;
    ImageView imageRender;
    TextView textRender, txtResult;
    AppCompatButton btnShowAnswers, btnContinue;
    private GradeToeicResult gradeToeicResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        this.gradeToeicResult = this.getGradeToeicResultFromIntent();
        this.initView();
    }

    private GradeToeicResult getGradeToeicResultFromIntent() {
        final Bundle bundle = getIntent().getExtras();
        return (GradeToeicResult)bundle.get("result");
    }

    public void initView(){
        imageRender = (ImageView) findViewById(R.id.activity_result_image);
        textRender = (TextView) findViewById(R.id.activity_result_text);
        btnShowAnswers = (AppCompatButton) findViewById(R.id.activity_result_button_show_answers);
        btnContinue = (AppCompatButton) findViewById(R.id.activity_result_button_continue);
        txtResult = findViewById(R.id.activity_result_text_result);

        txtResult.setText("Result " + this.gradeToeicResult.getNumberOfCorrectQuestions() + "/" + this.gradeToeicResult.getTotalQuestions());

        if (this.gradeToeicResult.getRate() == GradeToeicRate.BAD)
            setViewMode(MODE_BAD);
        else
            setViewMode(MODE_GOOD);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("select", "continue");
                setResult(1234, returnIntent);
                finish();
            }
        });

        btnShowAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("select", "show-answer");
                setResult(1234, returnIntent);
                finish();
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
