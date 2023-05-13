package com.hcmute.finalproject.toeicapp.components.homepage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.hcmute.finalproject.toeicapp.R;
// import com.hcmute.finalproject.toeicapp.activities.ListVocabularyActivity;
import com.hcmute.finalproject.toeicapp.components.common.BackButtonRoundedComponent;
//import com.hcmute.finalproject.toeicapp.model.vocabulary.AndroidToeicVocabTopic;
import com.hcmute.finalproject.toeicapp.model.vocabulary.VocabularyTopic;
import com.hcmute.finalproject.toeicapp.model.vocabulary.VocabularyTopicStatistic;
import com.hcmute.finalproject.toeicapp.network.test.RetrofitTestRetrofitClient01;
import com.hcmute.finalproject.toeicapp.network.test.TestAPIService;
import com.hcmute.finalproject.toeicapp.services.storage.StorageConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageListVocabularyComponent extends LinearLayout {
    private final static String VOCAB_CONFIG_FILE = "vocab.json";
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

//        if (!this.getVocabsConfigFile().exists()) {
//            this.downloadConfigFile();
//        }

        this.loadListVocabsTest();
    }

    private void loadListVocabsTest() {
        String json = null;
        this.statistics.clear();
        adapter.notifyDataSetChanged();

        // TODO: viet lai
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

        Gson gson = new Gson();
        //List<AndroidToeicVocabTopic> topics = List.of(gson.fromJson(json, AndroidToeicVocabTopic[].class));
        //

//        this.statistics.addAll(topics.stream()
//                .map(VocabularyTopicStatistic::new).collect(Collectors.toList()));

        adapter.notifyDataSetChanged();
    }

    @Deprecated
    private void downloadConfigFile() {
        ProgressDialog progressBar = new ProgressDialog(this.getContext());
        progressBar.setTitle("Đang tải 600 từ xuống");
        TestAPIService apiService = RetrofitTestRetrofitClient01.getInstance();
        apiService.download600ToeicVocabularies().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                final ResponseBody responseBody = response.body();

                try {
                    final byte[] content = responseBody.bytes();
                    FileOutputStream outputStream = new FileOutputStream(getVocabsConfigFile());
                    outputStream.write(content);
                    outputStream.close();
                    Toast.makeText(getContext(), "Tải file thành công", Toast.LENGTH_SHORT).show();

                    loadListVocabsTest();
                } catch (IOException e) {
                    Toast.makeText(getContext(), "Tải file lỗi", Toast.LENGTH_SHORT).show();
                }

                progressBar.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Tải file lỗi", Toast.LENGTH_SHORT).show();
                progressBar.dismiss();
            }
        });
    }

    private File getVocabsConfigFile() {
        File rootDirectory = StorageConfiguration.getRootDirectory(this.getContext());
        File testVocabDirectory = new File(rootDirectory, "test-vocab");
        File config = new File(testVocabDirectory, "config.json");
        return config;
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
            //final ImageView imgView = view.findViewById(R.id.component_home_page_list_vocabulary_image_view);

            File rootDirectory = StorageConfiguration.getRootDirectory(getContext());
            File vocabTestDirectory = new File(rootDirectory, "test-vocab");
            File dataDirectory = new File(vocabTestDirectory, "data");
            //File imageFile = new File(dataDirectory, statistic.getImageFileName());
            //Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            //imgView.setImageBitmap(bitmap);

            txtTopicName.setText(statistic.getTopicName());
            txtStatus.setText(statistic.getSuccess() + "/" + statistic.getTotal());

//            view.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //Intent intent = new Intent(getContext(), ListVocabularyActivity.class);
//                    intent.putExtra("topicName", statistic.getTopicName());
//                    intent.putExtra("status", statistic.getSuccess() + "/" + statistic.getTotal());
//                    getContext().startActivity(intent);
//                }
//            });

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
    private List<VocabularyTopicStatistic> getSampleVocabStatistic(final List<VocabularyTopic> topics) {
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

    private class Toeic600Topic {
        private String name;
        private List<Toeic600Vocab> vocabs;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Toeic600Vocab> getVocabs() {
            return vocabs;
        }

        public void setVocabs(List<Toeic600Vocab> vocabs) {
            this.vocabs = vocabs;
        }
    }

    private class Toeic600Vocab {
        @SerializedName("image_url")
        private String imageUrl;
        private String text;
        private String pronunciation;
        @SerializedName("audio_url")
        private String audioUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getPronunciation() {
            return pronunciation;
        }

        public void setPronunciation(String pronunciation) {
            this.pronunciation = pronunciation;
        }

        public String getAudioUrl() {
            return audioUrl;
        }

        public void setAudioUrl(String audioUrl) {
            this.audioUrl = audioUrl;
        }
    }
}
