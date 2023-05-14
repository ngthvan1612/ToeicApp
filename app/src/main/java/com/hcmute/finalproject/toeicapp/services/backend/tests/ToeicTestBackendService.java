package com.hcmute.finalproject.toeicapp.services.backend.tests;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.hcmute.finalproject.toeicapp.dao.ToeicAnswerChoiceDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicFullTestDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicItemContentDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicPartDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicQuestionDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicQuestionGroupDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.ToeicAnswerChoice;
import com.hcmute.finalproject.toeicapp.entities.ToeicFullTest;
import com.hcmute.finalproject.toeicapp.entities.ToeicItemContent;
import com.hcmute.finalproject.toeicapp.entities.ToeicPart;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestion;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.network.APIToeicTest;
import com.hcmute.finalproject.toeicapp.network.downloadfile.DownloadFileWithProgressService;
import com.hcmute.finalproject.toeicapp.network.downloadfile.OnDownloadListener;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.AndroidAnswerChoice;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.AndroidItemContent;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.AndroidToeicFullTest;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.AndroidToeicPart;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.AndroidToeicQuestion;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.AndroidToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.AndroidToeicTestsResponse;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.CheckSumStringResponse;
import com.hcmute.finalproject.toeicapp.services.base.Service;
import com.hcmute.finalproject.toeicapp.services.sharedpreference.SharedPreferenceService;
import com.hcmute.finalproject.toeicapp.services.storage.StorageConfiguration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Service
public class ToeicTestBackendService {
    private final Context context;
    private final SharedPreferenceService sharedPreferenceService;
    private final ToeicAppDatabase toeicAppDatabase;
    private DownloadFileWithProgressService downloadFileWithProgressService;

    public ToeicTestBackendService(
            @NonNull Context context
    ) {
        this.toeicAppDatabase = ToeicAppDatabase.getInstance(context);
        this.sharedPreferenceService = new SharedPreferenceService(context);
        this.context = context;
        this.downloadFileWithProgressService = new DownloadFileWithProgressService(context);
    }

