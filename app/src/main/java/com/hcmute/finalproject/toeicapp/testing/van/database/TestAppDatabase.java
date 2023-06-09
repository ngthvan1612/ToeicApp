package com.hcmute.finalproject.toeicapp.testing.van.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.hcmute.finalproject.toeicapp.entities.ToeicStorage;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabulary;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabularyTopic;

@Database(
        entities = {
                ToeicStorage.class,
                ToeicVocabulary.class,
                ToeicVocabularyTopic.class
        },
        version = 1
)
public abstract class TestAppDatabase extends RoomDatabase {
    public abstract TestToeicStorageDao getToeicStorageDao();
}
