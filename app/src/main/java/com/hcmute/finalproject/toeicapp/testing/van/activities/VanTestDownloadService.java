package com.hcmute.finalproject.toeicapp.testing.van.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.dao.ToeicStorageDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.ToeicStorage;
import com.hcmute.finalproject.toeicapp.services.storage.DownloadFileCallback;
import com.hcmute.finalproject.toeicapp.services.storage.DownloadFileService;

import java.util.List;

public class VanTestDownloadService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_test_download_service);

        final ProgressDialog dialog = new ProgressDialog(this);
        final DownloadFileService downloadFileService = new DownloadFileService(this);
        final String url = "http://ipv4.download.thinkbroadband.com/5MB.zip";
        final ToeicStorageDao storageDao = ToeicAppDatabase.getInstance(this).getToeicStorageDao();

        findViewById(R.id.activity_van_test_download_service_btn_download_1).setOnClickListener(view -> {
            dialog.show();
            downloadFileService.downloadFileAndSaveToInternalStorage(url, new DownloadFileCallback<byte[]>() {
                @Override
                public void onSuccess(ToeicStorage toeicStorage, byte[] data) {
                    Toast.makeText(VanTestDownloadService.this, "Thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(VanTestDownloadService.this, "Lỗi rồi: " + error, Toast.LENGTH_SHORT).show();
                    Log.d("ERR", error);
                    dialog.dismiss();
                }
            });
        });

        findViewById(R.id.activity_van_test_download_service_btn_show_all_file).setOnClickListener(view -> {
            List<ToeicStorage> storages = storageDao.getAll();
            for (ToeicStorage toeicStorage : storages) {
                Log.d("STORAGE-TOEIC", toeicStorage.getId() + " " + toeicStorage.getFileName());
            }
        });
    }
}