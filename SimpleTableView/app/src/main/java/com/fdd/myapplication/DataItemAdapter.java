package com.fdd.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fdd.myapplication.databinding.ItemTableBinding;

import java.util.ArrayList;

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.TitleViewHolder> {

    private final Context context;
    private final ArrayList<String> dataItem;

    public DataItemAdapter(Context context, ArrayList<String> dataItem) {
        this.context = context;
        this.dataItem = dataItem;
    }

    @NonNull
    @Override
    public TitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTableBinding binding = ItemTableBinding.inflate(LayoutInflater.from(context), parent, false);
        return new TitleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TitleViewHolder holder, int position) {
        holder.binding.itemTable.setText(dataItem.get(position));
    }

    @Override
    public int getItemCount() {
        return dataItem.size();
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder {
        ItemTableBinding binding;

        public TitleViewHolder(@NonNull ItemTableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
