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

/**
 *
 * @author simpl
 */
public class CancelRequestTask extends Task<String> {

    private String apiKey;
    private String requestID;

    public CancelRequestTask(String apiKey, String requestID) {
        this.apiKey = apiKey;
        this.requestID = requestID;
    }

    @Override
    protected String call() throws Exception {
        return cancel(apiKey, requestID);
    }



    public static String cancel(String apiKey,String requestID) {
        HttpURLConnection conn = null;
        try {
            String query = "";
            query = "http://api.pvaonline.net/request/cancel?key=" + apiKey + "&id=" + requestID;
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

   

}
