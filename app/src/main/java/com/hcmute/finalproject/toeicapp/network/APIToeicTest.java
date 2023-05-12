package com.hcmute.finalproject.toeicapp.network;

import com.hcmute.finalproject.toeicapp.dto.backend.AndroidToeicFullTest;
import com.hcmute.finalproject.toeicapp.dto.backend.AndroidToeicTestsResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface APIToeicTest {
    APIToeicTest instance = BaseRetrofitClient.createService(APIToeicTest.class, "https://khong-biet");
    static APIToeicTest getInstance() {
        return instance;
    }

    @GET("https://toeic-app.uteoj.com/api/android/toeic-test/get-tests-data")
    Call<AndroidToeicTestsResponse> getTestData();
}
