package com.hcmute.finalproject.toeicapp;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.gson.Gson;
import com.hcmute.finalproject.toeicapp.activities.HomeActivity;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.services.backend.tests.ToeicTestBackendService;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.AndroidToeicFullTest;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.AndroidToeicTestsResponse;
import com.hcmute.finalproject.toeicapp.services.backend.vocabs.ToeicVocabularyBackendService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CountDownLatch;

@RunWith(AndroidJUnit4.class)
public class SyncUnitTest {
    @Rule
    public ActivityTestRule<HomeActivity> activityRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void test_toeic_test_sync() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        Context context = activityRule.getActivity();
        ToeicTestBackendService service = new ToeicTestBackendService(context);
        final boolean[] result = {false};
        service.checkToeicTestDatabaseIsUpdated(new ToeicTestBackendService.OnBackupToeicListener() {
            @Override
            public void prepare() {

            }

            @Override
            public void onSuccess() {
                result[0] = true;
                signal.countDown();
            }

            @Override
            public void onException(Exception exception) {
                signal.countDown();
            }
        });

        signal.await();

        String str="https://toeic-app.uteoj.com/api/android/toeic-test/get-tests-data";
        URLConnection urlConn = null;
        BufferedReader bufferedReader = null;
        try
        {
            URL url = new URL(str);
            urlConn = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line);
            }

            Gson gson = new Gson();
            AndroidToeicTestsResponse response = gson.fromJson(stringBuffer.toString(), AndroidToeicTestsResponse.class);
            ToeicAppDatabase toeicAppDatabase = ToeicAppDatabase.getInstance(context);
            assert toeicAppDatabase.getToeicFullTestDao().getAll().size() == response.getData().size();
        }
        catch(Exception ex)
        {
            result[0] = false;
        }

        assert result[0];
    }

    @Test
    public void test_vocab_sync() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        Context context = activityRule.getActivity();
        ToeicVocabularyBackendService service = new ToeicVocabularyBackendService(context);
        ToeicAppDatabase toeicAppDatabase = ToeicAppDatabase.getInstance(context);
        final boolean[] result = {false};
        service.checkToeicTestDatabaseIsUpdated(new ToeicVocabularyBackendService.OnBackupToeicListener() {
            @Override
            public void prepare() {

            }

            @Override
            public void onSuccess() {
                result[0] = true;
                signal.countDown();
            }

            @Override
            public void onUpdateMessage(String message) {

            }

            @Override
            public void onUpdateProgress(Integer progress) {

            }

            @Override
            public void onException(Exception exception) {

            }
        });

        signal.await();
        assert toeicAppDatabase.getToeicVocabularyTopicDao().getAll().size() == 50;
        assert toeicAppDatabase.getToeicVocabularyDao().getAll().size() == 600;
        assert result[0];
    }
}