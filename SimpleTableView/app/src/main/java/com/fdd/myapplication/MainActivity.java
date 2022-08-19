package com.fdd.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fdd.myapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.itemTable.setText("Table");

        ArrayList<String> columnTitles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            columnTitles.add("竖标题" + i);
        }

        ArrayList<String> rowTitles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            rowTitles.add("横标题" + i);
        }

        ArrayList<ArrayList<String>> datas = new ArrayList<>();

        TitleAdapter columnTitleAdapter = new TitleAdapter(this, columnTitles);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rlColumnTitle.setLayoutManager(layoutManager);
        binding.rlColumnTitle.setAdapter(columnTitleAdapter);

        TitleAdapter rowTitleAdapter = new TitleAdapter(this, rowTitles);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rlRowTitle.setLayoutManager(layoutManager2);
        binding.rlRowTitle.setAdapter(rowTitleAdapter);

        DataAdapter dataAdapter = new DataAdapter(this, datas);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rlDate.setLayoutManager(layoutManager3);
        binding.rlDate.setAdapter(dataAdapter);

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                ArrayList<String> dataItem = new ArrayList<>();
                for (int j = 0; j < 20; j++) {
                    dataItem.add(i + " data " + j);
                }
                datas.add(dataItem);
            }
            runOnUiThread(() -> dataAdapter.notifyDataSetChanged());
        }).start();

        binding.rlColumnTitle.setOnMyScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                binding.rlDate.scrollBy(dx, dy);
            }
        });

        binding.rlDate.setOnMyScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                binding.rlColumnTitle.scrollBy(dx, dy);
            }
        });
    }
}