package com.hcmute.finalproject.toeicapp.network.test;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface TestAPIService {
    @Streaming
    @GET("https://dl.dropboxusercontent.com/s/kn9qtfdiaqg2gxo/ahihi-nguyen-thanh-van-20110224.mp3")
    Call<ResponseBody> downloadTestFile01();

    @Streaming
    @GET("https://dl.dropboxusercontent.com/s/whbu8or0k1w3olb/600-tu-toeic.json")
    Call<ResponseBody> download600ToeicVocabularies();

    @Streaming
    @GET("https://dl.dropboxusercontent.com/s/8emoz91lpt5210y/ahihi-full.json")
    Call<ResponseBody> downloadMockDatabase();

    @GET("http://toeic-app.uteoj.com/api/toeic-app/practice/{id}")
    Call<ResponseBody> fetchPartConfiguration(@Path("id") Integer id);
}
