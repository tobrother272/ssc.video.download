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
import api.ServiceAction;

/**
 *
 * @author simplesolution.co
 */
public class CheckProxyTinsoft extends Task<Boolean> {

    private String key;

    public CheckProxyTinsoft(String key) {
        this.key = key;
    }

    public static int getHourKeyExpired(String key) {
        int resultExpired = 0;
        try {
            String resultJson="";
            String result = ServiceAction.getResultFromAnyAPI("http://proxy.tinsoftsv.com/api/getKeyInfo.php?key=" + key, null);
            JSONParser parser = new JSONParser();
            JSONObject jSONObject = (JSONObject) parser.parse(result);
            if (jSONObject.get("description") != null) {
                resultJson = jSONObject.get("description").toString();
            } else {
                resultJson = jSONObject.get("date_expired").toString();
            }
            long current=System.currentTimeMillis();
            if(resultJson.length()==0){
                return resultExpired;
            }
            long getlongTime=StringUtil.getCurrentLongTimeFromDate("dd-MM-yyyy hh:mm:ss", resultJson);
            long subTime=(getlongTime-current)/1000/60/60;
            resultExpired = (int) subTime;
            
            
            // 
        } catch (Exception e) {
    
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
