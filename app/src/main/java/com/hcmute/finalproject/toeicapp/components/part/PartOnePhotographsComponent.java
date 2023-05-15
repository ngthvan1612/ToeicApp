package com.hcmute.finalproject.toeicapp.components.part;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.activities.ToeicTestListQuestionsActivity;
import com.hcmute.finalproject.toeicapp.components.AnswerSelectionComponent;
import com.hcmute.finalproject.toeicapp.components.common.CommonHeaderComponent;
import com.hcmute.finalproject.toeicapp.components.media.AudioPlayerComponent;
import com.hcmute.finalproject.toeicapp.dao.ToeicAnswerChoiceDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicItemContentDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicQuestionDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.ToeicAnswerChoice;
import com.hcmute.finalproject.toeicapp.entities.ToeicItemContent;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestion;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.model.toeic.TestToeicAnswerChoice;
import com.hcmute.finalproject.toeicapp.model.toeic.TestToeicQuestion;
import com.hcmute.finalproject.toeicapp.model.toeic.TestToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.services.storage.StorageConfiguration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PartOnePhotographsComponent extends ToeicPartComponentBase {
    private ImageView imageViewMainImage;
    private AudioPlayerComponent audioPlayerComponent;
    public AnswerSelectionComponent answerSelectionComponent;
    private CommonHeaderComponent commonHeaderComponent;
    private ToeicAnswerChoice toeicAnswerChoice;

    public PartOnePhotographsComponent(Context context) {
        this(context, null);
    }

    public PartOnePhotographsComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private File getImageFile(Integer serverId) {
        String fileName = serverId + ".bin";
        File root = StorageConfiguration.getTestDataDirectory(this.getContext());
        return new File(root, fileName);
    }

    private File getAudioFile(Integer serverId) {
        String fileName = serverId + ".bin";
        File root = StorageConfiguration.getTestDataDirectory(this.getContext());
        return new File(root, fileName);
    }

    private Bitmap getImageBitmap(Integer serverId) {
        File imageFile = this.getImageFile(serverId);
        Bitmap result = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        return result;
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
        this.toeicAnswerChoice = answerSelectionComponent.getCurrentChoice();


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

    @Override
    public void loadQuestionGroup(ToeicQuestionGroup toeicQuestionGroup) {
        ToeicAppDatabase toeicAppDatabase = ToeicAppDatabase.getInstance(getContext());
        ToeicQuestionDao toeicQuestionDao = toeicAppDatabase.getToeicQuestionDao();
        ToeicAnswerChoiceDao toeicAnswerChoiceDao = toeicAppDatabase.getToeicAnswerChoiceDao();
        ToeicQuestion toeicQuestion = toeicQuestionDao.getToeicQuestionByQuestionGroppId(toeicQuestionGroup.getId()).get(0);
        List<ToeicAnswerChoice> choices = toeicAnswerChoiceDao.getByQuestionId(toeicQuestion.getId());

        ToeicItemContentDao toeicItemContentDao = toeicAppDatabase.getToeicItemContentDao();
        List<ToeicItemContent> toeicItemContentList = toeicItemContentDao.getItemContentByGroupId(toeicQuestionGroup.getId());

        ToeicItemContent itemContentAudio = toeicItemContentList.stream().filter(a -> a.getContentType().equals("AUDIO")).findAny().get();
        ToeicItemContent itemContentImage = toeicItemContentList.stream().filter(a -> a.getContentType().equals("IMAGE")).findFirst().get();
        Bitmap bitmap = this.getImageBitmap(itemContentImage.getServerId());
        File audioFile = this.getAudioFile(itemContentAudio.getServerId());
        imageViewMainImage.setImageBitmap(bitmap);
        audioPlayerComponent.loadAudioFile(audioFile);
        audioPlayerComponent.setCurrentVolume(1.0f);

        this.answerSelectionComponent.setCorrectAnswer(toeicQuestion.getCorrectAnswer());
        this.answerSelectionComponent.setToeicAnswerChoices(choices);
        this.answerSelectionComponent.setToeicQuestion(toeicQuestion);
    }

    @Override
    public void showExplain() {
        this.answerSelectionComponent.setShowExplain(!this.answerSelectionComponent.isShowExplain());
    }

    @Override
    public Integer getNumberCorrectAnswer() {
        final ToeicAnswerChoice currentChoice = this.answerSelectionComponent.getCurrentChoice();
        if (currentChoice != null) {
            if (currentChoice.getLabel().equals(this.answerSelectionComponent.getCorrectAnswer())) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public Integer getTotalQuestions() {
        return 1;
    }
}
