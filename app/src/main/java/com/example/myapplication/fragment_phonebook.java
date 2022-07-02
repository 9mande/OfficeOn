package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

// 1번째 프래그먼트 (연락처)

public class fragment_phonebook extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View rootView;
    private RecyclerView recyclerView;
    private RVAdapter_phonebook rvAdapter;
    private final int REQUEST_PHONEBOOK = 10;

    public List<PhoneNumberVO> lstPhonebook = new ArrayList<PhoneNumberVO>();
    public Object PhoneNumberVO;

    Dialog mDialog;

    private String mParam1;
    private String mParam2;

    public fragment_phonebook() {
        // Required empty public constructor
    }

    public static fragment_phonebook newInstance(String param1, String param2) {
        fragment_phonebook fragment = new fragment_phonebook();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_phonebook, container, false);

        recyclerView = rootView.findViewById(R.id.contact_recyclerview);                                                         // Recycler View
        recyclerView.addItemDecoration(new DividerItemDecoration(rootView.getContext(), DividerItemDecoration.VERTICAL));        // 구분선 추가

        rvAdapter = new RVAdapter_phonebook(getContext(), lstPhonebook);                                                         // phonebook 어댑터
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));                                                   // linear layout manager (수직)
        recyclerView.setAdapter(rvAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);                                          // Item Touch Helper : Item 스와이프, 드래그 앤 드롭
        itemTouchHelper.attachToRecyclerView(recyclerView);

        mDialog = new Dialog(rootView.getContext());                                                                             // Item 팝업 Dialog
        mDialog.setContentView(R.layout.add_phonebook);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));                                         // 배경 투명하게

        EditText editText = rootView.findViewById(R.id.searchPhonebook);                                                         // 연락처 검색 기능
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                rvAdapter.getFilter().filter(charSequence.toString());                                                           // Text 바뀔때마다 Filter 바꿈
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        FloatingActionButton fab = rootView.findViewById(R.id.fab);                                                              // 연락처 추가 FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You can add a new address", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                mDialog.show();

                Button can_btn = (Button) mDialog.findViewById(R.id.button);
                Button ok_btn = (Button) mDialog.findViewById(R.id.button2);

                final EditText nameT = (EditText) mDialog.findViewById(R.id.editTextTextPersonName);
                final EditText phoneT = (EditText) mDialog.findViewById(R.id.editTextTextPersonName2);

                can_btn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });

                ok_btn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = nameT.getText().toString();
                        String phone = phoneT.getText().toString();

                        PhoneNumberVO phoneNumberVO = new PhoneNumberVO(name, phone);
                        lstPhonebook.add(phoneNumberVO);
                        rvAdapter.notifyDataSetChanged();
                        mDialog.dismiss();
                    }
                });

            }
        });

        FloatingActionButton fab2 = rootView.findViewById(R.id.fab_connect);                                                   // 연락처 불러오기
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, REQUEST_PHONEBOOK);
            }
        });


        return rootView;

    }

    Gson gson = new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstPhonebook = new ArrayList<>();

        try {
            InputStream is = getContext().getAssets().open("phoneNumbers.json");
            //AssetManager am = getResources().getAssets();
            //InputStream is = am.open("phoneNumbers.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("person");
            int index = 0;
            while (index < jsonArray.length()) {
                Object a = jsonArray.get(index);
                String b = a.toString();

                PhoneNumberVO phoneNumberVO = gson.fromJson(b, PhoneNumberVO.class);
                lstPhonebook.add(phoneNumberVO);
                index++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            lstPhonebook.remove(position);
            rvAdapter.notifyItemRemoved(position);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_PHONEBOOK && resultCode == Activity.RESULT_OK) {
            Cursor cursor = getActivity().getContentResolver().query(data.getData(),
                    new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER},
                    null, null, null);
            cursor.moveToFirst();
            String name = cursor.getString(0);
            String phone = cursor.getString(1);
            cursor.close();

            PhoneNumberVO phoneNumberVO = new PhoneNumberVO(name, phone);
            lstPhonebook.add(phoneNumberVO);

            rvAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
        }
    }


    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }


    public void onTextChanged(CharSequence s, int start, int before, int count) {
        rvAdapter.getFilter().filter(s.toString());
    }


    public void afterTextChanged(Editable editable) {

    }

}