package com.hcmute.finalproject.toeicapp.components.part;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.AnswerSelectionComponent;
import com.hcmute.finalproject.toeicapp.components.common.CommonHeaderComponent;
import com.hcmute.finalproject.toeicapp.components.media.AudioPlayerComponent;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.model.toeic.TestToeicAnswerChoice;
import com.hcmute.finalproject.toeicapp.model.toeic.TestToeicQuestion;
import com.hcmute.finalproject.toeicapp.model.toeic.TestToeicQuestionGroup;

import java.io.ByteArrayInputStream;
import java.io.File;

public class PartOnePhotographsComponent extends ToeicPartComponentBase {
    private ImageView imageViewMainImage;
    private AudioPlayerComponent audioPlayerComponent;
    private AnswerSelectionComponent answerSelectionComponent;
    private CommonHeaderComponent commonHeaderComponent;


    public PartOnePhotographsComponent(Context context) {
        this(context, null);
    }

    public PartOnePhotographsComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartOnePhotographsComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);

    }
    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_part_one_photographs, this);

        this.imageViewMainImage = view.findViewById(R.id.component_part_one_photographs_img);
        this.audioPlayerComponent = view.findViewById(R.id.component_part_one_photographs_audio);
        this.answerSelectionComponent = view.findViewById(R.id.component_part_one_photographs_answer_selection);

        commonHeaderComponent=view.findViewById(R.id.common_part_one_header_title);
        commonHeaderComponent.setTitle("Part one - Photographs");

        if (this.isInEditMode()) {
            return;
        }

        this.audioPlayerComponent.setOnAudioPlayerStateChanged(new AudioPlayerComponent.OnAudioPlayerChange() {
            @Override
            public boolean onPlayButtonClicked() {
                return true;
            }

            @Override
            public boolean onPauseButtonClicked() {
                return true;
            }

            @Override
            public void onChangeVolume(int currentVolumne, int maxVolume) {

            }
        });
    }

    public void showAnswer() {
        this.answerSelectionComponent.setShowExplain(true);
    }

    public void loadToeicQuestionGroup(Integer partId, TestToeicQuestionGroup testToeicQuestionGroup) {
        //Only one question
    }

    @Override
    public void loadQuestionGroup(ToeicQuestionGroup toeicQuestionGroup) {

    }

    @Override
    public void showExplain() {

    }
}
