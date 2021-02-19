/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import Setting.ToolSetting;
/**
 * @author simplesolution.co
 */
public class ServiceAction {
    private String url;
    private String param;
    private String result;
    public ServiceAction(String url, String param, String result) {
        this.url = url;
        this.param = param;
        this.result = result;
    }
    public static String getResultFromService(String urlString, Map<String, Object> params) {
        HttpURLConnection conn = null;
        try {
            String query = ToolSetting.getInstance().getServiceUrl() + "/" + urlString;
            //System.out.println("query "+query);
            ///System.out.println("query "+ToolSetting.getInstance().getUserToken());
            URL url = new URL(query);
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            conn = (HttpURLConnection) url.openConnection();
            if (params.size() != 0) {
                conn.setRequestMethod("POST");
                conn.setRequestProperty("charset", "utf-8");
                conn.setRequestProperty("Authorization","a61a620d67cc359aa6dcf711ef8c6d45");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                os.write(postDataBytes);
                os.close();
            } else {
                conn.setRequestProperty("charset", "utf-8");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Authorization", "a61a620d67cc359aa6dcf711ef8c6d45");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setRequestMethod("GET");
            }
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
    public static String getResultFromService2(String urlString, Map<String, Object> params) {
        HttpURLConnection conn = null;
        try {
            String query = ToolSetting.getInstance().getServiceUrl() + "/" + urlString;
            //System.out.println("query "+query);
            ///System.out.println("query "+ToolSetting.getInstance().getUserToken());
            URL url = new URL(query);
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            conn = (HttpURLConnection) url.openConnection();
            if (params.size() != 0) {
                conn.setRequestMethod("POST");
                conn.setRequestProperty("charset", "utf-8");
                conn.setRequestProperty("Authorization", "a87063dfc6d07cdcccf0780a00eb86d6");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                os.write(postDataBytes);
                os.close();
            } else {
                conn.setRequestProperty("charset", "utf-8");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Authorization", "a87063dfc6d07cdcccf0780a00eb86d6");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setRequestMethod("GET");
            }
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
    public static String getResultFromAnyAPI(String urlString, Map<String, Object> params) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlString);
            StringBuilder postData = new StringBuilder();
            if (params != null) {
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (postData.length() != 0) {
                        postData.append('&');
                    }
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
            }

            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("User-Agent", "Mozilla");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(postDataBytes);
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//            writer.write(postData.toString());
//            writer.flush();
//            writer.close();
            os.close();
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

    public static String getResultFromAnyAPIJson(String urlString, Map<String, Object> params) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlString);
            StringBuilder postData = new StringBuilder();
            if (params != null) {
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (postData.length() != 0) {
                        postData.append('&');
                    }
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("User-Agent", "Mozilla");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
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
