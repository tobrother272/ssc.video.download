/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.task;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import log.RunLog;
import static ssc.automation.selennium.SeleniumJSUtils.Sleep;
import ssc.network.ModemNW;
import ssc.proxy.CurrentIP;
import ssc.proxy.ProxyInfo;

/**
 * @author simpl
 */
public abstract class GoogleInteractive extends TaskModel {

    private ObservableList<RunLog> arrLog;
    private ModemNW modemNW;
    private String prefix = "";
    private Boolean actionSuccess = false;
    private boolean childTaskRunning;
    private String username;
    private int stt;
    private ProxyInfo proxyInfo = null;
    private SimpleStringProperty ip;

    public GoogleInteractive(int stt) {
        this.arrLog = FXCollections.observableArrayList();
        this.stt = stt;
        this.ip = new SimpleStringProperty("n/a");
    }

    public abstract void mainFunction();

    public abstract void afterFail();

    public abstract void afterSuccess();

    public abstract String initFunction();

    public abstract String objectHistory();

    public abstract void checkAndStopTask();

    public abstract void updateAccountInfo();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ModemNW getModemNW() {
        return modemNW;
    }

    public void setModemNW(ModemNW modemNW) {
        this.modemNW = modemNW;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Boolean getActionSuccess() {
        return actionSuccess;
    }

    public void setActionSuccess(Boolean actionSuccess) {
        this.actionSuccess = actionSuccess;
    }

    public void insertErrorLog(String message) {
        RunLog.insertRunLog("", message, "", arrLog);
    }

    public void insertSuccessLog(String message) {
        RunLog.insertRunLogSuccess("", message, "", arrLog);
    }

    public void insertErrorLog(Exception message) {
        RunLog.insertRunLogSuccess("", message.getMessage(), "", arrLog);
    }

    public ProxyInfo getProxyInfo() {
        return proxyInfo;
    }

    public void setProxyInfo(ProxyInfo proxyInfo) {
        this.proxyInfo = proxyInfo;
    }

    public SimpleStringProperty ipProperty() {
        return ip;
    }

    public SimpleStringProperty getIp() {
        return ip;
    }

    public void setIp(String _ip) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ip.set(_ip);
            }
        });
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public ObservableList<RunLog> getArrLog() {
        return arrLog;
    }

    public void setArrLog(ObservableList<RunLog> arrLog) {
        this.arrLog = arrLog;
    }

    private GoogleInteractiveChildTask currentRunningChildTask;

    public GoogleInteractiveChildTask getCurrentRunningChildTask() {
        return currentRunningChildTask;
    }

    public void setCurrentRunningChildTask(GoogleInteractiveChildTask currentRunningChildTask) {
        this.currentRunningChildTask = currentRunningChildTask;
    }

    public boolean isChildTaskRunning() {
        return childTaskRunning;
    }

    public void setChildTaskRunning(boolean childTaskRunning) {
        this.childTaskRunning = childTaskRunning;
    }
    private Thread childThread;
    public void changeChildTask() {
        currentRunningChildTask.bindChildToParent();
        if (childThread != null && childThread.isAlive()) {
            childThread.interrupt();
            childThread.stop();
        }
        childThread = new Thread(currentRunningChildTask);
        childThread.setDaemon(true);
        childThread.start();
    }

    public void waitChildTask(int waitTime, GoogleInteractiveChildTask task) {
        currentRunningChildTask = task;
        childTaskRunning = true;
        changeChildTask();
        int currentTime = 0;
        while (childTaskRunning) {
            if (currentTime >= waitTime) {
                break;
            }
            updateMyTitle((currentTime / 60) + "/" + (waitTime / 60) + " " + currentRunningChildTask.actionName());
            checkAndStopTask();
            currentTime++;
            Sleep(1 * 1000);
        }
        childTaskRunning = false;
        currentRunningChildTask.cancel();
    }

    public void wait(String message, int time) {
        updateMyTitle(" : " + message + " " + time + " giây");
        Sleep(1000 * time);
    }

    @Override
    public boolean main() {
        try {
            if (modemNW != null) {
                while (modemNW.isReseting()) {
                    updateMessage("Cho reset modem");
                    Sleep(5000);
                }
                if (!modemNW.isResetingSuccess()) {
                    updateMessage("reset modem loi");
                    return false;
                }
                setIp(modemNW.getCurrentIp());
            } else {
                setIp(CurrentIP.getInstance().getCurrentIP());
            }
            updateMyTitle("Khởi tạo");
            Sleep(2000);
            if (getProxyInfo() != null) {
                setIp(getProxyInfo().getIp());
            }
            String init = initFunction();
            if (init.length() != 0) {
                afterFail();
                updateMyTitle("Khởi tạo lỗi " + init);
                return false;
            }
            mainFunction();
            afterSuccess();
            return true;
        } catch (Exception e) {
            RunLog.insertRunLog("", this.username + " Khởi tạo tài khoản lỗi ", e.getMessage(), arrLog);
            e.printStackTrace();
        } finally {
            updateAccountInfo();
        }
        return true;
    }

}
