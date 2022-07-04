package com.example.myapplication;/* Java 1.8 샘플 코드 */


import static java.lang.Integer.parseInt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class WeatherData {
    private static final String serviceKey = "smqvpocvWHGwD1w9ADvTBw9I0a0n2sh2uknYbSmip4giy5JJaQtWurR7rJZEQk9VGgDJKFoG41BWy1xiQ8yAHg%3D%3D";
    private String nx;	//위도
    private String ny;	//경도
    private String baseDate;	//조회하고싶은 날짜
    private String baseTime;	//조회하고싶은 시간
    private String type = "json";	//조회하고 싶은 type(json, xml 중 고름)

    private int weather, temperature;

    public WeatherData(String x, String y, String baseDate, String baseTime){
        this.nx = x;
        this.ny = y;
        this.baseDate = baseDate;
        this.baseTime = timeChange(baseTime);
    }

    public int getWeather() throws IOException, JSONException {
        boolean fail = true;
        StringBuilder sb;
        do {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
            urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*‘21년 6월 28일발표*/
            urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /*05시 발표*/
            urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
            urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점의 Y 좌표값*/


            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(20000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            conn.connect();

            BufferedReader rd;

            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line;
            sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            String result = sb.toString();

            // response 키를 가지고 데이터를 파싱
            JSONObject jsonObj_1 = new JSONObject(result);
            String response = jsonObj_1.getString("response");

            JSONObject jsonObj_test = new JSONObject(response);
            String header = jsonObj_test.getString("header");

            JSONObject jsonObj_test2 = new JSONObject(header);
            String code = jsonObj_test2.getString("resultCode");

            baseDate = String.valueOf(parseInt(baseDate) - 1);
            fail = code == "03";

        }while(fail);

        String result = sb.toString();

        // response 키를 가지고 데이터를 파싱
        JSONObject jsonObj_1 = new JSONObject(result);
        String response = jsonObj_1.getString("response");

        // response 로 부터 body 찾기
        JSONObject jsonObj_2 = new JSONObject(response);
        String body = jsonObj_2.getString("body");

        // body 로 부터 items 찾기
        JSONObject jsonObj_3 = new JSONObject(body);
        String items = jsonObj_3.getString("items");

        // items로 부터 itemlist 를 받기
        JSONObject jsonObj_4 = new JSONObject(items);
        JSONArray jsonArray = jsonObj_4.getJSONArray("item");

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObj_4 = jsonArray.getJSONObject(i);
            String fcstValue = jsonObj_4.getString("fcstValue");
            String category = jsonObj_4.getString("category");

            if (category.equals("SKY")) {
                if (fcstValue.equals("1")) {
                    weather = 1; //맑음
                } else if (fcstValue.equals("2")) {
                    weather = 2; //비
                } else if (fcstValue.equals("3")) {
                    weather = 3; //구름
                } else if (fcstValue.equals("4")) {
                    weather = 4; //흐림
                }
            }

            if (category.equals("TMP")) {
                temperature = parseInt(fcstValue);
            }
        }

        return weather*100+temperature;
    }


    public String timeChange(String time)
    {
        switch(time) {

            case "0200":
            case "0300":
            case "0400":
                time = "0200";
                break;
            case "0500":
            case "0600":
            case "0700":
                time = "0500";
                break;
            case "0800":
            case "0900":
            case "1000":
                time = "0800";
                break;
            case "1100":
            case "1200":
            case "1300":
                time = "1100";
                break;
            case "1400":
            case "1500":
            case "1600":
                time = "1400";
                break;
            case "1700":
            case "1800":
            case "1900":
                time = "1700";
                break;
            case "2000":
            case "2100":
            case "2200":
                time = "2000";
                break;
            default:
                time = "2300";

        }
        return time;
    }
}