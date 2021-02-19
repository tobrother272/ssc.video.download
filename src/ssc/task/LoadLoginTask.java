/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.task;

import javafx.concurrent.Task;

/**
 *
 * @author simplesolution.co
 */
public class LoadLoginTask extends Task<Boolean>{
    @Override
    protected Boolean call() {
        try {
            
        } catch (Exception e) {
        }
        return true;
    }
    public void start(){
        Thread t=new Thread(this);
        t.setDaemon(true);
        t.start();
    }
}
