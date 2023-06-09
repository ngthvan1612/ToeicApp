package com.hcmute.finalproject.toeicapp.components.part;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;
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
import com.hcmute.finalproject.toeicapp.services.backend.tests.ToeicTestBackendService;
import com.hcmute.finalproject.toeicapp.services.storage.StorageConfiguration;

import java.io.File;
import java.util.List;

public class PartTwoComponent extends ToeicPartComponentBase {
    private CommonHeaderComponent commonHeaderComponent;
    public AnswerSelectionComponent answerSelectionComponent;
    private AudioPlayerComponent audioPlayerComponent;

    public PartTwoComponent(Context context) {
        this(context, null);
    }

    public PartTwoComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartTwoComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setPartNumber(2);
        this.initComponent(context, attrs, defStyleAttr);

    }
    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_part_two, this);

        if (this.isInEditMode())
            return;

        this.audioPlayerComponent = view.findViewById(R.id.component_part_two_audio);
        this.answerSelectionComponent = view.findViewById(R.id.component_part_two_answer);
        super.addAnswerSelectionComponent(this.answerSelectionComponent);
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
        this.answerSelectionComponent.setQuestionTitle("Question "+ toeicQuestion.getQuestionNumber().toString());
        this.answerSelectionComponent.setCorrectAnswer(toeicQuestion.getCorrectAnswer());
        this.answerSelectionComponent.setToeicAnswerChoices(choices);
        this.answerSelectionComponent.setToeicQuestion(toeicQuestion);

        Integer audioServerId = toeicItemContentList
                .stream()
                .filter(u -> u.getContentType().equals("AUDIO"))
                .findFirst()
                .get()
                .getServerId();

        File audioFile = this.getAudioFile(audioServerId);
        this.audioPlayerComponent.loadAudioFile(audioFile);

        Log.d("LOAD_AUDIO_2", audioFile.getAbsolutePath());

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

    private File getAudioFile(Integer serverId) {
        String fileName = serverId + ".bin";
        File root = StorageConfiguration.getTestDataDirectory(this.getContext());
        return new File(root, fileName);
    }

    @Override
    public void showExplain() {
        this.answerSelectionComponent.setShowExplain(!this.answerSelectionComponent.isShowExplain());
    }

    @Override
    public Integer getTotalQuestions() {
        return 1;
    }
}
