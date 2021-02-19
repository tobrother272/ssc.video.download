/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent.View;

import FormComponent.SSCProgressButton;
import Setting.ToolSetting;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.awt.Desktop;
import java.net.URI;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.CheckComboBox;

/**
 *
 * @author PC
 */
public class SSCLoginForm {

    private VBox formElement;
    private SSCProgressButton submit;
    public VBox getFormElement() {
        return formElement;
    }

    public void setFormElement(VBox formElement) {
        this.formElement = formElement;
    }

    public Button getSubmit() {
        return submit.getButton();
    }


    public SSCLoginForm(Scene scene, String rootID, String submitText) {
        anchorPane = (AnchorPane) scene.lookup("#" + rootID);

        Label title = new Label("Đăng nhập");
        title.getStyleClass().setAll("labelLogin");
        title.setPrefSize(anchorPane.getPrefWidth(), 40);
        title.setLayoutX(0);
        anchorPane.getChildren().add(title);
        anchorPane.setOpacity(0);
        anchorPane.setLayoutY(-200);
        formElement = new VBox();
        formElement.getStyleClass().setAll("formGroup");
        formElement.setPrefHeight(anchorPane.getPrefHeight());
        formElement.setLayoutX(0);
        formElement.setLayoutX(0);
        formElement.setLayoutY(60);
        anchorPane.getChildren().add(formElement);

        submit = new SSCProgressButton(submitText,anchorPane.getPrefWidth() - 40,35,"btnSubmit");
        submit.setLayoutY(anchorPane.getPrefHeight() - 15);
        submit.setLayoutX(20);
        formElement.setPrefWidth(anchorPane.getPrefWidth());
        formElement.setPrefHeight(anchorPane.getPrefHeight() - 100);

        Button btnReloadData = new Button("Đăng ký");
        btnReloadData.getStyleClass().setAll("btnReload");
        btnReloadData.setPrefHeight(30);
        btnReloadData.setPrefWidth(anchorPane.getPrefWidth() - 40);
        //btnReloadData.getStyleClass().setAll("btn-dialog-reload");
        btnReloadData.setLayoutY(submit.getLayoutY() + submit.getPrefHeight() + 20);
        btnReloadData.setLayoutX(20);

        btnReloadData.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().browse(new URI("http://portal.simplesolution.co/#/Login"));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        anchorPane.getChildren().add(submit.getAp());
        anchorPane.getChildren().add(btnReloadData);
        show();
    }
    private AnchorPane anchorPane;
    private Button btnReloadData;

    public void removeSecondaryButton() {
        anchorPane.getChildren().add(btnReloadData);
    }

    public void loadOld() {
        for (Node object : getFormElement().getChildren()) {
            Node child = ((VBox) object).getChildren().get(1);
            if (child instanceof TextField) {
                ((TextField) child).setText(ToolSetting.getInstance().getPre().get(child.getUserData().toString(), ""));
            } else if (child instanceof PasswordField) {
                ((PasswordField) child).setText(ToolSetting.getInstance().getPre().get(child.getUserData().toString(), ""));
            } else if (child instanceof JFXComboBox) {
                ((JFXComboBox) child).getSelectionModel().select(ToolSetting.getInstance().getPre().get(child.getUserData().toString(), ""));
            } else if (child instanceof JFXCheckBox) {
                ((JFXCheckBox) child).setSelected(ToolSetting.getInstance().getPre().getBoolean(child.getUserData().toString(), false));
            } else if (child instanceof CheckComboBox) {

            }
        }
    }

    public void show() {
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(1000), anchorPane);
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(1.0);
        fadeInTransition.setDelay(Duration.millis(1000));
        fadeInTransition.play();

        fadeInTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadOld();
            }
        });
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), anchorPane);
        tt.setByY(-200);
        tt.setDelay(Duration.millis(1000));
        tt.setToY(250);
        tt.play();
    }

    public void saveData() {
        for (Node object : getFormElement().getChildren()) {
            Node child = ((VBox) object).getChildren().get(1);
            if (child instanceof TextField) {
                ToolSetting.getInstance().getPre().put(child.getUserData().toString(), ((TextField) child).getText());
            } else if (child instanceof PasswordField) {
                ToolSetting.getInstance().getPre().put(child.getUserData().toString(), ((PasswordField) child).getText());
            } else if (child instanceof JFXComboBox) {
                ToolSetting.getInstance().getPre().put(child.getUserData().toString(), ((JFXComboBox) child).getValue().toString());
            } else if (child instanceof JFXCheckBox) {
                ToolSetting.getInstance().getPre().putBoolean(child.getUserData().toString(), ((JFXCheckBox) child).isSelected());
            } else if (child instanceof CheckComboBox) {
                ToolSetting.getInstance().getPre().put(child.getUserData().toString(), ((TextField) child).getText());
            }
        }
    }
}
