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

        private Integer serverId;

        private String text;

        private String pronunciation;

        private Integer topicId;

        private Integer imageStorageId;

        private Integer audioStorageId;

//        constructor
        public ToeicVocabulary(){}
        public ToeicVocabulary(@NonNull Integer id, Integer serverId, String text, String pronunciation, Integer topicId, Integer imageStorageId, Integer audioStorageId) {
                this.serverId = id;
                this.text = text;
                this.pronunciation = pronunciation;
                this.topicId = topicId;
                this.imageStorageId = imageStorageId;
                this.audioStorageId = audioStorageId;
        }


//        getter setter

        @NonNull
        public Integer getId() {
                return id;
        }

        public void setId(@NonNull Integer id) {
                this.id = id;
        }
        public Integer getServerId() {return serverId;}

        public void setServerId(Integer serverId) {this.serverId = serverId;}

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
