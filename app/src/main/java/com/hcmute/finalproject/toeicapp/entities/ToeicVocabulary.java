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
        )
})
public class ToeicVocabulary {
        @PrimaryKey
        @NonNull
        private Integer id;

        private Integer serverId;

        private String english;

        private String pronunciation;

        private String exampleEnglish;

        private String exampleVietnamese;

        private Integer topicId;

        private String imagePath;

        private String audioPath;

        @NonNull
        public Integer getId() {
                return id;
        }

        public void setId(@NonNull Integer id) {
                this.id = id;
        }

        public Integer getServerId() {
                return serverId;
        }

        public void setServerId(Integer serverId) {
                this.serverId = serverId;
        }

        public String getEnglish() {
                return english;
        }

        public void setEnglish(String english) {
                this.english = english;
        }

        public String getPronunciation() {
                return pronunciation;
        }

        public void setPronunciation(String pronunciation) {
                this.pronunciation = pronunciation;
        }

        public String getExampleEnglish() {
                return exampleEnglish;
        }

        public void setExampleEnglish(String exampleEnglish) {
                this.exampleEnglish = exampleEnglish;
        }

        public String getExampleVietnamese() {
                return exampleVietnamese;
        }

        public void setExampleVietnamese(String exampleVietnamese) {
                this.exampleVietnamese = exampleVietnamese;
        }

        public Integer getTopicId() {
                return topicId;
        }

        public void setTopicId(Integer topicId) {
                this.topicId = topicId;
        }

        public String getImagePath() {
                return imagePath;
        }

        public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
        }

        public String getAudioPath() {
                return audioPath;
        }

        public void setAudioPath(String audioPath) {
                this.audioPath = audioPath;
        }
}