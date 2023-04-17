package com.hcmute.finalproject.toeicapp.components.homepage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.HomeButtonRoundedComponent;

public class HomePageListPracticeComponent extends LinearLayout {
    public HomePageListPracticeComponent(Context context) {
        this(context, null);
    }

    public HomePageListPracticeComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomePageListPracticeComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);
    }

    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_home_page_list_practice, this);

        //HomeButtonRoundedComponent btnPart1 = view.findViewById(R.id.component_home_page_list_practice_btn_part_1);
        //HomeButtonRoundedComponent btnPart2 = view.findViewById(R.id.component_home_page_list_practice_btn_part_2);

        if (this.isInEditMode()) {
            return;
        }
    }
}