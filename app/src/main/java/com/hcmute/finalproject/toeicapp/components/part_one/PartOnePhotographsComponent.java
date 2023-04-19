package com.hcmute.finalproject.toeicapp.components.part_one;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.AnswerSelectionComponent;
import com.hcmute.finalproject.toeicapp.components.media.AudioPlayerComponent;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicItemContent;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicQuestion;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.services.mocktoeic.MockToeicTestDatabase;

import java.io.ByteArrayInputStream;
import java.io.File;

public class PartOnePhotographsComponent extends LinearLayout {
    private ImageView imageViewMainImage;
    private AudioPlayerComponent audioPlayerComponent;
    private AnswerSelectionComponent answerSelectionComponent;
    private MockToeicTestDatabase mockToeicTestDatabase;

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

        if (this.isInEditMode()) {
            return;
        }

        mockToeicTestDatabase = new MockToeicTestDatabase(context);
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

    public void loadToeicQuestionGroup(Integer partId, ToeicQuestionGroup toeicQuestionGroup) {
        //Only one question
        ToeicQuestion toeicQuestion = toeicQuestionGroup.getQuestions().get(0);

        this.answerSelectionComponent.setToeicAnswerChoices(toeicQuestion.getChoices());
        this.answerSelectionComponent.setQuestionTitle("Question " + toeicQuestion.getQuestionId());

        byte[] imageStream = mockToeicTestDatabase.getImageFromDisk(partId, toeicQuestionGroup);

        assert imageStream != null;

        Bitmap bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(imageStream));
        this.imageViewMainImage.setImageBitmap(bitmap);

        File file = new File(mockToeicTestDatabase.getAudioAbsPathFromDisk(partId, toeicQuestionGroup));
        this.audioPlayerComponent.loadAudioFile(file);
    }
}
