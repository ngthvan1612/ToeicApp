package com.hcmute.finalproject.toeicapp.dto;

public class BaseResponse<DataType> {
    private boolean isSuccess;
    private String message;
    private DataType data;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataType getData() {
        return data;
    }

    public void setData(DataType data) {
        this.data = data;
    }
}
