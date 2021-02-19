/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.automation.adb;

import javafx.concurrent.Task;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;

/**
 *
 * @author PC
 */
public class ReconnectAndCheckRealDeviceTask extends Task<JadbDevice> {
    
    @Override
    protected JadbDevice call() {
        try {
            updateMessage("Khởi động adb server");
            AdbUtils.reConnectAdb();
            AdbUtils.reconnectDevice("21503");
            for (int i = 0; i < 5; i++) {
                 updateMessage("Chờ connect "+(i+1)+"/5");
            }
            updateMessage("Đang kết nối thiết bị");
            JadbDevice device = new JadbConnection("localhost", 5037).getDevices().get(0);
            if (device != null) {
                return device;
            }
            
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }
    
    public void start() {
        Thread th = new Thread(this);
        th.setDaemon(true);
        th.start();
    }
}

