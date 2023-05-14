package com.hcmute.finalproject.toeicapp.services.backend.tests;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Service
public class ToeicTestBackendService {
    private final Context context;
    private final SharedPreferenceService sharedPreferenceService;
    private final ToeicAppDatabase toeicAppDatabase;

    public ToeicTestBackendService(
            @NonNull Context context
    ) {
        this.toeicAppDatabase = ToeicAppDatabase.getInstance(context);
        this.sharedPreferenceService = new SharedPreferenceService(context);
        this.context = context;
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

    public interface OnBackupToeicListener {
        void prepare();
        void onSuccess();
        void onException(Exception exception);
    }
}
