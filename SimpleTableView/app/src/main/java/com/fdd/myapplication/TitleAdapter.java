package com.fdd.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fdd.myapplication.databinding.ItemTableBinding;

import java.util.ArrayList;

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.TitleViewHolder> {

    private final Context context;
    private final ArrayList<String> columnTitles;

    public TitleAdapter(Context context, ArrayList<String> columnTitles) {
        this.context = context;
        this.columnTitles = columnTitles;
    }

    @NonNull
    @Override
    public TitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTableBinding binding = ItemTableBinding.inflate(LayoutInflater.from(context), parent, false);
        return new TitleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TitleViewHolder holder, int position) {
        holder.binding.itemTable.setText(columnTitles.get(position));
    }

    @Override
    public int getItemCount() {
        return columnTitles.size();
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder {
        ItemTableBinding binding;

        public TitleViewHolder(@NonNull ItemTableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
