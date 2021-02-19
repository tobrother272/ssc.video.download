/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import Setting.ToolSetting;

/**
 *
 * @author simpl
 */
public class CommandLineUtils {
    public static boolean disableInterface(String interfacename) {
        //
        try {
            String cmd = "cmd /c netsh interface set interface \""+interfacename+"\" disable";
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
    public static boolean enableInterface(String interfacename) {
        //
        try {
            String cmd = "cmd /c netsh interface set interface \""+interfacename+"\" enable && netsh interface ipv4 set address name=\""+interfacename+"\" source=dhcp ";
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
    
    
    public static boolean changeIpHMA() {
        //
        try {

            List<String> arrCmd =new ArrayList<>();
            arrCmd.addAll(Arrays.asList(new String[]{
                "cmd",
                "/c",
                ToolSetting.getInstance().getPre().get("txtHMAInstallFolder", "")+"\\bin\\vpn.exe",
                "-changeip",
            }));
            ProcessBuilder pb = new ProcessBuilder(arrCmd);
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
    public static boolean disconnectHMA() {
        //
        try {

            List<String> arrCmd =new ArrayList<>();
            arrCmd.addAll(Arrays.asList(new String[]{
                "cmd",
                "/c",
                ToolSetting.getInstance().getPre().get("txtHMAInstallFolder", "")+"\\bin\\vpn.exe",
                "-disconnect",
            }));
            ProcessBuilder pb = new ProcessBuilder(arrCmd);
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
    public static boolean connectHMA() {
        //
        try {

            List<String> arrCmd =new ArrayList<>();
            arrCmd.addAll(Arrays.asList(new String[]{
                "cmd",
                "/c",
                ToolSetting.getInstance().getPre().get("txtHMAInstallFolder", "")+"\\bin\\vpn.exe",
                "-connect",
            }));
            ProcessBuilder pb = new ProcessBuilder(arrCmd);
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
    
   public static String getComputerName() {
        String hostname = "Unknown";
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
            System.out.println("Hostname can not be resolved");
        }
        return hostname;
    }

    public static boolean changeLocalIP(String local_ip) {
        //
        /*
        try {
            Preferences pre = ToolSetting.getInstance().getPre();
            String dns[] = (pre.get(TextConstants.ResetIpSetting.SETTING_DNS_SERVER, "8.8.8.8-8.8.4.4").length() == 0 ? "8.8.8.8-8.8.4.4" : pre.get(TextConstants.ResetIpSetting.SETTING_DNS_SERVER, "8.8.8.8-8.8.4.4")).split("-");
            String dns1 = dns[1];
            String dns0 = dns[0];
            String cmd = "cmd /c netsh interface ipv4 set address name=\"" + pre.get(TextConstants.ResetIpSetting.MODEM_INTERFACE_CARD, "Ethernet") + "\" static " + local_ip + " 255.255.255.0 192.168.1.1 && netsh interface ipv4 add dnsserver \"" + pre.get(TextConstants.ResetIpSetting.MODEM_INTERFACE_CARD, "Ethernet") + "\" " + dns0 + " index=0 && netsh interface ipv4 add dnsserver \"" + pre.get(TextConstants.ResetIpSetting.MODEM_INTERFACE_CARD, "Ethernet") + "\" " + dns0 + " index=1";
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
        */
        return true;
    }

}
