/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import static ij.plugin.FFT.fileName;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import ssc.reup.api.task.PlashTask;
import ssc.task.TaskModel;

/**
 *
 * @author inet
 */
public class DownloadManagerUtils {
    public static String downloadFile(String linkDown, String path, PlashTask cloneVideoTask, int fileSize, String fileName) {
        OutputStream out = null;
        InputStream in = null;
        HttpURLConnection httpcon = null;
        BufferedImage image = null;
        try {
            URL url = new URL(linkDown);
            out = new BufferedOutputStream(new FileOutputStream(path));
            httpcon = (HttpURLConnection) (url).openConnection();
            httpcon.setConnectTimeout(5000);
            httpcon.setReadTimeout(5000);
            httpcon.addRequestProperty("User-Agent", Constants.USER_AGENT);
            in = httpcon.getInputStream();
            byte[] buffer = new byte[1024];
            int numRead;
            long numWritten = 0;
            while ((numRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, numRead);
                numWritten += numRead;
                cloneVideoTask.updateMyMessage(fileName + " " + (((int) (numWritten / 1024 / 1024) + "/" + (fileSize / 1024 / 1024))));
                //cloneVideoTask.updateMyProgress((int) (numWritten / 1024 / 1024), total);
            }
            long mbFileDown = new File(path).length() / 1024;
            long mbFileSize = fileSize / 1024;
            if (mbFileDown + 100 < mbFileSize) {
                return fileName + " Downloaded " + mbFileDown + "/" + mbFileSize;
            }
            return "";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return fileName + " " + e.getMessage();
        } catch (MalformedURLException e) {
            return fileName + " " + e.getMessage();
        } catch (IOException e) {
            return fileName + " " + e.getMessage();
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
                if (image != null) {
                    //image.re 
                }
            } catch (IOException e) {
                //error = true;
                e.printStackTrace();
            }
        }
    }
    
    public static String downloadFile(String linkDown, String path, TaskModel streamVideo) {
        OutputStream out = null;
        InputStream in = null;
        HttpURLConnection httpcon = null;
        BufferedImage image = null;
        try {
            URL url = new URL(linkDown);
            out = new BufferedOutputStream(new FileOutputStream(path));
            httpcon = (HttpURLConnection) (url).openConnection();
            httpcon.setConnectTimeout(5000);
            httpcon.setReadTimeout(5000);
            httpcon.addRequestProperty("User-Agent", Constants.USER_AGENT);
            in = httpcon.getInputStream();
            byte[] buffer = new byte[1024];
            int numRead;
            long numWritten = 0;
            while ((numRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, numRead);
                numWritten += numRead;
                streamVideo.updateMyMessage("Video Downloading "+ (numWritten / 1024 / 1024) + " Mb " );
                //cloneVideoTask.updateMyProgress((int) (numWritten / 1024 / 1024), total);
            }
            long mbFileDown = new File(path).length() / 1024;
           
            return "";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return fileName + " " + e.getMessage();
        } catch (MalformedURLException e) {
            return fileName + " " + e.getMessage();
        } catch (IOException e) {
            return fileName + " " + e.getMessage();
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
                if (image != null) {
                    //image.re 
                }
            } catch (IOException e) {
                //error = true;
                e.printStackTrace();
            }
        }
    }
    
    
    public static String downloadFile(String linkDown, String path, TaskModel cloneVideoTask, int fileSize, String fileName) {
        OutputStream out = null;
        InputStream in = null;
        HttpURLConnection httpcon = null;
        BufferedImage image = null;
        try {
            URL url = new URL(linkDown);
            out = new BufferedOutputStream(new FileOutputStream(path));
            httpcon = (HttpURLConnection) (url).openConnection();
            httpcon.setConnectTimeout(5000);
            httpcon.setReadTimeout(5000);
            httpcon.addRequestProperty("User-Agent", Constants.USER_AGENT);
            in = httpcon.getInputStream();
            byte[] buffer = new byte[1024];
            int numRead;
            long numWritten = 0;
            while ((numRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, numRead);
                numWritten += numRead;
                cloneVideoTask.updateMyMessage(fileName + " " + (((int) (numWritten / 1024 / 1024) + "/" + (fileSize / 1024 / 1024))));
                //cloneVideoTask.updateMyProgress((int) (numWritten / 1024 / 1024), total);
            }
            long mbFileDown = new File(path).length() / 1024;
            long mbFileSize = fileSize / 1024;
            if (mbFileDown + 100 < mbFileSize) {
                return fileName + " Downloaded " + mbFileDown + "/" + mbFileSize;
            }
            return "";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return fileName + " " + e.getMessage();
        } catch (MalformedURLException e) {
            return fileName + " " + e.getMessage();
        } catch (IOException e) {
            return fileName + " " + e.getMessage();
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
                if (image != null) {
                    //image.re 
                }
            } catch (IOException e) {
                //error = true;
                e.printStackTrace();
            }
        }
    }

    public static BufferedImage getImageFromUrl() {
        try {

        } catch (Exception e) {
        }
        return null;
    }
    public static Boolean downloadFile(String linkDown, String path, String prefix_message) {
        OutputStream out = null;
        InputStream in = null;
        HttpURLConnection httpcon = null;
        BufferedImage image = null;

        try {
            URL url = new URL(linkDown);
            out = new BufferedOutputStream(new FileOutputStream(path));
            httpcon = (HttpURLConnection) (url).openConnection();
            httpcon.setConnectTimeout(5000);
            httpcon.setReadTimeout(5000);
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
            //SSCLog.insertSSCLog("", "Download thumb error", linkDown + "\n" + e + "\n" + image.getWidth() + "\n" + path, arrLog);
            return false;
        } catch (IOException e) {
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
                if (image != null) {
                    //image.re 
                }
            } catch (IOException e) {
                //error = true;
                e.printStackTrace();
            }
        }
    }

    public static void saveImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        String fileName = url.getFile();
        String destName = "C:\\Users\\magictool\\Desktop\\image.jpg";
        System.out.println(destName);

        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destName);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

    public static String getStringFromArrayByIndex(String in_string, int index, String split_char) {
        String result = "";
        String search_keyword[] = in_string.split(split_char);
        if (in_string.length() == 0) {
            return "";
        }
        if (index == search_keyword.length - 1) {
            return in_string;
        }
        if (index == 0) {
            return "";
        }
        for (int i = 0; i < index; i++) {
            result = result + " " + search_keyword[i];
        }
        return result;

    }


}
