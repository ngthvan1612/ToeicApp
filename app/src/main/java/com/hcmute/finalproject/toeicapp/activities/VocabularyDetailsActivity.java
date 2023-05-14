package com.hcmute.finalproject.toeicapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.services.backend.vocabs.model.AndroidToeicVocabWord;

public class VocabularyDetailsActivity extends AppCompatActivity {
    private TextView txtEnglish, txtVietnamese, txtPronounce, txtEnglishExample, txtVietnameseExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_details);

        AndroidToeicVocabWord word = getVocabWord();
        initView(word);
    }

    private void initView(AndroidToeicVocabWord word) {
        this.txtEnglish = findViewById(R.id.activity_vocabulary_details_txt_english);
        this.txtVietnamese = findViewById(R.id.activity_vocabulary_details_txt_vietnamese);
        this.txtPronounce = findViewById(R.id.activity_vocabulary_details_txt_pronoune);
        this.txtEnglishExample = findViewById(R.id.activity_vocabulary_details_txt_english_example);
        this.txtVietnameseExample = findViewById(R.id.activity_vocabulary_details_txt_vietnamese_example);

        this.txtEnglish.setText(word.getEnglish());
        this.txtVietnamese.setText(word.getVietnamese());
        this.txtPronounce.setText(word.getPronounce());
        this.txtEnglishExample.setText(word.getExampleEnglish());
        this.txtVietnameseExample.setText(word.getExampleVietnamese());
    }

    private AndroidToeicVocabWord getVocabWord() {
        Bundle bundle = getIntent().getExtras();
        return (AndroidToeicVocabWord) bundle.getSerializable("word");
    }
}