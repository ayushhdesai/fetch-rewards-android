package com.example.fetchrewards.network;

import com.example.fetchrewards.model.Item;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface ApiService {
    @GET("hiring.json")
    Call<List<Item>> getItems();
}
