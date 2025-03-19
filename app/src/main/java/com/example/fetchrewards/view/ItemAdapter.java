package com.example.fetchrewards.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchrewards.R;
import com.example.fetchrewards.model.Item;

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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListIdViewHolder) {
            int listId = (int) getItem(position);
            ((ListIdViewHolder) holder).listIdTextView.setText("List ID: " + listId);
        } else if (holder instanceof ItemViewHolder) {
            Item item = (Item) getItem(position);
            ((ItemViewHolder) holder).nameTextView.setText(item.getName());
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        for (List<Item> itemList : groupedItems.values()) {
            itemCount += itemList.size() + 1;
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
            if (position <= count + size) {
                return 1;
            }
            count += size + 1;
        }
        return 0;
    }

    private Object getItem(int position) {
        int count = 0;
        for (Map.Entry<Integer, List<Item>> entry : groupedItems.entrySet()) {
            int size = entry.getValue().size();
            if (position == count) {
                return entry.getKey();
            }
            if (position <= count + size) {
                return entry.getValue().get(position - count - 1);
            }
            count += size + 1;
        }
        return null;
    }

    public void setGroupedItems(Map<Integer, List<Item>> groupedItems) {
        this.groupedItems = groupedItems;
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
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