    private void backupNewDatabaseFromServer(
            @NonNull List<AndroidToeicFullTest> tests
    ) {
        final ToeicFullTestDao testDao = toeicAppDatabase.getToeicFullTestDao();
        final ToeicPartDao partDao = toeicAppDatabase.getToeicPartDao();
        final ToeicQuestionGroupDao groupDao = toeicAppDatabase.getToeicQuestionGroupDao();
        final ToeicQuestionDao questionDao = toeicAppDatabase.getToeicQuestionDao();
        final ToeicAnswerChoiceDao answerChoiceDao = toeicAppDatabase.getToeicAnswerChoiceDao();
        final ToeicItemContentDao toeicItemContentDao = toeicAppDatabase.getToeicItemContentDao();

        // Clean all database
        List<ToeicFullTest> preparedDeleteTests = testDao.getAll();
        for (ToeicFullTest deleteTest : preparedDeleteTests) {
            testDao.delete(deleteTest);
        }

        // Write new data
        for (AndroidToeicFullTest testBackend : tests) {
            ToeicFullTest testEntity = new ToeicFullTest();
            testEntity.setServerId(testBackend.getServerId());
            final Integer newTestId = Math.toIntExact(testDao.insert(testEntity).get(0));

            for (AndroidToeicPart partBackend : testBackend.getParts()) {
                ToeicPart partEntity = new ToeicPart();
                partEntity.setServerId(partBackend.getServerId());
                partEntity.setPartNumber(partBackend.getPartNumber());
                partEntity.setToeicFullTestId(newTestId);
                final Integer newPartId = Math.toIntExact(partDao.insert(partEntity).get(0));

                for (AndroidToeicQuestionGroup questionGroupBackend : partBackend.getGroups()) {
                    ToeicQuestionGroup groupEntity = new ToeicQuestionGroup();
                    groupEntity.setServerId(questionGroupBackend.getServerId());
                    groupEntity.setToeicPartId(newPartId);
                    final Integer newGroupId = Math.toIntExact(groupDao.insert(groupEntity).get(0));

                    for (AndroidToeicQuestion questionBackend : questionGroupBackend.getQuestions()) {
                        ToeicQuestion questionEntity = new ToeicQuestion();
                        questionEntity.setContent(questionBackend.getContent());
                        questionEntity.setCorrectAnswer(questionBackend.getContent());
                        questionEntity.setServerId(questionBackend.getServerId());
                        questionEntity.setToeicQuestionGroupId(newGroupId);
                        questionEntity.setQuestionNumber(questionBackend.getQuestionNumber());
                        final Integer newQuestionId = Math.toIntExact(questionDao.insert(questionEntity).get(0));

                        for (AndroidAnswerChoice answerChoiceBackend : questionBackend.getChoices()) {
                            ToeicAnswerChoice answerChoiceEntity = new ToeicAnswerChoice();
                            answerChoiceEntity.setContent(answerChoiceBackend.getContent());
                            answerChoiceEntity.setExplain(answerChoiceBackend.getExplain());
                            answerChoiceEntity.setServerId(answerChoiceBackend.getServerId());
                            answerChoiceEntity.setLabel(answerChoiceBackend.getLabel());
                            answerChoiceEntity.setToeicQuestionId(newQuestionId);

                            answerChoiceDao.insert(answerChoiceEntity);
                        }
                    }

                    // Question Contents
                    if (questionGroupBackend.getQuestionContents() != null) {
                        for (AndroidItemContent itemContentBackend : questionGroupBackend.getQuestionContents()) {
                            ToeicItemContent itemContentEntity = new ToeicItemContent();
                            itemContentEntity.setContent(itemContentBackend.getRawContent());
                            itemContentEntity.setContentType(itemContentBackend.getContentType());
                            itemContentEntity.setServerId(itemContentBackend.getStorageServerId());
                            itemContentEntity.setToeicQuestionGroupEntityQuestionContentId(newGroupId);

                            toeicItemContentDao.insert(itemContentEntity);
                        }
                    }

                    if (questionGroupBackend.getTranscriptContents() != null) {
                        for (AndroidItemContent itemContentBackend : questionGroupBackend.getTranscriptContents()) {
                            ToeicItemContent itemContentEntity = new ToeicItemContent();
                            itemContentEntity.setContent(itemContentBackend.getRawContent());
                            itemContentEntity.setContentType(itemContentBackend.getContentType());
                            itemContentEntity.setServerId(itemContentBackend.getStorageServerId());
                            itemContentEntity.setToeicQuestionGroupEntityTranscriptId(newGroupId);

                            toeicItemContentDao.insert(itemContentEntity);
                        }
                    }
                }
            }
        }
    }

    private void getNewToeicDatabaseFromServer(
            @NonNull OnBackupToeicListener listener
    ) {
        listener.prepare();
        APIToeicTest.getInstance().getTestData().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<AndroidToeicTestsResponse> call, Response<AndroidToeicTestsResponse> response) {
                final AndroidToeicTestsResponse androidToeicTestsResponse = response.body();
                backupNewDatabaseFromServer(androidToeicTestsResponse.getData());
                listener.onSuccess();
            }

