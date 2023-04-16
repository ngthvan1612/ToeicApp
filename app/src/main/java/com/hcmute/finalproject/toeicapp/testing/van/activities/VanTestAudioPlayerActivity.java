package com.hcmute.finalproject.toeicapp.testing.van.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.widget.Toast;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.media.AudioPlayerComponent;
import com.hcmute.finalproject.toeicapp.network.test.RetrofitTestRetrofitClient01;
import com.hcmute.finalproject.toeicapp.network.test.TestAPIService;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_test_audio_player);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Đang tải...");

        audioPlayer = findViewById(R.id.activity_van_audio_comp);

        findViewById(R.id.activity_van_test_audio_player_btn_download).setOnClickListener(view -> {
            progressDialog.show();
            TestAPIService apiService = RetrofitTestRetrofitClient01.getInstance();
            apiService.downloadTestFile01().enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    final ResponseBody responseBody = response.body();
                    try {
                        final byte[] buffer = responseBody.bytes();
                        Toast.makeText(VanTestAudioPlayerActivity.this, "Tải thành công: " + buffer.length + " bytes", Toast.LENGTH_SHORT).show();
                        File file = getFileRef(DOWNLOADED_FILE_NAME);
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(buffer);
                        fileOutputStream.close();
                    } catch (IOException e) {
                        Toast.makeText(VanTestAudioPlayerActivity.this, "Tải file lỗi: " + e.toString(), Toast.LENGTH_SHORT).show();
                    } finally {
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(VanTestAudioPlayerActivity.this, "Tải file lỗi: " + t.toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        });

        findViewById(R.id.activity_van_test_audio_player_btn_load_audio_file).setOnClickListener(view -> {
            File file = getFileRef(DOWNLOADED_FILE_NAME);
            if (!file.exists()) {
                Toast.makeText(VanTestAudioPlayerActivity.this, "Bạn chưa tải file dìa", Toast.LENGTH_SHORT).show();
                return;
            }
            audioPlayer.loadAudioFile(file);
            Toast.makeText(VanTestAudioPlayerActivity.this, "Load file thành công", Toast.LENGTH_SHORT).show();
        });

        audioPlayer.setOnAudioPlayerStateChanged(new AudioPlayerComponent.OnAudioPlayerChange() {
            @Override
            public boolean onPlayButtonClicked() {
                if (!audioPlayer.isPrepared()) {
                    Toast.makeText(VanTestAudioPlayerActivity.this, "Bạn chưa loa file", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }

            @Override
            public boolean onPauseButtonClicked() {
                return true;
            }

            @Override
            public void onChangeVolume(int currentVolumne, int maxVolume) {

            }
        });
    }
}