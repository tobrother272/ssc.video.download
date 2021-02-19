/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ffmpeg;

import Setting.ToolSetting;
import static Utils.Constants.DATA.FFMPEG;
import Utils.MyFileUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static ssc.automation.selennium.SeleniumJSUtils.Sleep;
import ssc.reup.api.Utils.FFMPEGUtils;
import ssc.video.MyTableItemTask;

/**
 * @author PC
 */
public class AudioRender {

    public static String renderCutAudio(String prefixTitle,String inputMusic, String outputVideo, MyTableItemTask progress, int duration) {
        int maxDuration = (int) FFMPEGUtils.getDurationInString(inputMusic) / 1000;
        int end = 1200 + new Random().nextInt(maxDuration - 1200);
        int start = end - 1200;
        try {
            //-c copy
            //-c:a copy
            String cmd = "cmd /K " + FFMPEG + "\\ffmpeg -ss " + start + "  -i inputMusic -t 1200 -c copy  outputVideo -y";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("outputVideo") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("outputVideo")] = outputVideo;
            }
            if (Arrays.asList(cmd_array).indexOf("inputMusic") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("inputMusic")] = inputMusic;
            }
            return render(prefixTitle, duration, cmd_array, outputVideo, progress);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static String renderJoinMusic(List<String> arrMusic, String outputMusic, MyTableItemTask progress, int duration) {
        String contentJoin = new File(outputMusic).getParent() + File.separator + "join.txt";
        MyFileUtils.createFile(contentJoin);
        for (String string : arrMusic) {
            MyFileUtils.writeStringToFile("file '" + string + "'", contentJoin);
        }
        try {
            String cmd = "cmd /K " + FFMPEG + "\\ffmpeg.exe -safe 0 -f concat -i " + contentJoin + " -c copy  outputMusic -y";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("outputMusic") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("outputMusic")] = outputMusic;
            }
            return render("Nối nhạc ", duration, cmd_array, outputMusic, progress);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
      public static String renderJoinVideo(List<String> arrMusic, String outputMusic, MyTableItemTask progress, int duration) {
        String contentJoin = new File(outputMusic).getParent() + File.separator + "joinVideo.txt";
        MyFileUtils.createFile(contentJoin);
        for (String string : arrMusic) {
            MyFileUtils.writeStringToFile("file '" + string + "'", contentJoin);
        }
        try {
            String cmd = "cmd /K " + FFMPEG + "\\ffmpeg.exe -safe 0 -f concat -i " + contentJoin + " -c copy  outputMusic -y";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("outputMusic") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("outputMusic")] = outputMusic;
            }
            return render("Nối nhạc ", duration, cmd_array, outputMusic, progress);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public static String renderMusicVideo(String inputMusic, String outputVideo, String imageBackground, MyTableItemTask progress, int duration, boolean makeWave) {

        String waveMode = "showwavespic=s=1280x720:colors=cyan|aqua";
        switch (new Random().nextInt(8)) {
            case 0:
                waveMode = "showwavespic=s=1280x720:colors=cyan|aqua";
                break;
            case 1:
                waveMode = "avectorscope=s=1280x720,format=yuv420p";
                break;
            case 2:
                waveMode = "showcqt,format=yuv420p";
                break;
            case 3:
                waveMode = "showwaves=s=1280x720:mode=line,format=yuv420p";
                break;
            case 4:
                waveMode = "showwaves=s=1280x720:mode=cline,format=yuv420p";
                break;
            case 5:
                waveMode = "showfreqs=mode=line:fscale=log,format=yuv420p";
                break;
            case 6:
                waveMode = "showfreqs=mode=dot:fscale=log,format=yuv420p";
                break;
            case 7:
                waveMode = "showfreqs=mode=bar:fscale=log,format=yuv420p";
                break;
        }
        waveMode = "avectorscope=s=1280x720,format=yuv420p";

        String cmd = "";
        try {
            if (makeWave) {
                cmd = "cmd /K " + FFMPEG + "\\ffmpeg -i inputMusic -i imageBackground -filter_complex \""
                        + "[0:a]aformat=sample_fmts=fltp:sample_rates=44100:channel_layouts=stereo[a];"
                        + "[0:a]" + waveMode + "[fg];"
                        + "[1:v]scale=w=1280:h=720[bg];"
                        + "[bg][fg]overlay[video]"
                        + "\" -map [video] -ac 2 -ar 44100 -map [a] -shortest -threads 8 outputVideo -y";
            } else {
                cmd = "cmd /K " + FFMPEG + "\\ffmpeg -i imageBackground -i inputMusic -map 0:v -map 1:a -c copy -y outputVideo -y";
            }
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("inputMusic") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("inputMusic")] = inputMusic;
            }
            if (Arrays.asList(cmd_array).indexOf("imageBackground") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("imageBackground")] = imageBackground;
            }
            if (Arrays.asList(cmd_array).indexOf("outputVideo") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("outputVideo")] = outputVideo;
            }
            return render("Tạo video nhạc ", duration, cmd_array, outputVideo, progress);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    
    public static String makeVideoFromImage(String prefix,String inputImage, String outputVideo, MyTableItemTask progress){
        try {
            //-c copy
            //-c:a copy
            String cmd = "cmd /K " + FFMPEG + "\\ffmpeg -loop 1 -framerate 25 -i inputImage -pix_fmt yuv420p -c:v libx264  -s 1280x720 -preset veryfast -t 6 outputVideo -y";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("outputVideo") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("outputVideo")] = outputVideo;
            }
            if (Arrays.asList(cmd_array).indexOf("inputImage") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("inputImage")] = inputImage;
            }
            return render(prefix, 6, cmd_array, outputVideo, progress);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
     public static String loopVideo(String prefix,String inputVideo, String outputVideo,int duration,MyTableItemTask progress){
        try {
            //-c copy
            //-c:a copy
            String cmd = "cmd /K " + FFMPEG + "\\ffmpeg -stream_loop "+((duration/6)-1)+" -i inputVideo -c copy outputVideo -y";
            String cmd_array[] = cmd.split(" ");
            if (Arrays.asList(cmd_array).indexOf("outputVideo") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("outputVideo")] = outputVideo;
            }
            if (Arrays.asList(cmd_array).indexOf("inputVideo") != -1) {
                cmd_array[Arrays.asList(cmd_array).indexOf("inputVideo")] = inputVideo;
            }
            return render(prefix, duration , cmd_array, outputVideo, progress);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    private static String render(String prefix, int totalTime, String[] cmd_array, String outputFile, MyTableItemTask progress) {
        try {
            long startTime = System.currentTimeMillis();
            String renderCode = "";
            for (String string : cmd_array) {
                renderCode = renderCode + " " + string;
            }
            System.out.println("------------------ render code  ----------------");
            System.out.println(renderCode);
            System.out.println("------------------ render code  ----------------");
            ProcessBuilder pb = new ProcessBuilder(cmd_array);
            pb.redirectErrorStream(true);
            if (pb == null) {
                return "Không thể tạo process";
            }
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            String output = "";
            Pattern timePattern = Pattern.compile("(?<=time=)[^,]*");
            try {
                while (!(line = input.readLine()).equals("")) {
                    Matcher match = timePattern.matcher(line);
                    if (match.find()) {
                        String[] hms = match.group(0).substring(0, 8).split(":");
                        Integer totalSecs = Integer.parseInt(hms[0]) * 3600
                                + Integer.parseInt(hms[1]) * 60
                                + Integer.parseInt(hms[2]);
                        progress.updateMyMessage(prefix + " : " + ((int) (((double) totalSecs / (double) totalTime) * 100)) + " %");
                        //newsVideo.updateMyProgress(totalSecs, totalTime);
                        progress.updateMyTitle(line.split("speed=")[1]);
                    }
                    output = output + "\n" + line;
                    System.out.println("" + line);
                }
            } catch (Exception e) {
            }

            Sleep(3000);
            if (FFMPEGUtils.getDurationInString(outputFile) <= 0) {
                return "";
            }
            return outputFile;
        } catch (Exception e) {
            //new RenderLog(0, newsVideo.getId() + " " + prefix, "error initial", "", 0, "", 0).insertData();
        }
        return "";
    }
}
