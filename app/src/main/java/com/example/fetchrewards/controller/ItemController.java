package com.example.fetchrewards.controller;

import com.example.fetchrewards.model.Item;
import com.example.fetchrewards.network.RetrofitClient;
import com.example.fetchrewards.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class ItemController {

    private ApiService apiService;

    public ItemController() {
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public interface ItemFetchListener {
        void onItemsFetched(List<Item> items);
        void onError(String e);
    }

    public void fetchItems(final ItemFetchListener listener) {
        Call<List<Item>> call = apiService.getItems();

        call.enqueue(new Callback<List<Item>>() {

            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {

                if (response.isSuccessful()) {
                    List<Item> items = response.body();

                    if(items != null) {
                        List<Item> filterItem = new ArrayList<>();

                        for(Item item: items){
                            if(item.getName() != null && !item.getName().isEmpty()) {
                                filterItem.add(item);
                            }
                        }

                        filterItem.sort((item1, item2) -> {
                            int listIdComp = item1.getListId().compareTo(item2.getListId());
                            if (listIdComp == 0){
                                return item1.getName().compareTo(item2.getName());
                            }
                            return listIdComp;
                        });

                        listener.onItemsFetched(filterItem);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }


}
