package com.example.fetchrewards.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchrewards.R;
import com.example.fetchrewards.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Map<Integer, List<Item>> groupedItems;

    public ItemAdapter(Map<Integer, List<Item>> groupedItems) {
        this.groupedItems = groupedItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_id_header, parent, false);
            return new ListIdViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListIdViewHolder) {
            int listId = getListIdForPosition(position);
            ((ListIdViewHolder) holder).listIdTextView.setText("List ID: " + listId);
        } else if (holder instanceof ItemViewHolder) {
            List<Item> itemList = getItemListForPosition(position);
            int itemCount = itemList.size();
            ((ItemViewHolder) holder).countTextView.setText("Item Count: " + itemCount);
            StringBuilder itemNames = new StringBuilder();
            for (int i = 0; i < itemList.size(); i++) {
                itemNames.append(itemList.get(i).getName());
                if (i < itemList.size() - 1) {
                    itemNames.append(", ");
                }
            }
            ((ItemViewHolder) holder).nameTextView.setText(itemNames.toString());
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        for (List<Item> itemList : groupedItems.values()) {
            itemCount += 1;
            itemCount += 1;
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        int count = 0;
        for (Map.Entry<Integer, List<Item>> entry : groupedItems.entrySet()) {
            int size = entry.getValue().size();

            if (position == count) {
                return 0;
            }

            if (position == count + 1) {
                return 1;
            }

            count += 2;
        }
        return 0;
    }

    private List<Item> getItemListForPosition(int position) {
        int count = 0;
        for (Map.Entry<Integer, List<Item>> entry : groupedItems.entrySet()) {
            int size = entry.getValue().size();
            if (position == count + 1) {
                return entry.getValue();
            }
            count += 2;
        }
        return new ArrayList<>();
    }

    private int getListIdForPosition(int position) {
        int count = 0;
        for (Map.Entry<Integer, List<Item>> entry : groupedItems.entrySet()) {
            if (position == count) {
                return entry.getKey();
            }
            count += 2;
        }
        return -1;
    }

    public void setGroupedItems(Map<Integer, List<Item>> groupedItems) {
        this.groupedItems = groupedItems;
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, countTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
            countTextView = itemView.findViewById(R.id.item_list_count);
        }
    }

    public static class ListIdViewHolder extends RecyclerView.ViewHolder {
        TextView listIdTextView;

        public ListIdViewHolder(View itemView) {
            super(itemView);
            listIdTextView = itemView.findViewById(R.id.item_list_id_header);
        }
    }
}
