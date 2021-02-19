/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componant;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author magictool
 */
public abstract class MainTaskCall implements EventHandler<ActionEvent> {

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    private Task task;
    private Label lbMainTaskMessage;
    private Label lbMainTaskTitle;
    private Button button;
    private ProgressBar pbMainProgress;
    private AnchorPane apTaskMessage;
    public MainTaskCall(Task task, Label lbMainTaskMessage, Button button, ProgressBar pbMainProgress, AnchorPane apTaskMessage,Label lbMainTaskTitle) {
        this.task = task;
        this.lbMainTaskMessage = lbMainTaskMessage;
        this.button = button;
        this.pbMainProgress = pbMainProgress;
        this.apTaskMessage = apTaskMessage;
        this.lbMainTaskTitle=lbMainTaskTitle;
    }
    public abstract void start();
    public void then() {

    }
    @Override
    public void handle(ActionEvent event) {
        start();
        String text=button.getText();
        lbMainTaskMessage.textProperty().bind(task.messageProperty());
        button.disableProperty().bind(task.runningProperty());
        apTaskMessage.visibleProperty().bind(task.runningProperty());
        lbMainTaskTitle.textProperty().bind(task.titleProperty());
        pbMainProgress.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                then();
                lbMainTaskMessage.textProperty().unbind();
                button.disableProperty().unbind();
                lbMainTaskTitle.textProperty().unbind();
                apTaskMessage.visibleProperty().unbind();
                pbMainProgress.progressProperty().unbind();
                button.setText(text);         
            }

        });
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }
}
