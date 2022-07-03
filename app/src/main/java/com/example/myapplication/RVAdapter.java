package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private ArrayList<Integer> imgList = new ArrayList<>();
    Context mContext;
    List<TodoVO> mData;

    public RVAdapter(Context mContext, List<TodoVO> mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.todo_item, parent, false);
        final ViewHolder vHolder = new ViewHolder(view);

        vHolder.checked_todo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(mContext, "뿅", Toast.LENGTH_SHORT);
            }
        });

        vHolder.delete_todo.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               mData.remove(vHolder.getAdapterPosition());
               notifyItemRemoved(vHolder.getAdapterPosition());
           }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TodoVO todoVO = mData.get(position);

        holder.checked_todo.setChecked(todoVO.getChecked());
        holder.content_todo.setText(todoVO.getContent());
    }

    @Override
    public int getItemCount() {
        if (mData == null){
            return 0;
        }
        return mData.size();
    }

    void addItem(TodoVO data) {
        // 외부에서 item을 추가시킬 함수입니다.
        mData.add(data);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout_todo;
        private CheckBox checked_todo;
        private EditText content_todo;
        private Button delete_todo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_todo = itemView.findViewById(R.id.layout_todo);
            checked_todo = itemView.findViewById(R.id.checked_todo);
            content_todo = itemView.findViewById(R.id.content_todo);
            delete_todo = itemView.findViewById(R.id.delete_todo);
        }
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