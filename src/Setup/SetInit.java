/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Setup;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import Utils.CMDUtils;
/**
 * @author magictool
 */
public class SetInit{
       public static boolean setupXpath() {
        try {
            String jdk = (new File("C:\\Program Files\\Java")).listFiles()[0].getName();
            String cmd =
                      " SETX JAVA_HOME \"C:\\Program Files\\Java\\" + jdk + "\" /M &&"
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
    public static boolean setupEnviroment(String newValue) {
        try {
            String currentPath=CMDUtils.getCurrentPath();
            if(currentPath.length()==0){
                System.out.println("Cant get current windown path");
                return false;
            }
            System.out.println("currentPath "+currentPath);
            String cmd = "SETX /M PATH \""+currentPath.replace("Path=", "").replace("C:\\SSC\\Tool\\sdk\\platform-tools;", "").replace("C:\\SSC\\Tool\\sdk\\platform-tools", "").replace("C:\\SSC\\Tool\\sdk\\tools;", "").replace("C:\\SSC\\Tool\\sdk\\tools", "").replace("C:\\Program Files\\Java\\jdk1.8.0_161\\bin;", "").replace("C:\\Program Files\\Java\\jdk1.8.0_161\\bin", "")+";"+newValue+"\"  ";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while (input.readLine() != null && !(line = input.readLine()).equals("")) {
                    System.err.println(newValue+" setup xpath "+line);
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
    
    
    
    
  
    
    public static boolean setupRootEnviroment(String key,String newValue) {
        try {
            String cmd = "SETX "+key+" \""+newValue+"\" /M ";
            String cmd_array[] = cmd.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while (input.readLine() != null && !(line = input.readLine()).equals("")) {
                    System.err.println(key+"  "+line);
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
    
    
}
