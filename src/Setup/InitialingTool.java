/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Setup;

import static Utils.Constants.DATA.RESOURCE;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import static Utils.Constants.DATA.RESOURCE_DATA;
import static Utils.Constants.DATA.RESOURCE_TOOL;
import static Utils.Constants.DATA.SSC;
import static Utils.Constants.DATA.TEMP_INSERT_NEWS;
import Utils.DownloadManagerUtils;
import Utils.MyFileUtils;
import ssc.reup.api.task.PlashTask;

/**
 * @author simplesolution.co
 */
public class InitialingTool {
    public static List<String> getListFile() {
        List<String> listFile = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://ytb.simplesolution.co/backend/index.php/" + MyFileUtils.getStringFromFile(new File("updateController.txt")) + "/getListFile").get();
            Elements links = doc.select("a[href]");
            for (Element src : links) {
                listFile.add(src.attr("href") + "---" + src.text().split("---")[1]);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return listFile;
    }
    public static String checkResource(PlashTask plashTask) {
        try {
            plashTask.updateMyMessage("Initialing ...");
            MyFileUtils.createFolder(SSC);
            MyFileUtils.createFolder(TEMP_INSERT_NEWS);
            MyFileUtils.createFolder(RESOURCE_DATA);
            MyFileUtils.createFolder(RESOURCE);
            MyFileUtils.createFolder(RESOURCE+"\\Data");
            
            
            MyFileUtils.createFolder(RESOURCE_TOOL);
            List<String> listFile = getListFile();
            for (String string : listFile) {
                String downloadStatus = checkAndDownloadFile(RESOURCE_DATA, string, plashTask);
                if (downloadStatus.length() != 0) {
                    return downloadStatus;
                }
            }
           if (!new File(TEMP_INSERT_NEWS).exists()) {
                MyFileUtils.createFolder(TEMP_INSERT_NEWS);
                if (new File(TEMP_INSERT_NEWS).listFiles().length != 0) {
                    MyFileUtils.deleteFileInFolder(new File(TEMP_INSERT_NEWS));
                }
            }
        } catch (Exception e) {
        }
        if (isUpdate()) {
            return "update";
        }
        return "";
    }

    public static Boolean isUpdate() {
        Preferences pre = Preferences.userRoot().node(MyFileUtils.getStringFromFile(new File(System.getProperty("user.dir") + "\\package.txt")));
        try {
            Document doc = Jsoup.connect("http://ytb.simplesolution.co/backend/index.php/" + MyFileUtils.getStringFromFile(new File(System.getProperty("user.dir") + "\\updateController.txt")) + "/getListFileUpdate/run").get();
            Elements links = doc.select("a[href]");
            for (Element src : links) {
                if (pre.get("LastUpdate", "").length() == 0) {
                    pre.put("LastUpdate", src.text().split("---")[2]);
                    return false;
                } else {
                    if (src.text().split("---")[2].equals(pre.get("LastUpdate", ""))) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return false;
    }

    public static String checkAndDownloadFile(String folder, String fileURl, PlashTask plashTask) {
        try {
            String downloadURl = fileURl.split("---")[0];
            int fileSize = Integer.parseInt(fileURl.split("---")[1]);
            String fileName = FilenameUtils.getName(new URL(fileURl.split("---")[0]).getPath());
            String saveURl = folder + "\\" + fileName;
            if (!new File(saveURl).exists()) {
                return DownloadManagerUtils.downloadFile(downloadURl, saveURl, plashTask, fileSize, fileName);
            } else {
                if (((new File(saveURl).length() / 1024) + 100) < fileSize / 1024) {
                    return DownloadManagerUtils.downloadFile(downloadURl, saveURl, plashTask, fileSize, fileName);
                }
            }
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public static List<String> getListFile(String folder) {
        List<String> listFile = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://ytb.simplesolution.co/backend/index.php/" + MyFileUtils.getStringFromFile(new File(System.getProperty("user.dir") + "\\updateController.txt")) + "/getListFile/" + folder).get();
            Elements links = doc.select("a[href]");
            for (Element src : links) {
                listFile.add(src.attr("href") + "---" + src.text().split("---")[1]);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return listFile;
    }

}
