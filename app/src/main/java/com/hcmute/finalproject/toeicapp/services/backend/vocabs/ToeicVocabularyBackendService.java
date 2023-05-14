package com.hcmute.finalproject.toeicapp.services.backend.vocabs;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.hcmute.finalproject.toeicapp.dao.ToeicVocabularyDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicVocabularyTopicDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabulary;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabularyTopic;
import com.hcmute.finalproject.toeicapp.services.backend.vocabs.model.AndroidToeicVocabTopic;
import com.hcmute.finalproject.toeicapp.services.backend.vocabs.model.AndroidToeicVocabWord;
import com.hcmute.finalproject.toeicapp.services.base.Service;
import com.hcmute.finalproject.toeicapp.services.storage.DownloadFileCallback;
import com.hcmute.finalproject.toeicapp.services.storage.DownloadFileService;
import com.hcmute.finalproject.toeicapp.services.storage.StorageConfiguration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class ToeicVocabularyBackendService {
    private final Context context;
    private final ToeicAppDatabase toeicAppDatabase;
    private final DownloadFileService downloadFileService;

    public ToeicVocabularyBackendService(
            @NonNull Context context
    ) {
        this.downloadFileService = new DownloadFileService(context);
        this.toeicAppDatabase = ToeicAppDatabase.getInstance(context);
        this.context = context;
    }

    public void checkToeicTestDatabaseIsUpdated(
            @NonNull OnBackupToeicListener listener
    ) {
        final ToeicVocabularyTopicDao topicDao = this.toeicAppDatabase.getToeicVocabularyTopicDao();

        if (topicDao.getAll().size() > 0) {
            listener.onSuccess();
            return;
        }

        this.downloadVocabulary(listener);
    }

    private void runUIThread(Runnable runnable) {
        ((Activity)context).runOnUiThread(runnable);
    }

    private void downloadVocabulary(
            @NonNull OnBackupToeicListener listener
    ) {
        listener.prepare();

        downloadFileService.downloadFileByteArrayAsync("https://toeic-app.uteoj.com/api/android/toeic-test/get-vocabs-data-zip", new DownloadFileCallback<byte[]>() {
            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
            @Override
            public void onSuccess(byte[] toeicStorage) {
                listener.onUpdateMessage("Processing...");
                new Thread(() -> {
                    ToeicVocabularyTopicDao topicDao = toeicAppDatabase.getToeicVocabularyTopicDao();
                    ToeicVocabularyDao vocabularyDao = toeicAppDatabase.getToeicVocabularyDao();
                    List<AndroidToeicVocabTopic> topics = new ArrayList<>();

                    final File vocabRoot = StorageConfiguration.getVocabularyDataDirectory(context.getApplicationContext());

                    try {
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(toeicStorage);
                        ZipInputStream zipInputStream = new ZipInputStream(byteArrayInputStream);
                        ZipEntry zipEntry = null;
                        while ((zipEntry =zipInputStream.getNextEntry()) != null) {
                            if (zipEntry.getName().equals("config.json")) {
                                ByteArrayOutputStream output = new ByteArrayOutputStream();
                                byte[] buffer = new byte[1024];
                                int n = 0;
                                while ((n = zipInputStream.read(buffer, 0, 1024)) != -1) {
                                    output.write(buffer, 0, n);
                                }
                                String json = new String(output.toByteArray(), StandardCharsets.UTF_8);
                                Gson gson = new Gson();
                                topics = List.of(gson.fromJson(json, AndroidToeicVocabTopic[].class));
                            }
                            else {
                                ByteArrayOutputStream output = new ByteArrayOutputStream();
                                byte[] buffer = new byte[1024];
                                int n = 0;
                                while ((n = zipInputStream.read(buffer, 0, 1024)) != -1) {
                                    output.write(buffer, 0, n);
                                }
                                final String fileName = zipEntry.getName().split("/")[1];
                                File outputFile = new File(vocabRoot, fileName);
                                FileOutputStream fos = new FileOutputStream(outputFile);
                                fos.write(output.toByteArray());
                                fos.close();
                            }
                        }
                        zipInputStream.close();

                        runUIThread(() -> listener.onUpdateMessage("Updating..."));

                        List<ToeicVocabularyTopic> vocabularyTopicList = topicDao.getAll();
                        for(ToeicVocabularyTopic topic: vocabularyTopicList) {
                            topicDao.delete(topic);
                        }

                        for (AndroidToeicVocabTopic topic: topics) {
                            ToeicVocabularyTopic toeicVocabularyTopic = new ToeicVocabularyTopic();
                            toeicVocabularyTopic.setServerId(topic.getServerId());
                            toeicVocabularyTopic.setName(topic.getTopicName());
                            toeicVocabularyTopic.setImageFileName(topic.getImageFileName());

                            final Integer newTopicId = Math.toIntExact(topicDao.insert(toeicVocabularyTopic).get(0));

                            List<AndroidToeicVocabWord> androidToeicVocabWords = topic.getWords();
                            for (AndroidToeicVocabWord word: androidToeicVocabWords) {
                                ToeicVocabulary toeicVocabulary = new ToeicVocabulary();
                                toeicVocabulary.setServerId(word.getServerId());
                                toeicVocabulary.setEnglish(word.getEnglish());
                                toeicVocabulary.setVietnamese(word.getVietnamese());
                                toeicVocabulary.setPronunciation(word.getPronounce());
                                toeicVocabulary.setExampleEnglish(word.getExampleEnglish());
                                toeicVocabulary.setExampleVietnamese(word.getExampleVietnamese());
                                toeicVocabulary.setImagePath(word.getImageFilename());
                                toeicVocabulary.setAudioPath(word.getAudioFileName());
                                toeicVocabulary.setTopicId(newTopicId);

                                vocabularyDao.insert(toeicVocabulary);
                            }
                        }

                        runUIThread(listener::onSuccess);
                    } catch (IOException e) {
                        runUIThread(() -> listener.onException(e));
                    }
                }).start();
            }

            @Override
            public void onProgressUpdate(int percent) {
                listener.onUpdateProgress(percent);
            }

            @Override
            public void onError(String error) {
                listener.onException(new Exception(error));
            }
        });
    }

    public interface OnBackupToeicListener {
        void prepare();
        void onSuccess();
        void onUpdateMessage(String message);
        void onUpdateProgress(Integer progress);
        void onException(Exception exception);
    }
}
