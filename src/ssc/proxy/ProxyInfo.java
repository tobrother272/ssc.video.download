/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.proxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import static ssc.proxy.constants.PROXY_TYPE;
/**
 * @author simpl
 */
public class ProxyInfo extends Task<Boolean> {

    public static String configProxyDroidString(String ip, int port, String type, String username, String passwd) {
        String authString = "<boolean name=\"isAuth\" value=\"false\" />\n";
        if (username.length() != 0 && passwd.length() != 0) {
            authString
                    = "      <string name=\"user\">" + username + "</string>\n"
                    + "    <string name=\"password\">" + passwd + "</string>\n"
                    + "    <boolean name=\"isAuth\" value=\"true\" />\n";
        }
        return "<?xml version='1.0' encoding='utf-8' standalone='yes' ?>\n"
                + "<map>\n"
                + authString
                + "    <string name=\"host\">" + ip + "</string>\n"
                + "    <string name=\"App Restrictions\">AAAAAA==\n"
                + "    </string>\n"
                + "    <string name=\"ssid\"></string>\n"
                + "    <string name=\"profileValues\">1|0</string>\n"
                + "    <boolean name=\"2.7.6\" value=\"true\" />\n"
                + "    <string name=\"profile\">1</string>\n"
                + "    <boolean name=\"isConnecting\" value=\"false\" />\n"
                + "    <boolean name=\"isPAC\" value=\"false\" />\n"
                + "    <boolean name=\"settings_key_notif_vibrate\" value=\"true\" />\n"
                + "    <boolean name=\"isBypassApps\" value=\"false\" />\n"
                + "    <string name=\"port\">" + port + "</string>\n"
                + "    <string name=\"excludedSsid\"></string>\n"
                + "    <boolean name=\"isAutoSetProxy\" value=\"true\" />\n"
                + "    <string name=\"proxyType\">" + type + "</string>\n"
                + "    <boolean name=\"isRunning\" value=\"true\" />\n"
                + "    <string name=\"profileEntries\">Profile 1|New Profile</string>\n"
                + "</map>";
    }

    /*
      String configString2 = " base {\n"
                        + "log_debug = off;\n"
                        + "log_info = off;\n"
                        + "log = stderr;\n"
                        + "daemon = on;\n"
                        + "redirector = iptables;\n"
                        + "                        }\n"
                        + "\n"
                        + "\n"
                        + "                        redsocks {\n"
                        + "local_ip = 127.0.0.1;\n"
                        + "local_port = 8123;\n"
                        + "ip = " + getParentTask().getProxyInfo().getIp() + ";\n"
                        + "port = " + proxyPort + ";\n"
                        + "type = " + PROXY_TYPE.get(proxyType) + ";\n"
                        + authString2
                        + "}";
    */
    public static String configProxyManagerString(String ip, int port, String type, String username, String passwd) {
        
        
        if(ip.contains("usa.shared.proxyrack.net")){
            ip="69.55.54.140";
        }
        
        
        String authString = "<boolean name=\"isAuth\" value=\"false\" />\n";
        if (username.length() != 0 && passwd.length() != 0) {
            authString
                    = "    <string name=\"password\">" +  passwd+ "</string>\n"
                    + "    <boolean name=\"isAuth\" value=\"true\" />\n"
                    + "    <string name=\"user\">" +  username+ "</string>\n";
        }
        return "<map>\n"
                + authString
                + "    <string name=\"host\">" + ip + "</string>\n"
                + "    <string name=\"App Restrictions\">AAAAAA==\n"
                + "    </string>\n"
                + "    <string name=\"port\">" + port + "</string>\n"
                + "    <boolean name=\"1.2.5\" value=\"true\" />\n"
                + "    <boolean name=\"isConnecting\" value=\"false\" />\n"
                + "    <string name=\"proxyType\">" + type + "</string>\n"
                + "    <boolean name=\"isRunning\" value=\"true\" />\n"
                + "</map>";
    }

