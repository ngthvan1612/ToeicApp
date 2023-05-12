package com.hcmute.finalproject.toeicapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.assets.backend.AndroidAnswerChoice;
import com.hcmute.finalproject.toeicapp.assets.backend.AndroidItemContent;
import com.hcmute.finalproject.toeicapp.assets.backend.AndroidToeicFullTest;
import com.hcmute.finalproject.toeicapp.assets.backend.AndroidToeicPart;
import com.hcmute.finalproject.toeicapp.assets.backend.AndroidToeicQuestion;
import com.hcmute.finalproject.toeicapp.assets.backend.AndroidToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.assets.backend.CheckSumStringResponse;
import com.hcmute.finalproject.toeicapp.components.homepage.HomePageListPracticeComponent;
import com.hcmute.finalproject.toeicapp.components.homepage.HomePageListVocabularyComponent;
import com.hcmute.finalproject.toeicapp.components.homepage.MainBottomNavigationComponent;
import com.hcmute.finalproject.toeicapp.assets.backend.AndroidToeicTestsResponse;
import com.hcmute.finalproject.toeicapp.dao.ToeicAnswerChoiceDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicFullTestDao;
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

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends GradientActivity {
    private ToeicAppDatabase toeicAppDatabase;
    private static final int NUMBER_OF_PAGES = 5;
    private ViewPager viewPager;
    private MainBottomNavigationComponent mainBottomNavigationComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.toeicAppDatabase = ToeicAppDatabase.getInstance(this.getApplicationContext());

        viewPager = findViewById(R.id.activity_home_view_pager);
        mainBottomNavigationComponent = findViewById(R.id.activity_home_bottom_navigation_view);

        this.initViewPager();
        this.initBottomNavigation();

        FetchNewData();
    }

    private void FetchNewData() {
        ProgressDialog dialog = new ProgressDialog(HomeActivity.this);
        dialog.setTitle("Đang xử lý");
        dialog.setCancelable(false);
        dialog.setMessage("Đang tải...");
        dialog.show();

        APIToeicTest.getInstance().getTestData().enqueue(new Callback<AndroidToeicTestsResponse>() {
            @Override
            public void onResponse(Call<AndroidToeicTestsResponse> call, Response<AndroidToeicTestsResponse> response) {
                final AndroidToeicTestsResponse androidToeicTestsResponse = response.body();
                resetDatabase(androidToeicTestsResponse.getData());
                dialog.setMessage("Đang đồng bộ...");
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<AndroidToeicTestsResponse> call, Throwable t) {
                Log.d("err",t.toString());
                dialog.dismiss();
            }
        });
    }

    //TODO: chuan bi chuyen ra file rieng
    private void resetDatabase(
            @NonNull List<AndroidToeicFullTest> tests
    ) {
        final ToeicFullTestDao testDao = toeicAppDatabase.getToeicFullTestDao();
        final ToeicPartDao partDao = toeicAppDatabase.getToeicPartDao();
        final ToeicQuestionGroupDao groupDao = toeicAppDatabase.getToeicQuestionGroupDao();
        final ToeicQuestionDao questionDao = toeicAppDatabase.getToeicQuestionDao();
        final ToeicAnswerChoiceDao answerChoiceDao = toeicAppDatabase.getToeicAnswerChoiceDao();

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
                        }
                    }

                    if (questionGroupBackend.getTranscriptContents() != null) {
                        for (AndroidItemContent itemContentBackend : questionGroupBackend.getTranscriptContents()) {
                            ToeicItemContent itemContentEntity = new ToeicItemContent();
                            itemContentEntity.setContent(itemContentBackend.getRawContent());
                            itemContentEntity.setContentType(itemContentBackend.getContentType());
                            itemContentEntity.setServerId(itemContentBackend.getStorageServerId());
                            itemContentEntity.setToeicQuestionGroupEntityTranscriptId(newGroupId);
                        }
                    }
                }
            }
        }
    }

    private void CheckDataSync() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
       APIToeicTest.getInstance().getTestDataCheckSumString().enqueue(new Callback<CheckSumStringResponse>() {
           @Override
           public void onResponse(Call<CheckSumStringResponse> call, Response<CheckSumStringResponse> response) {
               final CheckSumStringResponse checkSumStringResponse = response.body();
//               Log.d("checksum",checkSumStringResponse.getData().toString());
//               editor.putString("Checksum",checkSumStringResponse.getData().toString());
//               editor.apply();

               Log.d("ChecksumSharedpreference",sharedPref.getString("Checksum", checkSumStringResponse.getData().toString()));

               String currentChecksum = sharedPref.getString("Checksum", checkSumStringResponse.getData().toString());
               String newChecksum = checkSumStringResponse.getData().toString();

               if(!currentChecksum.equals(newChecksum)) {
                   FetchNewData();
                   editor.putString("Checksum",newChecksum);
                   editor.apply();
               }
           }
           @Override
           public void onFailure(Call<CheckSumStringResponse> call, Throwable t) {
               Log.d("err",t.toString());
           }
       });

    }
    private String getLayoutTagByPosition(int position) {
        return "layout-" + position;
    }

    private void initBottomNavigation() {
        this.mainBottomNavigationComponent.setOnNavigationClicked(new MainBottomNavigationComponent.OnNavigationClicked() {
            @Override
            public void onClick(MainBottomNavigationComponent.MainBottomNavigationType itemType) {
                switch (itemType) {
                    case HOME:
                        viewPager.setCurrentItem(0, true);
                        break;
                    case VOCABULARY:
                        viewPager.setCurrentItem(1, true);
                        break;
                    case STATISTIC:
                        viewPager.setCurrentItem(2, true);
                        break;
                    case NOTE:
                        viewPager.setCurrentItem(3, true);
                        break;
                    case OPTIONS:
                        viewPager.setCurrentItem(4, true);
                        break;
                }
            }
        });
    }

    private void initViewPager() {
        viewPager.setOffscreenPageLimit(NUMBER_OF_PAGES);
        viewPager.setAdapter(new ViewPagerNavigationAdapter());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                final View view = viewPager.findViewWithTag(getLayoutTagByPosition(position));
                if (position == 0) {
                    //List Practice
                    HomePageListPracticeComponent component = (HomePageListPracticeComponent)view;
                }
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mainBottomNavigationComponent.setSelectedItem(MainBottomNavigationComponent.MainBottomNavigationType.HOME);
                        break;
                    case 1:
                        mainBottomNavigationComponent.setSelectedItem(MainBottomNavigationComponent.MainBottomNavigationType.VOCABULARY);
                        break;
                    case 2:
                        mainBottomNavigationComponent.setSelectedItem(MainBottomNavigationComponent.MainBottomNavigationType.STATISTIC);
                        break;
                    case 3:
                        mainBottomNavigationComponent.setSelectedItem(MainBottomNavigationComponent.MainBottomNavigationType.NOTE);
                        break;
                    case 4:
                        mainBottomNavigationComponent.setSelectedItem(MainBottomNavigationComponent.MainBottomNavigationType.OPTIONS);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class ViewPagerNavigationAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return NUMBER_OF_PAGES;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            if (position == 1) {
                HomePageListVocabularyComponent component = new HomePageListVocabularyComponent(HomeActivity.this);
                container.addView(component);
                component.setTag(getLayoutTagByPosition(position));

                component.setOnClickBackButton(() -> viewPager.setCurrentItem(0, true));

                return component;
            }

            HomePageListPracticeComponent component = new HomePageListPracticeComponent(HomeActivity.this);
            container.addView(component);
            component.setTag(getLayoutTagByPosition(position));
            return component;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}