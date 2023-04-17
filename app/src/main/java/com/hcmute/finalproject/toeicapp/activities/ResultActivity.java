package com.hcmute.finalproject.toeicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmute.finalproject.toeicapp.R;

public class ResultActivity extends GradientActivity {
    public final static int MODE_GOOD = 1;
    public final static int MODE_BAD = 2;
    private int viewMode;
    ImageView imageRender;
    TextView textRender;
    AppCompatButton btnShowAnswers, btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        this.initView();
        viewMode = 2;
        setViewMode(viewMode);
    }

    public void initView(){
        imageRender = (ImageView) findViewById(R.id.activity_result_image);
        textRender = (TextView) findViewById(R.id.activity_result_text);
        btnShowAnswers = (AppCompatButton) findViewById(R.id.activity_result_button_show_answers);
        btnContinue = (AppCompatButton) findViewById(R.id.activity_result_button_continue);
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
