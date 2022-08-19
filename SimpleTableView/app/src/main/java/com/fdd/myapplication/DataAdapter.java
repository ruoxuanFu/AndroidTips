package com.fdd.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fdd.myapplication.databinding.ItemTableDataBinding;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private final Context context;
    private final ArrayList<ArrayList<String>> datas;

    public DataAdapter(Context context, ArrayList<ArrayList<String>> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTableDataBinding binding = ItemTableDataBinding.inflate(LayoutInflater.from(context), parent, false);
        return new DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        ArrayList<String> rowData = datas.get(position);

        holder.binding.rlItemData.setFocusable(false);
        holder.binding.rlItemData.setNestedScrollingEnabled(false);
        DataItemAdapter dataItemAdapter = new DataItemAdapter(context, rowData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.binding.rlItemData.setLayoutManager(layoutManager);
        holder.binding.rlItemData.setAdapter(dataItemAdapter);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        ItemTableDataBinding binding;

        public DataViewHolder(@NonNull ItemTableDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
