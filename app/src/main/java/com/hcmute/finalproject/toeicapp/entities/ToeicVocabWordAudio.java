package com.hcmute.finalproject.toeicapp.entities;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class ToeicVocabWordAudio {

    @PrimaryKey
    @NonNull
    private Integer id;
    private Integer serverId;
    private String voice;
    private ToeicVocabWord word;
    private ToeicStorage audioStorage;

//    constructor
    public ToeicVocabWordAudio() {}
    public ToeicVocabWordAudio(@NonNull Integer id, Integer serverId, String voice, ToeicVocabWord word, ToeicStorage audioStorage) {
        this.serverId = id;
        this.voice = voice;
        this.word = word;
        this.audioStorage = audioStorage;
    }

//    getter setter
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

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public ToeicVocabWord getWord() {
        return word;
    }

    public void setWord(ToeicVocabWord word) {
        this.word = word;
    }

    public ToeicStorage getAudioStorage() {
        return audioStorage;
    }

    public void setAudioStorage(ToeicStorage audioStorage) {
        this.audioStorage = audioStorage;
    }
}
