package com.hcmute.finalproject.toeicapp.components.part;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.AnswerSelectionComponent;
import com.hcmute.finalproject.toeicapp.components.common.CommonHeaderComponent;
import com.hcmute.finalproject.toeicapp.components.media.AudioPlayerComponent;
import com.hcmute.finalproject.toeicapp.dao.ToeicAnswerChoiceDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicItemContentDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicQuestionDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicQuestionGroupDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.ToeicAnswerChoice;
import com.hcmute.finalproject.toeicapp.entities.ToeicItemContent;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestion;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.services.storage.StorageConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PartFourComponent extends ToeicPartComponentBase{
    private AudioPlayerComponent audioPlayerComponent;
    private List<ToeicQuestion> questions;

    private RecyclerView recyclerView;

    private ToeicQuestionDao toeicQuestionDao;
    private ToeicAnswerChoiceDao toeicAnswerChoiceDao;
    private ToeicItemContentDao toeicItemContentDao;
    private ToeicQuestionGroupDao toeicQuestionGroupDao;
    private ListQuestionPartFourAdapter listQuestionAdapter;
    private boolean isExplainShowed = false;
    private List<AnswerSelectionComponent> answerSelectionComponentList = new ArrayList<>();
    public PartFourComponent(Context context) {
        this(context, null);
    }

    public PartFourComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartFourComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);

    }
    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_part_four, this);
        audioPlayerComponent = findViewById(R.id.component_part_four_audio);
        recyclerView = findViewById(R.id.component_part_four_recyclerview);

        if (this.isInEditMode()) {
            return;
        }

        ToeicAppDatabase toeicAppDatabase = ToeicAppDatabase.getInstance(getContext());
        this.toeicQuestionDao = toeicAppDatabase.getToeicQuestionDao();
        this.toeicQuestionGroupDao = toeicAppDatabase.getToeicQuestionGroupDao();
        this.toeicAnswerChoiceDao = toeicAppDatabase.getToeicAnswerChoiceDao();
        this.toeicItemContentDao = toeicAppDatabase.getToeicItemContentDao();
        this.questions = new ArrayList<>();

        this.listQuestionAdapter = new ListQuestionPartFourAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(this.listQuestionAdapter);

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
        this.questions.clear();
        this.questions.addAll(
                this.toeicQuestionDao.getToeicQuestionByQuestionGroppId(toeicQuestionGroup.getId())
        );

        List<ToeicItemContent> toeicItemContentList = toeicItemContentDao.getItemContentByGroupId(toeicQuestionGroup.getId());

        ToeicItemContent itemContentAudio = toeicItemContentList.stream().filter(a -> a.getContentType().equals("AUDIO")).findAny().get();
        File audioFile = this.getAudioFile(itemContentAudio.getServerId());
        audioPlayerComponent.loadAudioFile(audioFile);
        audioPlayerComponent.setCurrentVolume(1.0f);

        this.listQuestionAdapter.notifyDataSetChanged();
    }

    private File getAudioFile(Integer serverId) {
        String fileName = serverId + ".bin";
        File root = StorageConfiguration.getTestDataDirectory(this.getContext());
        return new File(root, fileName);
    }

    private class ListQuestionPartFourAdapter extends RecyclerView.Adapter<PartFourComponent.ListQuestionPartFourAdapter.ListQuestionItemHodler> {

        @NonNull
        @Override
        public ListQuestionPartFourAdapter.ListQuestionItemHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            AnswerSelectionComponent answerSelectionComponent = new AnswerSelectionComponent(getContext());
            answerSelectionComponent.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            LayoutParams layoutParams = (LayoutParams) answerSelectionComponent.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 20);
            answerSelectionComponentList.add(answerSelectionComponent);
            return new ListQuestionPartFourAdapter.ListQuestionItemHodler(answerSelectionComponent);
        }

        @Override
        public void onBindViewHolder(@NonNull PartFourComponent.ListQuestionPartFourAdapter.ListQuestionItemHodler holder, int position) {
            holder.setData(questions.get(position), position);
        }

        @Override
        public int getItemCount() {
            return questions.size();
        }

        public class ListQuestionItemHodler extends RecyclerView.ViewHolder {

            public ListQuestionItemHodler(@NonNull View itemView) {
                super(itemView);
            }

            public void setData(ToeicQuestion question, int position) {
                final List<ToeicAnswerChoice> choices = toeicAnswerChoiceDao.getByQuestionId(question.getId());
                AnswerSelectionComponent component = (AnswerSelectionComponent)this.itemView;
                component.setToeicAnswerChoices(choices);
                assert question.getCorrectAnswer() != null && question.getCorrectAnswer().length() == 1;
                component.setCorrectAnswer(question.getCorrectAnswer());
                component.setQuestionTitle(question.getQuestionNumber() + ". " + question.getContent());

                component.setShowExplain(isExplainShowed);
            }
        }
    }

    @Override
    public void showExplain() {
        for (AnswerSelectionComponent answerSelectionComponent : answerSelectionComponentList) {
            answerSelectionComponent.setShowExplain(true);
        }

        isExplainShowed = true;
    }

    @Override
    public String getAnswer() {
        return null;
    }

    @Override
    public String getSelectedChoice() {
        return null;
    }

    @Override
    public Integer getNumberCorrectAnswer() {
        assert this.questions.size() >= answerSelectionComponentList.size();
        int counter = 0;
        for (AnswerSelectionComponent component : answerSelectionComponentList) {
            final ToeicAnswerChoice currentChoice = component.getCurrentChoice();
            if (currentChoice != null) {
                if (currentChoice.getLabel().equals(component.getCorrectAnswer())) {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    public Integer getTotalQuestions() {
        return this.questions.size();
    }
}