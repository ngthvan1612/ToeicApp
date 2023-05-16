package com.hcmute.finalproject.toeicapp.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Reference: Bai Giang Lap Trinh Android thay Nguyen Huu Trung
 */
public class BaseRetrofitClient {
    private final static HttpLoggingInterceptor sLogging
            = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private final static OkHttpClient.Builder sHttpClient
            = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        if (!sHttpClient.interceptors().contains(sLogging)) {
            sHttpClient.addInterceptor(sLogging);
            builder.client(sHttpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }
}
