/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import FormComponent.View.SSCMessage;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author PC
 */
public class SSCTextArea {
    private TextArea textField;
    private List<String> arrValidate;

    public List<String> getArrValidate() {
        return arrValidate;
    }

    public void setArrValidate(List<String> arrValidate) {
        this.arrValidate = arrValidate;
    }
    
    public TextArea getTextField() {
        return textField;
    }

    public void setTextField(TextArea textField) {
        this.textField = textField;
    }
    private String labelText;
    public SSCTextArea(String label,String hintText,HBox form,int child,String userData,List<String> arrValidate){
        labelText=label;
        VBox vb=new VBox();
        this.arrValidate=arrValidate;
        vb.getStyleClass().setAll("formGroupItem");
        Label lb=new Label(label);
        lb.getStyleClass().setAll("labelFor");
        textField=new TextArea("");
        textField.setUserData(userData);
        textField.setPromptText(hintText);
        textField.setPrefHeight(300);
        textField.getStyleClass().setAll("txtArea");
        textField.setPrefWidth(form.getPrefWidth()/child);
        vb.getChildren().add(lb);
        vb.getChildren().add(textField);
        form.getChildren().add(vb);
        vb.setPrefHeight(300);
    }
    public SSCTextArea(String label,String hintText,VBox form,String userData,List<String> arrValidate){
        labelText=label;
        VBox vb=new VBox();
        vb.getStyleClass().setAll("formGroupItem");
        Label lb=new Label(label);
        lb.getStyleClass().setAll("labelFor");
        textField=new TextArea("");
        textField.setUserData(userData);
        textField.getStyleClass().setAll("txtArea");
        textField.setPromptText(hintText);
        textField.setPrefHeight(300);
        this.arrValidate=arrValidate;
        vb.getChildren().add(lb);
        vb.getChildren().add(textField);
        vb.setPrefHeight(300);
        form.getChildren().add(vb);
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
                SSCMessage.showError(textField.getScene(), labelText);
                return true;
            }
        }
        return false;
    }
}
