package com.hcmute.finalproject.toeicapp.testing.van.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.*;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.entities.ToeicStorage;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabulary;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabularyTopic;
import com.hcmute.finalproject.toeicapp.testing.van.database.AppDatabase;
import com.hcmute.finalproject.toeicapp.testing.van.database.ToeicStorageDao;

import java.util.Date;
import java.util.List;

/**
 * Reference: https://viblo.asia/p/su-dung-room-database-trong-android-naQZRD0q5vx
 */
public class VanTestRoomDatabaseActivity extends AppCompatActivity {
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_test_room_database);

        appDatabase = Room.databaseBuilder(
                this.getApplicationContext(),
                AppDatabase.class,
                "hello-123.db")
                .allowMainThreadQueries()
                .build();

        test_01();
    }

    private void test_01() {
        assert appDatabase != null;

        ToeicStorageDao storageDao = appDatabase.getToeicStorageDao();

        ToeicStorage item = new ToeicStorage();
        item.setFileName(new Date() + " ahihi nhe");

        storageDao.insert(item);

        List<ToeicStorage> toeicStorages = storageDao.listAll();

        for (ToeicStorage toeicStorage : toeicStorages) {
            Log.d("TOEIC STORAGE", +toeicStorage.getId() + " " + toeicStorage.getFileName());
        }
    }
}