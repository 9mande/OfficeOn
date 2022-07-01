package com.example.myapplication;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    List<Gallery> items;
    Context context;
    Dialog mDialog, rDialog;

    private RecyclerView myrecyclerview;
    private RecyclerPhoto recyclerViewAdapter;

    public GridViewAdapter(Context mContext, List<Gallery> mData) {
        this.context = mContext;
        this.items = mData;
    }

    public void addItem(Gallery gallery) {
        items.add(gallery);
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        Gallery gallery = items.get(position);
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gallery_item, parent, false);
        }
        ImageView photo = convertView.findViewById(R.id.iv_image);

        if(gallery.getPhoto() == -1) { photo.setImageBitmap(gallery.getBitmap()); }
        else {
            photo.setImageResource(gallery.getPhoto());
        }



        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.recycler_photo);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //mDialog.getWindow().getAttributes().windowAnimations = R.anim.scale;
        ScrollView scroll = new ScrollView(context);

        rDialog = new Dialog(context);
        rDialog.setContentView(R.layout.remove_photo);


        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myrecyclerview = mDialog.findViewById(R.id.gallery_recyclerview);
                recyclerViewAdapter = new RecyclerPhoto(context, items);
                myrecyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                myrecyclerview.getLayoutManager().scrollToPosition(position + items.size()*300);
                myrecyclerview.setAdapter(recyclerViewAdapter);
                mDialog.show();
            }
        });


        photo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                rDialog.show();

                Button yes_btn = (Button) rDialog.findViewById(R.id.yes_button);
                Button no_btn = (Button) rDialog.findViewById(R.id.no_button);

                yes_btn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        items.remove(position);
                        notifyDataSetChanged();
                        rDialog.dismiss();
                    }
                });

                no_btn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(),"Cancel remove", Toast.LENGTH_SHORT).show();
                        rDialog.dismiss();
                    }
                });

                return true;
            }
        });

        return convertView;
    }
}
