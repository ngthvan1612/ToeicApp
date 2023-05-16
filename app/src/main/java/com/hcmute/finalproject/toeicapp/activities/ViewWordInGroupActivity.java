package com.hcmute.finalproject.toeicapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.common.CommonHeaderComponent;

public class ViewWordInGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_word_in_group);

        String groupName = getIntent().getStringExtra("groupName");
        ((CommonHeaderComponent)findViewById(R.id.activity_view_word_in_group_header_title)).setTitle(groupName);
    }
}