package com.hcmute.finalproject.toeicapp.services.backend.favorite.model;

import java.util.List;

public class BackupFavoriteGroup {
    private String groupName;

    private List<BackupFavoriteVocab> favoriteVocabs;

    public List<BackupFavoriteVocab> getFavoriteVocabs() {
        return favoriteVocabs;
    }

    public void setFavoriteVocabs(List<BackupFavoriteVocab> favoriteVocabs) {
        this.favoriteVocabs = favoriteVocabs;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
