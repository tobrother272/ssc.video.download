/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;
import ssc.automation.selennium.SeleniumJSUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import org.openqa.selenium.WebDriver;
import static ssc.automation.selennium.constants.getPhanTomJs;
/**
 * @author inet
 */
public class SocketUltil {
    public static int getFreePort() {
        int port = 6900;
        while (true) {
            System.out.println("--------------Testing port " + port);
            if(availablePort(port)){
                return port;
            }
            port++;
        }
    }
    public static boolean availablePort(int port) {
        // Assume port is available.
        boolean result = true;

        try {
          (new Socket("127.0.0.1", port)).close();
          // Successful connection means the port is taken.
          result = false;
        }
        catch(Exception e) {
          // Could not connect.
        }

        return result;
      }
    public static String getIP() {
        String ip = "";
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(
                        whatismyip.openStream()));
                ip = in.readLine();
                return ip;
            } catch (IOException ex) {
                //ex.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                }
            }
        } catch (MalformedURLException ex) {
              //ex.printStackTrace();
        }
        return ip;
    }

    public static String getCurrentMonth() {
        String ip = "";
        try {
            URL whatismyip = new URL("http://ytb.simplesolution.co/admin/get_license_time.html");
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(
                        whatismyip.openStream()));
                ip = in.readLine();
                JsonObject jsonObject = new JsonParser().parse(ip).getAsJsonObject();
                return jsonObject.get("license_time").toString().replaceAll("\"", "");
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

        }
        return "";
    }

    public static boolean checkPortFree(int usedport) {
        System.out.println("--------------Testing port " + usedport);
        Socket s = null;
        try {
            s = new Socket("localhost", usedport);
            // If the code makes it this far without an exception it means
            // something is using the port and has responded.
            System.out.println("--------------Port " + usedport + " is not available");
            return false;
        } catch (IOException e) {
            System.out.println("--------------Port " + usedport + " is available");
            return true;
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    throw new RuntimeException("You should handle this error.", e);
                }
            }
        }
    }

    public static boolean checkInternetConnection() {
        try {
            URL url = new URL("http://checkip.amazonaws.com");
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5000); //set timeout to 5 seconds
            connection.setReadTimeout(5000);
            connection.connect();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkInternetConnectionUsingSocket() {
        Socket socket = new Socket();
        InetSocketAddress addr = new InetSocketAddress("www.google.com", 80);
        try {
            socket.connect(addr, 5000);
            System.out.println("checkInternetConnection okkkkkkkkkkk");
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
            }
        }
    }

    public static boolean checkInternetConnectionUsingWebDriver() {
        WebDriver webDriver = getPhanTomJs(5);
        try {
            if (!SeleniumJSUtils.loadPage(webDriver, "http://checkip.amazonaws.com", 5)) {
                return false;
            } else {
                if (webDriver.getCurrentUrl().contains("checkip.amazonaws.com")) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        } finally {
            try {
                webDriver.quit();
            } catch (Exception e) {
            }
        }
    }

}