            @Override
            public void onFailure(Call<AndroidToeicTestsResponse> call, Throwable t) {
                listener.onException(new Exception(t.getMessage()));
            }
        });
    }

    public void checkToeicTestDatabaseIsUpdated(
            @NonNull OnBackupToeicListener listener
    ) {
        listener.prepare();

        APIToeicTest.getInstance().getTestDataCheckSumString().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<CheckSumStringResponse> call, Response<CheckSumStringResponse> response) {
                final CheckSumStringResponse checkSumStringResponse = response.body();
                final String currentCheckSum = sharedPreferenceService.getPrefToeicTestCheckSum();

                String newChecksum = checkSumStringResponse.getData();

                if (!currentCheckSum.equals(newChecksum)) {
                    getNewToeicDatabaseFromServer(new OnBackupToeicListener() {
                        @Override
                        public void prepare() {
                            // Nothing
                        }

                        @Override
                        public void onSuccess() {
                            listener.onSuccess();
                            sharedPreferenceService.setPrefToeicTestCheckSum(newChecksum);
                        }

                        @Override
                        public void onException(Exception exception) {
                            listener.onException(exception);
                        }
                    });
                } else {
                    listener.onSuccess();
                }
            }

            @Override
            public void onFailure(Call<CheckSumStringResponse> call, Throwable t) {
                listener.onException(new Exception(t.getMessage()));
            }
        });
    }

    private List<Integer> getAllToeicStorageIdsByPartServerId(Integer partServerId) {
        final ToeicPartDao partDao = toeicAppDatabase.getToeicPartDao();
        final ToeicQuestionGroupDao toeicQuestionGroupDao = toeicAppDatabase.getToeicQuestionGroupDao();
        final ToeicItemContentDao toeicItemContentDao = toeicAppDatabase.getToeicItemContentDao();
        final ToeicPart toeicPart = partDao.getToeicPartByServerId(partServerId);
        Log.d("toeicPart", toeicPart.getPartNumber().toString());
        final List<ToeicQuestionGroup> toeicQuestionGroups = toeicQuestionGroupDao.getGroupsByPartId(toeicPart.getId());
        final List<ToeicItemContent> itemContents = new ArrayList<>();

        for (ToeicQuestionGroup toeicQuestionGroupItem : toeicQuestionGroups) {
            final List<ToeicItemContent> temps =
                    toeicItemContentDao.getItemContentByGroupId(toeicQuestionGroupItem.getId());
            itemContents.addAll(temps);
        }

        return itemContents.stream().map(item -> item.getId()).collect(Collectors.toList());
    }

    public void downloadPartStorageData(
            @NonNull Integer partServerId,
            @NonNull OnBackupToeicListener listener
    ) {
        listener.prepare();

        final List<Integer> ids = this.getAllToeicStorageIdsByPartServerId(partServerId);
        final Map<String, Object> postData = new HashMap<>();

        postData.put("storageIdList", ids);

        if (ids.size() == 0) {
            listener.onSuccess();
            return;
        }

        this.downloadFileWithProgressService.downloadFileAsync(
                DownloadFileWithProgressService.METHOD_POST,
                "https://toeic-app.uteoj.com/api/toeic/toeic-storage/get-list-storage",
                postData,
                new OnDownloadListener<byte[]>() {
                    @Override
                    public void onProgressUpdate(@NonNull Integer percent) {
                        listener.onUpdateProgress(percent);
                    }

                    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                    @Override
                    public void onSuccess(@NonNull byte[] bytes) {
                        final File testStorageRootDir = StorageConfiguration.getTestDataDirectory(context);
                        try {
                            ZipInputStream zipInputStream = new ZipInputStream(
                                    new ByteArrayInputStream(bytes)
                            );
                            ZipEntry zipEntry = null;
                            Map<Integer, byte[]> dict = new HashMap<>();

                            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                                try {
                                    Integer id = Integer.parseInt(zipEntry.getName().split("\\.")[0]);
                                    byte[] data = zipInputStream.readAllBytes();
                                    dict.put(id, data);
                                }
                                catch (RuntimeException | IOException e) {
                                    listener.onException(new Exception("WRONG DATA FORMAT"));
                                }
                            }

                            zipInputStream.close();

                            for (Integer id : ids) {
                                if (!dict.containsKey(id)) {
                                    listener.onException(new Exception("MISSING DOWNLOADED DATA"));
                                }
                            }

                            for (Map.Entry<Integer, byte[]> entry : dict.entrySet()) {
                                final File outputFile = new File(testStorageRootDir, entry.getKey() + ".bin");
                                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                                fileOutputStream.write(entry.getValue());
                                fileOutputStream.close();
                                Log.d("WRITE_F", entry.getKey() + " " + outputFile.getAbsoluteFile());
                            }

                            listener.onSuccess();
                        }
                        catch (IOException e) {
                            listener.onException(e);
                        }
                    }

                    @Override
                    public void onError(@NonNull Exception exception) {
                        listener.onException(exception);
                    }
                }
        );
    }

    public interface OnBackupToeicListener {
        void prepare();
        void onSuccess();
        default void onUpdateProgress(int progress) { }
        void onException(Exception exception);
    }
}
