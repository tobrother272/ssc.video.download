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
import java.net.URLEncoder;
import javafx.concurrent.Task;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author simpl
 */
public class CheckBalanceTask extends Task<String> {

    private String apiKey;

    public CheckBalanceTask(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    protected String call() throws Exception {
        return check(apiKey);
    }

    public static String check(String apiKey) {
        String balance = "0";
        HttpURLConnection conn = null;
        try {
            String query = "";
            String method="POST";
            if (apiKey.length() == 25) {
                query = "http://api.pvaonline.net/balance?key=" + apiKey;
            } else {
                query = "https://api.rentcode.net/api/ig/balance?apiKey=" + URLEncoder.encode(apiKey, "UTF-8");
                method="GET";
            }
            URL url = new URL(query);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String result = "";
            for (int c; (c = in.read()) >= 0;) {
                result = result + ((char) c);
            }

            System.out.println("result " + result);

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(result);
            if (json.get("success") != null && json.get("success").toString().contains("true")) {
                if (apiKey.length() == 25) {
                    balance = json.get("balance").toString();
                } else {
                    balance =((JSONObject)json.get("results")).get("balance").toString();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return balance;
    }

}
