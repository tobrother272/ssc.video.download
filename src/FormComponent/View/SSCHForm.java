/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent.View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author PC
 */
public class SSCHForm {

    private HBox formElement;
    private Button submit;

    public HBox getFormElement() {
        return formElement;
    }

    public void setFormElement(HBox formElement) {
        this.formElement = formElement;
    }

    public Button getSubmit() {
        return submit;
    }

    public void setSubmit(Button submit) {
        this.submit = submit;
    }

    public SSCHForm(Scene scene, String rootID, String submitText) {
        AnchorPane anchorPane = (AnchorPane) scene.lookup("#" + rootID);
        formElement = new HBox();
        formElement.getStyleClass().setAll("formGroup");
        formElement.setPrefHeight(anchorPane.getPrefHeight());
        formElement.setLayoutX(10);

        anchorPane.getChildren().add(formElement);
        submit = new Button(submitText);
        submit.getStyleClass().setAll("btnSubmit");
        submit.setPrefHeight(anchorPane.getPrefHeight() - 10);
        submit.setPrefWidth(submit.getPrefHeight());
        submit.setWrapText(true);
        submit.setLayoutY(5);
        submit.setLayoutX(anchorPane.getPrefWidth() - (submit.getPrefWidth() + 10));
        formElement.setPrefWidth(anchorPane.getPrefWidth() - (submit.getPrefWidth() + 25));
        anchorPane.getChildren().add(submit);
    }
    public SSCHForm(Scene scene, AnchorPane anchorPane, String submitText) {
        formElement = new HBox();
        formElement.getStyleClass().setAll("formGroup");
        formElement.setPrefHeight(anchorPane.getPrefHeight());
        formElement.setLayoutX(10);

        anchorPane.getChildren().add(formElement);
        submit = new Button(submitText);
        submit.getStyleClass().setAll("btnSubmit");
        submit.setPrefHeight(anchorPane.getPrefHeight() - 10);
        submit.setPrefWidth(submit.getPrefHeight());
        submit.setWrapText(true);
        submit.setLayoutY(5);
        submit.setLayoutX(anchorPane.getPrefWidth() - (submit.getPrefWidth() + 10));
        formElement.setPrefWidth(anchorPane.getPrefWidth() - (submit.getPrefWidth() + 25));
        anchorPane.getChildren().add(submit);
    }
}
