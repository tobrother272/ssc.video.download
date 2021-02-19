/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.proxy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javafx.concurrent.Task;
import org.openqa.selenium.WebDriver;
import Utils.CommandLineUtils;
import Setting.ToolSetting;
import ssc.automation.adb.MemuCUtils;
import ssc.automation.selennium.constants;
import ssc.automation.selennium.SeleniumJSUtils;
import Utils.CMDUtils;
import Utils.MyFileUtils;
import Utils.SocketUltil;
import static ssc.automation.selennium.SeleniumJSUtils.Sleep;

/**
 * @author magictool
 */
public class ResetIpTask extends Task<Boolean> {

    public static String URL = "URL=";
    public static String WAIT_URL = "WAIT_URL=";
    public static String WAIT_ELEMENT = "WAIT_ELEMENT=";
    public static String CLICK = "CLICK=";
    public static String TYPE = "TYPE=";
    public static String CUSTOM = "CUSTOM=";
    private WebDriver driver;
    private int resetMode;
    Preferences pre;

    public ResetIpTask() {
        pre = ToolSetting.getInstance().getPre();
    }
    ResetIpTask myTask;
    Boolean waitConnect = true;

    public Boolean reset() {
        try {
            String ip = SocketUltil.getIP();
            myTask = this;
            long startW = System.currentTimeMillis();
            if (ToolSetting.getInstance().getResetMode() == 1) {
                try {
                    Sleep(5000);
                    driver = constants.getFireFoxGECKOHeadLess();
                    List<String> activeString = new ArrayList<>();
                    activeString.addAll(MyFileUtils.getListStringFromFile(ToolSetting.getInstance().getResetModemScript()));
                    for (String string : activeString) {
                        try {
                            if (string.contains(URL)) {
                                String inString = string.replaceAll(URL, "");
                                if (Integer.parseInt(inString.split("\\|")[1]) == 0) {
                                    SeleniumJSUtils.loadPage(driver, inString.split("\\|")[0]);
                                    Sleep(5000);
                                } else {
                                    if (!SeleniumJSUtils.loadPage(driver, inString.split("\\|")[0], Integer.parseInt(inString.split("\\|")[1]))) {
                                        System.out.println("time out load page " + inString.split("\\|")[0]);
                                        //RunLog.insertRunLogWithImage("", "Load " + inString.split("\\|")[0] + " quá " + inString.split("\\|")[1] + " s , vui lòng kiểm tra lại đường truyền", string, arrLog, driver);
                                        return false;
                                    }
                                }
                            } else if (string.contains(WAIT_URL)) {
                            } else if (string.contains(WAIT_ELEMENT)) {
                            } else if (string.contains(CLICK)) {
                                String inString = string.replaceAll(CLICK, "");
                                if (!SeleniumJSUtils.clickElement(inString, driver, 0, true)) {
                                    //RunLog.insertRunLogWithImage("", "Không thể click " + inString, string, arrLog, driver);
                                    return false;
                                }
                            } else if (string.contains(TYPE)) {
                                String inString = string.replaceAll(TYPE, "");
                                if (!SeleniumJSUtils.sendValueToText(inString.split("\\|")[0], driver, inString.split("\\|")[1])) {
                                    //RunLog.insertRunLogWithImage("", "Không thể nhập " + inString.split("\\|")[1] + " vào ô " + inString.split("\\|")[0], string, arrLog, driver);
                                    return false;
                                }
                            } else if (string.contains(CUSTOM)) {
                                String runCustomJS = string.replaceAll(CUSTOM, "");
                                if (!SeleniumJSUtils.runCustomJS(runCustomJS, driver)) {
                                    //RunLog.insertRunLogWithImage("", "Không thể thực hiện lệnh ", runCustomJS, arrLog, driver);
                                    return false;
                                }
                            } else {

                            }
                            SeleniumJSUtils.Sleep(2000);
                        } catch (Exception e) {
                            //e.printStackTrace();
                        }
                    }
                    try {
                        driver.switchTo().alert().accept();
                    } catch (Exception e) {
                    }
                    return waitConnect(startW);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    try {
                        driver.quit();
                        CMDUtils.checkAndKillProcess("MicrosoftEdgeCP.exe");
                    } catch (Exception e) {
                    }
                }
            } else if (ToolSetting.getInstance().getResetMode() == 2) {
                CurrentIP.getInstance().setCurrentIP(SocketUltil.getIP());
                MemuCUtils.reset4G(null, false);
                return waitConnect(startW);
            } else if (resetMode == 4) {
                CommandLineUtils.changeIpHMA();
                startW = System.currentTimeMillis();
                long currentTime = (System.currentTimeMillis() - startW) / 1000;
                while (currentTime < ToolSetting.getInstance().getTimeReset()) {
                    Sleep(10000);
                    String newIp = SocketUltil.getIP();
                    if (newIp.length() != 0 && !newIp.contains(CurrentIP.getInstance().getStartIP())) {
                        CurrentIP.getInstance().setCurrentIP(newIp);
                        return true;
                    }
                }
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected Boolean call() {
        return reset();
    }

    public void start() {
        Thread t = new Thread();
        t.setDaemon(true);
        t.start();
    }

    public boolean waitConnect(long startW) {
        try {

            CurrentIP.getInstance().setIconIp(false);
            SeleniumJSUtils.Sleep(5000);
            int timeSelenium = (int) (System.currentTimeMillis() - startW) / 1000;
            startW = System.currentTimeMillis();
            while ((System.currentTimeMillis() - startW) / 1000 < (ToolSetting.getInstance().getTimeReset() - timeSelenium)) {
                if (isInternetReachable()) {
                    System.out.println("đã connect");
                    break;
                }
                Sleep(2000);
            }
            SeleniumJSUtils.Sleep(2000);
            String currentIp = SocketUltil.getIP();
            System.out.println("ip cũ " + CurrentIP.getInstance().getCurrentIP());
            System.out.println("ip mới " + currentIp);
            if (CurrentIP.getInstance().getCurrentIP().equals(currentIp)) {
                return false;
            } else {
                CurrentIP.getInstance().setCurrentIP(currentIp);
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean isInternetReachable() {
        try {
            //make a URL to a known source
            URL url = new URL("http://www.google.com");

            //open a connection to that source
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            urlConnect.setConnectTimeout(2000);
            //trying to retrieve data from the source. If there
            //is no connection, this line will fail
            Object objData = urlConnect.getContent();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            return false;
        }
        return true;
    }

}
