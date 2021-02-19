/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componant;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import Utils.ThreadUtils;

/**
 * @author magictool
 */
public class BindTask {

    public BindTask() {

    }

    public void success() {

    }
    public void bind(Task task, AnchorPane apTaskMessage, Label lbMainTaskMessage, ProgressBar pbMainProgress, Button btClick) {
        String beforeText = btClick.getText();
        apTaskMessage.visibleProperty().bind(task.runningProperty());
        lbMainTaskMessage.textProperty().bind(task.messageProperty());
        pbMainProgress.progressProperty().bind(task.progressProperty());
        btClick.disableProperty().bind(task.runningProperty());
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                apTaskMessage.visibleProperty().unbind();
                lbMainTaskMessage.textProperty().unbind();
                pbMainProgress.progressProperty().unbind();
                btClick.disableProperty().unbind();
                btClick.setText(beforeText);
                success();
            }
        });
        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                apTaskMessage.visibleProperty().unbind();
                lbMainTaskMessage.textProperty().unbind();
                pbMainProgress.progressProperty().unbind();
                btClick.disableProperty().unbind();
                btClick.setText(beforeText);
                success();
            }
        });
        ThreadUtils.startTask(task);
    }

    public void bind(Task task, AnchorPane apTaskMessage, Label lbMainTaskMessage, ProgressBar pbMainProgress) {

        apTaskMessage.visibleProperty().bind(task.runningProperty());
        lbMainTaskMessage.textProperty().bind(task.messageProperty());
        pbMainProgress.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                apTaskMessage.visibleProperty().unbind();
                lbMainTaskMessage.textProperty().unbind();
                pbMainProgress.progressProperty().unbind();

                success();
            }
        });
        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                apTaskMessage.visibleProperty().unbind();
                lbMainTaskMessage.textProperty().unbind();
                pbMainProgress.progressProperty().unbind();

                success();
            }
        });
        ThreadUtils.startTask(task);
    }

}
