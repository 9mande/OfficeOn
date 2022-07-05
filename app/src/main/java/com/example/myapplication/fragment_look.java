package com.example.myapplication;

import static java.lang.Integer.parseInt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_look#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_look extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;

    private SimpleDateFormat sdf, sdf2;
    private Date date;

    ImageView look_weather;
    TextView look_temperature;
    TextView look_when;

    private static final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private static final String[] weathers = {"맑음", "비", "구름", "흐림"};
    private ArrayList<ArrayList<Integer>> clothes = new ArrayList<ArrayList<Integer>>();
    private String weather;
    private int temperature;
    private int season;

    public fragment_look() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_look.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_look newInstance(String param1, String param2) {
        fragment_look fragment = new fragment_look();
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

        sdf = new SimpleDateFormat("yyyyMMdd HHmm");
        date = new Date(System.currentTimeMillis());
        String date_string = (String) sdf.format(date);
        String dates = date_string.split(" ")[0];
        String times = date_string.split(" ")[1];
        times = times.substring(0, 2) + "00";

        WeatherData weatherData = new WeatherData("67", "101", dates, times);
        Thread weatherThread = new Thread(){
            public void run(){
                int tmp = 0;
                try {
                    tmp = weatherData.getWeather();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                weather = weathers[tmp/100 - 1];
                temperature = tmp%100;
            }
        };
        weatherThread.start();
        // Weather Thread 진행 동안 Cloth 불러옴

        try{
            weatherThread.join();
            update(rootView);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_look, container, false);
        look_weather = rootView.findViewById(R.id.look_weather);
        look_temperature = rootView.findViewById(R.id.look_temperature);
        look_when = rootView.findViewById(R.id.look_when);

        if (!(look_weather == null && look_temperature == null && look_when == null)){
            update(rootView);
        }

        Button look_another = rootView.findViewById(R.id.look_another);
        ImageView look_office = rootView.findViewById(R.id.look_office);


        ArrayList<Integer> tmp0 = new ArrayList<Integer>();

        tmp0.add(R.drawable.f_sp1);
        tmp0.add(R.drawable.f_sp2);
        tmp0.add(R.drawable.f_sp3);
        tmp0.add(R.drawable.f_sp4);
        tmp0.add(R.drawable.m_sp1);
        tmp0.add(R.drawable.m_sp2);
        tmp0.add(R.drawable.m_sp3);
        tmp0.add(R.drawable.m_sp4);
        clothes.add(tmp0);

        ArrayList<Integer> tmp1 = new ArrayList<Integer>();
        tmp1.add(R.drawable.f_s1);
        tmp1.add(R.drawable.f_s2);
        tmp1.add(R.drawable.f_s3);
        tmp1.add(R.drawable.f_s4);
        tmp1.add(R.drawable.m_s1);
        tmp1.add(R.drawable.m_s2);
        tmp1.add(R.drawable.m_s3);
        tmp1.add(R.drawable.m_s4);
        clothes.add(tmp1);

        ArrayList<Integer> tmp2 = new ArrayList<Integer>();
        tmp2.add(R.drawable.f_f1);
        tmp2.add(R.drawable.f_f2);
        tmp2.add(R.drawable.f_f3);
        tmp2.add(R.drawable.f_f4);
        tmp2.add(R.drawable.m_f1);
        tmp2.add(R.drawable.m_f2);
        tmp2.add(R.drawable.m_f3);
        tmp2.add(R.drawable.m_f4);
        clothes.add(tmp2);

        ArrayList<Integer> tmp3 = new ArrayList<Integer>();
        tmp3.add(R.drawable.f_w1);
        tmp3.add(R.drawable.f_w2);
        tmp3.add(R.drawable.f_w3);
        tmp3.add(R.drawable.f_w4);
        tmp3.add(R.drawable.m_w1);
        tmp3.add(R.drawable.m_w2);
        tmp3.add(R.drawable.m_w3);
        tmp3.add(R.drawable.m_w4);
        clothes.add(tmp3);
        tmp3.clear();

        Log.d("asdf", clothes.toString());

        Random i = new Random();

        look_another.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (temperature >= 23){
                    season = 1;
                } else if (temperature >= 17){
                    season = 0;
                } else if (temperature >= 9){
                    season = 2;
                } else{
                    season = 3;
                }

                look_office.setImageResource(clothes.get(season).get(i.nextInt(clothes.get(season).size())));
            }
        });

        return rootView;
    }

    public void update(View rootView){
        try{

            if (weather == "맑음") {
                look_weather.setImageResource(R.drawable.sun);

            }else if (weather == "비") {
                look_weather.setImageResource(R.drawable.rain);

            }else if (weather == "구름") {
                look_weather.setImageResource(R.drawable.cloud);

            }else if (weather == "흐림") {
                look_weather.setImageResource(R.drawable.sun_cloud);
            }

            look_temperature.setText(temperature + "℃");
            sdf2 = new SimpleDateFormat("MM월 dd일 날씨");
            look_when.setText((String)sdf2.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}