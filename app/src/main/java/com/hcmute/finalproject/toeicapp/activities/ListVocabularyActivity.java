package com.hcmute.finalproject.toeicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.AnswerSelectionComponent;
import com.hcmute.finalproject.toeicapp.components.common.BackButtonRoundedComponent;
import com.hcmute.finalproject.toeicapp.components.homepage.HomePageListVocabularyComponent;
import com.hcmute.finalproject.toeicapp.model.vocabulary.AndroidToeicVocabTopic;
import com.hcmute.finalproject.toeicapp.model.vocabulary.AndroidToeicVocabWord;
import com.hcmute.finalproject.toeicapp.model.vocabulary.VocabularyTopicStatistic;
import com.hcmute.finalproject.toeicapp.services.storage.StorageConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListVocabularyActivity extends AppCompatActivity {
    private TextView txtVocab, txtChecked;
    private AppCompatButton btnStartLearning;
    private BackButtonRoundedComponent btnBackButton;
    private RecyclerView recylerviewListVocab;
    private List<AndroidToeicVocabWord> androidToeicVocabWords = new ArrayList<>();
    private ListVocabularyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vocabulary);

        initComponent();
    }

    private File getVocabsConfigFile() {
        File rootDirectory = StorageConfiguration.getRootDirectory(this.getApplicationContext());
        File testVocabDirectory = new File(rootDirectory, "test-vocab");
        File config = new File(testVocabDirectory, "config.json");
        return config;
    }

    private void loadListVocabs() {
        String json = null;
        adapter.notifyDataSetChanged();

        try {
            final File file = this.getVocabsConfigFile();
            final FileInputStream fileInputStream = new FileInputStream(file);
            final byte[] buffer = new byte[(int) file.length()];
            fileInputStream.read(buffer);
            json = new String(buffer, StandardCharsets.UTF_8);
            fileInputStream.close();
        } catch (IOException e) {
            return;
        }

        Bundle bundle = getIntent().getExtras();

        Gson gson = new Gson();
        List<AndroidToeicVocabTopic> topics = List.of(gson.fromJson(json, AndroidToeicVocabTopic[].class));

        for (AndroidToeicVocabTopic topic : topics) {
            if (topic.getTopicName().equals(bundle.getString("topicName"))) {
                for (AndroidToeicVocabWord word: topic.getWords()) {
                    this.androidToeicVocabWords.add(word);
                    Log.d("WORD", word.getEnglish());
                }
                break;
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void initComponent() {
        txtVocab = findViewById(R.id.activity_list_vocabulary_text_vocab);
        txtChecked = findViewById(R.id.activity_list_vocabulary_text_checked);
        btnStartLearning = findViewById(R.id.activity_list_vocabulary_button_start_learning);
        recylerviewListVocab = findViewById(R.id.activity_list_vocabulary_recyclerview_list_vocab);
        btnBackButton = findViewById(R.id.activity_list_vocabulary_btn_back);

        Bundle bundle = getIntent().getExtras();
        txtVocab.setText(bundle.getString("topicName"));
        txtChecked.setText(bundle.getString("status") + " checked");
        Toast.makeText(this, bundle.getString("topicName").split(" ")[2], Toast.LENGTH_SHORT).show();

        this.adapter = new ListVocabularyAdapter();
        recylerviewListVocab.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        this.recylerviewListVocab.setAdapter(this.adapter);

        loadListVocabs();

        for (AndroidToeicVocabWord word: androidToeicVocabWords) {
            Log.d("WORD", word.getEnglish() + ": " + word.getVietnamese());
        }

        btnBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnStartLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ListVocabularyActivity.this, "click start", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class ListVocabularyAdapter extends RecyclerView.Adapter<ListVocabularyAdapter.ListVocabularyItemViewHolder> {
        @NonNull
        @Override
        public ListVocabularyItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());

            final View view = layoutInflater.inflate(
                    R.layout.activity_list_vocabulary_item,
                    parent,
                    false
            );

            return new ListVocabularyItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListVocabularyAdapter.ListVocabularyItemViewHolder holder, int position) {
            holder.setData(androidToeicVocabWords.get(position), position);
        }

        @Override
        public int getItemCount() {
            return androidToeicVocabWords.size();
        }

        private class ListVocabularyItemViewHolder extends RecyclerView.ViewHolder {
            private final TextView txtItemVocab, txtItemMeaning, txtItemOrder;

            public ListVocabularyItemViewHolder(@NonNull View itemView) {
                super(itemView);

                this.txtItemOrder = findViewById(R.id.activity_list_vocabulary_item_text_order);
                this.txtItemVocab = findViewById(R.id.activity_list_vocabulary_item_text_vocabulary);
                this.txtItemMeaning = findViewById(R.id.activity_list_vocabulary_item_text_meaning);
            }

            private void setData(AndroidToeicVocabWord androidToeicVocabWord, int position) {
                int order = position + 1;
                Log.d("order", String.valueOf(order) + "--->" + androidToeicVocabWord.getEnglish());
                this.txtItemOrder.setText(String.valueOf(order));
                this.txtItemVocab.setText(androidToeicVocabWord.getEnglish());
                this.txtItemMeaning.setText(androidToeicVocabWord.getVietnamese());
            }
        }
    }
}