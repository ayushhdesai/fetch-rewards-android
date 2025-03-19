package com.example.fetchrewards.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fetchrewards.R;
import com.example.fetchrewards.model.Item;
import com.example.fetchrewards.view.ItemAdapter;
import com.example.fetchrewards.controller.ItemController;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemController.ItemFetchListener {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private ItemController itemController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemAdapter = new ItemAdapter(new TreeMap<>());
        recyclerView.setAdapter(itemAdapter);

        itemController = new ItemController();
        itemController.fetchItems(this);
    }

    @Override
    public void onItemsFetched(List<Item> items) {
        Map<Integer, List<Item>> groupedItems = new TreeMap<>();

        for (Item item : items) {
            int listId = Integer.parseInt(item.getListId());
            groupedItems.putIfAbsent(listId, new ArrayList<>());
            groupedItems.get(listId).add(item);
        }

        for (Map.Entry<Integer, List<Item>> entry : groupedItems.entrySet()) {
            List<Item> itemList = entry.getValue();
            itemList.sort((item1, item2) -> {
                int number1 = extractNumberFromName(item1.getName());
                int number2 = extractNumberFromName(item2.getName());

                return Integer.compare(number1, number2);
            });
        }

        itemAdapter.setGroupedItems(groupedItems);
    }

    private int extractNumberFromName(String name) {
        String numberString = name.replaceAll("[^0-9]", "");
        return numberString.isEmpty() ? 0 : Integer.parseInt(numberString);
    }


    @Override
    public void onError(String e) {
        Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
    }
}
