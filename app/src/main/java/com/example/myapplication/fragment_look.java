package com.example.myapplication;

import static java.lang.Integer.parseInt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private String weather;
    private int temperature;

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
                weather = weathers[tmp/100];
                temperature = tmp%100;
            }
        };
        weatherThread.start();
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