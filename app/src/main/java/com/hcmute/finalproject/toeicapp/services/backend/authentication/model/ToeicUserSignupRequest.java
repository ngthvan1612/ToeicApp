package com.hcmute.finalproject.toeicapp.services.backend.authentication.model;

public class ToeicUserSignupRequest {
    private String fullName;

    private String gmail;

    public ToeicUserSignupRequest() {

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
}
