/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.task;

import java.util.prefs.Preferences;
import javafx.concurrent.Task;
import Setting.ToolSetting;
import static Utils.Constants.PRE.LAST_UPLOAD_TIME;
import static Utils.Constants.PRE.TIME_LEFT;
import ssc.automation.selennium.SeleniumJSUtils;
import Utils.StringUtil;

/**
 *
 * @author inet
 */
public class TimeReadyTask extends Task<Boolean> {
    Preferences pre;
    private long last_time;
    private long set_time;
    @Override
    protected Boolean call() throws Exception {
        pre=ToolSetting.getInstance().getPre();
        last_time = pre.getLong(LAST_UPLOAD_TIME, 0);
        set_time = pre.getLong(TIME_LEFT, 0);
        long left_time = last_time + set_time * 60 * 1000 - StringUtil.getCurrentDateTimeToLong();
        while(left_time>0){
            left_time = last_time + set_time * 60 * 1000 - StringUtil.getCurrentDateTimeToLong();
            System.out.println("left_time "+left_time);
            updateMessage("[ "+left_time/1000+" s ]");
            SeleniumJSUtils.Sleep(1000);
        }
        updateMessage("[ ready ]");
        return true;
    }

    public void regetValue(){
        pre=ToolSetting.getInstance().getPre();
        last_time = pre.getLong(LAST_UPLOAD_TIME, 0);
        set_time = pre.getLong(TIME_LEFT, 0);
    }
    public long getLast_time() {
        return last_time;
    }

    public void setLast_time(long last_time) {
        this.last_time = last_time;
    }

    public long getSet_time() {
        return set_time;
    }

    public void setSet_time(long set_time) {
        this.set_time = set_time;
    }
}
