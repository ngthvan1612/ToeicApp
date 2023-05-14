package com.hcmute.finalproject.toeicapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.AndroidAnswerChoice;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.AndroidItemContent;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.AndroidToeicFullTest;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.AndroidToeicPart;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.AndroidToeicQuestion;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.AndroidToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.CheckSumStringResponse;
import com.hcmute.finalproject.toeicapp.components.homepage.HomePageListPracticeComponent;
import com.hcmute.finalproject.toeicapp.components.homepage.HomePageListVocabularyComponent;
import com.hcmute.finalproject.toeicapp.components.homepage.HomePageUserProfileComponent;
import com.hcmute.finalproject.toeicapp.components.homepage.MainBottomNavigationComponent;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.AndroidToeicTestsResponse;
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
import com.hcmute.finalproject.toeicapp.services.authentication.AuthenticationService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends GradientActivity {
    private ToeicAppDatabase toeicAppDatabase;
    private static final int NUMBER_OF_PAGES = 5;
    private static final String CHECK_SUM_PREF = "Checksum";
    private ViewPager viewPager;
    private MainBottomNavigationComponent mainBottomNavigationComponent;

    private AuthenticationService authenticationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.authenticationService = new AuthenticationService(this);

        if (!this.authenticationService.isAuthenticated()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        this.toeicAppDatabase = ToeicAppDatabase.getInstance(this.getApplicationContext());

        viewPager = findViewById(R.id.activity_home_view_pager);
        mainBottomNavigationComponent = findViewById(R.id.activity_home_bottom_navigation_view);

        this.initViewPager();
        this.initBottomNavigation();

        CheckDataSync();

//        for (Integer item : getAllToeicStorageIdsByPartServerId(23)) {
//            Log.d("item", item.toString());
//        }
//        test(22);
    }

    private void test(Integer partServerId) {
        final ToeicPartDao partDao = toeicAppDatabase.getToeicPartDao();
        final ToeicQuestionGroupDao toeicQuestionGroupDao = toeicAppDatabase.getToeicQuestionGroupDao();
        final ToeicItemContentDao toeicItemContentDao = toeicAppDatabase.getToeicItemContentDao();
        final ToeicPart toeicPart = partDao.getToeicPartByServerId(partServerId);
        Log.d("toeicPart", toeicPart.getPartNumber().toString());
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

    private void FetchNewData() {
        ProgressDialog dialog = new ProgressDialog(HomeActivity.this);
        dialog.setTitle("Đang đồng bộ dữ liệu");
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
                Log.d("err", t.toString());
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

    private void CheckDataSync() {
        SharedPreferences sharedPref = this.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        ProgressDialog dialog = new ProgressDialog(HomeActivity.this);
        dialog.setMessage("Đang cập nhật thông tin");
        dialog.cancel();
        dialog.show();

        APIToeicTest.getInstance().getTestDataCheckSumString().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<CheckSumStringResponse> call, Response<CheckSumStringResponse> response) {
                final CheckSumStringResponse checkSumStringResponse = response.body();
                final String currentCheckSum = sharedPref.getString(CHECK_SUM_PREF, UUID.randomUUID() + "");

                String newChecksum = checkSumStringResponse.getData();
                Log.d("CHECKSUM_LOG", newChecksum);

                if (!currentCheckSum.equals(newChecksum)) {
                    dialog.dismiss();
                    FetchNewData();
                } else {
                    dialog.dismiss();
                }

                editor.putString(CHECK_SUM_PREF, newChecksum);
                editor.commit();
            }

            @Override
            public void onFailure(Call<CheckSumStringResponse> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
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
                    HomePageListPracticeComponent component = (HomePageListPracticeComponent) view;
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
            container.removeView((View) object);
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
            } else if (position == 4) {
                HomePageUserProfileComponent component = new HomePageUserProfileComponent(HomeActivity.this);
                container.addView(component);
                component.setTag(getLayoutTagByPosition(4));
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