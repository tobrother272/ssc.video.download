/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.proxy;

import javafx.concurrent.Task;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import api.ServiceAction;

/**
 *
 * @author simplesolution.co
 */
public class GetProxyTinSoft extends Task<ProxyInfo> {
    private String key;
    public GetProxyTinSoft(String key) {
        this.key = key;
    }
    public static ProxyInfo GetProxyTinSoftM(String key) {
        try {
            String result = ServiceAction.getResultFromAnyAPI("http://proxy.tinsoftsv.com/api/changeProxy.php?key=" + key + "&location=0", null);
            JSONParser parser = new JSONParser();
            JSONObject jSONObject = (JSONObject) parser.parse(result);
            ProxyInfo pi = new ProxyInfo(0, jSONObject.get("proxy").toString().split(":")[0], Integer.parseInt(jSONObject.get("proxy").toString().split(":")[1]), "", "", 0, "vn", "now", 0);
            return pi;

            // 
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }
    
    public static int getTinsoftLastTime(String key) {
        int lastTime = 120;
        try {
            String result = ServiceAction.getResultFromAnyAPI("http://proxy.tinsoftsv.com/api/getProxy.php?key=" + key, null);
            JSONParser parser = new JSONParser();
            JSONObject jSONObject = (JSONObject) parser.parse(result);
            if (jSONObject.get("description") != null) {
                if (jSONObject.get("description").toString().contains("proxy not found")) {
                    return 0;
                }
            }

            return Integer.parseInt(jSONObject.get("next_change").toString());
            // 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastTime;
    }

  
    @Override
    protected ProxyInfo call() throws Exception {
        return GetProxyTinSoftM(key);
    }
    public void start() {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }
}
