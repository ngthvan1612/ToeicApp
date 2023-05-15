package com.hcmute.finalproject.toeicapp.network.downloadfile;

import androidx.annotation.NonNull;

public interface OnDownloadListener<TResult> {
    void onProgressUpdate(
            @NonNull Integer percent
    );
    void onSuccess(
            @NonNull TResult result
    );
    void onError(
            @NonNull Exception exception
    );
}
