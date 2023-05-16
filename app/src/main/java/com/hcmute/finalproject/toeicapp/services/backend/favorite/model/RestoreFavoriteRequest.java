package com.hcmute.finalproject.toeicapp.services.backend.favorite.model;

import java.util.List;

public class RestoreFavoriteRequest {
    private String gmail;

    private List<RestoreFavoriteGroup> groups;

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public List<RestoreFavoriteGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<RestoreFavoriteGroup> groups) {
        this.groups = groups;
    }
}
