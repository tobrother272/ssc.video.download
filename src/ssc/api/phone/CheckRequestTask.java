/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.api.phone;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.concurrent.Task;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author simpl
 */
public class CheckRequestTask extends Task<String> {

    private String apiKey;
    private String requestID;
    private int mode;

    public CheckRequestTask(String apiKey, String requestID, int mode) {
        this.apiKey = apiKey;
        this.requestID = requestID;
        this.mode = mode;
    }

    @Override
    protected String call() throws Exception {
        return check(apiKey, requestID, mode);
    }

    public static String getSMSFromRentCode(String apiKey, String requestID) {
        try {
            Document document =Jsoup.connect("https://api.rentcode.net/api/v2/order/"+requestID+"/check?apiKey="+apiKey)
                    .userAgent("Mozilla/5.0")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .timeout(60 * 1000)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .get();
            return document.text();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return "";
    }

    public static String getPhoneFromRentCode(String apiKey, String requestID) {
        try {
            Document document =Jsoup.connect("https://api.rentcode.net/api/v2/order/"+requestID+"/check?apiKey="+apiKey)
                    .userAgent("Mozilla/5.0")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .timeout(60 * 1000)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .get();
            return document.text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getPhoneAndSMSFromSimThue(String apiKey, String requestID) {
        HttpURLConnection conn = null;
        try {
            String query = "";
            query = "http://api.pvaonline.net/request/check?key=" + apiKey + "&id=" + requestID;
            URL url = new URL(query);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String result = "";
            for (int c; (c = in.read()) >= 0;) {
                result = result + ((char) c);
            }
            return result ;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return "";
    }
    public static String check(String apiKey, String requestID, int mode) {
        String resutl = "";
        try {
            String getResult = "";
            if(apiKey.length()==25){
                getResult=getPhoneAndSMSFromSimThue(apiKey, requestID);
            }else{
                if(mode==0){
                    getResult=getPhoneFromRentCode(apiKey,requestID);
                    System.out.println("getResult "+getResult);
                }else{
                    getResult=getSMSFromRentCode(apiKey, requestID);
                    System.out.println("getResult "+getResult);
                }
            }
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(getResult);
            if (apiKey.length() == 25) {
                if (json.get("success") == null || !json.get("success").toString().contains("true")) {
                    return "";
                }
                if (mode == 0) {
                    resutl =json.get("phoneNumber").toString();
                } else {
                    resutl = json.get("message").toString().replaceAll("\\D+","");
                }
            } else {
                if(json.get("statusString").toString().contains("OutOfService")){
                    return "OutOfService";
                }
                if (mode == 0) {
                    resutl =json.get("phoneNumber").toString();
                } else {
                    resutl = json.get("message").toString().replaceAll("\\D+","");
                }
            }

        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
           
        }
        //Ma truy cap cua ban la 606826

        if (resutl.contains("G-")) {
            return resutl.substring(2, 8);
        } else if (resutl.contains("la")) {
            return resutl.replaceAll("\\D+", "");
        } else {
            return resutl.replaceAll("\\D+", "");
        }
    }

}
