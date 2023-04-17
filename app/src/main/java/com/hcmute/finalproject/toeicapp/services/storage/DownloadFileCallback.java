package com.hcmute.finalproject.toeicapp.services.storage;

import com.hcmute.finalproject.toeicapp.entities.ToeicStorage;

public interface DownloadFileCallback<T> {
    void onSuccess(ToeicStorage toeicStorage, T data);
    void onError(String error);
}
