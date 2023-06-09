package com.hcmute.finalproject.toeicapp.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ToeicFullTest {
    @PrimaryKey
    @NonNull
    private Integer id;
    private Integer serverId;
    private String fullName;
    private String slug;
    //    constructor
    public ToeicFullTest() {}
    public ToeicFullTest(@NonNull Integer id, Integer serverId, String fullName, String slug) {
        this.serverId = id;
        this.fullName = fullName;
        this.slug = slug;
    }

//    getter setter
    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }


}
