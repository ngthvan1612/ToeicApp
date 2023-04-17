package com.hcmute.finalproject.toeicapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.hcmute.finalproject.toeicapp.dao.ToeicDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicStorageDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicVocabularyDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicVocabularyTopicDao;
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
public abstract class ToeicAppDatabase extends RoomDatabase {
        public abstract ToeicStorageDao getToeicStorageDao();
        public abstract ToeicVocabularyDao getToeicVocabularyDao();
        public abstract ToeicVocabularyTopicDao getToeicVocabularyTopicDao();
}
