/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static Utils.Constants.DATA.FFMPEG;

/**
 *
 * @author inet
 */
public class FFMPEGUtils {
    
    public static long getDurationInString(String input_video) {
        long time = 0;
        try {
            String cmd = "cmd /c " + FFMPEG + "\\ffmpeg.exe -i input_video";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("input_video") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("input_video")] = input_video;
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start(); // Start the process.
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            Pattern timePattern = Pattern.compile("(?<=Duration: )[^,]*");
            while ((line = input.readLine()) != null) {
                Matcher match = timePattern.matcher(line);
                while (match.find()) {
                    String[] hms = match.group(0).substring(0, 8).split(":");
                    time = Long.parseLong(hms[0]) * 60 * 60 + Long.parseLong(hms[1]) * 60 + Long.parseLong(hms[2]);
                    time = time * 1000;
                    //System.err.println(time);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return time;
    }
     public static String getStringDurationInString(String input_video) {
        String time = "";
        try {
            String cmd = "cmd /c " + FFMPEG + "\\ffmpeg.exe -i input_video";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("input_video") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("input_video")] = input_video;
            }
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            Process process = pb.start(); // Start the process.
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            Pattern timePattern = Pattern.compile("(?<=Duration: )[^,]*");
            while ((line = input.readLine()) != null) {
                Matcher match = timePattern.matcher(line);
                while (match.find()) {
                    time=match.group(0);
                  
                    //System.err.println(time);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return time;
    }
    
    public static int getTBN(String input_video) {
        int bitrate = 0;
        try {
            String cmd = "cmd /c " + FFMPEG + "\\ffprobe.exe -i input_video";
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
                if (line.contains("Stream #0:0")) {
                    try {
                        bitrate= Integer.parseInt(line.substring(line.indexOf("tbr,"),line.indexOf("tbn,")).replaceAll("\\D+",""));
                    } catch (Exception e) {
                    }
                }
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bitrate;
    }   
    public static int getBitrate(String input_video) {
        int bitrate = 0;
        try {
            String cmd = "cmd /c " + FFMPEG + "\\ffprobe.exe -i input_video";
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
                if (line.contains("Stream #0:0")) {
                    try {
                        bitrate= Integer.parseInt(line.substring(line.indexOf("kb/s,"),line.indexOf("fps,")).replaceAll("\\D+",""));
                    } catch (Exception e) {
                    }
                }
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bitrate;
    }   
      public static Boolean checkAudio(String input_video) {
        try {
            String cmd = "cmd /c " + FFMPEG + "\\ffprobe.exe -i input_video";
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
                if (line.contains("Stream #0:1")) {
                   return false;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return true;
    } 
    
    
    
    
    
}
