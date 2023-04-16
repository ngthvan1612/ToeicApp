package com.hcmute.finalproject.toeicapp.components.homepage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.hcmute.finalproject.toeicapp.R;

public class MainBottomNavigationComponent extends LinearLayout {
    private BottomNavigationView bottomNavigationView;

    public enum MainBottomNavigationType {
        HOME, VOCABULARY, NOTE, STATISTIC, OPTIONS
    }

    private OnNavigationClicked onNavigationClicked;

    public MainBottomNavigationComponent(Context context) {
        this(context, null);
    }

    public MainBottomNavigationComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainBottomNavigationComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);
    }

    public OnNavigationClicked getOnNavigationClicked() {
        return onNavigationClicked;
    }

    public void setOnNavigationClicked(OnNavigationClicked onNavigationClicked) {
        this.onNavigationClicked = onNavigationClicked;
    }

    private void initComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_main_bottom_navigation, this);

        this.bottomNavigationView = view.findViewById(R.id.component_main_bottom_navigation_nav_bar);

        if (this.isInEditMode()) {
            return;
        }

        this.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (onNavigationClicked != null) {
                    switch (item.getItemId()) {
                        case R.id.menu_component_bottom_navigation_home:
                            onNavigationClicked.onClick(MainBottomNavigationType.HOME);
                            break;
                        case R.id.menu_component_bottom_navigation_vocabulary:
                            onNavigationClicked.onClick(MainBottomNavigationType.VOCABULARY);
                            break;
                        case R.id.menu_component_bottom_navigation_statistic:
                            onNavigationClicked.onClick(MainBottomNavigationType.STATISTIC);
                            break;
                        case R.id.menu_component_bottom_navigation_note:
                            onNavigationClicked.onClick(MainBottomNavigationType.NOTE);
                            break;
                        case R.id.menu_component_bottom_navigation_options:
                            onNavigationClicked.onClick(MainBottomNavigationType.OPTIONS);
                            break;
                    }
                }
                return true;
            }
        });
    }

    public interface OnNavigationClicked {
        void onClick(MainBottomNavigationType itemType);
    }
}
