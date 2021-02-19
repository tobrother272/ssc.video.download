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
 * @author simpl
 */
public class CheckRealDeviceTask extends Task<JadbDevice> {
    
    @Override
    protected JadbDevice call() {
        updateMessage("Đang kết nối thiết bị");
        try {
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
