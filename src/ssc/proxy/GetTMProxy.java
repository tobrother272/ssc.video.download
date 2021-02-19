/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.proxy;

import javafx.concurrent.Task;
import com.google.common.net.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author simplesolution.co
 */
public class GetTMProxy extends Task<ProxyInfo> {
    private String key;
    public GetTMProxy(String key) {
        this.key = key;
    }
    public static ProxyInfo GetTMProxy(String key) {
        try {
            OkHttpClient client = new OkHttpClient();
            /*
            Request request = new Request.Builder()
                    .url("https://tmproxy.com/api/proxy/get-new-proxy")
                    .post(RequestBody
                    .create(MediaType
                        .parse("application/json"),
                            "{\"api_key\": \""+key+"\"}"
                    ))
                    .addHeader("Accept", "application/json")
                    .build();
            
            Response response = client.newCall(request).execute();
            String resultJson = response.body().string();
            
            
            JSONParser parser = new JSONParser();
            JSONObject jSONObject = (JSONObject) parser.parse(resultJson);
            String proxyString =((JSONObject)jSONObject.get("data")).get("https").toString();
            
            System.out.println("proxyString "+proxyString);
            
            
            ProxyInfo pi = new ProxyInfo(0, proxyString.split(":")[0], Integer.parseInt(proxyString.split(":")[1]), "", "", 0, "vn", "now", 0);
            return pi;
            */
            // 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static int getTMProxyLastTime(String key) {
        int lastTime = 120;
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://backend.simplesolution.co/TiktokApi/tmproxycurrent/"+key)
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            String resultJson = response.body().string();
            JSONParser parser = new JSONParser();
            JSONObject jSONObject = (JSONObject) parser.parse(resultJson);
            String proxyString =((JSONObject)jSONObject.get("data")).get("next_request").toString();

            return Integer.parseInt(proxyString);
            // 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastTime;
    }

  
    @Override
    protected ProxyInfo call() throws Exception {
        return GetTMProxy(key);
    }
    public void start() {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }
}
