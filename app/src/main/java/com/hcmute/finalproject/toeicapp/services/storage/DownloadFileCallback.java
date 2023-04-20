package com.hcmute.finalproject.toeicapp.services.storage;

import com.hcmute.finalproject.toeicapp.entities.ToeicStorage;

public interface DownloadFileCallback<T> {
    void onSuccess(T toeicStorage);
    void onProgressUpdate(int percent);
    void onError(String error);
}
