/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import com.jfoenix.controls.JFXCheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author PC
 */
public class SSCCheckBox {

    private JFXCheckBox checkbox;

    public JFXCheckBox getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(JFXCheckBox checkbox) {
        this.checkbox = checkbox;
    }

    public SSCCheckBox(String text,HBox form,int child,String userData) {
        VBox vb = new VBox();
        vb.getStyleClass().setAll("formGroupItem");
        Label lb = new Label("");
        lb.getStyleClass().setAll("labelFor");
        checkbox = new JFXCheckBox(text);
        checkbox.getStyleClass().add("my-checbox");
        checkbox.setPrefHeight(30);
        checkbox.setUserData(userData);
        checkbox.setPrefWidth(form.getPrefWidth()/child);
        vb.getChildren().add(lb);
        vb.getChildren().add(checkbox);
        form.getChildren().add(vb);
    }

    public SSCCheckBox(String text,VBox form,String userData) {
        VBox vb = new VBox();
        vb.getStyleClass().setAll("formGroupItem");
        checkbox = new JFXCheckBox(text);
        checkbox.setPrefHeight(30);
        checkbox.setUserData(userData);
        checkbox.getStyleClass().add("my-checbox");
        vb.getChildren().add(checkbox);
        form.getChildren().add(vb);
    }

    public boolean isSelected() {
        return checkbox.isSelected();
    }

    public void setSelected(Boolean seBoolean) {
        checkbox.setSelected(seBoolean);
    }

    public void requestFocus() {
        checkbox.requestFocus();
    }

}
