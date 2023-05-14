package com.hcmute.finalproject.toeicapp.testing.huong.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.activities.HomeActivity;
import com.hcmute.finalproject.toeicapp.components.LoadingDialogComponent;

public class HuongTestActivity extends AppCompatActivity {
    private Button btn;
    Dialog dialog;

    private LoadingDialogComponent loadingDialogComponent = new LoadingDialogComponent(HuongTestActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(this,android.R.style.Theme_Light);
        setContentView(R.layout.activity_huong_test);
        btn = (Button) findViewById(R.id.loading_demo_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Đang đồng bộ dữ liệu";
                loadingDialogComponent.startLoadingDialog(dialog,message);
            }
        });
    }
}