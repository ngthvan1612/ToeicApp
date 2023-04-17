package com.hcmute.finalproject.toeicapp.testing.van.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.*;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.entities.ToeicStorage;
import com.hcmute.finalproject.toeicapp.testing.van.database.TestAppDatabase;
import com.hcmute.finalproject.toeicapp.testing.van.database.TestToeicStorageDao;

import java.util.Date;
import java.util.List;

/**
 * Reference: https://viblo.asia/p/su-dung-room-database-trong-android-naQZRD0q5vx
 */
public class VanTestRoomDatabaseActivity extends AppCompatActivity {
    private TestAppDatabase testAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_test_room_database);

        testAppDatabase = Room.databaseBuilder(
                this.getApplicationContext(),
                TestAppDatabase.class,
                "hello-123.db")
                .allowMainThreadQueries()
                .build();

        test_01();
    }

    private void test_01() {
        assert testAppDatabase != null;

        TestToeicStorageDao storageDao = testAppDatabase.getToeicStorageDao();

        // APPEND
        ToeicStorage item = new ToeicStorage();
        item.setFileName(new Date() + " ahihi nhe");

        storageDao.insert(item);

        // UPDATE 2
        ToeicStorage two = testAppDatabase.getToeicStorageDao().getOne(2);
        Log.d("TOEIC STORAGE", "PAR: " + two.getId() + " " + two.getFileName());
        two.setFileName("ahihi nhe " + new Date());
        testAppDatabase.getToeicStorageDao().update(two);

        // LIST ALL
        List<ToeicStorage> toeicStorages = storageDao.listAll();

        for (ToeicStorage toeicStorage : toeicStorages) {
            Log.d("TOEIC STORAGE",  "READ: " + +toeicStorage.getId() + " " + toeicStorage.getFileName());
        }

        // DELETE 3
        ToeicStorage three = storageDao.getOne(3);
        if (three != null) {
            storageDao.delete(three);
            Log.d("TOEIC STORAGE", "deleted 3 ok");
        }
        else {
            Log.d("TOEIC STORAGE", "3 is deleted");
        }
    }
}