package com.example.myapplication;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView iv_image;

    public ViewHolder(@NonNull View itemView){
        super(itemView);

        iv_image = itemView.findViewById(R.id.iv_image);
    }

    public void onBind(int data){
        iv_image.setImageResource(data);
    }
}