    private String ip;
    private int port;
    private int speed;
    private int stt;
    private int mode;
    private String username = "";
    private String password = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }
    private String country;
    private String time;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ProxyInfo(int stt, String ip, int port, String username, String password, int speed, String country, String time, int mode) {
        this.stt = stt;
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.speed = speed;
        this.country = country;
        this.time = time;
        this.mode = mode;
    }

    public ProxyInfo(String proxyString) {
        this.stt = 0;
        this.ip = proxyString.split(":")[0];
        this.port = Integer.parseInt(proxyString.split(":")[1]);
        this.username = proxyString.split(":")[2].equals("no")?"":proxyString.split(":")[2];
        this.password = proxyString.split(":")[3].equals("no")?"":proxyString.split(":")[3];
        this.speed = 10;
        this.country = "n/a";
        this.time = "n/a";
        this.mode = PROXY_TYPE.indexOf(proxyString.split(":")[4]);
    }

    public static void initTableWating(TableView tv, ObservableList<ProxyInfo> data) {

        TableColumn<ProxyInfo, String> ipCol = new TableColumn("IP");
        ipCol.setResizable(false);
        ipCol.setPrefWidth(100);
        ipCol.setCellValueFactory(new PropertyValueFactory<>("ip"));

        TableColumn<ProxyInfo, String> messageCol = new TableColumn("Tiến trình");
        messageCol.setResizable(false);
        messageCol.setPrefWidth(150);
        messageCol.setCellValueFactory(new PropertyValueFactory<>("message"));

        tv.getColumns().addAll(ipCol, messageCol);
        tv.setItems(data);
    }

    public static void initTable(TableView tv, ObservableList<ProxyInfo> data) {
        TableColumn<ProxyInfo, String> sttCol = new TableColumn("Stt");
        sttCol.setResizable(false);
        sttCol.setPrefWidth(50);
        sttCol.setCellValueFactory(new PropertyValueFactory<>("stt"));

        TableColumn<ProxyInfo, String> ipCol = new TableColumn("IP");
        ipCol.setResizable(false);
        ipCol.setPrefWidth(100);
        ipCol.setCellValueFactory(new PropertyValueFactory<>("ip"));

        TableColumn<ProxyInfo, String> portCol = new TableColumn("Port");
        portCol.setResizable(false);
        portCol.setPrefWidth(50);
        portCol.setCellValueFactory(new PropertyValueFactory<>("port"));

        TableColumn<ProxyInfo, String> speedCol = new TableColumn("Tốc độ");
        speedCol.setResizable(false);
        speedCol.setPrefWidth(100);
        speedCol.setCellValueFactory(new PropertyValueFactory<>("speed"));

        TableColumn<ProxyInfo, String> timeCol = new TableColumn("Thời gian");
        timeCol.setResizable(false);
        timeCol.setPrefWidth(200);
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<ProxyInfo, String> languageCol = new TableColumn("Quốc gia");
        languageCol.setResizable(false);
        languageCol.setPrefWidth(200);
        languageCol.setCellValueFactory(new PropertyValueFactory<>("country"));

        tv.getColumns().addAll(sttCol, ipCol, portCol, speedCol, timeCol, languageCol);
        tv.setItems(data);
    }

    private int min_speed = 30;

    public int getMin_speed() {
        return min_speed;
    }

    public void setMin_speed(int min_speed) {
        this.min_speed = min_speed;
    }

    public void start() {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }

    @Override
    protected Boolean call() {
        if (!checkIPFake()) {
            arrChecking.remove(this);
            return false;
        }
        if (!checkYoutubeSpeed()) {
            arrChecking.remove(this);
            return false;
        }
        arrChecking.remove(this);
        for (ProxyInfo proxy : arrProxies) {
            if (proxy.getSpeed() >= speed) {
                this.setStt(arrProxies.indexOf(proxy));
                //arrProxies.get(arrProxies.size()-1).setStt(arrProxies.size()+1);
                arrProxies.add(arrProxies.indexOf(proxy), this);
                return true;
            }
        }
        this.setStt(arrProxies.size() + 1);
        arrProxies.add(this);
        return true;
    }
    private ObservableList<ProxyInfo> arrProxies;
    private ObservableList<ProxyInfo> arrChecking;

    public ObservableList<ProxyInfo> getArrChecking() {
        return arrChecking;
    }

    public void setArrChecking(ObservableList<ProxyInfo> arrChecking) {
        this.arrChecking = arrChecking;
    }

    public ObservableList<ProxyInfo> getArrProxies() {
        return arrProxies;
    }

    public void setArrProxies(ObservableList<ProxyInfo> arrProxies) {
        this.arrProxies = arrProxies;
    }

    public boolean checkIPFake() {

        //WebDriver driver = null;
        HttpURLConnection uc = null;
        BufferedReader in = null;
        try {
            updateMessage("Check Faking");

            //System.out.println("bbbbbbbbbbbbbbbbb");
            //driver = constants.getPhanTomJs(30, this);
            //driver=constants.getFireFoxGECKO("", "",this);
            /*
            driver.get("http://checkip.amazonaws.com/");
            System.out.println("asdasdasdasasdasd");
            SeleniumJSUtils.waitForPageLoad(driver, 30);
            System.out.println(""+driver.getPageSource());
            if (driver.getPageSource().contains(ip)) {
                updateMessage("Check youtube");
                driver.get("https://www.youtube.com/");
                SeleniumJSUtils.waitForPageLoad(driver, 30);
                System.out.println(""+driver.getTitle());
                if(driver.getTitle().contains("YouTube")){
                     return true;
                }else{
                    return false;
                }
            }
             */
            URL url = new URL("https://kiemtraip.com/");
            Proxy proxy = null;
            if (mode == 0) {
                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port)); // or whatever your proxy is
            } else {
                proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(ip, port)); // or whatever your proxy is

            }
            long startTime = System.currentTimeMillis();
            uc = (HttpURLConnection) url.openConnection(proxy);
            uc.setConnectTimeout(min_speed * 1000);
            uc.setReadTimeout(min_speed * 1000);
            new Thread(new InterruptThread(uc)).start();
            uc.connect();

            String line = null;
            StringBuffer tmp = new StringBuffer();
            in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while ((line = in.readLine()) != null) {
                tmp.append(line);
            }

            Document doc = Jsoup.parse(String.valueOf(tmp));
            if (doc.text().contains(ip)) {
                int time = (int) ((System.currentTimeMillis() - startTime) / 1000);
                speed = time;
                //System.out.println("text " + doc.text() + " - " + time);

                return true;
            }

        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            //driver.quit();
            try {
                uc.disconnect();
                in.close();
            } catch (Exception e) {
            }

        }
        return false;
    }
    public boolean checkYoutubeSpeed() {
        //WebDriver driver = null;
        HttpURLConnection uc = null;
        BufferedReader in = null;
        try {
            updateMessage("Check speed");
            URL url = new URL("https://www.youtube.com/watch?v=4X-WYITeEeA");
            Proxy proxy = null;
            if (mode == 0) {
                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port)); // or whatever your proxy is
            } else {
                proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(ip, port)); // or whatever your proxy is
            }
            long startTime = System.currentTimeMillis();
            uc = (HttpURLConnection) url.openConnection(proxy);
            uc.setConnectTimeout(min_speed * 1000);
            uc.setReadTimeout(min_speed * 1000);
            new Thread(new InterruptThread(uc)).start();
            uc.connect();

            String line = null;
            StringBuffer tmp = new StringBuffer();
            in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while ((line = in.readLine()) != null) {
                tmp.append(line);
            }
            Document doc = Jsoup.parse(String.valueOf(tmp));
            if (doc.title().contains("YouTube")) {
                int time = (int) ((System.currentTimeMillis() - startTime) / 1000);
                speed = time;
                //System.out.println("title " + doc.title() + " - " + time);
                return true;
            }
            updateMessage("Hoàn thành");
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            //driver.quit();
            try {
                uc.disconnect();
                in.close();
            } catch (Exception e) {
            }

        }
        return false;
    }

    public class InterruptThread implements Runnable {

        HttpURLConnection con;

        public InterruptThread(HttpURLConnection con) {
            this.con = con;
        }

        public void run() {
            try {
                Thread.sleep(1000 * min_speed); // or Thread.sleep(con.getConnectTimeout())
            } catch (InterruptedException e) {

            }
            con.disconnect();
            //System.out.println("Timer thread forcing to quit connection");
        }
    }

}
