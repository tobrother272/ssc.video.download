/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import FormComponent.View.SSCMessage;
import com.jfoenix.controls.JFXComboBox;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.IndexedCheckModel;

/**
 *
 * @author PC
 */
public class SSCCheckComboBox {
    private CheckComboBox checkComboBox;

    public CheckComboBox getCheckComboBox() {
        return checkComboBox;
    }

    public void setCheckComboBox(CheckComboBox checkComboBox) {
        this.checkComboBox = checkComboBox;
    }
    
    private String labelText;
    public SSCCheckComboBox(String label,HBox form,int child,String userData,List<String> arrValidate ){
        labelText=label;
        this.arrValidate=arrValidate;
        VBox vb=new VBox();
        vb.getStyleClass().setAll("formGroupItem");
        Label lb=new Label(label);
        lb.getStyleClass().setAll("labelFor");
        checkComboBox=new CheckComboBox();
        checkComboBox.setPrefHeight(30);
        checkComboBox.setMaxWidth(200);
        checkComboBox.setUserData(userData);
        checkComboBox.setPrefWidth(form.getPrefWidth()/child);
        checkComboBox.getStyleClass().setAll("my-checbox");
        vb.getChildren().add(lb);
        vb.getChildren().add(checkComboBox);
        form.getChildren().add(vb);
    }
    public SSCCheckComboBox(String label,VBox form,String userData,List<String> arrValidate){
        labelText=label;
        this.arrValidate=arrValidate;
        VBox vb=new VBox();
        vb.getStyleClass().setAll("formGroupItem");
        Label lb=new Label(label);
        lb.getStyleClass().setAll("labelFor");
        checkComboBox=new CheckComboBox();
        checkComboBox.setPrefHeight(30);
        checkComboBox.setUserData(userData);
        checkComboBox.setMaxWidth(200);
        checkComboBox.getStyleClass().setAll("my-checbox");
        vb.getChildren().add(lb);
        vb.getChildren().add(checkComboBox);
        form.getChildren().add(vb);
    }

    public void requestFocus(){
        checkComboBox.requestFocus();
    }
    public ObservableList<String> getItems(){
        return checkComboBox.getItems();
    }
    public void setItems(ObservableList<String> items){
        checkComboBox.getItems().setAll(items);
    }
    public IndexedCheckModel getCheckModel(){
        return checkComboBox.getCheckModel();
    }
    private List<String> arrValidate;

    public List<String> getArrValidate() {
        return arrValidate;
    }

    public void setArrValidate(List<String> arrValidate) {
        this.arrValidate = arrValidate;
    }
    public boolean isValidate(){
        for (String string : arrValidate) {
            if(string.contains("require")&&checkComboBox.getCheckModel().getCheckedItems().size()==0){
                SSCMessage.showError(checkComboBox.getScene(), labelText);
                return true;
            }
        }
        return false;
    }
}
