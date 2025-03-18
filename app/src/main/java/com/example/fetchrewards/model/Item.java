package com.example.fetchrewards.model;

public class Item {

    private int id;
    private String listId;
    private String name;

    // Constructor for the Item class
    public Item(int id, String listId, String name) {
        this.id = id;
        this.listId = listId;
        this.name = name;
    }

    // Getter functions
    public int getId() {
        return id;
    }

    public String getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }
}
