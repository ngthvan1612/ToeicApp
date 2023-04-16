package com.hcmute.finalproject.toeicapp.network.test;

import com.hcmute.finalproject.toeicapp.network.BaseRetrofitClient;

public class RetrofitTestRetrofitClient01 extends BaseRetrofitClient {
    private static final String BASE_URL = "https://gido-khong-biet";
    private static TestAPIService apiService;

    public static TestAPIService getInstance() {
        if (apiService == null) {
            apiService = createService(TestAPIService.class, BASE_URL);
        }

        return apiService;
    }
}
