package com.hcmute.finalproject.toeicapp.components.homepage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.common.BackButtonRoundedComponent;
import com.hcmute.finalproject.toeicapp.model.vocabulary.VocabularyTopic;
import com.hcmute.finalproject.toeicapp.model.vocabulary.VocabularyTopicStatistic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class HomePageListVocabularyComponent extends LinearLayout {
    private ListView listViewVocabs;
    private ListVocabsAdapter adapter;
    private List<VocabularyTopicStatistic> statistics;
    private OnClickBackButton onClickBackButton;

    public HomePageListVocabularyComponent(Context context) {
        this(context, null);
    }

    public HomePageListVocabularyComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomePageListVocabularyComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);
    }

    public List<VocabularyTopicStatistic> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<VocabularyTopicStatistic> statistics) {
        this.statistics = statistics;
        this.adapter.notifyDataSetChanged();
    }

    public OnClickBackButton getOnClickBackButton() {
        return onClickBackButton;
    }

    public void setOnClickBackButton(OnClickBackButton onClickBackButton) {
        this.onClickBackButton = onClickBackButton;
    }

    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_home_page_list_vocabulary, this);

        this.statistics = new ArrayList<>();
        this.listViewVocabs = view.findViewById(R.id.component_home_page_list_vocabulary_list_vocabs);

        if (this.isInEditMode()) {
            return;
        }

        this.adapter = new ListVocabsAdapter();
        this.listViewVocabs.setAdapter(this.adapter);

        final BackButtonRoundedComponent btnClickBack = findViewById(R.id.component_home_page_list_vocabulary_btn_back);
        btnClickBack.setOnClickListener(v -> {
            if (this.onClickBackButton != null) {
                this.onClickBackButton.onClick();
            }
        });
    }

    private class ListVocabsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return statistics.size();
        }

        @Override
        public Object getItem(int position) {
            return statistics.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.component_home_page_list_vocabulary_item, parent, false);

            final VocabularyTopicStatistic statistic = statistics.get(position);
            final TextView txtTopicName = view.findViewById(R.id.component_home_page_list_vocabulary_txt_topic_name);
            final TextView txtStatus = view.findViewById(R.id.component_home_page_list_vocabulary_txt_status);

            txtTopicName.setText(statistic.getTopicName());
            txtStatus.setText(statistic.getSuccess() + "/" + statistic.getTotal());

            return view;
        }
    }

    public void loadSampleStatistics() {
        this.setStatistics(this.getSampleVocabStatistic(this.getSampleTopics()));
    }

    @Deprecated
    private List<VocabularyTopic> getSampleTopics() {
        List<VocabularyTopic> topics = new ArrayList<>();

        topics.add(new VocabularyTopic("Contracts - Hợp Đồng", 12));
        topics.add(new VocabularyTopic("Marketing - Nghiên Cứu Thị Trường", 12));
        topics.add(new VocabularyTopic("Warrranties - Sự Bảo Hành", 12));
        topics.add(new VocabularyTopic("Business Planning - Kế Hoạch Kinh Doanh", 12));
        topics.add(new VocabularyTopic("Conferences - Hội Nghị", 12));

        return topics;
    }

    @Deprecated
    public List<VocabularyTopicStatistic> getSampleVocabStatistic(final List<VocabularyTopic> topics) {
        final Random randomEngine = new Random();
        List<VocabularyTopicStatistic> result = topics.stream().map(VocabularyTopicStatistic::new).collect(Collectors.toList());

        for (VocabularyTopicStatistic statistic : result) {
            statistic.setSuccess(Math.abs(randomEngine.nextInt()) % (statistic.getTotal() + 1));
        }

        return result;
    }

    public interface OnClickBackButton {
        void onClick();
    }
}
