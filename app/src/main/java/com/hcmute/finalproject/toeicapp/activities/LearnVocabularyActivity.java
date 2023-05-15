package com.hcmute.finalproject.toeicapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.learnvocab.LearnVocabularyQuestionComponent;
import com.hcmute.finalproject.toeicapp.dao.ToeicVocabularyDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicVocabularyTopicDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabulary;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabularyTopic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Collectors;

public class LearnVocabularyActivity extends AppCompatActivity {
    private TextView txtTopicName;
    private LearnVocabularyQuestionComponent learnVocabularyContainer;
    private ToeicAppDatabase toeicAppDatabase;
    private ToeicVocabularyTopicDao toeicVocabularyTopicDao;
    private ToeicVocabularyDao toeicVocabularyDao;
    private List<ToeicVocabulary> rawVocabularies;
    private Queue<ToeicVocabulary> learningQueue;
    private ToeicVocabularyTopic currentTopic;
    private ToeicVocabulary currentVocabulary;
    private Integer numberOfCorrectAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_vocabulary);

        this.toeicAppDatabase = ToeicAppDatabase.getInstance(this);
        this.toeicVocabularyTopicDao = this.toeicAppDatabase.getToeicVocabularyTopicDao();
        this.toeicVocabularyDao = this.toeicAppDatabase.getToeicVocabularyDao();

        this.txtTopicName = findViewById(R.id.activity_learn_vocabulary_result_topic_name);
        this.learnVocabularyContainer = findViewById(R.id.activity_learn_vocabulary_question_container);

        this.currentTopic = this.getTopicFromIntent();
        this.loadVocabularyData();
        this.prepareVocabularies();

        this.prepareNextQuestion();
        this.learnVocabularyContainer.setOnQuestionContainerListener(new LearnVocabularyQuestionComponent.OnQuestionContainerListener() {
            @Override
            public void onNextQuestion() {
                prepareNextQuestion();
            }

            @Override
            public boolean onCheckAnswer(String userAnswer) {
                userAnswer = userAnswer.trim().toLowerCase();
                final String judgeAnswer = currentVocabulary.getEnglish().trim().toLowerCase();
                boolean result = judgeAnswer.equals(userAnswer);
                if (result)
                    numberOfCorrectAnswers++;
                return result;
            }
        });
    }

    private ToeicVocabularyTopic getTopicFromIntent() {
        final Integer topicId = getIntent().getIntExtra("topicId", -1);
        assert topicId > 0;
        return this.toeicVocabularyTopicDao.getOne(topicId);
    }

    private void loadVocabularyData() {
        this.rawVocabularies = new ArrayList<>();
        this.rawVocabularies.addAll(this.toeicVocabularyDao.getByTopicId(this.currentTopic.getId()));
    }

    private <T> void shuffleArray(List<T> list) {
        Random random = new Random();
        for (int i = 1; i < list.size(); ++i) {
            int j = Math.abs(random.nextInt()) % i;

            T left = list.get(i);
            list.set(i, list.get(j));
            list.set(j, left);
        }
    }

    private void prepareVocabularies() {
        List<ToeicVocabulary> preparedVocabularies = new ArrayList<>();
        preparedVocabularies.addAll(this.rawVocabularies);
        this.shuffleArray(preparedVocabularies);

        this.learningQueue = new LinkedList<>();
        for (ToeicVocabulary vocabulary : preparedVocabularies) {
            this.learningQueue.add(vocabulary);
        }
    }

    private void prepareNextQuestion() {
        if (this.learningQueue.size() == 0) {
            finish();
            Intent intent = new Intent(this, LearnVocabularyResultActivity.class);
            if (2 * numberOfCorrectAnswers >= rawVocabularies.size())
                intent.putExtra("mode", LearnVocabularyResultActivity.MODE_SUCCESS);
            else
                intent.putExtra("mode", LearnVocabularyResultActivity.MODE_FAILURE);
            intent.putExtra("text", numberOfCorrectAnswers + " / " + rawVocabularies.size());
            startActivity(intent);
            finish();
            return;
        }
        this.currentVocabulary = this.learningQueue.poll();
        this.learnVocabularyContainer.setViewMode(LearnVocabularyQuestionComponent.MODE_QUESTION);
        this.learnVocabularyContainer.loadVocabulary(this.currentVocabulary);
    }
}