/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.automation.adb;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;
import javafx.collections.ObservableList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import se.vidstige.jadb.JadbDevice;
import Setting.ToolSetting;
import static ssc.automation.selennium.constants.WINDOW_APP_PATH.ADB_C;
import static ssc.automation.selennium.constants.WINDOW_APP_PATH.MEMU_C;
import ssc.automation.selennium.SeleniumJSUtils;
import log.RunLog;
import static Utils.Constants.DATA.DUMPVIEW_PATH;
import ssc.reup.api.Utils.GraphicsUtils.EditImageUtils;
import Utils.MyFileUtils;

/**
 * @author simpl
 */
public class MemuCUtils {

    public static String installApk(int position, String apkPath) {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "adb installapp -i " + position + " apkPath ";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("adb") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("adb")] = MEMU_C;
            }
            if (Arrays.asList(cmd_array).indexOf("apkPath") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("apkPath")] = apkPath;
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    if (line.equalsIgnoreCase("quit")) {
                        process.destroy();
                        return "Lỗi " + line;
                    }
                    if (line.contains("SUCCESS")) {
                        process.destroy();
                        return "";
                    }
                }
                return "Lỗi " + line;
            } catch (Exception e) {
                return "Lỗi exception";
            }finally{
                process.destroy();
            }
        } catch (Exception ex) {
            return "Lỗi exception";
        }
    }

    /**
     * Mở menu chức năng thông qua các phím
     *
     * @param position
     * @param buttonName
     * @return
     */
    public static String clickAndroidButton(int position, String buttonName) {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "adb sendkey -i " + position + " " + buttonName + " ";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("adb") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("adb")] = MEMU_C;
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    if (line.equalsIgnoreCase("quit")) {
                        process.destroy();
                        return "Click " + buttonName + " " + line;
                    }
                    if (line.contains("SUCCESS")) {
                        process.destroy();
                        return "";
                    }
                }
                return "Click " + buttonName + " " + line;
            } catch (Exception e) {
                return "Lỗi " + buttonName + " exception";
            }
        } catch (Exception ex) {
            return "Lỗi " + buttonName + " exception";
        }
    }

    public static String exeAdbShellViaAdb(String title, String query) {
        try {
            //String[] cmd_array = {ADB_C, "shell", query};
            String[] cmd_array = {MEMU_C, "adb", "-i", "0", "\"", "shell", query, "\""};
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {

                }
                return " ";
            } catch (Exception e) {
                return title + " exception";
            }
        } catch (Exception ex) {
            return title + " exception";
        }
    }

    
  
    
    
    public static String exeAdbShell(int position, String title, String query) {
        if (ToolSetting.getInstance().getPre().getBoolean("sendbyClipboard", false)) {
            return exeAdbShellViaAdb(title, query);
        }
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "adb execcmd -i " + position + " \"" + query + "\"";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("adb") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("adb")] = MEMU_C;
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    if (line.equalsIgnoreCase("quit")) {
                        process.destroy();
                        return title + " " + line;
                    }
                    if (line.contains("SUCCESS")) {
                        process.destroy();
                        return "";
                    }
                }
                return title + " " + line;
            } catch (Exception e) {
                return title + " exception";
            }finally{
                 process.destroy();
            }
           
        } catch (Exception ex) {
            return title + " exception";
        }
    }

    public static String MEMU_SCREENCAP = "C:\\Users\\" + System.getProperty("user.name") + "\\Pictures\\MEmu Photo\\screencap.png";

    public static String captureScreen(int position) {
        MyFileUtils.deleteFile(MEMU_SCREENCAP);
        exeAdbShell(position, "Chụp màn hình ", "screencap -p /sdcard/Pictures/screencap.png");
        if (new File(MEMU_SCREENCAP).exists()) {
            return "";
        } else {
            return "Không thể chụp màn hình";
        }
    }

    public static boolean getCaptchaImg(int device, String file) {
        try {
            captureScreen(device);
            MyFileUtils.copyFile(new File(MEMU_SCREENCAP), new File(file));
            EditImageUtils.cropCaptchaImage(file, file);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String MEMU_DOWNLOAD_COMPUTER = "C:\\Users\\" + System.getProperty("user.name") + "\\Downloads\\MEmu Download";
    public static String MEMU_DOWNLOAD_EMULATOR = "/sdcard/Download";

    public static String checkFileExits(int position, String fileName) {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "adb execcmd -i " + position + " \"ls -R /sdcard/Download\"";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("adb") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("adb")] = MEMU_C;
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    if (line.equalsIgnoreCase("quit")) {
                        process.destroy();
                        return "Không có file ";
                    }
                    if (line.contains(fileName)) {
                        process.destroy();
                        return "";
                    }
                }
                return "Không có file ";
            } catch (Exception e) {
                return "Không có file ";
            }
        } catch (Exception ex) {
            return "Không có file ";
        }
    }

    public static String pushFile(int position, File localFile) {
        MyFileUtils.copyFile(localFile, new File(MEMU_DOWNLOAD_COMPUTER + "\\" + localFile.getName()));
        return checkFileExits(position, localFile.getName());
    }

    public static String deleteFile(int position, File localFile) {
        MyFileUtils.deleteFile(localFile.getAbsolutePath());
        return (checkFileExits(position, localFile.getName()).length() == 0 ? "Không thể xóa" : "");
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
                    //System.out.println("line setClipBoard " + line);
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

    /**
     * dump view đến thư mục share \\Downloads\\MEmu Download\\window_dump.xml
     *
     * @param position
     * @return
     */
    public static String dumpView(int device) {
        deleteFile(device, new File(DUMPVIEW_PATH));
        if (ToolSetting.getInstance().getPre().getBoolean("sendbyClipboard", false)) {
            return dumpViewViaAdb();
        }
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "adb execcmd -i " + device + " \"uiautomator dump /sdcard/Download/window_dump.xml\"";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("adb") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("adb")] = MEMU_C;
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    if (line.equalsIgnoreCase("quit")) {
                        process.destroy();
                        return "Dump view lỗi " + line;
                    }
                    if (line.contains("dumped to")) {
                        process.destroy();
                        return "";
                    }
                }
                return "Không có file ";
            } catch (Exception e) {
                return "Dump view exception ";
            }
        } catch (Exception ex) {
            return "Dump view exception ";
        }
    }

    public static String dumpViewViaAdb() {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;

            String[] cmd_array = {MEMU_C, "adb", "-i", "0", "\"", "shell", "uiautomator", "dump", "/sdcard/Download/window_dump.xml", "\""};

            //String[] cmd_array = {ADB_C, "shell", "uiautomator", "dump", "/sdcard/Download/window_dump.xml"};
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    System.out.println("line " + line);
                    if (line.equalsIgnoreCase("quit")) {
                        process.destroy();
                        return "Dump view lỗi " + line;
                    }
                    if (line.contains("dumped to")) {
                        process.destroy();
                        return "";
                    }
                }
                return "Không có file ";
            } catch (Exception e) {
                return "Dump view exception ";
            }
        } catch (Exception ex) {
            return "Dump view exception ";
        }
    }

    public static void enablePlantMode(JadbDevice device) {
        try {
            String[] cmd_array = {"cmd", "/c", "adb", "-s", device.getSerial(), "shell", "settings", "put", "global", "airplane_mode_on", "1", "&&", "adb", "-s", device.getSerial(), "shell", "\"", "su", "-c", "'am", "broadcast", "-a", "android.intent.action.AIRPLANE_MODE", "--ez", "state", "true", "'\""};
            
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    System.out.println("line " + line);

                }

            } catch (Exception e) {

            }
        } catch (Exception ex) {

        }
    }

    public static void disablePlantMode(JadbDevice device) {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;

            String[] cmd_array = {"cmd", "/c", "adb", "-s", device.getSerial(), "shell", "settings", "put", "global", "airplane_mode_on", "0", "&&", "adb", "-s", device.getSerial(), "shell", "\"", "su", "-c", "'am", "broadcast", "-a", "android.intent.action.AIRPLANE_MODE", "--ez", "state", "true", "'\""};

            //String[] cmd_array = {ADB_C, "shell", "uiautomator", "dump", "/sdcard/Download/window_dump.xml"};
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    System.out.println("line " + line);

                }

            } catch (Exception e) {

            }
        } catch (Exception ex) {

        }
    }

    public static void clickToPointViaDevice(String[] point) {
        //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
        //String[] cmd_array = {ADB_C, "shell", "input", "tap " + point[0] + " " + point[1]};
        try {
            String[] cmd_array = {"cmd", "/c", "adb", "-d", "shell", "input", "tap " + point[0] + " " + point[1]};
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = input.readLine()) != null) {
                System.out.println("line " + line);

            }
        } catch (Exception e) {

        }
    }

    public static void reset4G(JadbDevice device, boolean mode) {
        if (mode) {
            enablePlantMode(device);
            disablePlantMode(device);
        } else {
            String x = ToolSetting.getInstance().getLocationPlantMode().split(",")[0];
            String y = ToolSetting.getInstance().getLocationPlantMode().split(",")[1];
            clickToPointViaDevice(new String[]{x,y});
            SeleniumJSUtils.Sleep(2000);
            clickToPointViaDevice(new String[]{x,y});
        }
    }
    public static String reConnectRealDevice() {
        try {
          
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "cmd /c adb devices ";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    if (line.contains("device")) {
                        process.destroy();
                        return line;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                //return false;
            }
            
            //return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            //return false;
        }
        return "";
    }
    
    
    public static void clickToPointViaAdb(String[] point) {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            //String[] cmd_array = {ADB_C, "shell", "input", "tap " + point[0] + " " + point[1]};
            String[] cmd_array = {MEMU_C, "adb", "-i", "0", "\"", "shell", "input", "tap " + point[0] + " " + point[1], "\""};

            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {

                }
            } catch (Exception e) {
            }
        } catch (Exception ex) {
        }
    }

    /**
     * click trực tiếp vào có tọa độ trung bình point
     *
     * @param position
     * @param point
     */
    public static void clickToPoint(int device, String[] point) {
        if (ToolSetting.getInstance().getPre().getBoolean("sendbyClipboard", false)) {
            clickToPointViaAdb(point);
            return;
        }
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "adb execcmd -i " + device + " \"input tap " + point[0] + " " + point[1] + "\"";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("adb") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("adb")] = MEMU_C;
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {

                }
            } catch (Exception e) {
            }
        } catch (Exception ex) {
        }
    }

    /**
     * click giữ trực tiếp vào điểm có tọa độ trung bình point
     *
     * @param position
     * @param fromPoint
     */
    public static void longClickToPoint(int device, String[] fromPoint) {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "adb execcmd -i " + device + " \"input swipe " + fromPoint[0] + " " + fromPoint[1] + " " + fromPoint[0] + " " + fromPoint[1] + " 2000\"";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("adb") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("adb")] = MEMU_C;
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {

                }
            } catch (Exception e) {
            }
        } catch (Exception ex) {
        }
    }

    public static int BUTTON_LEFT = 21;
    public static int BUTTON_TAB = 61;
    public static int BUTTON_ENTER = 66;
    public static int BUTTON_DEL = 67;
    public static int BUTTON_APP_SWITCH = 187;

    /**
     * Click vào tọa độ vào điểm có tọa độ trước sau là pointStart và pointEnd
     *
     * @param position
     * @param pointStart
     * @param pointEnd
     */
    public static void clickToPoint(int device, int[] pointStart, int[] pointEnd) {
        if (ToolSetting.getInstance().getPre().getBoolean("sendbyClipboard", false)) {
            clickToPointViaAdb(device, pointStart, pointEnd);
            return;
        }
        try {
            int x = (pointStart[0] + pointEnd[0]) / 2;
            int y = (pointStart[1] + pointEnd[1]) / 2;
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "adb execcmd -i " + device + " \"input tap " + x + " " + y + "\"";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("adb") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("adb")] = MEMU_C;
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {

                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }

    public static void clickToPointViaAdb(int device, int[] pointStart, int[] pointEnd) {
        try {
            int x = (pointStart[0] + pointEnd[0]) / 2;
            int y = (pointStart[1] + pointEnd[1]) / 2;
            String[] cmd_array = {MEMU_C, "adb", "-i", "0", "\"", "shell", "input", "tap " + x + " " + y, "\""};
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {

                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }

    /**
     * chọn nút menu trong máy ảo
     *
     * @param position
     * @param keyeventNumber
     */
    public static void sendKeyEvent(int device, int keyeventNumber) {
        try {
            String[] cmd_array = {MEMU_C, "adb", "-i", "0", "\"", "shell", "input", "keyevent " + keyeventNumber, "\""};
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {

                }
            } catch (Exception e) {
            }
        } catch (Exception ex) {
        }
    }

    /**
     * lướt máy ảo từ điểm fromPoint sang toPoint
     *
     * @param device
     * @param fromPoint
     * @param toPoint
     */
    public static void swipeToPoint(int device, String[] fromPoint, String toPoint[]) {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String[] cmd_array = {MEMU_C, "adb", "-i", "0", "\"", "shell", " input swipe " + fromPoint[0] + " " + fromPoint[1] + " " + toPoint[0] + " " + toPoint[1], "\""};
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {

                }
            } catch (Exception e) {
            }
        } catch (Exception ex) {
        }
    }

    /**
     * kiểm tra xem thông báo có thông báo có tên notifiName
     *
     * @param device
     * @param notifiName
     * @return
     */
    public static String checkNotificationExist(int device, String notifiName) {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;

            String[] cmd_array = {MEMU_C, "adb", "-i", "0", "\"", "shell", "dumpsys notification | grep ticker | cut -d= -f2", "\""};
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    if (line.equalsIgnoreCase("quit")) {
                        process.destroy();
                        return "Check notification lỗi " + line;
                    }
                    if (line.contains(notifiName)) {
                        process.destroy();
                        return "";
                    }
                }
                return "Check notification lỗi " + line;
            } catch (Exception e) {
                return "Check notification lỗi Exception";
            }
        } catch (Exception ex) {
            return "Check notification lỗi Exception";
        }
    }

    /**
     * kiểm tra xem màn hình hiện tại có phải màn hình có tên activityString
     *
     * @param device
     * @param activityString
     * @return
     */
    public static String checkActivityExist(int device, String activityString) {
        try {
            if (ToolSetting.getInstance().getPre().getBoolean("sendbyClipboard", false)) {
                return checkActivityExistViaAdb(activityString);
            }
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "adb execcmd -i " + device + " \"dumpsys window windows | grep -E 'mCurrentFocus|mFocusedApp'\"";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("adb") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("adb")] = MEMU_C;
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    if (line.equalsIgnoreCase("quit")) {
                        process.destroy();
                        return "Check activity lỗi " + line;
                    }
                    if (line.contains(activityString)) {
                        process.destroy();
                        return "";
                    }
                }
                return "Check activity lỗi " + line;
            } catch (Exception e) {
                return "Check activity lỗi Exception";
            }
        } catch (Exception ex) {
            return "Check activity lỗi Exception";
        }
    }

    public static String checkActivityExistViaAdb(String activityString) {
        try {

            String[] cmd_array = {MEMU_C, "adb", "-i", "0", "\"", "shell", "dumpsys window windows | grep -E 'mCurrentFocus|mFocusedApp'", "\""};
            //String[] cmd_array = {ADB_C, "shell", "\"dumpsys window windows | grep -E 'mCurrentFocus|mFocusedApp'\""};
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    System.out.println("checkActivityExistViaAdb " + line);
                    if (line.equalsIgnoreCase("quit")) {
                        process.destroy();
                        return "Check activity lỗi " + line;
                    }
                    if (line.contains(activityString)) {
                        process.destroy();
                        return "";
                    }
                }
                return "Check activity lỗi " + line;
            } catch (Exception e) {
                return "Check activity lỗi Exception";
            }
        } catch (Exception ex) {
            return "Check activity lỗi Exception";
        }
    }

    public static String checkSettingActivity(String activityString) {
        try {

            String[] cmd_array = {MEMU_C, "adb", "-i", "0", "\"", "shell", "dumpsys window windows | grep -E 'mCurrentFocus|mFocusedApp'", "\""};
            //String[] cmd_array = {ADB_C, "shell", "\"dumpsys window windows | grep -E 'mCurrentFocus|mFocusedApp'\""};
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    System.out.println("checkSettingActivity " + line);
                    if (line.equalsIgnoreCase("quit")) {
                        process.destroy();
                        return "Check activity lỗi " + line;
                    }
                    if (line.contains(activityString)) {
                        process.destroy();
                        return "";
                    }
                }
                return "Check activity lỗi " + line;
            } catch (Exception e) {
                return "Check activity lỗi Exception";
            }
        } catch (Exception ex) {
            return "Check activity lỗi Exception";
        }
    }

    /**
     * truyền text trực tiếp ko cần copy clipboard
     *
     * @param device
     * @param text
     * @return
     */
    public static String sendText(int device, String text) {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String[] cmd_array = {MEMU_C, "input", "-i", "" + device, "\"" + text.trim() + "\""};

            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    if (line.equalsIgnoreCase("quit")) {
                        process.destroy();
                        return "Send text lỗi " + line;
                    }
                    if (line.contains("SUCCESS")) {
                        process.destroy();
                        return "";
                    }
                }
                return "Send text lỗi " + line;
            } catch (Exception e) {
                return "Send text lỗi exception";
            }
        } catch (Exception ex) {
            return "Send text lỗi exception";
        }
    }

    /**
     * click xác nhận vào google service bị lỗi
     *
     * @param timeDelayStep
     * @param arrLog
     */
    public static void checkGoogleServiceError(int timeDelayStep, ObservableList<RunLog> arrLog) {
        int error = 0;
        //MemuCUtils.checkElementExits(true, 0, "android.widget.Button#resource-id=android:id/button1").length() == 0 || 
        while (MemuCUtils.checkActivityExistViaAdb("Error: com.").length() == 0) {
            if ((error++) == 3) {
                break;
            }
            MemuCUtils.clickToPoint(0, new String[]{"786", "406"});
            //MemuCUtils.clickToPoint(0, new String[]{"640", "406"});
            RunLog.insertRunLogWarning("", "Click bỏ qua lỗi serivce", "", arrLog);
            SeleniumJSUtils.Sleep(timeDelayStep);
        }
    }

    /**
     * Lấy phần tử hiện tại có xpath
     *
     * @param xpath
     * @param position
     * @return
     */
    public static Element getElementVideoDumpFile(String xpath, int position) {
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
            File input = new File(DUMPVIEW_PATH);
            Document doc = Jsoup.parse(input, "UTF-8");
            List<Element> elements = new ArrayList<>();
            if (!xpath.contains("@")) {
                elements = doc.getElementsByAttributeValue(key, value).select("node[class=" + tag + "]");
            } else {
                elements = doc.getElementsByAttributeValueContaining(key, value).select("node[class=" + tag + "]");
            }
            if (elements.size() == 0) {
                return null;
            }
            return elements.get(position);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Lấy tọa độ trung bình của element có xpath
     *
     * @param xpath
     * @param position
     * @return
     */
    public static String[] getPointFromDumpFile(String xpath, int position) {
        try {
            String[] point = new String[2];
            Element element = getElementVideoDumpFile(xpath, position);
            if (element == null) {
                return null;
            }
            String bound = element.attr("bounds");
            String _bound = bound.replaceAll("]\\[", ",").replaceAll("\\[", "").replaceAll("]", "");
            int x1 = Integer.parseInt(_bound.split(",")[0]);
            int x2 = Integer.parseInt(_bound.split(",")[2]);
            int y1 = Integer.parseInt(_bound.split(",")[1]);
            int y2 = Integer.parseInt(_bound.split(",")[3]);
            point[0] = String.valueOf((x2 + x1) / 2);
            point[1] = String.valueOf((y2 + y1) / 2);
            return point;
        } catch (Exception e) {
            return null;
        }

    }

    public static String checkElementExits(Boolean dump, int device, String xpath) {
        try {
            if (dump) {
                String dumviewResult = dumpView(device);
                if (dumviewResult.length() != 0) {
                    return dumviewResult;
                }
            }
            String point[] = getPointFromDumpFile(xpath, 0);
            if (point == null) {
                return "Không tìm thấy element " + xpath;
            }
            return "";
        } catch (Exception e) {
            return "Không thể click";
        }
    }

    /**
     *
     * @param dump nếu biến này bằng true thì mới dumb lại view
     * @param device là thứ tự máy ảo
     * @param xpath xpath của element
     * @return
     */
    public static String clickComponantWithXpathViaDump(Boolean dump, int device, String xpath) {
        try {
            if (dump) {
                String dumviewResult = dumpView(device);
                if (dumviewResult.length() != 0) {
                    System.out.println(dumviewResult);
                    return dumviewResult;
                }
            }
            String point[] = getPointFromDumpFile(xpath, 0);
            if (point == null) {
                System.out.println("Không tìm thấy element " + xpath);
                return "Không tìm thấy element " + xpath;
            }
            clickToPoint(device, point);
            return "";
        } catch (Exception e) {
            return "Không thể click";
        }
    }

    /**
     * Click giữ im vào element
     *
     * @param dump nếu biến này bằng true thì mới dumb lại view
     * @param device là thứ tự máy ảo
     * @param xpath xpath của element
     * @return
     */
    public static String longclickComponantWithXpathViaDump(Boolean dump, int device, String xpath) {
        try {
            if (dump) {
                String dumviewResult = dumpView(device);
                if (dumviewResult.length() != 0) {
                    return dumviewResult;
                }
            }
            String point[] = getPointFromDumpFile(xpath, 0);
            if (point == null) {
                return "Không tìm thấy element " + xpath;
            }
            longClickToPoint(device, point);
            return "";
        } catch (Exception e) {
            return "Không thể click";
        }
    }

    /**
     * Click vào phần tử có xpath và chờ xem activity mới có hiện ra với timeout
     *
     * @param back : true thì phải mỗi lần chạy lại phải nhấn back
     * @param device
     * @param xpath
     * @param activity
     * @param timeOut
     * @return
     */
    public static String clickComponantWithXpathAndWaitAcViaDumpWithLoop(Boolean back, int device, String xpath, String activity, int timeOut, int delayAfterClick, ObservableList<RunLog> arrLog) {
        try {
            long start = System.currentTimeMillis();
            String checkString = "";
            boolean first = true;
            do {
                if (!first) {
                    checkGoogleServiceError(2, arrLog);
                    if (back) {
                        clickAndroidButton(device, "back");
                    }
                } else {
                    first = false;
                }
                if ((System.currentTimeMillis() - start) / 1000 > timeOut) {
                    return checkString;
                }
                if (clickComponantWithXpathViaDump(true, device, xpath).length() != 0) {
                    continue;
                }
                SeleniumJSUtils.Sleep(delayAfterClick);
                checkString = checkActivityExist(device, activity);
                SeleniumJSUtils.Sleep(1000);
            } while (checkString.length() != 0);
            return "";
        } catch (Exception e) {
            return "Không thể click element " + xpath;
        }
    }

    /**
     * Click vào phần tử có xpath và chờ xem phần tử mới vói nextXpath có hiện
     * ra với timeout
     *
     * @param back : true thì phải mỗi lần chạy lại phải nhấn back
     * @param device
     * @param xpath
     * @param activity
     * @param timeOut
     * @return
     */
    public static String clickComponantWithXpathAndWaitNextXpathViaDumpWithLoop(Boolean back, int device, String xpath, String nextXpath, int timeOut, int delayAfterClick, ObservableList<RunLog> arrLog) {
        try {
            long start = System.currentTimeMillis();
            Element nextElement = null;
            Boolean first = true;
            do {
                if (!first) {
                    checkGoogleServiceError(2, arrLog);
                    if (back) {
                        clickAndroidButton(device, "back");
                    }
                } else {
                    first = false;
                }
                if ((System.currentTimeMillis() - start) / 1000 > timeOut) {
                    return "Không đổi sang " + nextXpath;
                }
                if (clickComponantWithXpathViaDump(true, device, xpath).length() != 0) {
                    continue;
                }
                SeleniumJSUtils.Sleep(delayAfterClick);
                String dumviewResult = dumpView(device);
                if (dumviewResult.length() != 0) {
                    continue;
                }
                nextElement = getElementVideoDumpFile(nextXpath, 0);
                SeleniumJSUtils.Sleep(1000);
            } while (nextElement == null);
            return "";
        } catch (Exception e) {
            return "Không thể click element " + xpath;
        }
    }

    /**
     * Click vào phần tử có tọa độ pointStart,pointEnd và chờ xem activitymới
     * vói activityName có hiện ra với timeout
     *
     * @param back : true thì phải mỗi lần chạy lại phải nhấn back
     * @param device
     * @param pointStart
     * @param pointEnd
     * @param activityName
     * @param timeOut
     * @return
     */
    public static String clickComponantAndWaitAcByPoint(Boolean back, int device, int[] pointStart, int[] pointEnd, String activityName, int timeOut, int delayAfterClick, ObservableList<RunLog> arrLog) {
        long start = System.currentTimeMillis();
        long startRun = System.currentTimeMillis();
        String getActivityResult = "";
        Boolean first = true;
        do {
            if ((System.currentTimeMillis() - startRun) / 1000 > timeOut) {
                return "Không đổi sang " + activityName;
            }
            if (!first) {
                if (checkActivityExist(0, "com.microvirt.launcher.Launcher").length() == 0) {
                    RunLog.insertRunLogWarning("", "Đã trở về màn hình chính", "", arrLog);
                    return "Đã trở về màn hình chính";
                }
                start = System.currentTimeMillis();
                checkGoogleServiceError(2, arrLog);
                RunLog.insertRunLogWarning("", "[" + ((System.currentTimeMillis() - start) / 1000) + "]Đã check lỗi google service ", "", arrLog);
                if (back) {
                    clickAndroidButton(device, "back");
                }
            } else {
                first = false;
            }
            start = System.currentTimeMillis();
            clickToPoint(device, pointStart, pointEnd);
            RunLog.insertRunLogWarning("", "[" + ((System.currentTimeMillis() - start) / 1000) + "]Đã click ", "", arrLog);
            SeleniumJSUtils.Sleep(delayAfterClick * 1000);

            /*
            start = System.currentTimeMillis();
            String dumviewResult = dumpView(device);
            if (dumviewResult.length() != 0) {
                RunLog.insertRunLog("", "Không dump được view", "", arrLog);
                continue;
            }
            RunLog.insertRunLogWarning("", "[" + ((System.currentTimeMillis() - start) / 1000) + "]Đã dump view ", "", arrLog);
             */
            start = System.currentTimeMillis();
            getActivityResult = checkActivityExist(device, activityName);
            RunLog.insertRunLogWarning("", "[" + ((System.currentTimeMillis() - start) / 1000) + "]Đã check activity ", "", arrLog);
            //SeleniumJSUtils.Sleep(1000);
        } while (getActivityResult.length() != 0);
        return "";
    }

    /**
     * Click vào phần tử có tọa độ pointStart,pointEnd và chờ xem activitymới
     * vói activityName có hiện ra với timeout
     *
     * @param back : true thì phải mỗi lần chạy lại phải nhấn back
     * @param device
     * @param pointStart
     * @param pointEnd
     * @param nextXpath
     * @param timeOut
     * @return
     */
    public static String clickComponantAndWaitNextXpathByPoint(Boolean back, int device, int[] pointStart, int[] pointEnd, String nextXpath, int timeOut, int delayAfterClick, ObservableList<RunLog> arrLog) {
        long start = System.currentTimeMillis();
        Element nextElement = null;
        Boolean first = true;
        do {
            if (!first) {
                checkGoogleServiceError(2, arrLog);
                if (back) {
                    clickAndroidButton(device, "back");
                }
            } else {
                first = false;
            }
            if ((System.currentTimeMillis() - start) / 1000 > timeOut) {
                return "Không đổi sang " + nextXpath;
            }
            clickToPoint(device, pointStart, pointEnd);
            SeleniumJSUtils.Sleep(delayAfterClick);
            String dumviewResult = dumpView(device);
            if (dumviewResult.length() != 0) {
                continue;
            }
            nextElement = getElementVideoDumpFile(nextXpath, 0);
            SeleniumJSUtils.Sleep(1000);
        } while (nextElement == null);
        return "";
    }

    /**
     * get text của phần tử hiện tại với xpath có timeout
     *
     * @param dump
     * @param device
     * @param xpath
     * @param timeOut
     * @return
     */
    public static String getTextOfElement(Boolean dump, int device, String xpath, int timeOut) {
        long start = System.currentTimeMillis();
        Element thisElement = null;
        do {
            if ((System.currentTimeMillis() - start) / 1000 > timeOut) {
                return "";
            }
            if (dump) {
                String dumviewResult = dumpView(device);
                if (dumviewResult.length() != 0) {
                    continue;
                }
            }
            thisElement = getElementVideoDumpFile(xpath, 0);
            SeleniumJSUtils.Sleep(1000);
        } while (thisElement == null);
        try {
            return thisElement.attr("text");
        } catch (Exception e) {
            return "";
        }
    }

    public static void clickBackAndWaitAcExits(int device, String activityName, ObservableList<RunLog> arrLog) {
        int error = 0;
        do {
            if ((error++) == 3) {
                break;
            }
            MemuCUtils.clickAndroidButton(device, "back");
            RunLog.insertRunLogSuccess("", "Đã bấm quay lại", "", arrLog);
        } while (MemuCUtils.checkElementExits(true, device, activityName).length() == 0);
    }

    /**
     * chuyển text vào phần tử có xpath với timeout
     *
     * @param device
     * @param xpath
     * @param text
     * @param timeOut
     * @return
     */
    public static String sendKeyToElementByXpath(int device, String xpath, String text, int timeOut, ObservableList<RunLog> arrLog) {
        long start = System.currentTimeMillis();
        Element nextElement = null;
        boolean first = true;

        long startT = System.currentTimeMillis();
        do {
            if (!first) {
                checkGoogleServiceError(2, arrLog);
            }
            first = false;
            if ((System.currentTimeMillis() - start) / 1000 > timeOut) {
                return "Send text quá thời gian ";
            }
            startT = System.currentTimeMillis();
            if (clickComponantWithXpathViaDump(true, device, xpath).length() != 0) {
                continue;
            }
            RunLog.insertRunLogWarning("", "[" + ((System.currentTimeMillis() - startT) / 1000) + "]Đã click", "", arrLog);
            startT = System.currentTimeMillis();
            sendText(device, text);
            RunLog.insertRunLogWarning("", "[" + ((System.currentTimeMillis() - startT) / 1000) + "]Đã send text", "", arrLog);
            startT = System.currentTimeMillis();
            String textOfElement = getTextOfElement(true, device, xpath, timeOut);
            RunLog.insertRunLogWarning("", "[" + ((System.currentTimeMillis() - startT) / 1000) + "]text check " + textOfElement, "", arrLog);
            if (!textOfElement.contains(text)) {
                return "Không thể send key ";
            }
            SeleniumJSUtils.Sleep(1000);
            return "";
        } while (nextElement == null);
        return "";
    }

    public static String sendKeyToElementByXpathNonRecheck(int device, String xpath, String text, int timeOut) {
        long start = System.currentTimeMillis();
        Element nextElement = null;
        do {
            if ((System.currentTimeMillis() - start) / 1000 > timeOut) {
                return "Không thể send key ";
            }
            if (clickComponantWithXpathViaDump(true, device, xpath).length() != 0) {
                continue;
            }
            sendText(device, text);
            SeleniumJSUtils.Sleep(1000);
            return "";
        } while (nextElement == null);
        return "";
    }

    public static String setClipBoard(int device, String text) {
        try {
            String[] cmd_array = {MEMU_C, "execcmd", "-i", "" + device, "\"", "am", "broadcast", "-a", "clipper.set", "-e", "text", "'" + text + "'", "\""};
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    if (line.equalsIgnoreCase("quit")) {
                        process.destroy();
                        return "Send ClipBoard lỗi " + line;
                    }
                    if (line.contains("copied into")) {
                        process.destroy();
                        return "";
                    }
                }
                return "Send ClipBoard lỗi " + line;
            } catch (Exception e) {
                return "Send ClipBoard lỗi exception";
            }
        } catch (Exception ex) {
            return "Set ClipBoard lỗi exception";
        }
    }

    public static String killADB() {
        try {
            String[] cmd_array = {ADB_C, "kill-server"};
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    System.out.println("killADB " + line);
                }
                return "";
            } catch (Exception e) {
                return "Chưa sẵn sàng exception";
            }
        } catch (Exception ex) {
            return "Chưa sẵn sàng exception";
        }
    }

    public static String startADB() {
        try {
            String[] cmd_array = {ADB_C, "start-server"};
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    System.out.println("startADB " + line);
                }
                return "";
            } catch (Exception e) {
                return "Chưa sẵn sàng exception";
            }
        } catch (Exception ex) {
            return "Chưa sẵn sàng exception";
        }
    }

    public static String checkDeviceReady(int device) {
        try {
            String[] cmd_array = {MEMU_C, "adb", "-i", "" + device, "\"", "shell", "getprop", "sys.boot_completed", "\""};
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    System.out.println("checkDeviceReady " + line);
                    if (line.contains("ERROR")) {
                        process.destroy();
                        return "Chưa sẵn sàng";
                    }
                    if (line.contains("already")) {
                        process.destroy();
                        return "";
                    }
                }
                return "Chưa sẵn sàng";
            } catch (Exception e) {
                return "Chưa sẵn sàng exception";
            }
        } catch (Exception ex) {
            return "Chưa sẵn sàng exception";
        }
    }

    public static String sendKeyToElementByXpathViaClipBoard(int device, String xpath, String text, int timeOut) {
        long start = System.currentTimeMillis();
        Element nextElement = null;
        do {
            if ((System.currentTimeMillis() - start) / 1000 > timeOut) {
                return "Không thể send key ";
            }
            if (clickComponantWithXpathViaDump(true, timeOut, xpath).length() != 0) {
                continue;
            }
            if (setClipBoard(device, text).length() != 0) {
                continue;
            }
            sendText(device, "aaaaaaaaaa");
            longclickComponantWithXpathViaDump(false, device, xpath);
            clickToPoint(device, new String[]{"1225", "58"});
            if (!getTextOfElement(true, device, xpath, timeOut).contains(text)) {
                return "Không thể send key ";
            }
            SeleniumJSUtils.Sleep(1000);
            return "";
        } while (nextElement == null);
        return "";

    }

