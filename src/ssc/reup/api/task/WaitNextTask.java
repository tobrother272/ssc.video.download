/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.task;

import javafx.concurrent.Task;
import ssc.automation.selennium.SeleniumJSUtils;

/**
 *
 * @author magictool
 */
public class WaitNextTask extends Task<Boolean>{
    private int time;

    public WaitNextTask(int time) {
        this.time = time;
    }
    @Override
    protected Boolean call() throws Exception {
        try {
            for (int i = 1; i <= time; i++) {
                SeleniumJSUtils.Sleep(1000);
                updateMessage(i+"/"+time);
            }
        } catch (Exception e) {
        }
        return true;
    }
    
}
