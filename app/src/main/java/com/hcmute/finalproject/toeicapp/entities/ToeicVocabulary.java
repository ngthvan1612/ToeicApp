package com.hcmute.finalproject.toeicapp.entities;

import androidx.room.*;

import androidx.annotation.NonNull;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = ToeicVocabularyTopic.class,
                parentColumns = "id",
                childColumns = "topicId",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = ToeicStorage.class,
                parentColumns = "id",
                childColumns = "imageStorageId",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.SET_NULL
        ),
        @ForeignKey(
                entity = ToeicStorage.class,
                parentColumns = "id",
                childColumns = "audioStorageId",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.SET_NULL
        )
})
public class ToeicVocabulary {
        @PrimaryKey
        @NonNull
        private Integer id;

        private String text;

        private String pronunciation;

        private Integer topicId;

        private Integer imageStorageId;

        private Integer audioStorageId;

        @NonNull
        public Integer getId() {
                return id;
        }

        public void setId(@NonNull Integer id) {
                this.id = id;
        }

        public String getText() {
                return text;
        }

        public void setText(String text) {
                this.text = text;
        }

        public String getPronunciation() {
                return pronunciation;
        }

        public void setPronunciation(String pronunciation) {
                this.pronunciation = pronunciation;
        }

        public Integer getTopicId() {
                return topicId;
        }

        public void setTopicId(Integer topicId) {
                this.topicId = topicId;
        }

        public Integer getImageStorageId() {
                return imageStorageId;
        }

        public void setImageStorageId(Integer imageStorageId) {
                this.imageStorageId = imageStorageId;
        }

        public Integer getAudioStorageId() {
                return audioStorageId;
        }

        public void setAudioStorageId(Integer audioStorageId) {
                this.audioStorageId = audioStorageId;
        }
}
