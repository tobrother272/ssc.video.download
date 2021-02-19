/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.proxy;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author simpl
 */
public class ProxyRackSetting {

    public static ProxyRackSetting currentSetting = null;
    private int fromPort;
    private int toPort;
    private List<String> arrPort;

    public List<String> getArrPort() {
        return arrPort;
    }

    public void setArrPort(List<String> arrPort) {
        this.arrPort = arrPort;
    }

    public void initPort(int fromPort, int toPort) {
        this.fromPort = fromPort;
        this.toPort = fromPort;
        arrPort = new ArrayList<>();
        for (int i = fromPort; i < toPort; i++) {
            arrPort.add("" + i);
        }
    }

    public int getFreePort() {
        if (arrPort.size() == 0) {
            for (int i = fromPort; i < toPort; i++) {
                arrPort.add("" + i);
            }
        }
        String portString = arrPort.get(0);
        arrPort.remove(portString);
        return Integer.parseInt(portString);
    }

    private ProxyRackSetting() {

    }

    public static ProxyRackSetting getInstance() {
        if (currentSetting == null) {
            currentSetting = new ProxyRackSetting();
        }
        return currentSetting;
    }
}
