package com.hcmute.finalproject.toeicapp.testing.huong.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.LoadingDialogComponent;

public class HuongTestActivity extends AppCompatActivity {
    private Button btn;
    private LoadingDialogComponent loadingDialogComponent = new LoadingDialogComponent(HuongTestActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huong_test);
        btn = (Button) findViewById(R.id.loading_demo_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Đang đồng bộ dữ liệu";
                loadingDialogComponent.startLoadingDialog(message);
            }
        });
    }
}