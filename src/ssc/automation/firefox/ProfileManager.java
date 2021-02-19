/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.automation.firefox;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import Setting.ToolSetting;

/**
 *
 * @author simpl
 */
public class ProfileManager {

    /* 
    -CreateProfile profile name -- This creates a new user profile, but won't start it right away.
    -CreateProfile "profile name profile dir" -- Same as above, but will specify a custom profile directory on top of that.
    -ProfileManager, or -P -- Opens the built-in profile manager.
    -P "profile name" -- Starts Firefox with the specified profile. Profile manager is opened if the specified profile does not exist. Works only if no other instance of Firefox is running.
    -no-remote -- Add this to the -P commands to create a new instance of the browser. This lets you run multiple profiles at the same time.
    Browser specific options

    -headless -- Start Firefox in headless mode. Requires Firefox 55 on Linux, Firefox 56 on Windows and Mac OS X.
    -new-tab URL -- loads the specified URL in a new tab in Firefox.
    -new-window URL -- loads the specified URL in a new Firefox window.
    -private -- Launches Firefox in private browsing mode. Can be used to run Firefox in private browsing mode all the time.
    -private-window -- Open a private window.
    -private-window URL -- Open the URL in a new private window. If a private browsing window is open already, open the URL in that window instead.
    -search term -- Run the search using the default Firefox search engine.
    -url URL -- Load the URL in a new tab or window. Can be run without -url, and multiple URLs separated by space can be opened using the command.
    Other options

    -safe-mode -- Starts Firefox in Safe Mode.  You may also hold down the Shift-key while opening Firefox to start the browser in Safe Mode.
    -devtools -- Start Firefox with Developer Tools loaded and open.
    -inspector URL -- Inspect the specified address in the DOM Inspector.
    -jsconsole -- Start Firefox with the Browser Console.
    -tray -- Start Firefox minimized.
     */
    /**
     *
     * @param username
     * @param profilePath
     * @return
     */
    public static String makeProfile(String username, String profilePath) {
        try {
            String[] cmd_array = {ToolSetting.getInstance().getFirefoxPath(), "-CreateProfile", "\"" + username + " " + profilePath + "\""};
            runProgress(cmd_array);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Send text lỗi exception";
        }
        return "";
    }
    public static void runProgressOpen(String[] cmd_array) {
        try {
            List<String> arr = new ArrayList<>();
            //arr.add("cmd");
            //arr.add("/c");
            arr.addAll(Arrays.asList(cmd_array));
            ProcessBuilder pb = new ProcessBuilder(arr);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            if(!process.waitFor(5, TimeUnit.SECONDS)) {
                //timeout - kill the process. 
               // process.destroy(); // consider using destroyForcibly instead
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void runProgress(String[] cmd_array) {
        try {
            List<String> arr = new ArrayList<>();
            //arr.add("cmd");
            //arr.add("/c");
            arr.addAll(Arrays.asList(cmd_array));
            ProcessBuilder pb = new ProcessBuilder(arr);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println("runProgress " + line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String openFireFox(String profilePath, String startPage, String useragent) {
        try {
            if (startPage.length() == 0) {
                String[] cmd_array = {ToolSetting.getInstance().getFirefoxPath(), "-profile", "\"" + profilePath + "\""};
                runProgressOpen(cmd_array);
            } else {
                String[] cmd_array = {ToolSetting.getInstance().getFirefoxPath(), "-profile", "\"" + profilePath + "\"", "-url", "\"" + startPage + "\""};
                runProgressOpen(cmd_array);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Send text lỗi exception";
        }
        System.out.println("xxxxxxxx");
        return "";
    }

    public static String openFireFoxNewTab(String profilePath, String startPage, String useragent) {
        try {
            if (startPage.length() == 0) {
                String[] cmd_array = {ToolSetting.getInstance().getFirefoxPath(), "-profile", "\"" + profilePath + "\""};
                runProgressOpen(cmd_array);
            } else {
                String[] cmd_array = {ToolSetting.getInstance().getFirefoxPath(), "-profile", "\"" + profilePath + "\"", "-new-tab", "\"" + startPage + "\""};
                runProgressOpen(cmd_array);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Send text lỗi exception";
        }
        return "";
    }

    public static String openFireFox() {
        try {

            String[] cmd_array = {ToolSetting.getInstance().getFirefoxPath(), "\"https://youtube.com/\""};
            runProgress(cmd_array);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Send text lỗi exception";
        }
        return "";
    }

    public static String openFireFoxWithSearch(String profilePath, String searchQuery, String useragent) {
        try {
            String prefs = profilePath + "\\prefs.js";
            if (searchQuery.length() == 0) {
                String[] cmd_array = {ToolSetting.getInstance().getFirefoxPath(), "-profile", "\"" + profilePath + "\""};
                runProgress(cmd_array);
            } else {
                String[] cmd_array = {ToolSetting.getInstance().getFirefoxPath(), "-profile", "\"" + profilePath + "\"", "-search", "\"" + searchQuery + "\""};
                runProgress(cmd_array);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Send text lỗi exception";
        }
        return "";
    }

}
