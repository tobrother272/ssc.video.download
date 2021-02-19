/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent.View;

import Setting.ToolSetting;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;

/**
 *
 * @author PC
 */
public class SSCVForm {

    private VBox formElement;
    private Button submit;

    public VBox getFormElement() {
        return formElement;
    }

    public void setFormElement(VBox formElement) {
        this.formElement = formElement;
    }

    public Button getSubmit() {
        return submit;
    }

    public void setSubmit(Button submit) {
        this.submit = submit;
    }

    public SSCVForm(Scene scene, String rootID, String submitText) {
        AnchorPane anchorPane = (AnchorPane) scene.lookup("#" + rootID);
        formElement = new VBox();
        formElement.getStyleClass().setAll("formGroup");
        formElement.setPrefHeight(anchorPane.getPrefHeight());
        formElement.setLayoutX(0);

        anchorPane.getChildren().add(formElement);
        submit = new Button(submitText);
        submit.getStyleClass().setAll("btnSubmit");
        submit.setPrefHeight(30);
        submit.setPrefWidth(100);
        submit.setWrapText(true);
        submit.setLayoutY(anchorPane.getPrefHeight() - 25);
        submit.setLayoutX(anchorPane.getPrefWidth() - submit.getPrefWidth());
        formElement.setPrefWidth(anchorPane.getPrefWidth());
        formElement.setPrefHeight(anchorPane.getPrefHeight() - 40);

        Button btnReloadData = new Button("Dữ liệu cũ");
        btnReloadData.getStyleClass().setAll("btnReload");
        btnReloadData.setPrefHeight(30);
        btnReloadData.setTooltip(new Tooltip("Khôi phục dữ liệu cũ đã nhập lần trước"));
        btnReloadData.setPrefWidth(100);
        //btnReloadData.getStyleClass().setAll("btn-dialog-reload");
        btnReloadData.setLayoutY(anchorPane.getPrefHeight() - 25);
        btnReloadData.setLayoutX(5);
        btnReloadData.setLayoutX(submit.getLayoutX() - btnReloadData.getPrefWidth() - 10);

        btnReloadData.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (Node object : getFormElement().getChildren()) {
                    if (((VBox) object).getChildren().size() == 2) {
                        Node child = ((VBox) object).getChildren().get(1);
                        if (child instanceof AnchorPane) {
                            TextField tf = ((TextField) ((AnchorPane) child).getChildren().get(0));
                            tf.setText(ToolSetting.getInstance().getPre().get(tf.getUserData().toString(), ""));
                        } else if (child instanceof JFXComboBox) {
                            ((JFXComboBox) child).getSelectionModel().select(ToolSetting.getInstance().getPre().get(child.getUserData().toString(), ""));
                        } else if (child instanceof CheckComboBox) {

                        }
                    } else {
                        Node child = ((VBox) object).getChildren().get(0);
                        ((JFXCheckBox) child).setSelected(ToolSetting.getInstance().getPre().getBoolean(child.getUserData().toString(), false));
                    }

                }
            }
        });
        anchorPane.getChildren().add(submit);
        anchorPane.getChildren().add(btnReloadData);
    }
    private AnchorPane anchorPane;
    private Button btnReloadData;

    public void removeSecondaryButton() {
        anchorPane.getChildren().add(btnReloadData);
    }
    private EventHandler<ActionEvent> secondaryEvent;

    public EventHandler<ActionEvent> getSecondaryEvent() {
        return secondaryEvent;
    }

    public void setSecondaryEvent(EventHandler<ActionEvent> secondaryEvent) {
        if (secondaryEvent != null) {
            btnReloadData.setOnAction(secondaryEvent);
        }
    }
    private String secondaryTitle;

    public String getSecondaryTitle() {
        return secondaryTitle;
    }

    public void setSecondaryTitle(String secondaryTitle) {
        if (secondaryTitle != null) {
            btnReloadData.setText(secondaryTitle);
        }
    }

    //
    public void setStyle(String style) {
        formElement.getStyleClass().setAll(style);
    }

    public SSCVForm(Scene scene, AnchorPane anchorPane, String submitText) {
        this.anchorPane = anchorPane;
        ScrollPane sideBarScroller = new ScrollPane();
        formElement = new VBox();
        sideBarScroller.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        sideBarScroller.setHbarPolicy(ScrollBarPolicy.NEVER);
        formElement.getStyleClass().setAll("formGroupProcess");
        formElement.setPrefHeight(anchorPane.getPrefHeight());
        formElement.setLayoutX(0);
        sideBarScroller.setPrefWidth(anchorPane.getPrefWidth());
        sideBarScroller.setContent(formElement);
        anchorPane.getChildren().add(sideBarScroller);
        submit = new Button(submitText);
        submit.getStyleClass().setAll("btnSubmit");
        submit.setPrefHeight(30);
        submit.setPrefWidth(100);
        submit.setWrapText(true);
        submit.setLayoutY(anchorPane.getPrefHeight() - 25);
        submit.setLayoutX(anchorPane.getPrefWidth() - submit.getPrefWidth());
        formElement.setPrefWidth(anchorPane.getPrefWidth() - 5);
        formElement.setPrefHeight(1000);
        sideBarScroller.setPrefHeight(anchorPane.getPrefHeight() - 40);
        btnReloadData = new Button("Dữ liệu cũ");
        btnReloadData.getStyleClass().setAll("btnReload");
        btnReloadData.setPrefHeight(30);
        btnReloadData.setPrefWidth(100);
        btnReloadData.setTooltip(new Tooltip("Khôi phục dữ liệu cũ đã nhập lần trước"));
        //btnReloadData.getStyleClass().setAll("btn-dialog-reload");
        btnReloadData.setLayoutY(anchorPane.getPrefHeight() - 25);
        btnReloadData.setLayoutX(5);
        btnReloadData.setLayoutX(submit.getLayoutX() - btnReloadData.getPrefWidth() - 10);
        btnReloadData.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (Node object : getFormElement().getChildren()) {
                    if (((VBox) object).getChildren().size() == 2) {
                        Node child = ((VBox) object).getChildren().get(1);
                        if (child instanceof AnchorPane) {
                            TextField tf = ((TextField) ((AnchorPane) child).getChildren().get(0));
                            tf.setText(ToolSetting.getInstance().getPre().get(tf.getUserData().toString(), ""));
                        } else if (child instanceof JFXComboBox) {
                            ((JFXComboBox) child).getSelectionModel().select(ToolSetting.getInstance().getPre().get(child.getUserData().toString(), ""));
                        }
                    } else {
                        Node child = ((VBox) object).getChildren().get(0);
                        ((JFXCheckBox) child).setSelected(ToolSetting.getInstance().getPre().getBoolean(child.getUserData().toString(), false));
                    }

                }
            }
        });
        anchorPane.getChildren().add(submit);
        anchorPane.getChildren().add(btnReloadData);
    }

    public void saveData() {
        for (Node object : getFormElement().getChildren()) {
            if (((VBox) object).getChildren().size() == 2) {
                Node child = ((VBox) object).getChildren().get(1);
                if (child instanceof AnchorPane) {
                    TextField tf = ((TextField) ((AnchorPane) child).getChildren().get(0));
                    ToolSetting.getInstance().getPre().put(tf.getUserData().toString(), tf.getText());
                } else if (child instanceof JFXComboBox) {
                    ToolSetting.getInstance().getPre().put(child.getUserData().toString(), ((JFXComboBox) child).getValue().toString());
                } else if (child instanceof CheckComboBox) {
                    // ToolSetting.getInstance().getPre().put(child.getUserData().toString(), ((TextField) child).getText());
                }
            } else {
                Node child = ((VBox) object).getChildren().get(0);
                ToolSetting.getInstance().getPre().putBoolean(child.getUserData().toString(), ((JFXCheckBox) child).isSelected());
            }

        }
    }
}
