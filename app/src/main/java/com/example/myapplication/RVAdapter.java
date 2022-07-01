package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Integer> imgList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).onBind(imgList.get(position));
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }

    void addItem(int data) {
        // 외부에서 item을 추가시킬 함수입니다.
        imgList.add(data);
    }


}

/*
    private void getData(){
        adapter.addItem(R.drawable.one);
        adapter.addItem(R.drawable.two);
        adapter.addItem(R.drawable.three);
        adapter.addItem(R.drawable.four);
    }
 */