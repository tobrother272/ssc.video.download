/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Setting.ToolSetting;
import static Utils.Constants.DATA.ADB;
import static Utils.Constants.DATA.FFMPEG;
import static Utils.Constants.ERROR_FOLDER;
import static Utils.Constants.PRE.WINDOW_VERSION;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import static ssc.automation.selennium.SeleniumJSUtils.Sleep;
/**
 * @author inet
 */
public class CMDUtils {

    public static String getComputerName() {
        String hostname = "Unknown";
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (UnknownHostException ex) {
            System.out.println("Hostname can not be resolved");
        }
        return hostname;
    }
      public static boolean runCmd(String cmdQuery) {
        try {
            String cmd = "cmd /C " + cmdQuery + " ";
            String cmd_array[] = cmd.split(" ");

            for (String string : cmd_array) {
                System.out.println(" " + string);
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            String line = null;
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            try {
                while ((line = input.readLine()) != null) {
                    //System.out.println("line " + line);
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
    public static boolean checkCmdExits(String cmdQuery, String compare) {
        try {
            String cmd = "cmd /C " + cmdQuery + " ";
            String cmd_array[] = cmd.split(" ");

            for (String string : cmd_array) {
                System.out.println(" " + string);
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            String line = null;
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            try {
                while ((line = input.readLine()) != null) {
                    System.out.println("line " + line);
                    if (line.contains(compare)) {
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
    public static String getLocalIp2() {
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    if(i.getHostAddress().contains("192.168.1")){
                        return i.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";

    }
    public static String OpenMittm() {
        checkAndKillProcess("mitmweb.exe");
        String currentPath = "";
        try {
            String cmd = "cmd /C mitmweb --set block_global=false";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            if (!process.waitFor(5, TimeUnit.SECONDS)) {
                process.destroy(); // consider using destroyForcibly instead
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        //}
        return currentPath;
    }

     
    public static String getCurrentPath() {
        String currentPath = "";
        try {
            String cmd = "cmd /K set path";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor(5, TimeUnit.SECONDS);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            List<String> lines = new ArrayList<>();
            while (!(line = input.readLine()).equals("")) {
                if (line.contains("Path=")) {
                    currentPath = line;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        //}
        return currentPath;
    }

    public static String getLocalIp() {
        String hostname = "Unknown";
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostAddress();
        } catch (UnknownHostException ex) {
            System.out.println("Hostname can not be resolved");
        }
        return hostname;
    }

    public static String getConnectTionID() {
        String id = "";
        try {
            String cmd = "cmd /c netsh interface show interface ";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while (input.readLine() != null && !(line = input.readLine()).equals("")) {
                    System.out.println("line " + line);
                    if (line.contains("Connected")) {
                        id = line.split("Dedicated")[1].trim();
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }
     public static String installTiktokScraper() {
        String id = "";
        try {
            String cmd = "cmd /c npm install -g tiktok-scraper ";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while (input.readLine() != null && !(line = input.readLine()).equals("")) {
                   
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }
    public static boolean changeLocalIP(String local_ip) {
        //
        try {
            String dns[] = "8.8.8.8-8.8.4.4".split("-");
            String cmd = "cmd /c netsh interface ipv4 set address name=\"" + ToolSetting.getInstance().getEthernetName() + "\" static " + local_ip + " 255.255.255.0 192.168.1.1 && netsh interface ipv4 add dnsserver \"" + ToolSetting.getInstance().getEthernetName() + "\" " + dns[1] + " index=0";
            System.out.println("change local cmd " + cmd);
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            if (!process.waitFor(3, TimeUnit.SECONDS)) {
                process.destroy(); // consider using destroyForcibly instead
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static Boolean checkUserSuccess(String user_name, String user_password) {

        HttpURLConnection conn = null;
        try {
            String query = "http://portal.simplesolution.co/backend/index.php/UserController/checkLoginProduct";
            URL url = new URL(query);
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("user_name", user_name);
            params.put("user_password", user_password);
            params.put("user_log_computername", CMDUtils.getComputerName());
            params.put("product_code", "lUjXr");
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String result = "";
            for (int c; (c = in.read()) >= 0;) {
                result = result + ((char) c);
            }
            String r = result.replaceAll("\"", "");
            MyFileUtils.writeStringToFile(StringUtil.getCurrentDateTime("dd/MM/YY hh:mm:ss") + " " + r, System.getProperty("user.dir") + "\\loginLog.txt");
            if (result.contains("false")) {
                return false;
            } else {
                ToolSetting.getInstance().getPre().putInt("user_id", Integer.parseInt(r.split("###")[0].trim()));
                ToolSetting.getInstance().getPre().put("product_expired_date", r.split("###")[1]);
                ToolSetting.getInstance().getPre().put("admin_name", user_name);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("500 for URL")) {
                MyFileUtils.writeStringToFile(StringUtil.getCurrentDateTime("dd/MM/YY hh:mm:ss") + " " + "Không thể lấy kết quả", System.getProperty("user.dir") + "\\loginLog.txt");
            } else {
                MyFileUtils.writeStringToFile(StringUtil.getCurrentDateTime("dd/MM/YY hh:mm:ss") + " " + "Không thể connect server", System.getProperty("user.dir") + "\\loginLog.txt");
            }
        } finally {
            conn.disconnect();
        }
        return false;
    }

    public static boolean setupXpath() {
        try {
            String jdk = (new File("C:\\Program Files\\Java")).listFiles()[0].getName();
            String cmd = "cmd /C"
                    + " SETX JAVA_HOME \"C:\\Program Files\\Java\\" + jdk + "\" /M &&"
                    + " SETX ANDROID_HOME \"C:\\SSC\\YtbSystem\\Tool\\sdk\" /M &&"
                    + " SETX JAVA_TOOL_OPTIONS \"-Dfile.encoding=UTF8\" /M &&"
                    + " SETX /M PATH \"%PATH%;%ANDROID_HOME%\\tools;%ANDROID_HOME%\\platform-tools;%JAVA_HOME%\\bin\"  ";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while (input.readLine() != null && !(line = input.readLine()).equals("")) {
                    System.err.println(line);
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void clearProfile(String profileName) {
        String folderName = "";
        Preferences prefs;
        try {
            File file = new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Mozilla\\Firefox\\profiles.ini");
            prefs = new IniPreferences(new Ini(file));
            folderName = prefs.node(profileName).get("Path", null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        File profileFolder = new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Mozilla\\Firefox\\" + folderName);
        for (File file : profileFolder.listFiles()) {
            if (!file.getName().equals("prefs.js")) {
                if (file.isDirectory()) {
                    try {
                        FileUtils.deleteDirectory(file);
                    } catch (IOException localIOException1) {
                    }
                } else {
                    file.delete();
                }
            } else {
                try {
                    FileOutputStream writer = new FileOutputStream(file);
                    writer.write("".getBytes());
                    writer.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static String replaceProfile(String profileName, String profilePath) {
        String folderName = "";
        Preferences prefs;
        try {
            File file = new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Mozilla\\Firefox\\profiles.ini");
            prefs = new IniPreferences(new Ini(file));
            folderName = prefs.node(profileName).get("Path", null);
            String folder = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Mozilla\\Firefox\\" + folderName;
            MyFileUtils.deleteFolder(new File(folder));
            CMDUtils.extractZip(profilePath, folder);
            return folder;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static Boolean extractZip(String source, String destination) {
        try {
            ZipFile zipFile = new ZipFile(source);
            zipFile.extractAll(destination);
            return true;
        } catch (ZipException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String[] getDetailMedia(String input_video) {
        String[] result_array = new String[3];
        String result = "";
        try {
            String cmd = "cmd /c " + FFMPEG + "\\ffprobe.exe -v quiet -print_format json -show_format -show_streams input_video";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("input_video") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("input_video")] = input_video;
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start(); // Start the process.
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = input.readLine()) != null) {
                result = result + line;
            }
            JSONObject json = (JSONObject) new JSONParser().parse(result);
            JSONArray json2 = (JSONArray) new JSONParser().parse(json.get("streams").toString());
            JSONObject json3 = (JSONObject) new JSONParser().parse(json2.get(0).toString());
            JSONObject json4 = (JSONObject) new JSONParser().parse(json2.get(1).toString());
            result_array[0] = "1280";
            result_array[1] = "720";
            result_array[2] = "48000";
            result_array[0] = json3.get("width").toString();
            result_array[1] = json3.get("height").toString();
            result_array[2] = json4.get("sample_rate").toString();
        } catch (Exception ex) {

        }
        return result_array;
    }

    public static Boolean extractFile(String file, String extract) {
        try {
            String cmd = "cmd /c winrar x file extract";
            String cmd_array[] = cmd.split(" ");
            for (int i = 0; i < cmd_array.length; i++) {
                if (cmd_array[i].equals("file")) {
                    cmd_array[i] = file;
                }
                if (cmd_array[i].equals("extract")) {
                    cmd_array[i] = extract;
                }
                if (cmd_array[i].equals("winrar")) {
                    cmd_array[i] = "C:\\Program Files\\WinRAR\\UnRAR";
                }
            }
            for (String string : cmd_array) {
                System.out.print(" " + string);
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();
            Sleep(2000);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while (input.readLine() != null && !(line = input.readLine()).equals("")) {
                    if (line.contains("has been terminated")) {
                        process.destroy();
                    }
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
            return true;
        } catch (Exception ex) {
            //ex.printStackTrace();
            return false;
        }
    }

    public static Boolean checkAndKillProcess(String processName) {
        try {
            String cmd = "cmd /c Taskkill /IM " + processName + " /F ";
            String cmd_array[] = cmd.split(" ");
            for (int i = 0; i < cmd_array.length; i++) {
                if (cmd_array[i].equals("processName")) {
                    cmd_array[i] = processName;
                }
            }
            for (String string : cmd_array) {
                System.out.print(" " + string);
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();
            Sleep(2000);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while (input.readLine() != null && !(line = input.readLine()).equals("")) {
                    System.out.println("line " + line);
                    if (line.contains("has been terminated")) {
                        process.destroy();
                    }
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
            return true;
        } catch (Exception ex) {
            //ex.printStackTrace();
            return false;
        }
    }

    public static Boolean runningMSI() {
        try {
            String cmd = "cmd /c TASKLIST /FI \"imagename eq msiexec.exe\" /svc";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();
            Sleep(2000);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            int countLine = 0;
            try {
                while (input.readLine() != null && !(line = input.readLine()).equals("")) {
                    if (line.contains("msiexec.exe")) {
                        countLine++;
                    }
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
            if (countLine > 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean checkSDK() {
        //for (int j = 0; j < 3; j++) {
        try {
            String cmd = "cmd /K java -version";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor(5, TimeUnit.SECONDS);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            List<String> lines = new ArrayList<>();
            while (!(line = input.readLine()).equals("")) {
                lines.add(line);
            }
            for (String line1 : lines) {
                if (line1.contains("java version")) {
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        //}
        return false;
    }

    public static boolean checkPATH() {
        try {
            String cmd = "cmd /K set path";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor(5, TimeUnit.SECONDS);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            List<String> lines = new ArrayList<>();
            while (!(line = input.readLine()).equals("")) {
                lines.add(line);
            }
            for (String line1 : lines) {
                if (line1.contains("sdk") && line1.contains("Java\\jdk")) {
                    System.out.println("line1 " + line1);
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        //}
        return false;
    }

    public static boolean checkADB() {
        try {
            String cmd = "cmd /K adb";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor(5, TimeUnit.SECONDS);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            List<String> lines = new ArrayList<>();
            while (!(line = input.readLine()).equals("")) {
                lines.add(line);
            }
            for (String line1 : lines) {
                if (line1.contains("Debug Bridge version")) {
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        //}
        return false;
    }

    public static boolean runMSI(String processName) {
        try {
            String cmd = "cmd /c msiExec.exe -i " + processName;
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while (true) {
                line = input.readLine();
                if (line == null) {
                    process.destroy();
                    break;
                }
                if (line.contains("No tasks")) {
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean checkProcessNotRunning(String processName) {
        try {
            String cmd = "cmd /c TASKLIST /FI \"IMAGENAME eq " + processName + "\" ";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while (true) {
                line = input.readLine();
                if (line == null) {
                    process.destroy();
                    break;
                }
                if (line.contains("No tasks")) {
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public static boolean dumpView(String filePath) {
        try {
            String cmd = "cmd /c adb shell uiautomator dump && adb pull /sdcard/window_dump.xml filePath ";
            String cmd_array[] = cmd.split(" ");
            for (int i = 0; i < cmd_array.length; i++) {
                if (cmd_array[i].equals("filePath")) {
                    cmd_array[i] = filePath;
                }
                if (cmd_array[i].equals("adb")) {
                    cmd_array[i] = ERROR_FOLDER + "\\" + ADB;
                }
            }
            System.out.print("cmd dumpView");
            for (String string : cmd_array) {
                System.out.print(string + " ");
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    System.err.println("line dumpView " + line);
                    if (line.contains("dumped to")) {
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

    public static boolean checkPackageInstalled(String packageName) {
        try {
            String cmd = "cmd /c adb shell \"pm list packages\" ";
            String cmd_array[] = cmd.split(" ");

            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while (input.readLine() != null && !(line = input.readLine()).equals("")) {
                    System.out.println("line " + line);
                    if (line.equals(packageName)) {
                        process.destroy();
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

    public static boolean swipeData(String _package) {
        //
        try {
            String cmd = "cmd /c adb shell pm clear " + _package;
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            if (!process.waitFor(3, TimeUnit.SECONDS)) {
                process.destroy(); // consider using destroyForcibly instead
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void chmodSSCChanger() {
        //adb shell "su -c 'chmod 777 /data/data/offer.ssc.sharepreferencestest/shared_prefs/config.xml'"
        //adb push ‪C:/Users/inet/Desktop/shared_prefs /data/data/offer.ssc.changer/shared_prefs
        try {
            String cmd = "cmd /c start cmd.exe /K \" adb shell \"su -c 'chmod 777 /data/data/offer.ssc.changer'\" \" ";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            if (!process.waitFor(10, TimeUnit.SECONDS)) {
                process.destroy(); // consider using destroyForcibly instead
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getFirefoxVersion() {
        try {
            String path = "";
            if (ToolSetting.getInstance().getPre().getInt(WINDOW_VERSION, 0) == 64) {
                path = "C:\\Program Files (x86)\\Mozilla Firefox";
            } else {
                path = "C:\\Program Files\\Mozilla Firefox";
            }
            String cmd = "cmd /C cd path && firefox -v | more ";
            String cmd_array[] = cmd.split(" ");
            for (int i = 0; i < cmd_array.length; i++) {
                if (cmd_array[i].equals("firefox")) {
                    cmd_array[i] = "firefox";
                }
                if (cmd_array[i].equals("path")) {
                    cmd_array[i] = path;
                }
            }
            for (String string : cmd_array) {
                System.out.print(" " + string);
            }

            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            String line = null;
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while (input.readLine() != null && !(line = input.readLine()).equals("")) {
                    System.out.println("results " + line);
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
            return "";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static boolean createProfileAndOpenFirefox(String path, String profile_name) {
        //
        try {
            if (ToolSetting.getInstance().getPre().getInt(WINDOW_VERSION, 0) == 64) {
                path = "C:\\Program Files (x86)\\Mozilla Firefox";
            } else {
                path = "C:\\Program Files\\Mozilla Firefox";
            }
            String cmd = "cmd /C cd path &&"
                    + " firefox -CreateProfile " + profile_name + " &&"
                    + " firefox -P \"" + profile_name + "\" ";
            String cmd_array[] = cmd.split(" ");
            for (int i = 0; i < cmd_array.length; i++) {
                if (cmd_array[i].equals("firefox")) {
                    cmd_array[i] = "firefox.exe";
                }
                if (cmd_array[i].equals("path")) {
                    cmd_array[i] = path;
                }
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            if (!process.waitFor(5, TimeUnit.SECONDS)) {
                process.destroy(); // consider using destroyForcibly instead
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean openFirefox() {
        //
        try {
            String path = "";
            if (ToolSetting.getInstance().getPre().getInt(WINDOW_VERSION, 0) == 64) {
                path = "C:\\Program Files (x86)\\Mozilla Firefox";
            } else {
                path = "C:\\Program Files\\Mozilla Firefox";
            }
            String cmd = "cmd /C cd path && firefox ";
            String cmd_array[] = cmd.split(" ");
            for (int i = 0; i < cmd_array.length; i++) {
                if (cmd_array[i].equals("firefox")) {
                    cmd_array[i] = "firefox.exe";
                }
                if (cmd_array[i].equals("path")) {
                    cmd_array[i] = path;
                }
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            if (!process.waitFor(5, TimeUnit.SECONDS)) {
                process.destroy(); // consider using destroyForcibly instead
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static int checkWindowVersion() {
        try {
            String cmd = "cmd /C wmic os get osarchitecture";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start(); // Start the process.
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    if (line.contains("64-bit")) {
                        return 64;
                    }
                }
            } catch (Exception e) {
            }
            return 32;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 32;
        }
    }

    public static boolean checkWindow7OSVersion() {
        try {
            String cmd = "cmd /C ver";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start(); // Start the process.
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = input.readLine()) != null) {
                    if (line.contains("Version 6")) {
                        return true;
                    }
                }
            } catch (Exception e) {
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static String openAndroidApp(String app_package) {
        String getADBDeviceName = "";
        try {
            //String cmd = "cmd /c start cmd.exe /C \"adb shell am start -n " + app_package + "\"";
            String cmd = "cmd /c adb shell am start -n " + app_package;
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            if (!process.waitFor(2, TimeUnit.SECONDS)) {
                process.destroy(); // consider using destroyForcibly instead
            }
            return getADBDeviceName;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return getADBDeviceName;
    }

    public static boolean runTask(String bluestack_path) {
        try {
            Runtime runTime = Runtime.getRuntime();
            runTime.exec(bluestack_path);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public static boolean openAnotherApp(String appPath) {
        try {
            File file = new File(appPath);
            Desktop.getDesktop().open(file);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public static boolean runTask(String bluestack_path, int timeout) {
        try {
            Runtime runTime = Runtime.getRuntime();
            runTime.exec(bluestack_path);
            //while (isRunning(BLUESTACK_PROCESSRUNING)) {
            //    Sleep(3000);
            //}
            Sleep(1000 * timeout);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public static void restartADB(String port) {
        try {
            String cmd = "cmd /c adb kill-server && adb start-server ";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            if (!process.waitFor(5, TimeUnit.SECONDS)) {
                process.destroy(); // consider using destroyForcibly instead
            }
            Sleep(5000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
