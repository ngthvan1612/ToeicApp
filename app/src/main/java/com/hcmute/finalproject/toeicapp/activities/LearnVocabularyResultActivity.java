package com.hcmute.finalproject.toeicapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmute.finalproject.toeicapp.R;

public class LearnVocabularyResultActivity extends AppCompatActivity {
    public static final int MODE_SUCCESS = 0;
    public static final int MODE_FAILURE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_vocabulary_result);

        this.initView();
    }

    private void initView() {
        final Intent intent = getIntent();
        int mode = intent.getIntExtra("mode", 0);
        ImageView imgView = findViewById(R.id.activity_learn_vocabulary_result_image);

        if (mode == MODE_FAILURE) {
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.activity_learn_vocabulary_result_failure));
        }
        else {
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.activity_learn_vocabulary_result_success_image));
        }

        TextView textView = findViewById(R.id.activity_learn_vocabulary_result_txt_score_content);
        textView.setText(intent.getStringExtra("text"));

        findViewById(R.id.activity_learn_vocabulary_result_btn_continue).setOnClickListener(view -> {
            finish();
        });
    }
}