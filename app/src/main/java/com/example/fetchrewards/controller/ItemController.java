package com.example.fetchrewards.controller;

import com.example.fetchrewards.model.Item;
import com.example.fetchrewards.network.RetrofitClient;
import com.example.fetchrewards.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

                    if (items != null) {
                        List<Item> filteredItems = new ArrayList<>();
                        for (Item item : items) {
                            if (item.getName() != null && !item.getName().isEmpty()) {
                                filteredItems.add(item);
                            }
                        }

                        Map<Integer, List<Item>> groupedItems = new TreeMap<>();
                        for (Item item : filteredItems) {
                            int listId = Integer.parseInt(item.getListId());
                            groupedItems.putIfAbsent(listId, new ArrayList<>());
                            groupedItems.get(listId).add(item);
                        }

                        List<Item> sortedItems = new ArrayList<>();
                        for (List<Item> group : groupedItems.values()) {
                            group.sort(Comparator.comparing(Item::getName));
                            sortedItems.addAll(group);
                        }

                        listener.onItemsFetched(sortedItems);
                    }
                } else {
                    listener.onError("Response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }



}
