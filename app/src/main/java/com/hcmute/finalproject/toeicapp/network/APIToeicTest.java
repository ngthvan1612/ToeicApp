package com.hcmute.finalproject.toeicapp.network;

import com.hcmute.finalproject.toeicapp.assets.backend.AndroidToeicTestsResponse;
import com.hcmute.finalproject.toeicapp.assets.backend.CheckSumStringResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIToeicTest {
    APIToeicTest instance = BaseRetrofitClient.createService(APIToeicTest.class, "https://khong-biet");
    static APIToeicTest getInstance() {
        return instance;
    }

    @GET("https://toeic-app.uteoj.com/api/android/toeic-test/get-tests-data")
    Call<AndroidToeicTestsResponse> getTestData();

    @GET("https://toeic-app.uteoj.com/api/toeic/toeic-full-test/check-sum-string")
    Call<CheckSumStringResponse> getTestDataCheckSumString();
}
