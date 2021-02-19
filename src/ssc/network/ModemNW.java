/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.network;

import Setting.ToolSetting;
import Utils.CMDUtils;
import Utils.MyFileUtils;
import Utils.SocketUltil;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.openqa.selenium.WebDriver;
import ssc.automation.selennium.SeleniumJSUtils;
import static ssc.automation.selennium.SeleniumJSUtils.Sleep;
import ssc.automation.selennium.constants;
import ssc.task.GoogleInteractive;
import ssc.proxy.CurrentIP;
import static ssc.proxy.ResetIpTask.isInternetReachable;

/**
 *
 * @author ssc
 */
public class ModemNW extends Task<Boolean> {

    private List<String> arryRun;
    private SimpleStringProperty currentAccount;
    private SimpleStringProperty currentIp;
    private SimpleIntegerProperty count;
    public SimpleIntegerProperty countProperty() {
        return count;
    }

    public int getCount() {
        return count.get();
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    public List<String> getArryRun() {
        return arryRun;
    }

    public void setArryRun(List<String> arryRun) {
        this.arryRun = arryRun;
    }

    public SimpleStringProperty currentAccountProperty() {
        return currentAccount;
    }

    public String getCurrentAccount() {
        return currentAccount.get();
    }

    public void setCurrentAccount(String currentAccount) {
        this.currentAccount.set(currentAccount);
    }

    public SimpleStringProperty currentIpProperty() {
        return currentIp;
    }

    public String getCurrentIp() {
        return currentIp.get();
    }

    public void setCurrentIp(String currentIp) {
        this.currentIp.set(currentIp);
    }

    public void start() {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }

    public ModemNW() {
        arryRun = new ArrayList<>();
        currentIp = new SimpleStringProperty("n/a");
        currentAccount = new SimpleStringProperty("n/a");
        count = new SimpleIntegerProperty(0);
    }
    public static String URL = "URL=";
    public static String WAIT_URL = "WAIT_URL=";
    public static String WAIT_ELEMENT = "WAIT_ELEMENT=";
    public static String CLICK = "CLICK=";
    public static String TYPE = "TYPE=";
    public static String CUSTOM = "CUSTOM=";
    public boolean reseting = true;
    public boolean resetingSuccess;
    public void addItem(String name){
        arryRun.add(name);
        count.set(arryRun.size());
    }
    public void removeItem(String name){
        
        count.set(arryRun.size());
    }
    public boolean isReseting() {
        return reseting;
    }

    public void setReseting(boolean reseting) {
        this.reseting = reseting;
    }

    public boolean isResetingSuccess() {
        return resetingSuccess;
    }

    public void setResetingSuccess(boolean resetingSuccess) {
        this.resetingSuccess = resetingSuccess;
    }

    public boolean waitConnect(long startW) {
        try {
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
                setCurrentIp(currentIp);
                return true;
            }
        } catch (Exception e) {
        }finally{
            CurrentIP.getInstance().refreshLocalComputerIp();
        }
        return false;
    }
    
    
    @Override
    protected Boolean call() {
        WebDriver driver=null;
        try {
            long startW = System.currentTimeMillis();
            updateMessage("reset modem");
            setCurrentIp(CurrentIP.getInstance().getCurrentIP());
            List<String> activeString = new ArrayList<>();
            activeString.addAll(MyFileUtils.getListStringFromFile("/home/ssc/Desktop/script.txt"));
            driver = constants.getFireFoxGECKOHeadLess();
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
            updateMessage("cho ket noi");
            resetingSuccess = waitConnect(startW);
            setCurrentIp(CurrentIP.getInstance().getCurrentIP());
            updateMessage("hoan thanh");
        } catch (Exception e) {

        } finally {
            driver.quit();
            reseting = false;
        }
        return true;
    }

    public static void initTable(TableView tv, ObservableList<ModemNW> data) {
        TableColumn<ModemNW, String> ipCol = new TableColumn("IP");
        TableColumn<ModemNW, String> accountCol = new TableColumn("Tài khoản");
        TableColumn<ModemNW, String> nextTimeCol = new TableColumn("Next");
        TableColumn<ModemNW, String> messageCol = new TableColumn("TT");
        ipCol.setCellValueFactory(new PropertyValueFactory<>("currentIp"));
        ipCol.setResizable(false);
        ipCol.setPrefWidth(150);

        accountCol.setCellValueFactory(new PropertyValueFactory<>("currentAccount"));
        accountCol.setResizable(false);
        accountCol.setPrefWidth(200);

        nextTimeCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        nextTimeCol.setResizable(false);
        nextTimeCol.setPrefWidth(50);

        messageCol.setCellValueFactory(new PropertyValueFactory<>("message"));
        messageCol.setResizable(false);
        messageCol.setPrefWidth(tv.getPrefWidth()-400);

        tv.getColumns().addAll(ipCol, accountCol, nextTimeCol,messageCol);
        tv.setItems(data);
    }
}
