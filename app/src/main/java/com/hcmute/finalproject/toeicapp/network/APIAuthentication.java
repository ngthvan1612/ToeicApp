package com.hcmute.finalproject.toeicapp.network;

import androidx.annotation.NonNull;

import com.hcmute.finalproject.toeicapp.services.authentication.model.ToeicUserSignupRequest;
import com.hcmute.finalproject.toeicapp.services.authentication.model.ToeicUserSignupResponse;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.AndroidToeicTestsResponse;
import com.hcmute.finalproject.toeicapp.services.backend.tests.model.CheckSumStringResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIAuthentication {
    APIAuthentication instance = BaseRetrofitClient
            .createService(APIAuthentication.class, "https://toeic-app.uteoj.com/");
    static APIAuthentication getInstance() {
        return instance;
    }

    @POST("/api/toeic/auth/signup")
    Call<ToeicUserSignupResponse> refreshRegisterUser(
            @NonNull @Body ToeicUserSignupRequest request
            );
}
