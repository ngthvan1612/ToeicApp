package com.hcmute.finalproject.toeicapp.components.homepage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.hcmute.finalproject.toeicapp.activities.ListGroupQuestionsActivity;
import com.hcmute.finalproject.toeicapp.activities.PartFiveActivity;
import com.hcmute.finalproject.toeicapp.activities.PartFourActivity;
import com.hcmute.finalproject.toeicapp.activities.PartOnePhotographsActivity;
import com.hcmute.finalproject.toeicapp.activities.PartSevenActivity;
import com.hcmute.finalproject.toeicapp.activities.PartSixActivity;
import com.hcmute.finalproject.toeicapp.activities.PartThreeActivity;
import com.hcmute.finalproject.toeicapp.activities.PartTwoActivity;
import com.hcmute.finalproject.toeicapp.components.HomeButtonRoundedComponent;

public class HomePageListPracticeComponent extends LinearLayout {
    HomeButtonRoundedComponent btnPart1, btnPart2, btnPart3, btnPart4, btnPart5, btnPart6, btnPart7;
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

        btnPart1 = view.findViewById(R.id.component_home_page_list_practice_btn_part_1);
        btnPart2 = view.findViewById(R.id.component_home_page_list_practice_btn_part_2);
        btnPart3 = view.findViewById(R.id.component_home_page_list_practice_btn_part_3);
        btnPart4 = view.findViewById(R.id.component_home_page_list_practice_btn_part_4);
        btnPart5 = view.findViewById(R.id.component_home_page_list_practice_btn_part_5);
        btnPart6 = view.findViewById(R.id.component_home_page_list_practice_btn_part_6);
        btnPart7 = view.findViewById(R.id.component_home_page_list_practice_btn_part_7);

        if (this.isInEditMode()) {
            return;
        }

        btnPart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PartOnePhotographsActivity.class);
                intent.putExtra("partId", 1);
                intent.putExtra("partName", "Part 1 - Photographs");
                context.startActivity(intent);
            }
        });

        btnPart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PartTwoActivity.class);
                intent.putExtra("partId", 2);
                intent.putExtra("partName", "Part 2 - Q & R");
                context.startActivity(intent);
            }
        });

        btnPart3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PartThreeActivity.class);
                intent.putExtra("partId", 3);
                intent.putExtra("partName", "Part 3 - Conversations");
                context.startActivity(intent);
            }
        });

        btnPart4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PartFourActivity.class);
                intent.putExtra("partId", 4);
                intent.putExtra("partName", "Part 4 - Short Talks");
                context.startActivity(intent);
            }
        });

        btnPart5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PartFiveActivity.class);
                intent.putExtra("partId", 5);
                intent.putExtra("partName", "Part 5 - Incomplete sentences");
                context.startActivity(intent);
            }
        });

        btnPart6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PartSixActivity.class);
                intent.putExtra("partId", 6);
                intent.putExtra("partName", "Part 6 - Text Completion");
                context.startActivity(intent);
            }
        });

        btnPart7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PartSevenActivity.class);
                intent.putExtra("partId", 7);
                intent.putExtra("partName", "Part 7 - Reading Comprehension");
                context.startActivity(intent);
            }
        });
    }
}