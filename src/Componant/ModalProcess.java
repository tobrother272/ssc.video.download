/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componant;

import java.util.Arrays;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author magictool
 */
public abstract class ModalProcess {

    private AnchorPane apContainer;
    private boolean effect = true;

    public abstract void finishToDo();

    public ModalProcess(AnchorPane apContainer, Boolean effect) {
        this.apContainer = apContainer;
        this.effect = effect;
    }
    private Thread t;

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
        MyLabel lbContent = new MyLabel("", apRoot.getPrefWidth() - 20, 40, 10, 70, "lb-nav", apRoot);
        apRoot.setLayoutY(100);
        ProgressBar bpProgress = new ProgressBar();
        bpProgress.prefWidth(apRoot.getPrefWidth() - 20);
        bpProgress.prefHeight(30);
        bpProgress.setLayoutY(120);
        bpProgress.setLayoutX(10);
        apRoot.getChildren().add(bpProgress);
        MyButton btExit = new MyButton("Thoát");
        lbContent.textProperty().bind(task.messageProperty());
        bpProgress.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(new EventHandler() {
            @Override
            public void handle(Event event) {
                lbContent.textProperty().bind(task.messageProperty());
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
                lbContent.textProperty().bind(task.messageProperty());
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
        btExit.initView((apRoot.getPrefWidth() - 30) / 2, 30, 10, 160, Arrays.asList("btn","btn-xs","btn-danger"), apRoot);
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