//
//    public static int getSizeOfElements(JadbDevice device, String xpath, int timeOut) {
//        try {
//            int time = 0;
//            String tag = xpath.split("#")[0];
//            String xa = xpath.split("#")[1];
//            String key = "";
//            String value = "";
//            if (!xpath.contains("@")) {
//                key = xa.split("=")[0];
//                value = xa.split("=")[1];
//            } else {
//                key = xa.split("@")[0];
//                value = xa.split("@")[1];
//            }
//            Element currentElement = null;
//            while (currentElement == null) {
//                if (time == timeOut) {
//                    return 0;
//                }
//                time++;
//                if (!dumpView(device)) {
//                    continue;
//                }
//                File input = new File(DUMPVIEW_PATH);
//                Document doc = Jsoup.parse(input, "UTF-8");
//                //doc.get
//                Elements elements = null;
//                if (!xpath.contains("@")) {
//                    elements = doc.getElementsByAttributeValue(key, value).select("node[class=" + tag + "]");
//                } else {
//                    elements = doc.getElementsByAttributeValueContaining(key, value).select("node[class=" + tag + "]");
//                }
//                if (elements.size() == 0) {
//                    //System.out.println("can't fount element " + xpath);
//                    continue;
//                }
//                return elements.size();
//            }
//            return 0;
//        } catch (Exception e) {
//            return 0;
//        }
//    }
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

    public static void openApp(int device, String appPackage) {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "adb startapp -i " + device + " " + appPackage + " ";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("adb") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("adb")] = MEMU_C;
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {

                }
            } catch (Exception e) {
            }
        } catch (Exception ex) {
        }
    }

    public static void stopApp(int device, String appPackage) {
        try {
            //String cmd = "cmd /c adb kill-server && adb start-server && adb connect " + deviceIP;
            String cmd = "adb stopapp -i " + device + " " + appPackage + " ";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("adb") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("adb")] = MEMU_C;
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {

                }
            } catch (Exception e) {
            }
        } catch (Exception ex) {
        }
    }

    public static void openNotification(int device) {
        try {
            String[] cmd_array = {MEMU_C, "adb", "-i", "" + device, "\"", "shell", "service call statusbar 1", "\""};
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {

                }
            } catch (Exception e) {
            }
        } catch (Exception ex) {
        }

    }

}
