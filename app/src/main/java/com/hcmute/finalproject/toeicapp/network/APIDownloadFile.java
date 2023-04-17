package com.hcmute.finalproject.toeicapp.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface APIDownloadFile {
    APIDownloadFile instance = BaseRetrofitClient.createService(APIDownloadFile.class, "");
    static APIDownloadFile getInstance() {
        return instance;
    }

    @GET
    Call<ResponseBody> downloadGET(@Url String url);

    @POST
    Call<ResponseBody> downloadPOST(@Url String url);
}
