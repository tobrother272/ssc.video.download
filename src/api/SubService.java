/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.prefs.Preferences;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import Setting.ToolSetting;
import Utils.MyFileUtils;
import Utils.StringUtil;

/*
 * @author magictool
 */
public class SubService {
    public static String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest);
            // Convert message digest into hex value 
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static Boolean checkUserSuccess(String user_name, String user_password) {
        try {
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("username", user_name);
            params.put("passwd", getMd5(user_password));
            String result = ServiceAction.getResultFromService("SSCUserOauth/" + ToolSetting.getInstance().getToolCode(), params);
            JSONObject jo = (JSONObject) new JSONParser().parse(result);
            if (jo != null) {
                if (jo.get("status").toString().contains("success")) {
                    ToolSetting.getInstance().setUserToken(jo.get("token").toString());
                    ToolSetting.getInstance().setUserId(jo.get("user_id").toString());
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
