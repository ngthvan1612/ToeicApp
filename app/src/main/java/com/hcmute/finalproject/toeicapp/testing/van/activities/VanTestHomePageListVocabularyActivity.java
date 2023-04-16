package com.hcmute.finalproject.toeicapp.testing.van.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.homepage.HomePageListVocabularyComponent;
import com.hcmute.finalproject.toeicapp.model.vocabulary.VocabularyTopic;
import com.hcmute.finalproject.toeicapp.model.vocabulary.VocabularyTopicStatistic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class VanTestHomePageListVocabularyActivity extends AppCompatActivity {
    private final static Random randomEngine = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_test_home_page_list_vocabulary);

        HomePageListVocabularyComponent component = findViewById(R.id.activity_van_test_home_page_list_vocabulary_component);

        final List<VocabularyTopic> sampleTopics = this.getSampleTopics();
        final List<VocabularyTopicStatistic> sampleStatistic = this.getSampleVocabStatistic(sampleTopics);

        component.setStatistics(sampleStatistic);
    }

    private List<VocabularyTopic> getSampleTopics() {
        List<VocabularyTopic> topics = new ArrayList<>();

        topics.add(new VocabularyTopic("Contracts - Hợp Đồng", 12));
        topics.add(new VocabularyTopic("Marketing - Nghiên Cứu Thị Trường", 12));
        topics.add(new VocabularyTopic("Warrranties - Sự Bảo Hành", 12));
        topics.add(new VocabularyTopic("Business Planning - Kế Hoạch Kinh Doanh", 12));
        topics.add(new VocabularyTopic("Conferences - Hội Nghị", 12));

        return topics;
    }

    public List<VocabularyTopicStatistic> getSampleVocabStatistic(final List<VocabularyTopic> topics) {
        List<VocabularyTopicStatistic> result = topics.stream().map(VocabularyTopicStatistic::new).collect(Collectors.toList());

        for (VocabularyTopicStatistic statistic : result) {
            statistic.setSuccess(Math.abs(randomEngine.nextInt()) % (statistic.getTotal() + 1));
        }

        return result;
    }
}