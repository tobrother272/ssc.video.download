/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.task;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

/**
 *
 * @author PC
 */
public abstract class TaskModel extends Task<Boolean> {

    private long startTime;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public abstract boolean main();

    public void updateMyMessage(String message) {
        updateMessage(message);
    }

    public void updateMyTitle(String message) {
        updateTitle(message);
    }

    @Override
    protected Boolean call() throws Exception {
        startTime = System.currentTimeMillis();
        return main();
    }
    Thread t;

    public void start() {
        t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }
    public void stop() {
        try {
            if(t.isAlive()){
              t.stop();
            }
        } catch (Exception e) {
        }
    }
    public void eventListener(EventFinistTask myEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        myEvent.main();
                    }
                });
                setOnFailed(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        myEvent.main();
                    }
                });
                setOnCancelled(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        myEvent.main();
                    }
                });
            }
        });
    }
}
