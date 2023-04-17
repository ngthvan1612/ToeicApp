package com.hcmute.finalproject.toeicapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hcmute.finalproject.toeicapp.dao.ToeicStorageDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicVocabularyDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicVocabularyTopicDao;
import com.hcmute.finalproject.toeicapp.entities.ToeicStorage;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabulary;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabularyTopic;

@Database(entities = {ToeicStorage.class, ToeicVocabulary.class, ToeicVocabularyTopic.class}, version = 1)
public abstract class ToeicAppDatabase extends RoomDatabase {
    private final static String DATABASE_FILE_NAME = "toeic-db";

    public abstract ToeicStorageDao getToeicStorageDao();

    public abstract ToeicVocabularyDao getToeicVocabularyDao();

    public abstract ToeicVocabularyTopicDao getToeicVocabularyTopicDao();

    public static ToeicAppDatabase instance;

    public static ToeicAppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ToeicAppDatabase.class, DATABASE_FILE_NAME).allowMainThreadQueries().build();
        }

        return instance;
    }
}
