/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.proxy;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import Setting.ToolSetting;
import static Utils.CMDUtils.getLocalIp;
import static Utils.CMDUtils.getLocalIp2;
import Utils.SocketUltil;

/**
 * @author simpl
 */
public class CurrentIP {

    public static CurrentIP current_ip_instance = null;
    public SimpleStringProperty currentIP;
    public SimpleStringProperty iconIp;
    public List<String> arrWaiting;
    public List<String> arrRunning;
    public SimpleBooleanProperty resetting = new SimpleBooleanProperty(false);
    private String localComputerIp;

    public String getLocalComputerIp() {
        return localComputerIp;
    }

    public void refreshLocalComputerIp() {
        this.localComputerIp = System.getProperty("os.name").contains("Linux")?getLocalIp2():getLocalIp();
    }
    
    public SimpleBooleanProperty resettingProperty() {
        return resetting;
    }

    public boolean getResetting() {
        return resetting.get();
    }

    public void setResetting(boolean _resetting) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                resetting.set(_resetting);
            }
        });
    }
    private String startIP = "";

    public String getStartIP() {
        return startIP;
    }

    public void setStartIP(String startIP) {
        this.startIP = startIP;
    }

    public SimpleStringProperty iconIpProperty() {
        return iconIp;
    }

    public String getIconIp() {
        return iconIp.get();
    }

    public Boolean faking = false;

    public Boolean getFaking() {
        return faking;
    }

    public void setFaking(Boolean faking) {
        this.faking = faking;
    }

    // icon disconnect 
    // icon mobile  
    // icon net 
    // non reset 
    public void setIconIp(boolean connect) {
        if (connect) {
            switch (ToolSetting.getInstance().getResetMode()) {
                case 0:
                    this.iconIp.set("");
                    return;
                case 1:
                    this.iconIp.set("");
                    return;
                case 2:
                    this.iconIp.set("");
                    return;
                case 4:
                default:
                    this.iconIp.set("");
                    return;
            }
        } else {
            this.iconIp.set("");
            return;
        }

        //
    }

    public SimpleStringProperty currentIPProperty() {
        return currentIP;
    }

    public String getCurrentIP() {
        if (ToolSetting.getInstance().getResetMode() == 0) {
            this.iconIp.set("");
        } else {
            this.iconIp.set("");
        }
        return currentIP.get();
    }

    public void setCurrentIP(String _currentIP) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                currentIP.set(_currentIP);
            }
        });
    }

    private CurrentIP(String ip) {
        currentIP = new SimpleStringProperty(ip);
        startIP = ip;
        arrWaiting = new ArrayList<>();
        arrRunning = new ArrayList<>();
        refreshLocalComputerIp();
        switch (ToolSetting.getInstance().getResetMode()) {
            case 0:
                iconIp = new SimpleStringProperty("");
                break;
            case 1:
                iconIp = new SimpleStringProperty("");
                break;
            case 2:
                iconIp = new SimpleStringProperty("");
                break;
            case 4:
            default:
                iconIp = new SimpleStringProperty("");
                break;
        }
    }

    public static CurrentIP getInstance() {
        if (current_ip_instance == null) {
            current_ip_instance = new CurrentIP(SocketUltil.getIP());
        }
        return current_ip_instance;
    }

    int maxAccountPerIp = 1;

    public String getIp(String account_username) {
        if (getResetting()) {
            System.out.println("Đang reset");
            return "";
        }
        if (arrWaiting.size() >= maxAccountPerIp) {
            System.out.println("Chờ lớn hơn");
            return "";
        }
        if (arrWaiting.size() == 0) {
            System.out.println("Đang reset");
            setResetting(true);
            while (!new ResetIpTask().reset()) {

            }
            setResetting(false);
            System.out.println("Đã reset xong");
            arrWaiting.add(account_username);
            return currentIP.get();
        }
        if (arrWaiting.size() < maxAccountPerIp) {
            arrWaiting.add(account_username);
            return currentIP.get();
        }
        return "";
    }

    public void removeAccount(String account_username) {
        arrRunning.add(account_username);
        if (arrRunning.size() >= maxAccountPerIp) {
            arrRunning.clear();
            arrWaiting.clear();
        }
    }

}
