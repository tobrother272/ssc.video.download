/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.socket;

import java.io.File;
import java.util.List;
import ssc.automation.selennium.SeleniumJSUtils;
import ssc.task.GoogleInteractive;

/**
 * @author PC
 */
public class ConnectRemoteTool {

    private GoogleInteractive task;
    private TaskClientConnection connection;

    public TaskClientConnection getConnection() {
        return connection;
    }

    public void setConnection(TaskClientConnection connection) {
        this.connection = connection;
    }

    public ConnectRemoteTool(GoogleInteractive task) {
        this.task = task;
    }

    public void disconnect() {
        TaskClientConnection clientR = null;
        for (TaskClientConnection client : ToolSocket.getInstance().getArrClients()) {
            if (client.getName().equals(task.getUsername().toLowerCase())) {
                clientR = client;
                break;
            }
        }
        ToolSocket.getInstance().getArrClients().remove(clientR);
        try {
            //client.close();
            connection.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Boolean connectClient() {
        int time = 30;
        int startTime = 1;
        while (startTime < time) {
            for (TaskClientConnection client : ToolSocket.getInstance().getArrClients()) {
                if (client.getName().equals(task.getUsername().toLowerCase())) {
                    connection = client;
                    return true;
                }
            }
            task.updateMyMessage("Chờ kết nối trình duyệt " + startTime + "/" + time);
            SeleniumJSUtils.Sleep(1000);
            startTime++;
        }
        return false;
    }

    public String getCurrentMessage() {
        return connection.currentMessage();
    }

    private long lastID;

    public void sendMessage(String message) {
        lastID = System.currentTimeMillis();
        connection.sendMessage(message + "lastID:" + lastID);
    }

    public boolean waitImage(String image, int time,  String message) {
        sendMessage("#WAIT#" + image + "|" + time);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        connection.setCurrentMessage("");
        while (!connection.currentMessage().startsWith("#" + lastID + "#")) {
            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            //if (currentTime >= time + 3) {
            //    return false;
            //}
            SeleniumJSUtils.Sleep(1000);
            task.updateMyMessage("Chờ " + message + " " + currentTime + "/" + (time + 3));
        }
        if (connection.currentMessage().contains("true")) {
            task.insertSuccessLog("Đã xuất hiện : " + message);
            return true;
        }
        return false;
    }

    public boolean loadUrl(String url, int time,  String message) {
        sendMessage("#LOAD#" + url);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        connection.setCurrentMessage("");

        while (!connection.currentMessage().startsWith("#" + lastID + "#")) {
            if (currentTime >= time) {
                task.insertErrorLog(message + " quá thời gian");
                return false;
            }
            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            SeleniumJSUtils.Sleep(1000);
            currentTime++;
            task.updateMyMessage("Chờ " + message + " " + currentTime + "/" + (time + 3));
        }
        if (connection.currentMessage().contains("true")) {
            task.insertSuccessLog("Đã xuất hiện : " + message);
            return true;
        }
        return false;
    }

    public int getLengthByXpath(String xpath,  String message) {
        int resutl = 0;
        try {
            resutl = Integer.parseInt(getJSValue(5, "document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotLength", " Check số lượng " + message));
        } catch (Exception e) {

        }
        return resutl;
    }

    public boolean setAttibute(String xpath, int position, String key, String value,  String message) {
        try {
            exeJS(5, "document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(" + position + ").setAttribute(\"" + key + "\",\"" + value + "\")",  "Đổi " + key + " thành " + value + " của " + message);
            task.insertSuccessLog("Đã sét giá trị " + value + " cho " + key + " của xpath " + message);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean removeClass(String xpath, int position, String className,  String message) {
        try {
            exeJS(5, "document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(" + position + ").classList.remove(\"" + className + "\")",  "Xóa class " + className + " của " + message);
            task.insertSuccessLog("Đã xóa class " + className + " xpath " + message);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean waitAndClearText(String image, int time,  String message) {
        sendMessage("#CLEAR#" + image + "|" + time);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        connection.setCurrentMessage("");
        while (!connection.currentMessage().startsWith("#" + lastID + "#")) {
            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            if (currentTime >= time + 3) {
                return false;
            }
            SeleniumJSUtils.Sleep(1000);
            task.updateMyMessage("Chờ " + message + " " + currentTime + "/" + (time + 3));
        }
        if (connection.currentMessage().contains("true")) {
            return true;
        }
        return false;
    }

    public boolean waitAndClickImage(String image, int time,  String message) {
        sendMessage("#WAITANDCLICK#" + image + "|" + time);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        connection.setCurrentMessage("");
        String currentMessage = "";
        while (!currentMessage.contains("continue")) {
            currentMessage = new String(connection.currentMessage());

            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            //if (currentTime >= (time + 3)) {
            //    task.insertErrorLog("Time out : " + new File(image).getName());
            //    return false;
            //}
            //System.out.println("cm "+currentMessage);
            if (currentMessage.contains("true " + image)) {
                //System.out.println("có hình nè "+new File(image).getName());
                task.insertSuccessLog("Đã click " + message);
                return true;
            }
            if (currentMessage.contains("false")) {
                task.insertErrorLog("Không thấy hình " + new File(image).getName());
                return false;
            }
            SeleniumJSUtils.Sleep(1000);
            task.updateMyMessage("Chờ " + message + " " + currentTime + "/" + time);
        }
        if (currentMessage.contains("true")) {
            task.insertSuccessLog("Đã click " + message);
            return true;
        }
        task.insertErrorLog("Không thấy hình " + new File(image).getName());
        return false;
    }

    public boolean waitAndDoubleClickImage(String image, int time, String message) {
        sendMessage("#WAITAND2CLICK#" + image + "|" + time);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        connection.setCurrentMessage("");
        String currentMessage = "";
        while (!connection.currentMessage().startsWith("#" + lastID + "#")) {
            currentMessage = new String(connection.currentMessage());
            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            //if (currentTime >= (time + 3)) {
            //    task.insertErrorLog("Time out : " + new File(image).getName());
            //    return false;
            //}
            // System.out.println("cm "+currentMessage);
            if (currentMessage.contains("true " + image)) {
                //System.out.println("có hình nè "+new File(image).getName());
                task.insertSuccessLog("Đã click " + message);
                return true;
            }
            if (currentMessage.contains("false")) {
                task.insertErrorLog("Không thấy hình " + new File(image).getName());
                return false;
            }
            SeleniumJSUtils.Sleep(1000);
            task.updateMyMessage("Chờ " + message + " " + currentTime + "/" + time);
        }
        if (currentMessage.contains("true")) {
            task.insertSuccessLog("Đã click " + message);
            return true;
        }
        task.insertErrorLog("Không thấy hình " + new File(image).getName());
        return false;
    }

    public boolean clickByXpathAndWaitLoad(String xpath, int time, int position,  String message) {
        return excJSWaitLoad("document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(" + position + ").click();", time,  "Chờ click " + message);
    }

    public boolean excJSWaitLoad(String script, int time,  String message) {
        sendMessage("#CLICKANDWAITLOAD#" + script + "|" + time);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        connection.setCurrentMessage("");
        String currentMessage = "";
        while (!currentMessage.startsWith("#" + lastID + "#")) {
            if (currentTime >= time) {
                task.insertErrorLog(message + " quá thời gian");
                return false;
            }
            currentMessage = new String(connection.currentMessage());
            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            SeleniumJSUtils.Sleep(1000);
            task.updateMyMessage("Chờ " + message + " " + currentTime + "/" + time);
        }
        if (currentMessage.contains("true")) {
            task.insertSuccessLog("Đã click " + message);
            return true;
        } else {
            task.insertErrorLog("Không thể click");
            return false;
        }

    }

    public String getUrl(int time,  String message) {
        sendMessage("#GETURL#get|" + time);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        connection.setCurrentMessage("");
        String currentMessage = "";
        while (!currentMessage.startsWith("#" + lastID + "#")) {
            if (currentTime >= time) {
                task.insertErrorLog(message + " quá thời gian");
                return "";
            }
            currentMessage = new String(connection.currentMessage());
            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            SeleniumJSUtils.Sleep(1000);
            task.updateMyMessage("Chờ " + message + " " + currentTime + "/" + time);
        }
        if (currentMessage.contains("false")) {
            task.insertErrorLog("Không thể lấy url");
            return "";
        } else {
            task.insertSuccessLog("Url hiện tại " + currentMessage.split("url=")[1]);
            return currentMessage.split("url=")[1];
        }
    }

    public String getJSValue(int time, String query,  String message) {
        sendMessage("#GETJS#" + query + "|" + time);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        connection.setCurrentMessage("");
        String currentMessage = "";
        while (!currentMessage.startsWith("#" + lastID + "#")) {
            if (currentTime >= time) {
                task.insertErrorLog(message + " quá thời gian");
                return "";
            }
            currentMessage = new String(connection.currentMessage());
            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            SeleniumJSUtils.Sleep(1000);
            if (message.length() != 0) {
                task.updateMyMessage("Chờ " + message + " " + currentTime + "/" + time);
            }
        }
        if (currentMessage.contains("false")) {
            task.insertErrorLog("Không thể lấy giá trị");
            return "";
        } else {
            if (currentMessage.contains("jsvalue=")) {
                if (currentMessage.endsWith("jsvalue=")) {
                    return "";
                }
                return currentMessage.split("jsvalue=")[1];
            }
        }
        return "";
    }

    public String exeJS(int time, String query,  String message) {
        sendMessage("#EXEJS#" + query + "|" + time);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        connection.setCurrentMessage("");
        String currentMessage = "";
        while (!currentMessage.startsWith("#" + lastID + "#")) {
            if (currentTime >= time) {
                task.insertErrorLog(message + " quá thời gian");
                return "";
            }
            currentMessage = new String(connection.currentMessage());
            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            SeleniumJSUtils.Sleep(1000);
            task.updateMyMessage("Chờ " + message + " " + currentTime + "/" + time);
        }
        return "";
    }

    public boolean clickAndWaitLoad(String image, int time,  String message) {
        sendMessage("#CLICKANDWAITLOAD#" + image + "|" + time);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        connection.setCurrentMessage("");
        String currentMessage = "";
        while (!connection.currentMessage().startsWith("#" + lastID + "#")) {
            if (currentTime >= time) {
                task.insertErrorLog(message + " quá thời gian");
                return false;
            }
            currentMessage = new String(connection.currentMessage());
            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            if (currentMessage.contains("true " + image)) {
                task.insertSuccessLog("Đã click " + message);
                return true;
            }
            if (currentMessage.contains("false")) {
                task.insertErrorLog("Không thấy hình " + new File(image).getName());
                return false;
            }
            SeleniumJSUtils.Sleep(1000);
            task.updateMyMessage("Chờ " + message + " " + currentTime + "/" + time);
        }
        if (currentMessage.contains("true")) {
            task.insertSuccessLog("Đã click " + message);
            return true;
        }
        task.insertErrorLog("Không thấy hình " + new File(image).getName());
        return false;
    }

    public boolean type(String xpath, String text, int position, int time,  String message) {
        sendMessage("#TYPE#" + "document.evaluate(\"" + xpath + "\", document, null, 7, null).snapshotItem(" + position + ").value=\"" + text + "\"" + "|" + time);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        connection.setCurrentMessage("");
        String currentMessage = "";
        while (!currentMessage.startsWith("#" + lastID + "#")) {
            if (currentTime >= time) {
                task.insertErrorLog(message + " quá thời gian");
                return false;
            }
            currentMessage = new String(connection.currentMessage());
            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            if (currentMessage.contains("true ")) {
                task.insertSuccessLog("Đã nhập " + text);
                return true;
            }
            if (currentMessage.contains("false")) {
                task.insertErrorLog("Không thể nhập  ");
                return false;
            }
            SeleniumJSUtils.Sleep(1000);
            task.updateMyMessage("Chờ " + message + " " + currentTime + "/" + time);
        }
        if (currentMessage.contains("true")) {
            task.insertSuccessLog("Đã click " + message);
            return true;
        }
        task.insertErrorLog("Không thể nhập ");
        return false;
    }

    public boolean waitAndClickImages(List<String> images, int time,  String message) {
        String arrImage = "";
        for (String image : images) {
            arrImage = arrImage + image + "|";
        }
        sendMessage("#WAITANDCLICK#" + arrImage + time);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        connection.setCurrentMessage("");
        while (!connection.currentMessage().startsWith("#" + lastID + "#")) {
            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            if (currentTime >= (time + 6)) {

                return false;
            }
            SeleniumJSUtils.Sleep(1000);
            task.updateMyMessage("Chờ " + message + " " + currentTime + "/" + (time + 6));
        }
        if (connection.currentMessage().contains("true")) {
            task.insertSuccessLog("Đã " + message);
            SeleniumJSUtils.Sleep(1000);
            return true;
        }
        return false;
    }

    public boolean waitAndClickImageEx(String image, int time,  String message) {
        sendMessage("#WAITANDCLICKEX#" + image + "|" + time);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        connection.setCurrentMessage("");
        while (!connection.currentMessage().startsWith("#" + lastID + "#")) {
            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            if (currentTime >= (time + 3)) {

                return false;
            }
            SeleniumJSUtils.Sleep(1000);
            task.updateMyMessage("Chờ " + message + " " + currentTime + "/" + (time + 3));
        }
        if (connection.currentMessage().contains("true")) {
            task.insertSuccessLog("Đã " + message);
            return true;
        }
        return false;
    }

    public boolean waitAndType(String image, String text, int time,  String message) {
        sendMessage("#TYPE#" + image + "#TEXT#" + text + "|" + time);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        connection.setCurrentMessage("");
        while (!connection.currentMessage().startsWith("#" + lastID + "#")) {
            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            if (currentTime >= time + 3) {
                return false;
            }
            SeleniumJSUtils.Sleep(1000);
            task.updateMyMessage("Chờ " + message + " " + currentTime + "/" + (time + 3));
        }
        if (connection.currentMessage().contains("true")) {
            task.insertSuccessLog("Đã " + message);
            return true;
        }
        return false;
    }


    public boolean typeKey(String key, double time,  String message) {
        sendMessage("#TYPEKEY#" + key + "|" + time);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        connection.setCurrentMessage("");
        String currentMessage = "";
        while (!currentMessage.contains("continue")) {
            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            currentMessage = new String(connection.currentMessage());
            //if (currentTime >= time + 3) {
            //    return false;
            //}
            SeleniumJSUtils.Sleep(1000);
            if (currentMessage.contains("true ")) {
                task.insertSuccessLog("Đã type " + message);
                return true;
            }
            task.updateMyMessage("Chờ " + message + " " + currentTime + "/" + (time + 3));
        }
        if (connection.currentMessage().contains("true")) {
            task.insertSuccessLog("Đã " + message);
            return true;
        }
        return false;
    }

    public boolean selectAll(GoogleInteractive task) {
        sendMessage("#SELECTALL#|3");
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        connection.setCurrentMessage("");
        String currentMessage = "";
        while (!currentMessage.contains("continue")) {
            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            currentMessage = new String(connection.currentMessage());
            //if (currentTime >= time + 3) {
            //    return false;
            //}
            SeleniumJSUtils.Sleep(1000);
            if (currentMessage.contains("true")) {
                task.insertSuccessLog("Đã Select All ");
                return true;
            }
            task.updateMyMessage("Chờ Select All " + currentTime + "/3");
        }
        if (connection.currentMessage().contains("true")) {
            task.insertSuccessLog("Đã Select All");
            return true;
        }
        return false;
    }

    public boolean clearSelect(GoogleInteractive task) {
        sendMessage("#CLEARSELECT#|3");
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        connection.setCurrentMessage("");
        String currentMessage = "";
        while (!currentMessage.contains("continue")) {
            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            currentMessage = new String(connection.currentMessage());
            //if (currentTime >= time + 3) {
            //    return false;
            //}
            SeleniumJSUtils.Sleep(1000);
            if (currentMessage.contains("true")) {
                task.insertSuccessLog("Đã clear Select ");
                return true;
            }
            task.updateMyMessage("Chờ clear Select " + currentTime + "/3");
        }
        if (connection.currentMessage().contains("true")) {
            task.insertSuccessLog("Đã clear Select ");
            return true;
        }
        return false;
    }

}
