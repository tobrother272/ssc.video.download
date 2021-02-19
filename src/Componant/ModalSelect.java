/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componant;

import java.util.Arrays;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author magictool
 */
public abstract class ModalSelect {

    private AnchorPane apContainer;
    private boolean effect = true;

    public abstract void confirmPrimary();
    public abstract void confirmSeconary();
    
    public ModalSelect(AnchorPane apContainer, Boolean effect) {
        this.apContainer = apContainer;
        this.effect = effect;
    }

    public void show(String title,String confirm1,String confirm2) {
        AnchorPane apRoot = new AnchorPane();
        apRoot.getStyleClass().setAll("dialog");
        apRoot.setPrefWidth(apContainer.getPrefWidth() / 3);
        apRoot.setLayoutX((apContainer.getPrefWidth()-(apContainer.getPrefWidth() / 3))/2);
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
        MyButton btConfirm1 = new MyButton(confirm1);
        btConfirm1.initView(apRoot.getPrefWidth()-20, 30,10, 70, Arrays.asList("btn","btn-primary"), apRoot);
        MyButton btConfirm2 = new MyButton(confirm2);
        btConfirm2.initView(apRoot.getPrefWidth()-20, 30,10, 110, Arrays.asList("btn","btn-primary"), apRoot);
        MyButton btExit = new MyButton("Thoát");
        btExit.initView(apRoot.getPrefWidth()-20, 30,10, 150, Arrays.asList("btn","btn-danger"), apRoot);
        btConfirm1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               apContainer.getChildren().remove(apRoot);
                confirmPrimary();
            }
        });
        btConfirm2.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
               confirmSeconary();
               apContainer.getChildren().remove(apRoot);
            }
        });
        btExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               apContainer.getChildren().remove(apRoot);
            }
        });
        apContainer.getChildren().add(apRoot);
    }
}
