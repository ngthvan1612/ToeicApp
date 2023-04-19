package com.hcmute.finalproject.toeicapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicPart;

import java.util.List;

public class ToeicTestListQuestionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toeic_test_list_questions);
    }

    public void loadListDataQuestions(List<ToeicPart> toeicParts) {
        
    }
}