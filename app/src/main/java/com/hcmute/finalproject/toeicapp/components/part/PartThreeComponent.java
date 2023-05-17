package com.hcmute.finalproject.toeicapp.components.part;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.AnswerSelectionComponent;
import com.hcmute.finalproject.toeicapp.components.QuestionSentenceComponent;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PartThreeComponent extends ToeicPartComponentBase {
    private static final int TYPE_QUESTION_SELECTION = 2;
    private static final int TYPE_QUESTION_TRANSCRIPT = 3;
    private ToeicItemContent itemTranscript;
    private List<RenderItem> renderItems = new ArrayList<>();
    private AudioPlayerComponent audioPlayerComponent;
    private List<ToeicQuestion> questions;

    private RecyclerView recyclerView;

    private ToeicQuestionDao toeicQuestionDao;
    private ToeicQuestionGroupDao toeicQuestionGroupDao;
    private ToeicAnswerChoiceDao toeicAnswerChoiceDao;
    private ToeicItemContentDao toeicItemContentDao;
    private ListQuestionAdapter listQuestionAdapter;
    private boolean isExplainShowed = false;
    private List<AnswerSelectionComponent> answerSelectionComponentList = new ArrayList<>();
    private QuestionSentenceComponent qSentenceComponent;

    public PartThreeComponent(Context context) {
        this(context, null);
    }

    public PartThreeComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartThreeComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setPartNumber(3);
        this.initComponent(context, attrs, defStyleAttr);

    }

    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_part_three, this);
        audioPlayerComponent = findViewById(R.id.component_part_three_audio);
        this.recyclerView = view.findViewById(R.id.component_part_three_recyclerview);

        if (this.isInEditMode()) {
            return;
        }

        ToeicAppDatabase toeicAppDatabase = ToeicAppDatabase.getInstance(getContext());
        this.toeicQuestionDao = toeicAppDatabase.getToeicQuestionDao();
        this.toeicQuestionGroupDao = toeicAppDatabase.getToeicQuestionGroupDao();
        this.toeicAnswerChoiceDao = toeicAppDatabase.getToeicAnswerChoiceDao();
        this.toeicItemContentDao = toeicAppDatabase.getToeicItemContentDao();
        this.questions = new ArrayList<>();

        this.listQuestionAdapter = new ListQuestionAdapter();
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

        itemTranscript = toeicItemContentList.stream().filter(a ->a.getContentType().equals("HTML")).findAny().get();

        ToeicItemContent itemContentAudio = toeicItemContentList.stream().filter(a -> a.getContentType().equals("AUDIO")).findAny().get();
        File audioFile = this.getAudioFile(itemContentAudio.getServerId());
        audioPlayerComponent.loadAudioFile(audioFile);
        audioPlayerComponent.setCurrentVolume(1.0f);

        if (itemTranscript != null) {
            RenderItem renderItem = new RenderItem();
            renderItem.setType(PartThreeComponent.TYPE_QUESTION_TRANSCRIPT);
            renderItem.setData(itemTranscript);
            renderItems.add(renderItem);
        }

        for (ToeicQuestion question: questions) {
            RenderItem renderItem = new RenderItem();
            renderItem.setType(PartThreeComponent.TYPE_QUESTION_SELECTION);
            renderItem.setData(question);
            renderItems.add(renderItem);
        }

        this.listQuestionAdapter.notifyDataSetChanged();
    }

    @Override
    public void showExplain() {
        for (AnswerSelectionComponent answerSelectionComponent : answerSelectionComponentList) {
            answerSelectionComponent.setShowExplain(true);
        }
        qSentenceComponent.setShowTranscript(true);

        isExplainShowed = true;
    }

    private File getAudioFile(Integer serverId) {
        String fileName = serverId + ".bin";
        File root = StorageConfiguration.getTestDataDirectory(this.getContext());
        return new File(root, fileName);
    }

    private class ListQuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == TYPE_QUESTION_TRANSCRIPT) {
                QuestionSentenceComponent questionSentenceComponent = new QuestionSentenceComponent(getContext());
                questionSentenceComponent.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                questionSentenceComponent.setTitle(TYPE_QUESTION_TRANSCRIPT);
                LayoutParams layoutParams = (LayoutParams) questionSentenceComponent.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 20);
                qSentenceComponent = questionSentenceComponent;
                return new TranscriptItemHolder(questionSentenceComponent);
            } else {
                AnswerSelectionComponent answerSelectionComponent = new AnswerSelectionComponent(getContext());
                answerSelectionComponent.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                LayoutParams layoutParams = (LayoutParams) answerSelectionComponent.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 20);
                answerSelectionComponentList.add(answerSelectionComponent);
                addAnswerSelectionComponent(answerSelectionComponent);
                return new ListQuestionItemHodler(answerSelectionComponent);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (renderItems.get(position).getType() == TYPE_QUESTION_TRANSCRIPT) {
                TranscriptItemHolder contentHolder = new TranscriptItemHolder(holder.itemView);
                contentHolder.setTranscriptContent((ToeicItemContent) renderItems.get(position).getData(), position);
            } else {
                ListQuestionItemHodler itemHolder = new ListQuestionItemHodler(holder.itemView);
                itemHolder.setData((ToeicQuestion) renderItems.get(position).getData(), position);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return renderItems.get(position).getType();
        }


        @Override
        public int getItemCount() {
            return renderItems.size();
        }

        public class TranscriptItemHolder extends RecyclerView.ViewHolder {

            public TranscriptItemHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void setTranscriptContent(ToeicItemContent toeicItemContent, int position) {
                QuestionSentenceComponent questionSentenceComponent1 = (QuestionSentenceComponent)this.itemView;
                //TODO
                if (toeicItemContent.getContent() != null) {
                    questionSentenceComponent1.setQuestionDescription(toeicItemContent.getContent().replace("\n", ""));
                    questionSentenceComponent1.setShowTranscript(isExplainShowed);
                }
            }
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
                component.setToeicQuestion(question);
                component.setQuestionTitle(question.getQuestionNumber() + ". " + question.getContent());
                component.setToeicQuestion(question);

                component.setShowExplain(isExplainShowed);
            }
        }
    }

    @Override
    public Integer getTotalQuestions() {
        return this.questions.size();
    }

    private class RenderItem {
        private int type;
        private Object data;

        public RenderItem() { }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
