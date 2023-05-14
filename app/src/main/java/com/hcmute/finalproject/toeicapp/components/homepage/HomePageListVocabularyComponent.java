package com.hcmute.finalproject.toeicapp.components.homepage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;
// import com.hcmute.finalproject.toeicapp.activities.ListVocabularyActivity;
import com.hcmute.finalproject.toeicapp.activities.ListVocabularyActivity;
import com.hcmute.finalproject.toeicapp.components.common.BackButtonRoundedComponent;
//import com.hcmute.finalproject.toeicapp.services.backend.vocabs.model.AndroidToeicVocabTopic;
import com.hcmute.finalproject.toeicapp.dao.ToeicVocabularyTopicDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabularyTopic;
import com.hcmute.finalproject.toeicapp.model.vocabulary.VocabularyTopicStatistic;
import com.hcmute.finalproject.toeicapp.services.backend.vocabs.ToeicVocabularyBackendService;
import com.hcmute.finalproject.toeicapp.services.storage.StorageConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomePageListVocabularyComponent extends LinearLayout {
    private final static String VOCAB_CONFIG_FILE = "vocab.json";
    private ToeicAppDatabase toeicAppDatabase;
    private ListView listViewVocabs;
    private ListVocabsAdapter adapter;
    private List<VocabularyTopicStatistic> statistics;
    private OnClickBackButton onClickBackButton;
    private ToeicVocabularyBackendService toeicVocabularyBackendService;

    public HomePageListVocabularyComponent(Context context) {
        this(context, null);
    }

    public HomePageListVocabularyComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomePageListVocabularyComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.toeicAppDatabase = ToeicAppDatabase.getInstance(getContext());
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

        this.toeicAppDatabase = ToeicAppDatabase.getInstance(context);
        this.toeicVocabularyBackendService = new ToeicVocabularyBackendService(context);

        this.adapter = new ListVocabsAdapter();
        this.listViewVocabs.setAdapter(this.adapter);

        final BackButtonRoundedComponent btnClickBack = findViewById(R.id.component_home_page_list_vocabulary_btn_back);
        btnClickBack.setOnClickListener(v -> {
            if (this.onClickBackButton != null) {
                this.onClickBackButton.onClick();
            }
        });

        this.checkListVocabTestsIsUpdated();
    }

    public void checkListVocabTestsIsUpdated() {
        ProgressDialog progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setTitle("Đang tải từ vựng");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);

        this.toeicVocabularyBackendService.checkToeicTestDatabaseIsUpdated(new ToeicVocabularyBackendService.OnBackupToeicListener() {
            @Override
            public void prepare() {
                progressDialog.show();
            }

            @Override
            public void onSuccess() {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Vocabulary is up to date", Toast.LENGTH_SHORT).show();
                loadListVocabsTest();
            }

            @Override
            public void onUpdateMessage(String message) {
                progressDialog.setMessage(message);
            }

            @Override
            public void onUpdateProgress(Integer progress) {
                progressDialog.setProgress(progress);
            }

            @Override
            public void onException(Exception exception) {
                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadListVocabsTest() {
        ToeicVocabularyTopicDao topicDao = toeicAppDatabase.getToeicVocabularyTopicDao();
        this.statistics.clear();

        List<ToeicVocabularyTopic> topics = topicDao.getAll();
        for (ToeicVocabularyTopic topic: topics) {
            VocabularyTopicStatistic vocabularyTopicStatistic = new VocabularyTopicStatistic();
            vocabularyTopicStatistic.setTopicName(topic.getName());
            vocabularyTopicStatistic.setSuccess(0);
            vocabularyTopicStatistic.setTotal(10); //TODO
            vocabularyTopicStatistic.setImageFileName(topic.getImageFileName());

            this.statistics.add(vocabularyTopicStatistic);
            Log.d("statistic", vocabularyTopicStatistic.getTopicName());
        }

        adapter.notifyDataSetChanged();
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
            final ImageView imgView = view.findViewById(R.id.component_home_page_list_vocabulary_image_view);

            File rootDirectory = StorageConfiguration.getRootDirectory(getContext());
            File vocabTestDirectory = new File(rootDirectory, "test-vocab");
            File dataDirectory = StorageConfiguration.getVocabularyDataDirectory(getContext().getApplicationContext());
            File imageFile = new File(dataDirectory, statistic.getImageFileName());
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            imgView.setImageBitmap(bitmap);

            txtTopicName.setText(statistic.getTopicName());
            txtStatus.setText(statistic.getSuccess() + "/" + statistic.getTotal());

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ListVocabularyActivity.class);
                    intent.putExtra("topicName", statistic.getTopicName());
                    intent.putExtra("status", statistic.getSuccess() + "/" + statistic.getTotal());
                    getContext().startActivity(intent);
                }
            });

            return view;
        }
    }

    public interface OnClickBackButton {
        void onClick();
    }
}
