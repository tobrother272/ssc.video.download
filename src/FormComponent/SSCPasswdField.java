/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import FormComponent.View.SSCMessage;
import Componant.ChooseFile;
import Componant.ChooseFolder;
import static Utils.Constants.DESKTOP_PATH;
import java.io.File;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 *
 * @author PC
 */
public class SSCPasswdField {
    private PasswordField textField;
    private List<String> arrValidate;

    public List<String> getArrValidate() {
        return arrValidate;
    }

    public void setArrValidate(List<String> arrValidate) {
        this.arrValidate = arrValidate;
    }
    
    public TextField getTextField() {
        return textField;
    }

    public void setTextField(PasswordField textField) {
        this.textField = textField;
    }
    private String labelText;
    public SSCPasswdField(String label,String hintText,HBox form,int child,String userData,List<String> arrValidate){
        labelText=label;
        VBox vb=new VBox();
        this.arrValidate=arrValidate;
        vb.getStyleClass().setAll("formGroupItem");
        Label lb=new Label(label);
        lb.getStyleClass().setAll("labelFor");
        textField=new PasswordField();
        textField.setUserData(userData);
        textField.setPromptText(hintText);
        textField.getStyleClass().setAll("txtInput");
        textField.setPrefWidth(form.getPrefWidth()/child);
        vb.getChildren().add(lb);
        vb.getChildren().add(textField);
        form.getChildren().add(vb);
    }
    public SSCPasswdField(String label,String hintText,VBox form,String userData,List<String> arrValidate){
        labelText=label;
        VBox vb=new VBox();
        vb.getStyleClass().setAll("formGroupItem");
        Label lb=new Label(label);
        lb.getStyleClass().setAll("labelFor");
        textField=new PasswordField();
        textField.setUserData(userData);
        textField.getStyleClass().setAll("txtInput");
        textField.setPromptText(hintText);
        this.arrValidate=arrValidate;
        vb.getChildren().add(lb);
        vb.getChildren().add(textField);
        form.getChildren().add(vb);
    }
    public void enableSelectFileMode(List<String> fileFilter){
        FileChooser.ExtensionFilter[] arrFilter=new FileChooser.ExtensionFilter[fileFilter.size()];
        for (String string : fileFilter) {
            arrFilter[fileFilter.indexOf(string)]=new FileChooser.ExtensionFilter(string, "*."+string);
        }
        textField.setOnMouseClicked(new ChooseFile(textField, labelText, DESKTOP_PATH,arrFilter,textField.getUserData().toString()));
    }
    public void enableSelectFolderMode(){
        textField.setOnMouseClicked(new ChooseFolder(textField, labelText, DESKTOP_PATH) {});
    }
    public void requestFocus(){
        textField.requestFocus();
    }
    public String getText(){
        return textField.getText();
    }
    public void setText(String text){
        textField.setText(text);
    }
    public void setPromptText(String hintText){
        textField.setPromptText(hintText);
    }
    
    
    public boolean isValidate(){
        for (String string : arrValidate) {
            if(string.contains("require")&&textField.getText().length()==0){
                SSCMessage.showWarning(textField.getScene(), labelText+" không để trống");
                return true;
            }
            if(string.contains("audio")&&new File(textField.getText()).listFiles()==null){
                SSCMessage.showWarning(textField.getScene(), "Không tìm thấy file");
                return true;
            }
            if(string.contains("audio")){
                File arrF[]=new File(textField.getText()).listFiles();
                for (File file : arrF) {
                    if(!file.getAbsolutePath().toLowerCase().contains(".mp3")){
                         SSCMessage.showWarning(textField.getScene(), "File "+file.getAbsolutePath()+" không đúng dịnh dạng");
                         return true;
                    }
                }
            }
        }
        return false;
    }
}
