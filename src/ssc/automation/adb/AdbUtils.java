/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.automation.adb;

import static Utils.Constants.ANDROID_FOLDER.DESCRIPTION_FILE_SERVICE;
import static Utils.Constants.DATA.DUMPVIEW_ERROR_PATH;
import static Utils.Constants.DATA.DUMPVIEW_PATH;
import static Utils.Constants.DESCRIPTION_FILE;
import Utils.MyFileUtils;
import static Utils.StringUtil.getCurrentDateTime;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.RemoteFile;
import se.vidstige.jadb.managers.PackageManager;
import ssc.automation.selennium.SeleniumJSUtils;

/**
 * @author magictool
 */
public class AdbUtils {

    public static boolean openLink(JadbDevice device, String link) {
        try {
            String cmd = "start -a android.intent.action.VIEW -d" + link;
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("am", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.length() == 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean killApp(JadbDevice device, String _package) {
        try {
            String cmd = "force-stop " + _package;
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("am", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
                if (line.length() != 0) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getElementAttrNotDump(JadbDevice device, String xpath, String attr, int position, int timeOut) {
        try {
            int time = 0;
            String tag = xpath.split("#")[0];
            String xa = xpath.split("#")[1];
            String key = "";
            String value = "";
            if (!xpath.contains("@")) {
                key = xa.split("=")[0];
                value = xa.split("=")[1];
            } else {
                key = xa.split("@")[0];
                value = xa.split("@")[1];
            }
            Element currentElement = null;
            while (currentElement == null) {
                if (time == timeOut) {
                    return "";
                }
                time++;
                File input = new File(DUMPVIEW_PATH);
                Document doc = Jsoup.parse(input, "UTF-8");
                //doc.get
                Elements elements = null;
                if (!xpath.contains("@")) {
                    elements = doc.getElementsByAttributeValue(key, value).select("node[class=" + tag + "]");
                } else {
                    elements = doc.getElementsByAttributeValueContaining(key, value).select("node[class=" + tag + "]");
                }
                if (elements.size() == 0) {
                    System.out.println("can't fount element " + xpath);
                    continue;
                } else {
                    currentElement = elements.get(position);
                    try {
                        return currentElement.attr(attr);
                    } catch (Exception e) {
                        return "";
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static int getLengthElements(JadbDevice device, String xpath, int timeOut) {
        try {

            int time = 0;
            String tag = xpath.split("#")[0];
            String xa = xpath.split("#")[1];
            String key = "";
            String value = "";
            if (!xpath.contains("@")) {
                key = xa.split("=")[0];
                value = xa.split("=")[1];
            } else {
                key = xa.split("@")[0];
                value = xa.split("@")[1];
            }
            Element currentElement = null;
            while (currentElement == null) {
                if (time == timeOut) {
                    return 0;
                }
                time++;
                if (!dumpView(device)) {
                    continue;
                }
                if (!new File(DUMPVIEW_PATH).exists()) {
                    continue;
                }
                File input = new File(DUMPVIEW_PATH);
                Document doc = Jsoup.parse(input, "UTF-8");
                //doc.get
                Elements elements = null;
                if (!xpath.contains("@")) {
                    elements = doc.getElementsByAttributeValue(key, value).select("node[class=" + tag + "]");
                } else {
                    elements = doc.getElementsByAttributeValueContaining(key, value).select("node[class=" + tag + "]");
                }
                if (elements.size() == 0) {
                    System.out.println("can't fount element " + xpath);
                    continue;
                } else {

                    try {
                        return elements.size();
                    } catch (Exception e) {
                        return 0;
                    }
                }
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean installApk(JadbDevice device, String apkPath) {
        try {
            new PackageManager(device).install(new File(apkPath));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void reConnectAdb() {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "cmd /c adb kill-server && adb start-server ";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    if (line.equalsIgnoreCase("quit")) {
                        process.destroy();
                        break;
                    }
                    if (line.contains("connected to")) {
                        process.destroy();
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                //return false;
            }
            //reConnectAdbN();
            //return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            //return false;
        }
    }

    public static boolean getCaptchaImg(JadbDevice device, String file) {
        try {
            captureScreen(device);
            device.pull(new RemoteFile("/sdcard/screencap.png"), new File(file));
            //EditImageUtils.cropCaptchaImage(file, file);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean pushFile(JadbDevice device, String file, String removeFile) {
        try {
            device.push(new File(file), new RemoteFile(removeFile));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteFile(JadbDevice device, String removeFile) {
        try {
            String cmd = "-r " + removeFile;
            String cmd_array[] = cmd.split(" ");
            device.executeShell("rm", cmd_array);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void goRecovery(JadbDevice device) {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "cmd /c adb reboot recovery && echo completed";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    //while (true) {
                    line = input.readLine();
                    if (checkDeviceReady(device)) {
                        System.out.println("line recovery sẵn sàng");
                    } else {
                        System.out.println("line recovery đang chờ");
                    }
                    SeleniumJSUtils.Sleep(1000);
                }
            } catch (Exception e) {
                System.out.println("Kết thúc recovery");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void restoreData(String path) {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "cmd /c adb restore path";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("path") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("path")] = path;
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    System.out.println("line restore " + line);
                }
            } catch (Exception e) {
                System.out.println("Kết thúc restore");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void backupData(String path) {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "cmd /c adb backup -f path --twrp data  ";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("path") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("path")] = path;
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    System.out.println("line backup " + line);
                }
            } catch (Exception e) {
                System.out.println("Kết thúc backup");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void reconnectDevice(String port) {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "cmd /c adb connect 127.0.0.1:"+port;
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    System.out.println("line reconnectAdb " + line);
                }
            } catch (Exception e) {
                System.out.println("Kết thúc reconnectAdb");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void resetDevice() {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "cmd /c adb reboot";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    System.out.println("line restore " + line);
                }
            } catch (Exception e) {
                System.out.println("Kết thúc restore");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean captureScreen(JadbDevice device) {
        try {
            String cmd = "-p /sdcard/screencap.png";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("screencap", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.length() == 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean openYoutubeVideo(JadbDevice device, String video_id) {
        try {
            String cmd = "start -a android.intent.action.VIEW -d \"https://www.youtube.com/watch?v=" + video_id + "\" ";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("am", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.length() == 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean openYoutubeApp(JadbDevice device) {
        try {
            String cmd = "start -a android.intent.action.VIEW -d \"market://details?id=com.google.android.youtube\" ";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("am", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.length() == 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean openYoutubeStudioApp(JadbDevice device) {
        try {
            String cmd = "start -a android.intent.action.VIEW -d \"market://details?id=com.google.android.apps.youtube.creator\" ";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("am", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.length() == 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean makeFolder(JadbDevice device, String folderPath) {
        try {
            String cmd = "-m 777 " + folderPath;
            String cmd_array[] = cmd.split(" ");
            device.executeShell("mkdir", cmd_array);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Boolean checkFolderEmpty(JadbDevice device, String folderPath) {
        try {
            String cmd = "-R " + folderPath;
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("ls", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            int count = 0;
            while ((line = input.readLine()) != null) {

                if (line.length() != 0 && !line.contains(folderPath + ":")) {
                    System.out.println("checkFolderEmpty line " + line);
                    count++;
                }
            }
            if (count == 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkThumb(JadbDevice device) {
        try {
            String cmd = "-R /sdcard/Download";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("ls", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            int count = 0;
            while ((line = input.readLine()) != null) {
                if (line.contains("thumb")) {
                    count++;
                }
            }
            if (count == 2) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkVideo(JadbDevice device) {
        try {
            String cmd = "-R /sdcard/Download";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("ls", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            int count = 0;
            while ((line = input.readLine()) != null) {
                if (line.contains("video")) {
                    count++;
                }
            }
            if (count == 1) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkThumbExist(JadbDevice device) {
        try {
            String cmd = "-R /sdcard/Download";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("ls", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.contains("thumb")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    
    public static boolean clearApp(JadbDevice device, String apkPath) {
        try {
            String cmd = "clear " + apkPath;
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("pm", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.length() == 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean resetApp(JadbDevice device, String apkPath) {
        try {
            String cmd = "force-stop " + apkPath;
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("am", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.length() == 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean copyFile(JadbDevice device, String file, String removeFile) {
        try {
            String cmd = file + " " + removeFile;
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("cp", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
                if (line.contains("node returned by UiTestAutomationBridge") || line.contains("could not get") || line.contains("No such file")) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkDeviceReady(JadbDevice device) {
        try {
            String cmd = " sys.boot_completed";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("getprop", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    if (line.contains("not found")) {
                        return false;
                    } else {
                        return true;
                    }
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean copyClipboard(JadbDevice device) {
        try {
            String cmd = "startservice -n com.gravmatt.copyservice/.CopyService -c copytext ";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("am", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    System.out.println("line setClipBoard " + line);
                    if (line == null) {
                        break;
                    }
                    if (line.contains("com.gravmatt.copyservice/.CopyService")) {
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean dumpView(JadbDevice device) {
        try {
            deleteFile(device, "/sdcard/window_dump.xml");
            String cmd = "dump";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("uiautomator", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.contains("could not get")) {
                    return false;
                }
                if (line.equalsIgnoreCase("quit")) {
                    return false;
                }
                if (line.contains("node returned by UiTestAutomationBridge") || line.contains("could not get")) {
                    return false;
                }
            }
            pullFile(device);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean longClickToPoint(JadbDevice device, String[] fromPoint) {
        try {
            String cmd = "swipe " + fromPoint[0] + " " + fromPoint[1] + " " + fromPoint[0] + " " + fromPoint[1] + " 2000";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("input", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean clickToPoint(JadbDevice device, String[] point) {
        try {
            String cmd = "tap " + point[0] + " " + point[1];
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("input", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void clickClear(JadbDevice device) {
        try {
            String cmd = "keyevent 62";
            if (device.getSerial().contains("62001")) {
                cmd = "keyevent 62";
            }
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("input", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clickEnter(JadbDevice device) {
        try {
            String cmd = "keyevent 66";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("input", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clickTab(JadbDevice device) {
        try {
            String cmd = "keyevent 61";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("input", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clickBack(JadbDevice device) {
        try {
            String cmd = "keyevent 4";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("input", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean clickLeft(JadbDevice device) {
        try {
            String cmd = "keyevent 21";
            if (device.getSerial().contains("62001")) {
                cmd = "keyevent 21";
            }
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("input", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean openAppSwitch(JadbDevice device) {
        try {
            String cmd = "keyevent KEYCODE_APP_SWITCH";
            if (device.getSerial().contains("215")) {
                cmd = "keyevent KEYCODE_APP_SWITCH";
            }
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("input", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean swipeToPoint(JadbDevice device, String[] fromPoint, String toPoint[]) {

        try {
            String cmd = "swipe " + fromPoint[0] + " " + fromPoint[1] + " " + toPoint[0] + " " + toPoint[1];
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("input", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkNotificationExist(JadbDevice device, String notifiName) {
        try {
            String cmd = "notification | grep ticker | cut -d= -f2";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("dumpsys", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                System.out.println("checkNotificationExist line " + line);
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
                if (line.contains(notifiName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static boolean checkActivityExist(JadbDevice device, String activityString) {
        try {
            String cmd = "window windows | grep -E 'mCurrentFocus|mFocusedApp'";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("dumpsys", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
                if (line.contains(activityString)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static boolean sendText(JadbDevice device, String text) {
        try {
            String cmd = "text \"" + text + "\"";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("input", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean sendText(String text) {
        try {
            String myString = text;
            StringSelection stringSelection = new StringSelection(myString);
            Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
            clpbrd.setContents(stringSelection, null);
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean pullFile(JadbDevice device) {
        try {
            device.pull(new RemoteFile("/sdcard/window_dump.xml"), new File(DUMPVIEW_PATH));
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static boolean pullErrorFile(JadbDevice device) {
        try {
            String fileName = getCurrentDateTime("hhmmssddMMYYYY") + ".xml";
            device.pull(new RemoteFile("/sdcard/window_dump.xml"), new File(DUMPVIEW_ERROR_PATH + fileName));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean clickComponant(JadbDevice device, String xpath) {
        try {
            String key = xpath.split("=")[0];
            String value = xpath.split("=")[1];
            if (!dumpView(device)) {
                System.out.println("Không thể dump");
                return false;
            }
            File input = new File(DUMPVIEW_PATH);
            Document doc = Jsoup.parse(input, "UTF-8");
            List<Element> elements = doc.getElementsByAttributeValueContaining(key, value);
            if (elements.size() == 0) {
                System.out.println("Không có element");
                return false;
            }
            String bound = elements.get(0).attr("bounds");
            String _bound = bound.replaceAll("]\\[", ",").replaceAll("\\[", "").replaceAll("]", "");
            int x1 = Integer.parseInt(_bound.split(",")[0]);
            int x2 = Integer.parseInt(_bound.split(",")[2]);
            int y1 = Integer.parseInt(_bound.split(",")[1]);
            int y2 = Integer.parseInt(_bound.split(",")[3]);
            String[] point = new String[2];
            point[0] = String.valueOf((x2 + x1) / 2);
            point[1] = String.valueOf((y2 + y1) / 2);
            return clickToPoint(device, point);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String clickComponant(JadbDevice device, String xpath, String activity, int timeOut) {
        try {
            int time = 0;
            String tag = xpath.split("#")[0];
            String xa = xpath.split("#")[1];
            String key = "";
            String value = "";
            if (!xpath.contains("@")) {
                key = xa.split("=")[0];
                value = xa.split("=")[1];
            } else {
                key = xa.split("@")[0];
                value = xa.split("@")[1];
            }
            Element currentElement = null;
            while (currentElement == null) {
                if (time == timeOut) {
                    return "Cant found element";
                }
                time++;
                if (!dumpView(device)) {
                    continue;
                }
                File input = new File(DUMPVIEW_PATH);
                Document doc = Jsoup.parse(input, "UTF-8");
                //doc.get
                Elements elements = null;
                if (!xpath.contains("@")) {
                    elements = doc.getElementsByAttributeValue(key, value).select("node[class=" + tag + "]");
                } else {
                    elements = doc.getElementsByAttributeValueContaining(key, value).select("node[class=" + tag + "]");
                }
                if (elements.size() == 0) {
                    System.out.println("can't fount element " + xpath);
                    continue;
                }
                currentElement = elements.get(0);
            }
            time = 0;
            String bound = currentElement.attr("bounds");
            String _bound = bound.replaceAll("]\\[", ",").replaceAll("\\[", "").replaceAll("]", "");
            int x1 = Integer.parseInt(_bound.split(",")[0]);
            int x2 = Integer.parseInt(_bound.split(",")[2]);
            int y1 = Integer.parseInt(_bound.split(",")[1]);
            int y2 = Integer.parseInt(_bound.split(",")[3]);
            String[] point = new String[2];
            point[0] = String.valueOf((x2 + x1) / 2);
            point[1] = String.valueOf((y2 + y1) / 2);
            if (!clickToPoint(device, point)) {
                return "Cant click element";
            }
            while (!checkActivityExist(device, activity)) {
                if (time == timeOut) {
                    return "Cant change view";
                }
                SeleniumJSUtils.Sleep(1000);
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "click element error " + e.getMessage();
        }
    }

    public static String clickComponantAndWaitElementWithNextDif(JadbDevice device, String xpath, String newXpath, String nextXpath, int timeOut) {
        int time = 0;
        while (true) {
            if (time == 3) {
                return "cant click and change view ";
            }
            time++;
            String result = clickComponantWithNextDif(device, xpath, nextXpath, timeOut);
            if (result.length() != 0) {
                continue;
            }
            if (isElementPresent(device, newXpath, timeOut).length() != 0) {
                continue;
            }
            return "";
        }
    }

    public static String clickComponantAndWaitElement(JadbDevice device, String xpath, String newXpath, int timeOut) {
        int time = 0;
        while (true) {
            if (time == 3) {
                return "cant click and change view ";
            }
            time++;
            String result = clickComponant(device, xpath, timeOut);
            if (result.length() != 0) {
                continue;
            }
            if (isElementPresent(device, newXpath, timeOut).length() != 0) {
                continue;
            }
            return "";
        }
    }

    public static String clickComponantWithNextDif(JadbDevice device, String xpath, String nextXpath, int timeOut) {
        try {
            int time = 0;
            String tag = xpath.split("#")[0];
            String xa = xpath.split("#")[1];
            String key = "";
            String value = "";
            if (!xpath.contains("@")) {
                key = xa.split("=")[0];
                value = xa.split("=")[1];
            } else {
                key = xa.split("@")[0];
                value = xa.split("@")[1];
            }
            Element currentElement = null;
            while (currentElement == null) {
                if (time == timeOut) {
                    return "Cant found element";
                }
                time++;
                if (!dumpView(device)) {
                    continue;
                }
                File input = new File(DUMPVIEW_PATH);
                Document doc = Jsoup.parse(input, "UTF-8");
                //doc.get
                Elements elements = null;
                if (!xpath.contains("@")) {
                    elements = doc.getElementsByAttributeValue(key, value).select("node[class=" + tag + "]");
                } else {
                    elements = doc.getElementsByAttributeValueContaining(key, value);
                }
                if (elements.size() == 0) {
                    continue;
                }
                for (Element element : elements) {
                    try {
                        String nextKey = nextXpath.split("=")[0];
                        String nextValue = nextXpath.split("=")[1];
                        if (element.firstElementSibling().attr(nextKey).contains(nextValue)) {
                            continue;
                        }
                        currentElement = element;
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
            time = 0;
            String bound = currentElement.attr("bounds");
            String _bound = bound.replaceAll("]\\[", ",").replaceAll("\\[", "").replaceAll("]", "");
            int x1 = Integer.parseInt(_bound.split(",")[0]);
            int x2 = Integer.parseInt(_bound.split(",")[2]);
            int y1 = Integer.parseInt(_bound.split(",")[1]);
            int y2 = Integer.parseInt(_bound.split(",")[3]);
            String[] point = new String[2];
            point[0] = String.valueOf((x2 + x1) / 2);
            point[1] = String.valueOf((y2 + y1) / 2);
            if (!clickToPoint(device, point)) {
                return "Cant click element";
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "click element error " + e.getMessage();
        }
    }

    public static String clickComponant(JadbDevice device, String xpath, int timeOut) {
        try {
            int time = 0;
            String tag = xpath.split("#")[0];
            String xa = xpath.split("#")[1];
            String key = "";
            String value = "";
            if (!xpath.contains("@")) {
                key = xa.split("=")[0];
                value = xa.split("=")[1];
            } else {
                key = xa.split("@")[0];
                value = xa.split("@")[1];
            }
            Element currentElement = null;
            while (currentElement == null) {
                if (time == timeOut) {
                    return "Cant found element";
                }
                time++;
                if (!dumpView(device)) {
                    continue;
                }
                File input = new File(DUMPVIEW_PATH);
                Document doc = Jsoup.parse(input, "UTF-8");
                //doc.get
                Elements elements = null;
                if (!xpath.contains("@")) {
                    System.out.println("loai so sanh =");
                    elements = doc.getElementsByAttributeValue(key, value).select("node[class=" + tag + "]");
                } else {
                    System.out.println("loai so sanh contains xa=" + xa + " key=" + key + " value= " + value);

                    elements = doc.getElementsByAttributeValueContaining(key, value);
                }
                if (elements.size() == 0) {
                    System.out.println("can't fount element " + tag + " " + key + (xpath.contains("@") ? "@" : "=") + value);
                    continue;
                }
                currentElement = elements.get(0);
            }
            time = 0;
            String bound = currentElement.attr("bounds");
            String _bound = bound.replaceAll("]\\[", ",").replaceAll("\\[", "").replaceAll("]", "");
            int x1 = Integer.parseInt(_bound.split(",")[0]);
            int x2 = Integer.parseInt(_bound.split(",")[2]);
            int y1 = Integer.parseInt(_bound.split(",")[1]);
            int y2 = Integer.parseInt(_bound.split(",")[3]);
            String[] point = new String[2];
            point[0] = String.valueOf((x2 + x1) / 2);
            point[1] = String.valueOf((y2 + y1) / 2);
            if (!clickToPoint(device, point)) {
                return "Cant click element";
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "click element error " + e.getMessage();
        }
    }

    public static String getTextOfElement(JadbDevice device, String xpath, int timeOut) {
        try {
            int time = 0;
            String tag = xpath.split("#")[0];
            String xa = xpath.split("#")[1];
            String key = "";
            String value = "";
            if (!xpath.contains("@")) {
                key = xa.split("=")[0];
                value = xa.split("=")[1];
            } else {
                key = xa.split("@")[0];
                value = xa.split("@")[1];
            }
            Element currentElement = null;
            while (currentElement == null) {
                if (time == timeOut) {
                    System.out.println("timeOut");
                    return "";
                }
                time++;
                if (!dumpView(device)) {
                    System.out.println("dump fail");
                    continue;
                }
                File input = new File(DUMPVIEW_PATH);
                Document doc = Jsoup.parse(input, "UTF-8");
                //doc.get
                Elements elements = null;
                if (!xpath.contains("@")) {
                    elements = doc.getElementsByAttributeValue(key, value).select("node[class=" + tag + "]");
                } else {
                    elements = doc.getElementsByAttributeValueContaining(key, value).select("node[class=" + tag + "]");
                }
                if (elements.size() == 0) {
                    System.out.println("can't fount element " + xpath);
                    continue;
                }
                currentElement = elements.get(0);
            }
            return currentElement.attr("text");
        } catch (Exception e) {
            return "";
        }
    }

    public static String getBoundOfElement(JadbDevice device, String xpath) {
        try {
            String tag = xpath.split("#")[0];
            String xa = xpath.split("#")[1];
            String key = "";
            String value = "";
            if (!xpath.contains("@")) {
                key = xa.split("=")[0];
                value = xa.split("=")[1];
            } else {
                key = xa.split("@")[0];
                value = xa.split("@")[1];
            }
            Element currentElement = null;

            File input = new File(DUMPVIEW_PATH);
            Document doc = Jsoup.parse(input, "UTF-8");
            //doc.get
            Elements elements = null;

            elements = doc.getElementsByAttributeValueContaining(key, value).select("node[class=" + tag + "]");

            currentElement = elements.get(0);
            return currentElement.attr("bounds");
        } catch (Exception e) {
            return "";
        }
    }

    public static String typeComponant(JadbDevice device, String xpath, String text, int timeOut) {
        try {
            int time = 0;
            String tag = xpath.split("#")[0];
            String xa = xpath.split("#")[1];
            String key = "";
            String value = "";
            if (!xpath.contains("@")) {
                key = xa.split("=")[0];
                value = xa.split("=")[1];
            } else {
                key = xa.split("@")[0];
                value = xa.split("@")[1];
            }
            Element currentElement = null;
            while (currentElement == null) {
                if (time == timeOut) {
                    return "Cant found element";
                }
                time++;
                if (!dumpView(device)) {
                    continue;
                }
                File input = new File(DUMPVIEW_PATH);
                Document doc = Jsoup.parse(input, "UTF-8");
                //doc.get
                Elements elements = null;
                if (!xpath.contains("@")) {
                    elements = doc.getElementsByAttributeValue(key, value).select("node[class=" + tag + "]");
                } else {
                    elements = doc.getElementsByAttributeValueContaining(key, value).select("node[class=" + tag + "]");
                }
                if (elements.size() == 0) {
                    System.out.println("can't fount element " + xpath);
                    continue;
                }
                currentElement = elements.get(0);
            }
            time = 0;
            String bound = currentElement.attr("bounds");
            String _bound = bound.replaceAll("]\\[", ",").replaceAll("\\[", "").replaceAll("]", "");
            int x1 = Integer.parseInt(_bound.split(",")[0]);
            int x2 = Integer.parseInt(_bound.split(",")[2]);
            int y1 = Integer.parseInt(_bound.split(",")[1]);
            int y2 = Integer.parseInt(_bound.split(",")[3]);
            String[] point = new String[2];
            point[0] = String.valueOf((x2 + x1) / 2);
            point[1] = String.valueOf((y2 + y1) / 2);
            if (!clickToPoint(device, point)) {
                return "Cant click element";
            }
            //for (char ch : text.toCharArray()) {
            sendText(device, text);
            //}
            return "";
        } catch (Exception e) {
            return "click element error " + e.getMessage();
        }
    }

    public static boolean setClipBoard(String text, JadbDevice device) {
        try {
            String[] command = {"broadcast", "-a", "clipper.set", "-e", "text", text};
            InputStream is = device.executeShell("am", command);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    System.out.println("line setClipBoard " + line);
                    if (line.equalsIgnoreCase("quit")) {
                        break;
                    }
                    if (line.contains("Text is copied")) {
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
//        public static boolean setClipBoard(String text) {
//        try {
//            String cmd = "cmd /c \"adb\" shell \"am broadcast -a clipper.set -e text '" + text + "'\" ";
//            String cmd_array[] = cmd.split(" ");
//            for (int i = 0; i < cmd_array.length; i++) {
//                if (cmd_array[i].equals("adb")) {
//                    cmd_array[i] = ADB;
//                }
//            }
//            for (String string : cmd_array) {
//                System.out.print(string+" ");
//            }
//            ProcessBuilder pb = new ProcessBuilder(cmd_array);
//            pb.redirectErrorStream(true);
//            Process process = pb.start();
//            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line = null;
//            try {
//                while ((line = input.readLine()) != null) {
//                    System.out.println("line setClipBoard " + line);
//                    if (line.equalsIgnoreCase("quit")) {
//                        break;
//                    }
//                    if (line.contains("Text is copied")) {
//                        return true;
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            //SSCLog.insertSSCLog("", "Không thể copy data vào bộ nhớ tạm", text, arrLog);
//            return false;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//
//    }

    public static String typeLongComponant(JadbDevice device, String xpath, String text, int timeOut) {
        try {
            int time = 0;
            String tag = xpath.split("#")[0];
            String xa = xpath.split("#")[1];
            String key = "";
            String value = "";
            if (!xpath.contains("@")) {
                key = xa.split("=")[0];
                value = xa.split("=")[1];
            } else {
                key = xa.split("@")[0];
                value = xa.split("@")[1];
            }
            Element currentElement = null;
            while (currentElement == null) {
                if (time == timeOut) {
                    return "Cant found element";
                }
                time++;
                if (!dumpView(device)) {
                    continue;
                }
                File input = new File(DUMPVIEW_PATH);
                Document doc = Jsoup.parse(input, "UTF-8");
                //doc.get
                Elements elements = null;
                if (!xpath.contains("@")) {
                    elements = doc.getElementsByAttributeValue(key, value).select("node[class=" + tag + "]");
                } else {
                    elements = doc.getElementsByAttributeValueContaining(key, value).select("node[class=" + tag + "]");
                }
                if (elements.size() == 0) {
                    System.out.println("can't fount element " + xpath);
                    continue;
                }
                currentElement = elements.get(0);
            }
            time = 0;
            String bound = currentElement.attr("bounds");
            String _bound = bound.replaceAll("]\\[", ",").replaceAll("\\[", "").replaceAll("]", "");
            int x1 = Integer.parseInt(_bound.split(",")[0]);
            int x2 = Integer.parseInt(_bound.split(",")[2]);
            int y1 = Integer.parseInt(_bound.split(",")[1]);
            int y2 = Integer.parseInt(_bound.split(",")[3]);
            String[] point = new String[2];
            point[0] = String.valueOf(x1);
            point[1] = String.valueOf(y1);
            if (!AdbUtils.clickToPoint(device, point)) {
                return "Cant set click text";
            }
            if (!xpath.contains("description")) {
                if (!setClipBoard(text.replaceAll("'", ""), device)) {
                    System.err.println("Cant set clipboard");
                    return "Cant set clipboard";
                }
            } else {
                if (!MyFileUtils.deleteFile(DESCRIPTION_FILE)) {
                    return "Cant delete description file ";
                }
                if (!MyFileUtils.writeStringToFileUTF8(text, DESCRIPTION_FILE)) {
                    return "Cant write data to file";
                }
                try {
                    device.push(new File(DESCRIPTION_FILE), new RemoteFile(DESCRIPTION_FILE_SERVICE));
                } catch (Exception e) {
                    return "Cant push file des to device";
                }
                if (!AdbUtils.copyClipboard(device)) {
                    return "Cant copy data to clipboard";
                }
            }
            //  if (xpath.contains("title")) {
            //      longClickToPoint(device, point);
            //  } else {
            AdbUtils.sendText(device, "abcdefg");
            //AdbUtils.clickToPoint(device, point);
            //AdbUtils.clickToPoint(device, point);     
            longClickToPoint(device, point);
            // }
            SeleniumJSUtils.Sleep(2000);
            return clickComponant(device, "android.widget.TextView#resource-id=android:id/paste", 10);
        } catch (Exception e) {
            return "click element error " + e.getMessage();
        }
    }

    public static String cleartypeLongComponant(JadbDevice device, String xpath, int timeOut) {
        try {
            int time = 0;
            String tag = xpath.split("#")[0];
            String xa = xpath.split("#")[1];
            String key = "";
            String value = "";
            if (!xpath.contains("@")) {
                key = xa.split("=")[0];
                value = xa.split("=")[1];
            } else {
                key = xa.split("@")[0];
                value = xa.split("@")[1];
            }
            Element currentElement = null;
            while (currentElement == null) {
                if (time == timeOut) {
                    return "Cant found element";
                }
                time++;
                if (!dumpView(device)) {
                    continue;
                }
                File input = new File(DUMPVIEW_PATH);
                Document doc = Jsoup.parse(input, "UTF-8");
                //doc.get
                Elements elements = null;
                if (!xpath.contains("@")) {
                    elements = doc.getElementsByAttributeValue(key, value).select("node[class=" + tag + "]");
                } else {
                    elements = doc.getElementsByAttributeValueContaining(key, value).select("node[class=" + tag + "]");
                }
                if (elements.size() == 0) {
                    System.out.println("can't fount element " + xpath);
                    continue;
                }
                currentElement = elements.get(0);
            }
            time = 0;
            String bound = currentElement.attr("bounds");
            String _bound = bound.replaceAll("]\\[", ",").replaceAll("\\[", "").replaceAll("]", "");
            int x1 = Integer.parseInt(_bound.split(",")[0]);
            int x2 = Integer.parseInt(_bound.split(",")[2]);
            int y1 = Integer.parseInt(_bound.split(",")[1]);
            int y2 = Integer.parseInt(_bound.split(",")[3]);
            String[] point = new String[2];
            point[0] = String.valueOf(x1);
            point[1] = String.valueOf(y1);
            if (!AdbUtils.clickToPoint(device, point)) {
                return "Cant set click text";
            }
            sendText(device, "aaaaaaaaaaa");
            longClickToPoint(device, point);
            SeleniumJSUtils.Sleep(2000);
            clickComponant(device, "android.widget.TextView#resource-id=android:id/selectAll", 10);
            AdbUtils.clickClear(device);
            return "";
        } catch (Exception e) {
            return "click element error " + e.getMessage();
        }
    }

    public static String isElementPresent(JadbDevice device, String xpath, int timeOut) {
        try {
            int time = 0;
            String tag = xpath.split("#")[0];
            String xa = xpath.split("#")[1];
            String key = "";
            String value = "";
            if (!xpath.contains("@")) {
                key = xa.split("=")[0];
                value = xa.split("=")[1];
            } else {
                key = xa.split("@")[0];
                value = xa.split("@")[1];
            }
            Element currentElement = null;
            while (currentElement == null) {
                if (time >= timeOut) {
                    return "Cant found element";
                }
                time++;
                if (!dumpView(device)) {
                    continue;
                }
                if (!new File(DUMPVIEW_PATH).exists()) {
                    System.out.println("can't dump file " + xpath);
                    continue;
                }
                File input = new File(DUMPVIEW_PATH);
                Document doc = Jsoup.parse(input, "UTF-8");
                //doc.get
                Elements elements = null;
                if (!xpath.contains("@")) {
                    elements = doc.getElementsByAttributeValue(key, value).select("node[class=" + tag + "]");
                } else {
                    elements = doc.getElementsByAttributeValueContaining(key, value).select("node[class=" + tag + "]");
                }
                if (elements.size() == 0) {
                    System.out.println("can't fount element " + xpath);
                    continue;
                } else {
                    currentElement = elements.get(0);
                    return "";
                }
            }
            return "";
        } catch (Exception e) {
            return "click element error " + e.getMessage();
        }
    }

    public static String getNextElementAttr(JadbDevice device, String xpath, String nextAttr, int timeOut) {
        try {
            int time = 0;
            String tag = xpath.split("#")[0];
            String xa = xpath.split("#")[1];
            String key = "";
            String value = "";
            if (!xpath.contains("@")) {
                key = xa.split("=")[0];
                value = xa.split("=")[1];
            } else {
                key = xa.split("@")[0];
                value = xa.split("@")[1];
            }
            Element currentElement = null;
            while (currentElement == null) {
                if (time == timeOut) {
                    return "";
                }
                time++;
                if (!dumpView(device)) {
                    continue;
                }
                if (!new File(DUMPVIEW_PATH).exists()) {
                    continue;
                }
                File input = new File(DUMPVIEW_PATH);
                Document doc = Jsoup.parse(input, "UTF-8");
                //doc.get
                Elements elements = null;
                if (!xpath.contains("@")) {
                    elements = doc.getElementsByAttributeValue(key, value).select("node[class=" + tag + "]");
                } else {
                    elements = doc.getElementsByAttributeValueContaining(key, value).select("node[class=" + tag + "]");
                }
                if (elements.size() == 0) {
                    System.out.println("can't fount element " + xpath);
                    continue;
                } else {
                    currentElement = elements.get(0);
                    try {
                        return currentElement.firstElementSibling().attr(nextAttr);
                    } catch (Exception e) {
                        return "";
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static String longclickComponant(JadbDevice device, String xpath, int timeOut) {
        try {
            int time = 0;
            String tag = xpath.split("#")[0];
            String xa = xpath.split("#")[1];
            String key = "";
            String value = "";
            if (!xpath.contains("@")) {
                key = xa.split("=")[0];
                value = xa.split("=")[1];
            } else {
                key = xa.split("@")[0];
                value = xa.split("@")[1];
            }
            Element currentElement = null;
            while (currentElement == null) {
                if (time == timeOut) {
                    return "Cant found element";
                }
                time++;
                if (!dumpView(device)) {
                    continue;
                }
                File input = new File(DUMPVIEW_PATH);
                Document doc = Jsoup.parse(input, "UTF-8");
                //doc.get
                Elements elements = null;
                if (!xpath.contains("@")) {
                    elements = doc.getElementsByAttributeValue(key, value).select("node[class=" + tag + "]");
                } else {
                    elements = doc.getElementsByAttributeValueContaining(key, value).select("node[class=" + tag + "]");
                }
                if (elements.size() == 0) {
                    System.out.println("can't fount element " + xpath);
                    continue;
                }
                currentElement = elements.get(0);
            }
            time = 0;
            String bound = currentElement.attr("bounds");
            String _bound = bound.replaceAll("]\\[", ",").replaceAll("\\[", "").replaceAll("]", "");
            int x1 = Integer.parseInt(_bound.split(",")[0]);
            int x2 = Integer.parseInt(_bound.split(",")[2]);
            int y1 = Integer.parseInt(_bound.split(",")[1]);
            int y2 = Integer.parseInt(_bound.split(",")[3]);
            String[] point = new String[2];
            point[0] = String.valueOf((x2 + x1) / 2);
            point[1] = String.valueOf((y2 + y1) / 2);
            if (!longClickToPoint(device, point)) {
                return "Cant long click element";
            }
            return "";
        } catch (Exception e) {
            return "click element error " + e.getMessage();
        }
    }

    public static Boolean checkDeviceConnect(JadbDevice device) {
        try {
            InputStream is = device.executeShell("getprop", "ro.build.version.release");
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                //System.out.println("checkDeviceConnect "+line);
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
                if (line.contains("5.1.1")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static int getSizeOfElements(JadbDevice device, String xpath, int timeOut) {
        try {
            int time = 0;
            String tag = xpath.split("#")[0];
            String xa = xpath.split("#")[1];
            String key = "";
            String value = "";
            if (!xpath.contains("@")) {
                key = xa.split("=")[0];
                value = xa.split("=")[1];
            } else {
                key = xa.split("@")[0];
                value = xa.split("@")[1];
            }
            Element currentElement = null;
            while (currentElement == null) {
                if (time == timeOut) {
                    return 0;
                }
                time++;
                if (!dumpView(device)) {
                    continue;
                }
                File input = new File(DUMPVIEW_PATH);
                Document doc = Jsoup.parse(input, "UTF-8");
                //doc.get
                Elements elements = null;
                if (!xpath.contains("@")) {
                    elements = doc.getElementsByAttributeValue(key, value).select("node[class=" + tag + "]");
                } else {
                    elements = doc.getElementsByAttributeValueContaining(key, value).select("node[class=" + tag + "]");
                }
                if (elements.size() == 0) {
                    System.out.println("can't fount element " + xpath);
                    continue;
                }
                return elements.size();
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean killAllApp(JadbDevice device) {
        try {
            String cmd = "kill-all";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("am", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
                if (line.length() != 0) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String openApp(JadbDevice device, String appPackage) {
        try {
            String cmd = "-p " + appPackage + " -c android.intent.category.LAUNCHER 1";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("monkey", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
//                if (line.length() != 0) {
//                    System.out.println("line result openApp : " + line);
//                    return false;
//                }
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();

        }
    }

    public static String startActivity(JadbDevice device, String appPackage_activity) {
        try {
            String cmd = "start -n " + appPackage_activity;
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("am", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
//                if (line.length() != 0) {
//                    System.out.println("line result openApp : " + line);
//                    return false;
//                }
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();

        }
    }

    public static String openNotification(JadbDevice device) {
        try {
            String cmd = "call statusbar 1";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("service ", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();

        }
    }

    public static boolean chmodFolder(JadbDevice device, String folder) {
        try {
            String cmd = "-c 'chmod 777 " + folder + "'";
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("su", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                System.out.println("line result : " + line);
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
                if (line.length() != 0) {

                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void mkdirSSCChanger(JadbDevice device, String folder) {
        //adb shell "su -c 'chmod 777 /data/data/offer.ssc.sharepreferencestest/shared_prefs'"
        //adb push ‪C:/Users/inet/Desktop/shared_prefs /data/data/offer.ssc.changer/shared_prefs
        try {
            String cmd = folder;
            String cmd_array[] = cmd.split(" ");
            InputStream is = device.executeShell("mkdir", cmd_array);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
                System.out.println("Line entered : " + line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
