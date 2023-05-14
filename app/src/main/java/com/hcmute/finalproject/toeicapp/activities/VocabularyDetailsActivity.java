package com.hcmute.finalproject.toeicapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.common.BackButtonRoundedComponent;
import com.hcmute.finalproject.toeicapp.services.backend.vocabs.ToeicVocabularyBackendService;
import com.hcmute.finalproject.toeicapp.services.backend.vocabs.model.AndroidToeicVocabWord;
import com.hcmute.finalproject.toeicapp.services.media.AudioPlayerBackground;
import com.hcmute.finalproject.toeicapp.services.media.AudioPlayerBackgroundFactory;
import com.hcmute.finalproject.toeicapp.services.media.AudioPlayerBackgroundService;

import java.io.File;
import java.util.Map;

public class VocabularyDetailsActivity extends AppCompatActivity {
    private TextView txtEnglish, txtVietnamese, txtPronounce, txtEnglishExample, txtVietnameseExample, txtTopicName, txtChecked;
    private ImageView imgWord, imgVolumn;
    private BackButtonRoundedComponent btnBack;
    private ToeicVocabularyBackendService toeicVocabularyBackendService;
    private AudioPlayerBackground audioPlayerBackground = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_details);

        toeicVocabularyBackendService = new ToeicVocabularyBackendService(this);
        AndroidToeicVocabWord word = getVocabWord();
        initView(word);
    }

    private void initView(AndroidToeicVocabWord word) {
        this.txtTopicName = findViewById(R.id.activity_vocabulary_details_topic_name);
        this.txtChecked = findViewById(R.id.activity_vocabulary_details_text_checked);
        this.txtEnglish = findViewById(R.id.activity_vocabulary_details_txt_english);
        this.txtVietnamese = findViewById(R.id.activity_vocabulary_details_txt_vietnamese);
        this.txtPronounce = findViewById(R.id.activity_vocabulary_details_txt_pronoune);
        this.txtEnglishExample = findViewById(R.id.activity_vocabulary_details_txt_english_example);
        this.txtVietnameseExample = findViewById(R.id.activity_vocabulary_details_txt_vietnamese_example);
        this.imgWord = findViewById(R.id.activity_vocabulary_details_img_word);
        this.imgVolumn = findViewById(R.id.activity_vocabulary_details_img_volumn);
        this.btnBack = findViewById(R.id.activity_vocabulary_details_btn_back);

        Bundle topicBundle = getIntent().getExtras();
        txtTopicName.setText(topicBundle.getString("topicName").split(" ")[2]);
        txtChecked.setText(topicBundle.getString("status"));

        this.txtEnglish.setText(word.getEnglish());
        this.txtVietnamese.setText(word.getVietnamese());
        this.txtPronounce.setText(word.getPronounce());
        this.txtEnglishExample.setText(word.getExampleEnglish());
        this.txtVietnameseExample.setText(word.getExampleVietnamese());
        String temp = "a";
        for (int i = 0; i<=10; i++ ){
            temp = temp + temp;
        }
//        this.txtVietnameseExample.setText(temp);

        if (word.getImageFilename() != null) {
            File imgFile = toeicVocabularyBackendService.getImageFileByPath(word.getImageFilename());
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgWord.setImageBitmap(bitmap);
        }

        if(word.getAudioFileName() != null) {
            imgVolumn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    audioPlayerBackground = AudioPlayerBackgroundFactory.createInstance(AudioPlayerBackgroundFactory.AudioMediaPlayerLibrary.GOOGLE_EXO_PLAYER, VocabularyDetailsActivity.this);
                    File audioFile = toeicVocabularyBackendService.getAudioFileByPath(word.getAudioFileName());
                    audioPlayerBackground.prepareAudioFile(audioFile);
//                    audioPlayerBackground.start(true);
                    audioPlayerBackground.setOnAudioPlayerRunningEvent(new AudioPlayerBackground.OnAudioPlayerRunningEvent() {
                        @Override
                        public void afterStarted() {

                        }

                        @Override
                        public void afterPaused() {

                        }

                        @Override
                        public void onFinished() {

                        }

                        @Override
                        public void onException(Exception e) {

                        }

                        @Override
                        public void playing(long currentTime) {

                        }

                        @Override
                        public void onReady() {
                            audioPlayerBackground.start(true);
                        }

                        @Override
                        public void onProcessing() {

                        }
                    });

                }
            });
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }

    private AndroidToeicVocabWord getVocabWord() {
        Bundle bundle = getIntent().getExtras();
        return (AndroidToeicVocabWord) bundle.getSerializable("word");
    }
}