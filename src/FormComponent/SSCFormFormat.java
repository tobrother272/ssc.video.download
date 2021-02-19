/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import FormComponent.SSCButtonChildTab;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;

/**
 *
 * @author PC
 */
public class SSCFormFormat {
    private String value;
    private int type;
    private String label;
    private SSCButtonChildTab sSCButtonChildTab;

    public SSCButtonChildTab getsSCButtonChildTab() {
        return sSCButtonChildTab;
    }

    public void setsSCButtonChildTab(SSCButtonChildTab sSCButtonChildTab) {
        this.sSCButtonChildTab = sSCButtonChildTab;
    }

    
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    private ObservableList<String> arrValue;

    public ObservableList<String> getArrValue() {
        return arrValue;
    }

    public void setArrValue(ObservableList<String> arrValue) {
        this.arrValue = arrValue;
    }
    
    
     /**
     * @param scene
     * @param arrFormItem 0 textfield|1 textarea |2 combobox|3 checkbox|4 checkcombobox|5 combobox with button
     * @param title
     * @param content
     * @param buttonTitle 
     */
    public SSCFormFormat(String value, int type, String label) {
        this.value = value;
        this.type = type;
        this.label = label;
    }
    public SSCFormFormat(String value, int type, String label,ObservableList<String> arrValue) {
        this.value = value;
        this.type = type;
        this.label = label;
        this.arrValue=arrValue;
    }     
}
