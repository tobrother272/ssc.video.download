/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import com.github.junrar.extract.ExtractArchive;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import static Utils.Constants.PATH.JDK_PATH;
import ssc.reup.api.task.MyBooleanTask;
import ssc.reup.api.task.PlashTask;
/**
 * @author inet
 */
public class MyFileUtils {

    public static void unzipfile(File rar, String dest) {
        try {
            File tmpDir = File.createTempFile("bip.", ".unrar");
            if (!(tmpDir.delete())) {
                throw new IOException("Could not delete temp file: " + tmpDir.getAbsolutePath());
            }
            if (!(tmpDir.mkdir())) {
                throw new IOException("Could not create temp directory: " + tmpDir.getAbsolutePath());
            }
            System.out.println("tmpDir=" + tmpDir.getAbsolutePath());
            ExtractArchive extractArchive = new ExtractArchive();
            extractArchive.extractArchive(rar, tmpDir);
            MyFileUtils.copyDirectory(new File(tmpDir.getAbsolutePath()), new File(dest));
            MyFileUtils.deleteFolder(new File(tmpDir.getAbsolutePath()));
            System.out.println("finished.");
        } catch (Exception e) {
        }

    }

    public static long folderSize(File directory) {
        long length = 0;
        try {
            for (File file : directory.listFiles()) {
                if (file.isFile()) {
                    length += file.length();
                } else {
                    length += folderSize(file);
                }
            }
        } catch (Exception e) {
        }

        return length;
    }
    public static void zipAllFileInFolder(List<String> arrFile, String outFile) {
        try {
            // Initiate ZipFile object with the path/name of the zip file.
            ZipFile zipFile = new ZipFile(outFile);
            // Folder to add
            // Initiate Zip Parameters which define various properties such
            // as compression method, etc.
            ZipParameters parameters = new ZipParameters();
            // set compression method to store compression
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            // Set the compression level
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            for (String file : arrFile) {
                zipFile.addFile(new File(file), parameters);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean deleteFolder(File file) {
        try {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    deleteFolder(f);
                }
            }
            file.delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteFolder(File file, PlashTask t) {
        try {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    t.updateMyMessage("Deleting " + f.getName());
                    deleteFolder(f);
                }
            }
            file.delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getFileExtension(String file) {
        try {
            return file.substring(file.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getFileName(String file) {
        try {
            return file.substring(file.lastIndexOf("\\") + 1, file.lastIndexOf("."));
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean checkDDrive() {
        try {
            File checkDFile = new File("D:\\");
            if (checkDFile.exists()) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static List<File> sortByName(File[] files) {
        List<File> results = new ArrayList<>();
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                int n1 = extractNumber(o1.getName());
                int n2 = extractNumber(o2.getName());
                return n1 - n2;
            }

            private int extractNumber(String name) {
                int i = 0;
                try {
                    int e = name.lastIndexOf('.');
                    String number = name.substring(0, e);
                    i = Integer.parseInt(number);
                } catch (Exception e) {
                    i = 0; // if filename does not match the format
                    // then default to 0
                }
                return i;
            }
        });

        for (File f : files) {
            results.add(f);
        }
        return results;
    }

    public static boolean checkFreeDrive(String drive) {
        try {
            File file = new File(drive + ":");
            if (file.getUsableSpace() / 1024 / 1024 / 1024 < 5) {
                return false;
            }
        } catch (Exception e) {
        }
        return true;
    }

    public static boolean checkJDKFolder() {
        try {
            File folderJAVA[] = new File(JDK_PATH).listFiles();
            if (folderJAVA.length == 0) {
                return false;
            }
            File jdkFolder = null;
            for (File file : folderJAVA) {
                if (file.getName().contains("jdk")) {
                    jdkFolder = file;
                }
            }
            if (!(new File(jdkFolder.getAbsolutePath() + "\\bin\\java.exe")).exists()) {
                return false;
            }
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean deleteFileInFolder(File file) {
        try {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    deleteFolder(f);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteFileInFolder(File file, PlashTask task) {
        try {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    task.updateMyMessage("Deleting " + f.getName());
                    deleteFolder(f);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean sortFilesByDate(File[] files) {
        try {
            Arrays.sort(files, new Comparator() {
                public int compare(Object o1, Object o2) {
                    if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                        return -1;
                    } else if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                        return +1;
                    } else {
                        return 0;
                    }
                }
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void copy(File sourceLocation, File targetLocation, MyBooleanTask task) throws IOException {
        if (sourceLocation.isDirectory()) {
            copyDirectory(sourceLocation, targetLocation);
        } else {
            copyFile(sourceLocation, targetLocation, task);
        }
    }

    public static void copy(File sourceLocation, File targetLocation) throws IOException {
        //&&!sourceLocation.getName().contains("firefox") khong bit de lam gi
        if (sourceLocation.isDirectory() && !sourceLocation.getName().contains("cache2")) {
            copyDirectory(sourceLocation, targetLocation);
        } else {
            copyFile(sourceLocation, targetLocation);
        }
    }


    public static void copyDirectory(File source, File target) {
        try {
            if (!target.exists()) {
                //System.out.println("tạo "+target.getAbsolutePath());
                target.mkdir();
            }
            for (String f : source.list()) {
                copy(new File(source, f), new File(target, f));
            }
        } catch (Exception e) {
        }
    }



    public static void copyDirectory(File source, File target, MyBooleanTask task) throws IOException {
        if (!target.exists()) {
            target.mkdir();
        }
        for (String f : source.list()) {
            task.updateMyMessage(f);
            copy(new File(source, f), new File(target, f), task);
        }
    }

    public static boolean copyFile(File source, File target, MyBooleanTask task) {
        try (
                InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(target)) {
            byte[] buf = new byte[1024];
            int length;
            long totalLength = 0;
            long sourceLength = source.length();
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
                totalLength = totalLength + length;
                task.updateMyMessage("Đã copy " + (totalLength) + "/" + (sourceLength) + " " + source.getAbsolutePath());
                task.updateMyProgress(totalLength, sourceLength);
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean copyFile(File source, File target) {
        try (
                InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(target)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

   
    public static boolean deleteFile(String fileurl) {
        try {
            File file = new File(fileurl);
            if (!file.exists()) {
                return true;
            }
            if (file.delete()) {
                return true;
                //System.out.println(file.getName() + " is deleted!");
            } else {
                return false;
                //System.out.println("Delete operation is failed.");
            }
        } catch (Exception e) {
            System.err.println("delete fail :" + e.getMessage());
            return false;
        }
    }

    public static int countLines(File input) throws IOException {
        try (InputStream is = new FileInputStream(input)) {
            int count = 1;
            for (int aChar = 0; aChar != -1; aChar = is.read()) {
                count += aChar == '\n' ? 1 : 0;
            }
            return count;
        } catch (Exception ex) {
            return 0;
        }
    }

    public static int getStringPixelWidth(String str, int fontSize) {
        BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        try {
            FontMetrics metrics = g.getFontMetrics(new Font("Arial Unicode MS", Font.TRUETYPE_FONT, fontSize));
            return metrics.stringWidth(str);
        } catch (Exception e) {
        } finally {
            g.dispose();
        }
        return 0;
    }

    public static int writeStringToString(String content, String path, int mode, int fontSize) {
        //System.err.println(content + " \n*******************");
        int countRow = -1;
        List<String> listContent = StringUtil.getRowInSlideByWidth(content, fontSize, mode);
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path), "UTF-8"));
            countRow = listContent.size();
            int maxlength = 0;
            int countTab = 0;
            int lengthSpace = getStringPixelWidth(" ", fontSize);
            maxlength = getStringPixelWidth(listContent.get(0), fontSize);
            for (String string : listContent) {
                if (getStringPixelWidth(string, fontSize) >= maxlength) {
                    maxlength = getStringPixelWidth(string, fontSize);
                }
            }
            for (String string : listContent) {
                if (getStringPixelWidth(string, fontSize) != maxlength) {
                    String tab = "";
                    countTab = (maxlength - getStringPixelWidth(string, fontSize)) / lengthSpace;
                    countTab = countTab < 2 ? 1 : (countTab / 2);
                    for (int i = 0; i < countTab; i++) {
                        tab = tab + " ";
                    }
                    String _string = tab + string;
                    listContent.set(listContent.indexOf(string), _string);
                }
            }
            for (String string : listContent) {
                if (!string.equals("") && string.length() != 0) {
                    writer.write(string);
                    if (listContent.indexOf(string) != listContent.size() - 1) {
                        writer.newLine();
                    }
                }
            }
            writer.close();
            return countRow;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean writeStringToFileUTF8(String content, String path) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path), "UTF-8"));
            writer.write(content);
            writer.newLine();
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean writeStringToFileUTF8OnlyLine(String content, String path) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.write(content);
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean writeStringToFile(String content, String path) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.write(content);
            writer.newLine();
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static ObservableList<String> getListFileNameFromFolder(String path) {
        ObservableList<String> optionLanguage = FXCollections.observableArrayList();
        File folder = new File(path);
        for (File file : folder.listFiles()) {
            optionLanguage.add(file.getName());
        }
        return optionLanguage;
    }

    public static boolean checkCreateAdmin(String path) {
        try {
            File file = new File("c:\\newfile.txt");
            if (file.createNewFile()) {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static boolean createFile(String path) {
        try {
            File file = new File(path);
            if (file.createNewFile()) {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static void createFolder(String uri) {
        File theDir = new File(uri);
        // if the directory does not exist, create it
        if (!theDir.exists()) {
            boolean result = false;
            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
                se.printStackTrace();
            }
            if (result) {
                //System.out.println("DIR created");  
            }
        }
    }

    public static String getStringFromFile(File filePath) {
        String result = "";
        try {
            //BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "Cp1252"));      
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim();
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getStringFromFile(File filePath, String spaceChar) {
        String result = "";
        try {
            //BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "Cp1252"));      
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim() + spaceChar;
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getStringFromFile(String filePath, String spaceChar) {
        String result = "";
        try {
            //BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "Cp1252"));      
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line + spaceChar;
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<String> getListStringFromFile(String filePath) {
        ArrayList<String> result = new ArrayList<>();
        try {
            //BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "Cp1252"));      
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
            br.close();

        } catch (IOException e) {
            //e.printStackTrace();
        }
        return result;
    }

    public static void zipFolder(String path, String outFile) {
        try {
            // Initiate ZipFile object with the path/name of the zip file.
            ZipFile zipFile = new ZipFile(outFile);
            // Folder to add
            String folderToAdd = path;
            // Initiate Zip Parameters which define various properties such
            // as compression method, etc.
            ZipParameters parameters = new ZipParameters();
            // set compression method to store compression
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            // Set the compression level
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            zipFile.addFolder(folderToAdd, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zipAllFileInFolder(String path, String outFile, MyBooleanTask cft) {
        try {
            // Initiate ZipFile object with the path/name of the zip file.
            ZipFile zipFile = new ZipFile(outFile);
            // Folder to add
            // Initiate Zip Parameters which define various properties such
            // as compression method, etc.
            ZipParameters parameters = new ZipParameters();
            // set compression method to store compression
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            // Set the compression level
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            File listp[] = new File(path).listFiles();

            for (File file : listp) {
                cft.updateMyMessage("zipping file " + file.getAbsolutePath());
                if (file.isDirectory()) {
                    zipFile.addFolder(file, parameters);
                } else {
                    zipFile.addFile(file, parameters);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean copyFileUsingStream(File source, File dest) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(MyFileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(MyFileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static boolean downloadImage(String linkDown, String path) {
        OutputStream out = null;
        InputStream in = null;
        HttpURLConnection httpcon = null;
        try {
            URL url = new URL(linkDown);
            out = new BufferedOutputStream(new FileOutputStream(path));
            httpcon = (HttpURLConnection) (url).openConnection();
            httpcon.addRequestProperty("User-Agent", Constants.USER_AGENT);
            in = httpcon.getInputStream();
            byte[] buffer = new byte[1024];
            int numRead;
            long numWritten = 0;
            while ((numRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, numRead);
                numWritten += numRead;

            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (httpcon != null) {
                    httpcon.disconnect();
                }
            } catch (IOException e) {
                return false;
            }
        }
    }

}
