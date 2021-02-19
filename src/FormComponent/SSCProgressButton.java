/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import com.jfoenix.controls.JFXProgressBar;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author PC
 */
public class SSCProgressButton {

    private Button button;
    private JFXProgressBar progressBar;
    private AnchorPane ap;
    
    public AnchorPane getAp() {
        return ap;
    }

    public void setAp(AnchorPane ap) {
        this.ap = ap;
    }
    
    public SSCProgressButton(String t, double w, double h, String style) {
        ap = new AnchorPane();
        ap.setPrefSize(w, h);
        button = new Button(t);
        button.setPrefSize(w,h-5);
        button.getStyleClass().setAll(style);
        progressBar = new JFXProgressBar();
        //progressBar.getStyleClass().add("load-progress-bar");
        progressBar.setVisible(false);
        progressBar.setLayoutY(h-5);
        progressBar.setProgress(-1.0f);
        progressBar.setPrefSize(w, 5);
        ap.getChildren().addAll(progressBar,button);
        progressBar.visibleProperty().bind(button.disableProperty());
    }

    public void setLayoutX(double x) {
        ap.setLayoutX(x);
    }

    public void setLayoutY(double y) {
        ap.setLayoutY(y);
    }

    public Button getButton() {
        return button;
    }

    public double getPrefHeight() {
        return ap.getPrefHeight();
    }

    public double getPrefWidth() {
        return ap.getPrefWidth();
    }

    public double getLayoutY() {
        return ap.getLayoutY();
    }

    public double getLayoutX() {
        return ap.getLayoutX();
    }

    public void setButton(Button button) {
        this.button = button;
    }

}
