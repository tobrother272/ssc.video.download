/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent.View;

import javafx.concurrent.Task;

/**
 *
 * @author PC
 */
public abstract class StopRunningTask extends Task<Boolean>{
    public abstract boolean mainThread();
    @Override
    protected Boolean call() {
        return mainThread();
    }
    public void start(){
        Thread t=new Thread(this);
        t.setDaemon(true);
        t.start();
    }
    
}
