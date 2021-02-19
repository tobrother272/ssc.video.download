/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.task;

import javafx.concurrent.Task;

/**
 *
 * @author tob
 */
public abstract class MyBooleanTask extends Task<Boolean>{
    public void updateMyMessage(String message){
        updateMessage(message);
    }
    public void updateMyProgress(double in , double  all){
        updateProgress(in, all);
    }
    public abstract boolean mainCall();
    @Override
    protected Boolean call() throws Exception {
       return mainCall();
    }
    
}
