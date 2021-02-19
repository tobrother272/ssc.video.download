/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * @author tobrother272
 */
public class SeleniumUtils {

    public static boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static String getIpAddress() {
        String ipAddress = null;
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(
                        whatismyip.openStream()));
                ipAddress = in.readLine();
            } catch (IOException ex) {
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (MalformedURLException ex) {
            System.err.println("Can't get your IP");
        }
        return ipAddress;
    }

    public static void saveErrorImage(WebDriver webdriver, String fileWithPath) {
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
            
            //Call getScreenshotAs method to create image file
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            
            //Move image file to new destination
            File DestFile = new File(fileWithPath);
            
            //Copy file at destination
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (IOException ex) {
            Logger.getLogger(SeleniumUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void waitForPageLoad(WebDriver d, int timeout) {
        String s = "";
        int countTime = 0;
        while (!s.equals("complete") && countTime < timeout) {
            s = (String) ((JavascriptExecutor) d).executeScript("return document.readyState");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            countTime++;
        }
    }

    public static Task downloadImage(final String address, final String localFileName) {
        return new Task() {
            @Override
            protected Object call() throws MalformedURLException, IOException {
                String decodedValue = "";
                try {
                    //http://thumbs.dreamstime.com/z/woman-wearing-scarf-portrait-3-1812521.jpg
                    decodedValue = URLDecoder.decode(address, "UTF-8");
                } catch (UnsupportedEncodingException uee) {
                }
                URL url = new URL(decodedValue);
                InputStream is = url.openStream();
                OutputStream os = new FileOutputStream(localFileName);
                byte[] b = new byte[1024];
                int length;
                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }
                is.close();
                os.close();
                return true;
            }
        };
    }

    public static long folderSize(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                length += file.length();
            } else {
                length += folderSize(file);
            }
        }
        return length;
    }

    public static Document parseURLToString(String address) {
        try {
            return Jsoup.connect(address)
                    .userAgent("Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36")
                    .get();
        } catch (IOException ex) {
            Logger.getLogger(SeleniumUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static String shorten(String longUrl) {
        if (longUrl == null) {
            return longUrl;
        }

        StringBuilder sb = null;
        String line = null;
        String urlStr = longUrl;

        try {
            URL url = new URL("http://goo.gl/api/url");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "toolbar");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("url=" + URLEncoder.encode(urlStr, "UTF-8"));
            writer.close();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line + '\n');
            }

            String json = sb.toString();
            //It extracts easily...
            return json.substring(json.indexOf("http"), json.indexOf("\"", json.indexOf("http")));
        } catch (MalformedURLException e) {
            return longUrl;
        } catch (IOException e) {
            return longUrl;
        }
    }

}
