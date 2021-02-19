/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.api.phone;

import Setting.ToolSetting;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.concurrent.Task;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
/**
 *
 * @author simpl
 */
public class CreateRequestTask extends Task<String> {
    private String apiKey;
    private String serviceID;
    public CreateRequestTask(String apiKey, String serviceID) {
        this.apiKey = apiKey;
        this.serviceID = serviceID;
    }
    @Override
    protected String call() throws Exception {
        return create(apiKey, serviceID);
    }
    public static String getResultFromRentCode(String apiKey,String serviceID) {
        try {
            Document document =Jsoup.connect("https://api.rentcode.net/api/v2/order/request?"
                    +"apiKey="+apiKey
                    +"&ServiceProviderId=40"
                    +"&NetworkProvider="+ToolSetting.getInstance().getPhoneOperation())
                    .userAgent("Mozilla/5.0")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .timeout(60 * 1000)
                    .ignoreContentType(true)
                    .method(Method.GET)
                    .get();
            return document.text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String getResultFromSimThue(String apiKey,String serviceID) {
        HttpURLConnection conn = null;
        try {
            String query = "";
            query = "http://api.pvaonline.net/request/create?key=" + apiKey + "&service_id=" + serviceID;
            URL url = new URL(query);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String result = "";
            for (int c; (c = in.read()) >= 0;) {
                result = result + ((char) c);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return "";
    }

    public static String create(String apiKey, String serviceID) {
        String requestId = "";
        try {
            String result = "";
            if(apiKey.length()==25){
                result=getResultFromSimThue(apiKey, serviceID);
            }else{
                result=getResultFromRentCode(apiKey, serviceID.contains("37")?"40":serviceID);
            }
            System.out.println(""+result);
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(result);
            if (json.get("success") != null && json.get("success").toString().contains("true")) {
                if (apiKey.length() == 25) {
                    requestId = json.get("id").toString();
                } else {
                    requestId = json.get("id").toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           
        }
        return requestId;
    }

}
