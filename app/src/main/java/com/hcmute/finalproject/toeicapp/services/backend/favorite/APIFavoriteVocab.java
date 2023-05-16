package com.hcmute.finalproject.toeicapp.services.backend.favorite;

import androidx.annotation.NonNull;

import com.hcmute.finalproject.toeicapp.network.BaseRetrofitClient;
import com.hcmute.finalproject.toeicapp.services.backend.favorite.model.BackupFavoriteRequest;
import com.hcmute.finalproject.toeicapp.services.backend.favorite.model.BackupFavoriteResponse;
import com.hcmute.finalproject.toeicapp.services.backend.favorite.model.RestoreFavoriteRequest;
import com.hcmute.finalproject.toeicapp.services.backend.favorite.model.RestoreFavoriteResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIFavoriteVocab {
    APIFavoriteVocab instance = BaseRetrofitClient
            .createService(APIFavoriteVocab.class, "https://toeic-app.uteoj.com/");
    static APIFavoriteVocab getInstance() {
        return instance;
    }

    @POST("/api/android/favorite-vocab/restore")
    Call<RestoreFavoriteResponse> restoreFavoriteDatabase(
            @NonNull @Body RestoreFavoriteRequest request
    );

    @POST("/api/android/favorite-vocab/backup")
    Call<BackupFavoriteResponse> backupFavoriteDatabase(
            @NonNull @Body BackupFavoriteRequest request
    );
}
