package com.hcmute.finalproject.toeicapp.services.backend.favorite.model;

import java.util.List;

public class BackupFavoriteDataResponse {
    private List<BackupFavoriteGroup> groups;

    public List<BackupFavoriteGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<BackupFavoriteGroup> groups) {
        this.groups = groups;
    }
}
