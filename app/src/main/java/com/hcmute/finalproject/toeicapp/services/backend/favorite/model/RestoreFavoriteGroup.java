package com.hcmute.finalproject.toeicapp.services.backend.favorite.model;

import java.util.List;

public class RestoreFavoriteGroup {
    private String groupName;

    private List<RestoreFavoriteVocab> favoriteVocabs;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<RestoreFavoriteVocab> getFavoriteVocabs() {
        return favoriteVocabs;
    }

    public void setFavoriteVocabs(List<RestoreFavoriteVocab> favoriteVocabs) {
        this.favoriteVocabs = favoriteVocabs;
    }
}
