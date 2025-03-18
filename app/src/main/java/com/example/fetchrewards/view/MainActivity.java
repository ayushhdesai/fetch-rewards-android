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

        itemAdapter = new ItemAdapter();
        recyclerView.setAdapter(itemAdapter);

        itemController = new ItemController();
        itemController.fetchItems(this);
    }

    @Override
    public void onItemsFetched(List<Item> items) {
        itemAdapter.setItems(items);
    }

    @Override
    public void onError(String e) {
        Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
    }

}
