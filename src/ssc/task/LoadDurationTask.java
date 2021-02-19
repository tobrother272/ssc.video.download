/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.task;

import javafx.concurrent.Task;
import ssc.reup.api.Utils.FFMPEGUtils;

/**
 *
 * @author PC
 */
public class LoadDurationTask extends Task<Long> {
    String path;
    public LoadDurationTask(String path) {
        this.path = path;
    }
    @Override
    protected Long call() {
        long duration = 0;
        try {
            System.out.println("aasdasdsd");
            duration = FFMPEGUtils.getDurationInString(path) / 1000;
        } catch (Exception e) {
        }
        return duration;
    }

    public void start() {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }
}
