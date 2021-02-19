/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.automation.adb;

import javafx.concurrent.Task;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import static ssc.automation.adb.MemuCUtils.reConnectRealDevice;

/**
 *
 * @author simpl
 */
public class GetPlaneLocationTask extends Task<String> {

    private String text;

    public GetPlaneLocationTask(String text) {
        this.text = text;
    }

    @Override
    protected String call() {
        try {
            //if (reConnectRealDevice().length() != 0) {
            JadbDevice device = new JadbConnection("localhost", 5037).getDevices().get(0);

            if (device != null) {
                AdbUtils.dumpView(device);
                AdbUtils.pullFile(device);

                String bound = AdbUtils.getBoundOfElement(device, "android.widget.Switch#content-desc='" + text + "'").replaceAll("\\[", "");
                if (bound.length() == 0) {
                    bound = AdbUtils.getBoundOfElement(device, "android.widget.TextView#content-desc='" + text + "'").replaceAll("\\[", "");
                }
                if (bound.length() == 0) {
                    bound = AdbUtils.getBoundOfElement(device, "android.widget.Button#content-desc='" + text + "'").replaceAll("\\[", "");
                }
                if (bound.length() == 0) {
                    bound = AdbUtils.getBoundOfElement(device, "android.widget.ImageView#content-desc='" + text + "'").replaceAll("\\[", "");
                }

                String group1 = bound.split("\\]")[0].replaceAll("\\[", "");
                String group2 = bound.split("\\]")[1].replaceAll("]", "");
                int x = (Integer.parseInt(group1.split(",")[0]) + Integer.parseInt(group2.split(",")[0])) / 2;
                int y = (Integer.parseInt(group1.split(",")[1]) + Integer.parseInt(group2.split(",")[1])) / 2;
                String b = x + "," + y;
                return b;
            } else {
                return "Không thấy thiết bị";
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return "";
    }

    public void start() {
        Thread th = new Thread(this);
        th.setDaemon(true);
        th.start();
    }
}
