
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.task;

import java.io.File;
import java.util.prefs.Preferences;
import javafx.concurrent.Task;
import Utils.MyFileUtils;
import Setting.ToolSetting;
import static Utils.Constants.DATA.TEMP_INSERT_NEWS;
import static Utils.UIUtils.printLogToDesktop;

/**
 * @author inet
 */
public class PlashTask extends Task<Boolean> {

    Preferences pre;
    private static final String iconImageLoc = "http://icons.iconarchive.com/icons/scafer31000/bubble-circle-3/16/GameCenter-icon.png";
    private String checkUpdate;

    public String getCheckUpdate() {
        return checkUpdate;
    }

    public void setCheckUpdate(String checkUpdate) {
        this.checkUpdate = checkUpdate;
    }

    public void updateMyMessage(String message) {
        updateMessage(message);
    }

    @Override
    protected Boolean call() {
        try {
            printLogToDesktop();
            pre=ToolSetting.getInstance().getPre();
            checkUpdate = "";
            MyFileUtils.createFolder(TEMP_INSERT_NEWS);
            MyFileUtils.deleteFileInFolder(new File(TEMP_INSERT_NEWS));
            updateMessage("Hoàn thành , mở tool sau 3 s .");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            updateMessage("Error : " + e.getMessage());
            return true;
        }

    }

}
