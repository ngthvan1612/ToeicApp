package com.hcmute.finalproject.toeicapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.LoadingDialogComponent;
import com.hcmute.finalproject.toeicapp.components.favoritevocab.HomePageFavoriteVocabComponent;
import com.hcmute.finalproject.toeicapp.components.homepage.HomePageListPracticeComponent;
import com.hcmute.finalproject.toeicapp.components.homepage.HomePageListVocabularyComponent;
import com.hcmute.finalproject.toeicapp.components.homepage.HomePageStatisticComponent;
import com.hcmute.finalproject.toeicapp.components.homepage.HomePageUserProfileComponent;
import com.hcmute.finalproject.toeicapp.components.homepage.MainBottomNavigationComponent;
import com.hcmute.finalproject.toeicapp.dao.FavoriteVocabGroupDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.services.backend.authentication.AuthenticationService;
import com.hcmute.finalproject.toeicapp.services.backend.favorite.FavoriteVocabService;
import com.hcmute.finalproject.toeicapp.services.backend.tests.ToeicTestBackendService;
import com.hcmute.finalproject.toeicapp.services.dialog.DialogSyncService;


public class HomeActivity extends GradientActivity {
    private static final int NUMBER_OF_PAGES = 5;
    private static final String CHECK_SUM_PREF = "Checksum";
    private ViewPager viewPager;
    private MainBottomNavigationComponent mainBottomNavigationComponent;
    private ToeicTestBackendService toeicTestBackendService;
    private AuthenticationService authenticationService;
    private FavoriteVocabService favoriteVocabService;
    private FavoriteVocabGroupDao favoriteVocabGroupDao;
    private ViewPagerNavigationAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.authenticationService = new AuthenticationService(this);
        this.toeicTestBackendService = new ToeicTestBackendService(this);
        this.favoriteVocabService = new FavoriteVocabService(this);
        this.favoriteVocabGroupDao = ToeicAppDatabase
                .getInstance(this)
                .getFavoriteVocabGroupDao();
        this.adapter = new ViewPagerNavigationAdapter();

        if (!this.authenticationService.isAuthenticated()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        viewPager = findViewById(R.id.activity_home_view_pager);
        mainBottomNavigationComponent = findViewById(R.id.activity_home_bottom_navigation_view);

        this.initViewPager();
        this.initBottomNavigation();

        this.authenticationService.refreshRegisterUserWithServer(() -> {
            if (this.favoriteVocabGroupDao.getAll().size() == 0) {
                this.favoriteVocabService.backupFavoriteVocabFromServer(new FavoriteVocabService.OnFavoriteVocabServiceListener() {
                    @Override
                    public void onSuccess() {
                        Log.d("BACKUP_FAVORITE", "ok ");
                        HomePageFavoriteVocabComponent homePageFavoriteVocabComponent = viewPager
                                .findViewWithTag("c-3");

                        Log.d("HOME_ACTIVITY", "before reload fav groups");

                        if (homePageFavoriteVocabComponent != null) {
                            Log.d("HOME_ACTIVITY", "reload fav groups");
                            homePageFavoriteVocabComponent.reloadListFavoriteGroups();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("BACKUP_FAVORITE", "error " + e.getMessage());
                    }
                });
            }
            else
                this.favoriteVocabService.restoreFavoriteVocabsToServer(null);
        });

        CheckDataAsync();
    }

    private void CheckDataAsync() {
        this.toeicTestBackendService.checkToeicTestDatabaseIsUpdated(new ToeicTestBackendService.OnBackupToeicListener() {
            @Override
            public void prepare() {
                Log.d("DIALOG_SERVICE", " check update");
                DialogSyncService.showDialog(HomeActivity.this);
            }

            @Override
            public void onSuccess() {
                DialogSyncService.dismissDialog();
            }

            @Override
            public void onException(Exception exception) {
                Toast.makeText(HomeActivity.this, exception.toString(), Toast.LENGTH_SHORT).show();
                DialogSyncService.dismissDialog();
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
        viewPager.setAdapter(this.adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                final View view = viewPager.findViewWithTag(getLayoutTagByPosition(position));
                if (position == 0) {
                    //List Practice
                    HomePageListPracticeComponent component = (HomePageListPracticeComponent) view;
                }
                else if (position == 2) {
                    HomePageStatisticComponent component = (HomePageStatisticComponent)view;
                    component.reloadListStatistic();
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
            }
            else if (position == 2) {
                HomePageStatisticComponent component = new HomePageStatisticComponent(HomeActivity.this);
                container.addView(component);
                component.setTag(getLayoutTagByPosition(position));
                return component;
            }
            else if (position == 3) {
                HomePageFavoriteVocabComponent component = new HomePageFavoriteVocabComponent(HomeActivity.this);
                container.addView(component);
                component.setTag(getLayoutTagByPosition(position));

                return component;
            }
            else if (position == 4) {
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