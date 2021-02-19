/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Setup;

import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author magictool
 */
public class CheckInit {

    public static List<String> getListFont() {
        List<String> arr = new ArrayList<>();
        String fonts[]
                = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for (int i = 0; i < fonts.length; i++) {
            arr.add(fonts[i]);
        }
        return arr;
    }

    public static List<String> getListXpath() {
        try {
            List<String> xpathList = new ArrayList<>();
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
            String re = "";
            for (String line1 : lines) {
                re = re + line1;
            }
            String arr[] = re.split(";");
            for (String string : arr) {
                xpathList.add(string.trim());
            }
            return xpathList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static List<String> getListJAVA() {
        try {
            List<String> xpathList = new ArrayList<>();
            String cmd = "cmd /K set JAVA";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor(5, TimeUnit.SECONDS);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while (!(line = input.readLine()).equals("")) {
                if (line.length() != 0) {
                    xpathList.add(line);
                }
            }
            return xpathList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String checkADB() {
        try {
            String result = "";
            String cmd = "adb";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor(5, TimeUnit.SECONDS);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while (!(line = input.readLine()).equals("")) {
                if (line.length() != 0) {
                    System.out.println("line " + line);
                    result=result+line;
                }
            }
            return result;
        } catch (Exception ex) {
            //ex.printStackTrace();
            return "";
        }
    }

    public static List<String> getListAndroid() {
        try {
            List<String> xpathList = new ArrayList<>();
            String cmd = "cmd /K set android";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor(5, TimeUnit.SECONDS);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while (!(line = input.readLine()).equals("")) {
                if (line.length() != 0) {
                    System.out.println("line " + line);
                    xpathList.add(line);
                }
            }
            return xpathList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
