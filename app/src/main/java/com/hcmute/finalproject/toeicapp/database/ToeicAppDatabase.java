package com.hcmute.finalproject.toeicapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hcmute.finalproject.toeicapp.dao.ToeicAnswerChoiceDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicFullTestDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicItemContentDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicPartDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicQuestionDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicQuestionGroupDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicStorageDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicVocabularyDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicVocabularyTopicDao;
import com.hcmute.finalproject.toeicapp.entities.ToeicAnswerChoice;
import com.hcmute.finalproject.toeicapp.entities.ToeicFullTest;
import com.hcmute.finalproject.toeicapp.entities.ToeicItemContent;
import com.hcmute.finalproject.toeicapp.entities.ToeicPart;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestion;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.entities.ToeicStorage;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabulary;
import com.hcmute.finalproject.toeicapp.entities.ToeicVocabularyTopic;

@Database(entities = {
        ToeicAnswerChoice.class,
        ToeicFullTest.class,
        ToeicItemContent.class,
        ToeicPart.class,
        ToeicQuestion.class,
        ToeicQuestionGroup.class,
        ToeicStorage.class,
        ToeicVocabulary.class,
        ToeicVocabularyTopic.class
}, version = 1)
public abstract class ToeicAppDatabase extends RoomDatabase {
    private final static String DATABASE_FILE_NAME = "toeic-db";

    public abstract ToeicAnswerChoiceDao getToeicAnswerChoiceDao();
    public abstract ToeicFullTestDao getToeicFullTestDao();
    public abstract ToeicItemContentDao getToeicItemContentDao();
    public abstract ToeicPartDao getToeicPartDao();
    public abstract ToeicQuestionDao getToeicQuestionDao();
    public abstract ToeicQuestionGroupDao getToeicQuestionGroupDao();
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
