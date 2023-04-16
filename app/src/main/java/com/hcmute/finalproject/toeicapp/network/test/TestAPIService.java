package com.hcmute.finalproject.toeicapp.network.test;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

public interface TestAPIService {
    @Streaming
    @GET("https://dl.dropboxusercontent.com/s/kn9qtfdiaqg2gxo/ahihi-nguyen-thanh-van-20110224.mp3")
    Response<ResponseBody> downloadTestFile01();
}
