package com.hcmute.finalproject.toeicapp.dto;

public class CheckSumString {
    private boolean isSuccess;
    private String message;
    private String data;
    public CheckSumString(boolean isSuccess, String message, String data) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }
    public CheckSumString(){}

}
