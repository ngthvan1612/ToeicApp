package com.hcmute.finalproject.toeicapp.network;

import androidx.annotation.NonNull;

import com.hcmute.finalproject.toeicapp.services.backend.favorite.model.RestoreFavoriteRequest;
import com.hcmute.finalproject.toeicapp.services.backend.favorite.model.RestoreFavoriteResponse;

import okhttp3.ResponseBody;
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

    @POST("")
    Call<ResponseBody> backupFavoriteDatabase(

    );
}
