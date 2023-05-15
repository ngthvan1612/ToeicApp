package com.hcmute.finalproject.toeicapp.testing.van.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.widget.Toast;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.media.AudioPlayerComponent;
import com.hcmute.finalproject.toeicapp.services.media.AudioPlayerBackgroundService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VanTestAudioPlayerActivity extends AppCompatActivity {
    private final static String DOWNLOADED_FILE_NAME = "test-1111.mp3";

    private AudioPlayerComponent audioPlayer;
    private ProgressDialog progressDialog;

    private File getFileRef(String fileName) {
        ContextWrapper contextWrapper = new ContextWrapper(
                getApplicationContext());
        File directory = contextWrapper.getDir("test-01-01-02", Context.MODE_PRIVATE);
        return new File(directory, fileName);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AudioPlayerBackgroundService.backupAudioPlayerState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AudioPlayerBackgroundService.restoreAudioPlayerState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_test_audio_player);
    }
}