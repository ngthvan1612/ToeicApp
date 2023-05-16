package com.hcmute.finalproject.toeicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.common.CommonHeaderComponent;

public class ViewWordInGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_word_in_group);

        String groupName = getIntent().getStringExtra("groupName");
        ((CommonHeaderComponent)findViewById(R.id.activity_view_word_in_group_header_title)).setTitle(groupName);

        RecyclerView recyclerView = findViewById(R.id.activity_view_word_in_group_rcView);

    }

    private class WordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        private class WordItemHolder extends RecyclerView.ViewHolder {

            public WordItemHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void setData() {

            }
        }
    }
}