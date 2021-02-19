/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.video;

import java.io.File;
import java.util.Random;
import ssc.task.TaskModel;

/**
 *
 * @author PC
 */
public abstract class MyTableItemTask extends TaskModel {

    public MyTableItemTask() {
        folder=System.getProperty("user.dir")
                    + File.separator
                    + "temps"
                    + File.separator
                    + System.currentTimeMillis()+(new Random().nextInt(1000));
    }


    private String folder;

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
    
    public boolean zipVideo(){
        try {
            
        } catch (Exception e) {
        }
        return false;      
    }
    
    
    
}
