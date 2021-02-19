/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.proxy;

import javafx.concurrent.Task;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import Utils.StringUtil;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 *
 * @author simplesolution.co
 */
public class CheckTMProxy extends Task<Boolean> {

    private String key;

    public CheckTMProxy(String key) {
        this.key = key;
    }

    public static int getHourKeyExpired(String key) {
        int resultExpired = 0;
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://backend.simplesolution.co/TiktokApi/tmproxy/71df024f3680a8dd899476d3ab0cf8c9")
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            String resultJson = response.body().string();
            System.out.println(""+resultJson);
            JSONParser parser = new JSONParser();
            JSONObject jSONObject = (JSONObject) parser.parse(resultJson);
            if (jSONObject.get("data") != null) {
                JSONObject objData= (JSONObject)(jSONObject.get("data"));
                resultJson = objData.get("expired_at").toString();
            } else {
                resultJson = jSONObject.get("date_expired").toString();
            }
            long current=System.currentTimeMillis();
            if(resultJson.length()==0){
                return resultExpired;
            }
            long getlongTime=StringUtil.getCurrentLongTimeFromDate("HH:mm:ss dd/MM/yyyy", resultJson);
            long subTime=(getlongTime-current)/1000/60/60;
            resultExpired = (int) subTime;
            // 
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultExpired;
    }

    @Override
    protected Boolean call() throws Exception {
        updateMessage(""+getHourKeyExpired(key));
        return true;
    }

    public void start() {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }
}
