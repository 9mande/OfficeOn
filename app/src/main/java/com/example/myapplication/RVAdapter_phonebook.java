package com.example.myapplication;


import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RVAdapter_phonebook extends RecyclerView.Adapter<RVAdapter_phonebook.MyViewHolder> implements Filterable {

    //액티비티의 Context, Data추가
    Context mContext;
    List<PhoneNumberVO> mData; // filteredList
    List<PhoneNumberVO> unFilData; // unfilteredlist
    Dialog mDialog;
    private final int GALLERY_REQUEST_CODE = 50;


    public RVAdapter_phonebook(Context mContext, List<PhoneNumberVO> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.unFilData = mData;
    }

    //1) onCreateViewHolder: ViewHolder 생성 시 호출. 처음 화면에 보이는 View에 대해 생성
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v ;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_phonebook, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        //Dialog init
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_phonebook);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //------------------------------------------------------------------------------------------
        vHolder.item_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Text Click"+ String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();

                TextView dialog_name_tv = mDialog.findViewById(R.id.dialog_name);
                TextView dialog_phone_tv = mDialog.findViewById(R.id.dialog_phone);
                ImageView dialog_img = mDialog.findViewById(R.id.dialog_img);
                dialog_name_tv.setText(mData.get(vHolder.getAdapterPosition()).getName());
                dialog_phone_tv.setText(mData.get(vHolder.getAdapterPosition()).getPhone());

                mDialog.show();

                dialog_img.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                        Activity activity = (Activity) mContext;
                        activity.startActivityForResult(intent, GALLERY_REQUEST_CODE);
                    }

                });

                Button call_btn = (Button) mDialog.findViewById(R.id.dialog_btn_call);
                Button msg_btn = (Button) mDialog.findViewById(R.id.dialog_btn_message);

                call_btn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "Call button click", Toast.LENGTH_SHORT).show();

                        try {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + vHolder.tv_phone.getText().toString()));
                            v.getContext().startActivity(callIntent);
                        }
                        catch (ActivityNotFoundException e) {
                            Log.e("Call", "Fail to Calling", e);
                        }
                    }
                });

                msg_btn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "Msg button click", Toast.LENGTH_SHORT).show();

                        try {
                            Intent msgIntent = new Intent(Intent.ACTION_VIEW);
                            msgIntent.setData(Uri.parse("sms:" + vHolder.tv_phone.getText().toString()));
                            msgIntent.putExtra("sms_body", "Hello world!");
                            v.getContext().startActivity(msgIntent);
                        }
                        catch (ActivityNotFoundException e) {
                            Log.e("Msg", "Fail to Messaging", e);
                        }
                    }
                });

            }
        });

        vHolder.item_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Move to Dial"+ String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();

                try {
                    Intent newIntent = new Intent(Intent.ACTION_DIAL);
                    newIntent.setData(Uri.parse("tel:" + vHolder.tv_phone.getText().toString()));
                    v.getContext().startActivity(newIntent);
                }
                catch (ActivityNotFoundException e) {
                    Log.e("Dial", "Fail to dial", e);
                }

            }
        });
        //------------------------------------------------------------------------------------------

        return vHolder;
    }

    //2 onBindViewHolder: 화면에 ViewHolder가 붙을 때마다 호출 (실질적인 데이터 처리 이루어짐)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //PhoneNumberVO phoneNumberVO = mData.get(position);
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_phone.setText(mData.get(position).getPhone());
        holder.tv_position.setText(mData.get(position).getPosition());
        // holder.tv_company.setText(mData.get(position).getCompany());
        //holder.img.setImageResource(mData.get(position).getPhoto());
    }

    //3) getItemCount
    @Override
    public int getItemCount() {
        if (mData == null){
            return 0;
        }
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String str = constraint.toString();
                if(str.isEmpty()) {
                    mData = unFilData;
                } else {
                    List<PhoneNumberVO> filteringList = new ArrayList<>();
                    for(PhoneNumberVO item : unFilData) {
                        if(item.getName().toLowerCase().contains(str) || item.getName().contains(str) || item.getPhone().contains(str))
                            filteringList.add(item);
                    }
                    mData = filteringList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mData;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mData = (List<PhoneNumberVO>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    //0) 뷰홀더 이너클래스 생성
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item_contact;      //item_contact의 부모레이아웃
        private ImageView item_call;
        private TextView tv_name;
        private TextView tv_phone;
        private TextView tv_company;
        private TextView tv_position;
        private TextView tv_email;

        private ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_contact = itemView.findViewById(R.id.contact_item_id);
            item_call = itemView.findViewById(R.id.call_img);
            tv_name = itemView.findViewById(R.id.name_contact);
            tv_phone = itemView.findViewById(R.id.phone_contact);
            //tv_company = itemView.findViewById(R.id.company_contact);
            tv_position = itemView.findViewById(R.id.position_contact);
            // tv_email = itemView.findViewById(R.id.email_contact);
            img = itemView.findViewById(R.id.img_contact);
        }
    }

}
