package com.demo;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;

import javax.swing.text.Document;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.ceil;

public class test2 {

    public static void main(String[] args) throws JSONException, IOException {
        String dataUrl = "http://localhost:3000/content";
        CloseableHttpClient client = HttpClientBuilder.create().build();
        RequestConfig requestConfig = RequestConfig.custom().build();
        HttpGet httpGet = new HttpGet(dataUrl);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = client.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == 200){
            String jsonString = EntityUtils.toString(response.getEntity());
            JSONArray files =new JSONArray(jsonString);
            filter(files);
            response.close();
            client.close();
        }
    }

    public static List<String> filter(JSONArray arr) throws JSONException {
        JSONArray weeklyData = new JSONArray();
        JSONArray storeByStore = new JSONArray();
        JSONArray onlineStore = new JSONArray();
        JSONArray webAnalytics = new JSONArray();
        for(int i = 0; i < arr.length(); i++){
            JSONObject obj = arr.getJSONObject(i);
            if(obj.getString("name").contains("Online")){
                onlineStore.put(obj.getString("name"));
            } else if(obj.getString("name").contains("StoreReport")){
                    storeByStore.put(obj.getString("name"));
            } else if(obj.getString("name").contains("WebAnalytics")){
                webAnalytics.put(obj.getString("name"));
            } else {
                weeklyData.put(obj.getString("name"));
            }
        }

        List<String> filesToDownload = new ArrayList<>();
        filesToDownload.addAll(processMap(groupByCountry(weeklyData)));
        filesToDownload.addAll(processMap(groupByCountry(weeklyData)));
        filesToDownload.addAll(processMap(groupByCountry(weeklyData)));
        filesToDownload.addAll(processMap(groupByCountry(weeklyData)));
        System.out.println(filesToDownload);
        return filesToDownload;
    }

    public static List<String> processMap(Map<String, List<String>> GBCData){
        List<String> filesToDownload = new ArrayList<>();
        for (String key : GBCData.keySet()){
            filesToDownload.add(getLatestFile(GBCData.get(key)));
        }
        return filesToDownload;
    }

    public static String getLatestFile(List<String> arr) {
        String latestFile = arr.get(0);
        for (int i = 0; i < arr.size(); i++){
            String file = arr.get(i);
            if (file.compareToIgnoreCase(latestFile) == 1){
                latestFile = file;
            }
        }
        return latestFile;
    }

    public static Map<String, List<String>> groupByCountry(JSONArray arr) throws JSONException {
        Map<String, List<String>> groups = new HashMap<String, List<String>>();
        for(int i = 0; i < arr.length(); i++){
            String key = arr.getString(i).substring(0, 10);
            if(groups.containsKey(key)){
                groups.get(key).add(arr.getString(i));
            } else {
                List<String> newList = new ArrayList<String>();
                newList.add(arr.getString(i));
                groups.put(key, newList);
            }
        }
        return groups;
    }

}
