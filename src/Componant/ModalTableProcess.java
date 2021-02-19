/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componant;

import java.util.Arrays;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import log.RunLog;

/**
 *
 * @author magictool
 */
public abstract class ModalTableProcess {

    private AnchorPane apContainer;
    private boolean effect = true;

    public abstract void finishToDo();

    public ModalTableProcess(AnchorPane apContainer, Boolean effect,ObservableList<RunLog> arrLog) {
        this.apContainer = apContainer;
        this.effect = effect;
        this.arrLog=arrLog;
    
        
        
    }
    private Thread t;
    private ObservableList<RunLog> arrLog;
    public void show(String title, Task task, Boolean autoClose) {
        AnchorPane apRoot = new AnchorPane();
        apRoot.getStyleClass().setAll("dialog");
        apRoot.setPrefWidth(apContainer.getPrefWidth() / 3);
        apRoot.setLayoutX((apContainer.getPrefWidth() - (apContainer.getPrefWidth() / 3)) / 2);
        MyLabel lbHeader = new MyLabel(title, apRoot.getPrefWidth() - 20, 30, 10, 20, "lb-header", apRoot);
        apRoot.setLayoutY(100);
        if (effect) {
            apRoot.setLayoutY(0);
            TranslateTransition tt = new TranslateTransition(Duration.millis(200), apRoot);
            tt.setFromY(0);
            tt.setToY(100);
            tt.setCycleCount(1);
            tt.setAutoReverse(false);
            tt.play();
        }
        TableView tvLog = new TableView(arrLog);
        tvLog.getStyleClass().setAll("table-view-video-log");
        tvLog.setPrefSize(apRoot.getPrefWidth()-20, 400);
        tvLog.setLayoutX(10);
        tvLog.setLayoutY(lbHeader.getLayoutY()+lbHeader.getPrefHeight()+10);
        apRoot.getChildren().add(tvLog);
        RunLog.initTable(tvLog, arrLog);
        
        
        apRoot.setLayoutY(100);
        ProgressBar bpProgress = new ProgressBar();
        bpProgress.prefWidth((apRoot.getPrefWidth() - 30)/2);
        bpProgress.prefHeight(30);
        bpProgress.setLayoutY(tvLog.getLayoutY()+tvLog.getPrefHeight()+10);
        bpProgress.setLayoutX((apRoot.getPrefWidth() - 30)/2+20);
        apRoot.getChildren().add(bpProgress);
        MyButton btExit = new MyButton("Thoát");
        bpProgress.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(new EventHandler() {
            @Override
            public void handle(Event event) {
                bpProgress.progressProperty().bind(task.progressProperty());
                btExit.setDisable(false);
                finishToDo();
                if (autoClose) {
                    apContainer.getChildren().remove(apRoot);
                }
            }
        });
        task.setOnFailed(new EventHandler() {
            @Override
            public void handle(Event event) {
                bpProgress.progressProperty().bind(task.progressProperty());
                btExit.setDisable(false);
                finishToDo();
                if (autoClose){
                    apContainer.getChildren().remove(apRoot);
                }
            }
        });
        t = new Thread(task);
        t.setDaemon(true);
        t.start();
        btExit.setDisable(true);
        btExit.initView((apRoot.getPrefWidth() - 30) / 2, 30, 10, tvLog.getLayoutY()+tvLog.getPrefHeight()+10, Arrays.asList("btn","btn-xs","btn-danger"), apRoot);
        btExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                apContainer.getChildren().remove(apRoot);
                if (t != null && t.isAlive()) {
                    t.stop();
                }
            }
        });
        apContainer.getChildren().add(apRoot);
    }
}
