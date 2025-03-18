package com.example.fetchrewards.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String Base_URl = "https://fetch-hiring.s3.amazonaws.com/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if(retrofit ==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Base_URl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
