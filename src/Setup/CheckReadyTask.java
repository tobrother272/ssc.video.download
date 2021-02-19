/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Setup;

import java.io.File;
import javafx.concurrent.Task;
import static ssc.reup.api.Controller.SetupController.SETUP_APPIUM;
import static ssc.reup.api.Controller.SetupController.SETUP_JDK;
import static ssc.reup.api.Controller.SetupController.SETUP_NODEJS;
import static ssc.reup.api.Controller.SetupController.SETUP_SDK;
import Utils.CMDUtils;
import static Utils.Constants.PROCESS_NAME.APPIUM_SETUP_PCNAME;
import static Utils.Constants.PROCESS_NAME.KOPLAYER_SETUP_PCNAME;
import Utils.Constants.SETUP;
import static Utils.Constants.SETUP.JDK_SETUP_PATH;
import static Utils.Constants.SETUP.KOPLAYER_SETUP_PATH;
import static Utils.Constants.SETUP.SDK_SETUP_PATH;
import static Utils.Constants.checkReady.APPIUM;
import static Utils.Constants.checkReady.KOPLAYER;
import static Utils.Constants.checkReady.NODEJS;
import static Utils.Constants.checkReady.NODEJSEXE;
import static Utils.Constants.checkReady.SDK;
import Utils.MyFileUtils;
import ssc.automation.selennium.SeleniumJSUtils;

/**
 * @author magictool
 */
public class CheckReadyTask extends Task<String> {

    private String inputString;

    public CheckReadyTask(String inputString) {
        this.inputString = inputString;
    }



    @Override
    protected String call() throws Exception {
        updateMessage("Đang đợi cài đặt " + inputString + " ...");
        if (inputString.equals(SETUP_APPIUM)) {
            Runtime.getRuntime().exec(SETUP.APPIUM_SETUP_PATH);
            int i = 0;
            while (!CMDUtils.checkProcessNotRunning(APPIUM_SETUP_PCNAME) || !(new File(APPIUM).exists())) {
                String expand = " ";
                if (i < 3) {
                    i++;
                } else {
                    i = 0;
                }
                for (int j = 0; j < i; j++) {
                    expand = expand + ".";
                }
                SeleniumJSUtils.Sleep(1000);
                updateMessage("Đang đợi cài đặt " + inputString + expand);
            }
        } else if (inputString.equals(SETUP_NODEJS)) {
            Runtime.getRuntime().exec("msiExec.exe -i " + SETUP.NODEJS_SETUP_PATH);
            int i = 0;
            //while (!CMDUtils.checkProcessNotRunning(WINDOW_SETUP_PCNAME) || !(new File(NODEJSEXE).exists())) {
            while (!(new File(NODEJSEXE).exists())) {
                String expand = " ";
                if (i < 3) {
                    i++;
                } else {
                    i = 0;
                }
                for (int j = 0; j < i; j++) {
                    expand = expand + ".";
                }
                SeleniumJSUtils.Sleep(1000);
                updateMessage("Đang đợi cài đặt " + inputString + expand);
            }
        } else if (inputString.equals(SETUP_JDK)) {
            Runtime.getRuntime().exec(JDK_SETUP_PATH);
            SeleniumJSUtils.Sleep(10000);
            int i = 0;
            while (CMDUtils.runningMSI()) {
                String expand = " ";
                if (i < 3) {
                    i++;
                } else {
                    i = 0;
                }
                for (int j = 0; j < i; j++) {
                    expand = expand + ".";
                }
                SeleniumJSUtils.Sleep(1000);
                updateMessage("Đang đợi cài đặt " + inputString + expand);
            }
        } else if (inputString.equals(SETUP_SDK)) {
            try {
                if(!new File(SDK).exists()){
                    MyFileUtils.createFolder(SDK);
                }
                CMDUtils.extractZip(SDK_SETUP_PATH + ".zip", SDK);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int i = 0;
            while (!(new File(SDK).exists())) {

                String expand = " ";
                if (i < 3) {
                    i++;
                } else {
                    i = 0;
                }
                for (int j = 0; j < i; j++) {
                    expand = expand + ".";
                }
                SeleniumJSUtils.Sleep(1000);
                updateMessage("Đang đợi cài đặt " + inputString + expand);
            }
        } else {
            //SETUP_KOPLAYER
            Runtime.getRuntime().exec(KOPLAYER_SETUP_PATH);
            int i = 0;
            while (!CMDUtils.checkProcessNotRunning(KOPLAYER_SETUP_PCNAME) || !(new File(KOPLAYER).exists())) {
                String expand = " ";
                if (i < 3) {
                    i++;
                } else {
                    i = 0;
                }
                for (int j = 0; j < i; j++) {
                    expand = expand + ".";
                }
                SeleniumJSUtils.Sleep(1000);
                updateMessage("Đang đợi cài đặt " + inputString + expand);
            }
        }
        String result = "";
        updateMessage("Đang kiểm tra appium ...");
        File fileAppium = new File(APPIUM);
        if (fileAppium.exists()) {
            result = "1 ";
        } else {
            result = "0 ";
        }
        updateMessage("Đang kiểm tra nodejs ...");
        File fileNodeJS = new File(NODEJS);
        if (fileNodeJS.exists()) {
            result = result + "1 ";
        } else {
            result = result + "0 ";
        }
        try {
            updateMessage("Đang kiểm tra jdk 1...");
            if (CMDUtils.checkSDK() && MyFileUtils.checkJDKFolder()) {
                result = result + "1 ";
            } else {
                result = result + "0 ";
            }
            updateMessage("Đang kiểm tra sdk ...");
            if (new File(SDK).exists()) {
                result = result + "1 ";
            } else {
                result = result + "0 ";
            }
            updateMessage("Đang kiểm tra koplayer ...");
            File fileBlueStack = new File(KOPLAYER);
            if (fileBlueStack.exists()) {
                result = result + "1";
            } else {
                result = result + "0";
            }

            System.out.println("result " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }
}
