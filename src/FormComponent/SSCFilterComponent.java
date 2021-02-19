/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import com.jfoenix.controls.JFXComboBox;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author PC
 */
public class SSCFilterComponent {
    private String name;
    private List<SSCFilterOptionModel> arrOptions;
    public SSCFilterComponent(String name, List<SSCFilterOptionModel> arrOptions) {
        this.name = name;
        this.arrOptions = arrOptions;
    }
    ObservableList<String> arrKey;
    ObservableList<String> arrValue;
    public String getCurrentValue(){
       return arrValue.get(comboBox.getSelectionModel().getSelectedIndex());
    }
    public ObservableList<String> getArrValue() {
        return arrValue;
    }

    public void setArrValue(ObservableList<String> arrValue) {
        this.arrValue = arrValue;
    }
    
    JFXComboBox comboBox;
    public JFXComboBox getComboBox() {
        return comboBox;
    }
    public void setComboBox(JFXComboBox comboBox) {
        this.comboBox = comboBox;
    }
    public AnchorPane getView(int width){
        AnchorPane root=new AnchorPane();
        root.setPrefSize(width, 30);
        Label lbName=new Label(name);
        lbName.setLayoutX(10);
        lbName.setLayoutY(0);
        root.getChildren().add(lbName);
        lbName.getStyleClass().setAll("lbFilterTitle");
        lbName.setPrefSize(width, 15);
        arrKey=FXCollections.observableArrayList();
        arrValue=FXCollections.observableArrayList();
        System.out.println("arrOptions "+arrOptions.size());
        arrOptions.forEach((item) -> { 
            arrKey.add(item.getName());
            arrValue.add(item.getValue());
        });
        comboBox=new JFXComboBox(arrKey);
        comboBox.getSelectionModel().selectFirst();
        comboBox.setPrefWidth(width);
        comboBox.setLayoutX(10);
        comboBox.setLayoutY(lbName.getPrefHeight()+2);
        comboBox.getStyleClass().setAll("combo-box-filter");
        root.getChildren().add(comboBox);
        return root;
    }
}
