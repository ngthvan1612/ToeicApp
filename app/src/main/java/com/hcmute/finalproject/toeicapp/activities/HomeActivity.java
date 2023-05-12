package com.hcmute.finalproject.toeicapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.homepage.HomePageListPracticeComponent;
import com.hcmute.finalproject.toeicapp.components.homepage.HomePageListVocabularyComponent;
import com.hcmute.finalproject.toeicapp.components.homepage.MainBottomNavigationComponent;
import com.hcmute.finalproject.toeicapp.dto.backend.AndroidToeicFullTest;
import com.hcmute.finalproject.toeicapp.dto.backend.AndroidToeicTestsResponse;
import com.hcmute.finalproject.toeicapp.network.APIToeicTest;
import com.hcmute.finalproject.toeicapp.services.mocktoeic.MockToeicTestDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends GradientActivity {
    private static final int NUMBER_OF_PAGES = 5;
    private ViewPager viewPager;
    private MainBottomNavigationComponent mainBottomNavigationComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = findViewById(R.id.activity_home_view_pager);
        mainBottomNavigationComponent = findViewById(R.id.activity_home_bottom_navigation_view);

        this.initViewPager();
        this.initBottomNavigation();
        ProgressDialog dialog = new ProgressDialog(HomeActivity.this);
        dialog.setMessage("loading...");
        dialog.show();
       APIToeicTest.getInstance().getTestData().enqueue(new Callback<AndroidToeicTestsResponse>() {
           @Override
           public void onResponse(Call<AndroidToeicTestsResponse> call, Response<AndroidToeicTestsResponse> response) {
               final AndroidToeicTestsResponse androidToeicTestsResponse = response.body();
               Log.d("testapi",androidToeicTestsResponse.isSuccess()+"");
               Log.d("testapi",androidToeicTestsResponse.getMessage()+"");
               Log.d("testapi",androidToeicTestsResponse.getData().size()+"");

           }

           @Override
           public void onFailure(Call<AndroidToeicTestsResponse> call, Throwable t) {
               Log.d("testerr",t.toString());

           }
       });
        CheckDataSync();
        dialog.dismiss();
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
    private void CheckDataSync() {
        APIToeicTest.getInstance().getTestDataCheckSumString().enqueue(new Callback<AndroidToeicTestsResponse>() {
            @Override
            public void onResponse(Call<AndroidToeicTestsResponse> call, Response<AndroidToeicTestsResponse> response) {
                Log.d("checksum:",response.toString());
            }

            @Override
            public void onFailure(Call<AndroidToeicTestsResponse> call, Throwable t) {

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