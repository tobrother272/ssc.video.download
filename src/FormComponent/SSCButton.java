/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author PC
 */
public class SSCButton {

    private Button button;

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
    
    public SSCButton(String hintText, HBox form) {
        
        form.getChildren().add(button);
    }

    public SSCButton(String hintText, VBox form) {
        button = new Button(hintText);
        button.setPrefHeight(form.getPrefHeight()-10);
        button.setPrefWidth(form.getPrefHeight()-10);
        button.getStyleClass().setAll("btnSubmit");
        form.getChildren().add(button);
    }

}
