package com.example.myapplication;

import static java.lang.Integer.parseInt;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

import java.text.SimpleDateFormat;

// 3번째 프래그먼트 (자유 주제)

public class fragment_3 extends Fragment {

    private SimpleDateFormat sdf;
    private Date date;
    private static final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View rootView;
    private RecyclerView recyclerView;
    private RVAdapter rvAdapter;
    public List<TodoVO> lstTodo = new ArrayList<TodoVO>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_3() {
        // Required empty public constructor
    }

    public static fragment_3 newInstance(String param1, String param2) {
        fragment_3 fragment = new fragment_3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_3, container, false);

        recyclerView = rootView.findViewById(R.id.todo_container);                                                               // Recycler View
        recyclerView.addItemDecoration(new DividerItemDecoration(rootView.getContext(), DividerItemDecoration.VERTICAL));        // 구분선 추가
        rvAdapter = new RVAdapter(getContext(), lstTodo);                                                                               // 어댑터
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));                                                   // linear layout manager (수직)
        recyclerView.setAdapter(rvAdapter);

        sdf = new SimpleDateFormat("MM dd, yyyy");
        date = new Date(System.currentTimeMillis());
        String date_string = (String) sdf.format(date);
        String[] date_arr = date_string.split(" ");
        int month = parseInt(date_arr[0]);
        date_arr[0] = months[month-1];

        TextView tv_date = (TextView) rootView.findViewById(R.id.today_date);
        tv_date.setText(String.join(" ", date_arr));


        EditText input_todo = rootView.findViewById(R.id.input_todo);
        input_todo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()==0){
                    input_todo.setHintTextColor(ContextCompat.getColor(rootView.getContext(), R.color.red));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Button todo_button_add = rootView.findViewById(R.id.todo_btn_add);
        todo_button_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                boolean checked_todo = false;
                String content_todo = input_todo.getText().toString();


                if( ! (content_todo.length() == 0)){
                    TodoVO todoVO = new TodoVO(checked_todo, content_todo);
                    lstTodo.add(todoVO);
                    rvAdapter.notifyDataSetChanged();
                    input_todo.setText("");
                    input_todo.setHintTextColor(ContextCompat.getColor(rootView.getContext(), R.color.dark));
                }
                else {
                    Toast.makeText(getContext(), "내용을 채워주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}