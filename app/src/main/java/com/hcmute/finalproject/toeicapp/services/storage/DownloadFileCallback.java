package com.hcmute.finalproject.toeicapp.services.storage;

import com.hcmute.finalproject.toeicapp.entities.ToeicStorage;

public interface DownloadFileCallback {
    void onSuccess(ToeicStorage toeicStorage);
    void onProgressUpdate(int percent);
    void onError(String error);
}
