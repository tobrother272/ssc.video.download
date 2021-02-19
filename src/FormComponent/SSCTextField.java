/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import FormComponent.View.SSCMessage;
import Componant.ChooseFile;
import Componant.ChooseFolder;
import Setting.ToolSetting;
import static Utils.Constants.DESKTOP_PATH;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import java.io.File;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 *
 * @author PC
 */
public class SSCTextField {

    private TextField textField;
    private List<String> arrValidate;
    private Label lb;

    public Label getLb() {
        return lb;
    }

    public void setLb(Label lb) {
        this.lb = lb;
    }

    public List<String> getArrValidate() {
        return arrValidate;
    }

    public void setArrValidate(List<String> arrValidate) {
        this.arrValidate = arrValidate;
    }

    public TextField getTextField() {
        return textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    public void setStyle(String labelStyle, String txtStyle) {
        lb.getStyleClass().setAll(labelStyle);
        textField.getStyleClass().setAll(txtStyle);
    }
    private String labelText;

    public SSCTextField(String label, String hintText, HBox form, int child, String userData, List<String> arrValidate) {
        labelText = label;
        VBox vb = new VBox();
        this.arrValidate = arrValidate;
        vb.getStyleClass().setAll("formGroupItem");
        lb = new Label(label);
        lb.getStyleClass().setAll("labelFor");
        textField = new TextField("");
        textField.setUserData(userData);
        textField.setPromptText(hintText);
        textField.getStyleClass().setAll("txtInput");
        textField.setPrefWidth(form.getPrefWidth() / child);
        vb.getChildren().add(lb);
        vb.getChildren().add(textField);
        form.getChildren().add(vb);
    }
    private Button btnOpenFile;

    public SSCTextField(String label, String hintText, VBox form, String userData, List<String> arrValidate) {
        labelText = label;
        VBox vb = new VBox();
        vb.getStyleClass().setAll("formGroupItem");
        lb = new Label(label);
        lb.getStyleClass().setAll("labelFor");
        AnchorPane txtRoot = new AnchorPane();
        btnOpenFile = new Button("");
        btnOpenFile.setPrefSize(30, 30);

        textField = new TextField("");
        textField.setUserData(userData);
        textField.getStyleClass().setAll("txtInput");
        textField.setPromptText(hintText);
        this.arrValidate = arrValidate;
        vb.getChildren().add(lb);
        textField.setPrefWidth(form.getPrefWidth() - 40);
        txtRoot.setPrefWidth(form.getPrefWidth() - 40);
        txtRoot.getChildren().add(textField);
        txtRoot.getChildren().add(btnOpenFile);
        btnOpenFile.getStyleClass().setAll("btnOpenFolder");
        btnOpenFile.setVisible(false);
        btnOpenFile.setLayoutY(0);
        btnOpenFile.setLayoutX(form.getPrefWidth() - 60);
        GlyphsDude.setIcon(btnOpenFile, FontAwesomeIcons.valueOf("FOLDER_OPEN"), "1em", ContentDisplay.LEFT);
        vb.getChildren().add(txtRoot);
        form.getChildren().add(vb);
    }

    public void enableSelectFileMode(List<String> fileFilter) {
        FileChooser.ExtensionFilter[] arrFilter = new FileChooser.ExtensionFilter[fileFilter.size()];
        for (String string : fileFilter) {
            arrFilter[fileFilter.indexOf(string)] = new FileChooser.ExtensionFilter(string, "*." + string);
        }
        textField.setOnMouseClicked(new ChooseFile(textField, labelText, DESKTOP_PATH, arrFilter, textField.getUserData().toString()));
        btnOpenFile.setVisible(true);
        btnOpenFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ToolSetting.openFile(getText());
            }
        });
        textField.setEditable(false);
    }

    public void enableSelectFolderMode() {
        textField.setOnMouseClicked(new ChooseFolder(textField, labelText, DESKTOP_PATH) {
        });
        btnOpenFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ToolSetting.openFile(getText());
            }
        });
        btnOpenFile.setVisible(true);
        textField.setEditable(false);
    }

    public void requestFocus() {
        textField.requestFocus();
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public void setPromptText(String hintText) {
        textField.setPromptText(hintText);
    }

    public boolean isValidate() {
        for (String string : arrValidate) {
            if (string.contains("require") && textField.getText().length() == 0) {
                SSCMessage.showWarning(textField.getScene(), labelText + " không để trống");
                return true;
            }
            if (string.contains("audio") && new File(textField.getText()).listFiles() == null) {
                SSCMessage.showWarning(textField.getScene(), "Không tìm thấy file");
                return true;
            }
            if (string.contains("audio")) {
                File arrF[] = new File(textField.getText()).listFiles();
                for (File file : arrF) {
                    if (!file.getAbsolutePath().toLowerCase().contains(".mp3")) {
                        SSCMessage.showWarning(textField.getScene(), "File " + file.getAbsolutePath() + " không đúng dịnh dạng");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
