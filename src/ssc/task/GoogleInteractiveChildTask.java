/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.task;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import log.RunLog;
import ssc.automation.selennium.SeleniumJSUtils;

/**
 *
 * @author simpl
 */
public abstract class GoogleInteractiveChildTask extends Task<Boolean> {
    private GoogleInteractive parentTask;
    
    public abstract void sendResultToParent();
    public void insertErrorLog(String message) {
        RunLog.insertRunLog("", message, "", parentTask.getArrLog());
    }

    public void insertSuccessLog(String message) {
        RunLog.insertRunLogSuccess("", message, "", parentTask.getArrLog());
    }

    public void insertWarningLog(String message) {
        RunLog.insertRunLogWarning("", message, "", parentTask.getArrLog());
    }

    public void insertErrorLog(Exception message) {
        RunLog.insertRunLogSuccess("", message.getMessage(), "", parentTask.getArrLog());
    }

    public void bindChildToParent() {
        this.setOnSucceeded(new EventHandler() {
            @Override
            public void handle(Event event) {
                parentTask.setChildTaskRunning(false);
                sendResultToParent();
            }
        });
        this.setOnFailed(new EventHandler() {
            @Override
            public void handle(Event event) {
                parentTask.setChildTaskRunning(false);
                sendResultToParent();
            }
        });
        this.setOnCancelled(new EventHandler() {
            @Override
            public void handle(Event event) {
                parentTask.setChildTaskRunning(false);
                sendResultToParent();
            }
        });
    }

    public GoogleInteractive getParentTask() {
        return parentTask;
    }

    public void updateMyMessage(String message) {
        if (parentTask != null) {
            parentTask.updateMyMessage(message);
        } else {
            updateMessage(message);
        }
    }
    

    public GoogleInteractiveChildTask(GoogleInteractive parentTask) {
        this.parentTask = parentTask;
    }
    public abstract String setActionName();
    public String actionName() {
        return setActionName();
    }
    public void wait(String message, int time) {
        for (int i = time; i >= 0; i--) {
            SeleniumJSUtils.Sleep(1000);
            updateMyMessage("Chờ " + message + " " + i + " s");
        }
    }
}
