/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componant;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
/**
 * @author magictool
 */
public class MyTextField extends JFXTextField {
    private Boolean labelFloat;
    private String unfocusColor;
    private double width;
    private double height;
    private double x;
    private double y;
    private String holderText;
    private String text;
    private String styleClass;
    private Pane apParent;
    
    public MyTextField() {
        super();
    }
    public MyTextField(String text) {
        super(text);
    }
    public MyTextField(Boolean labelFloat, String unfocusColor, double width, double height, double x, double y, String holderText, String styleClass,Pane apParent) {
        super();
        this.labelFloat = labelFloat;
        this.unfocusColor = unfocusColor;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.holderText = holderText;
        initView(labelFloat, unfocusColor, width, height, x, y, holderText, styleClass,apParent);
    }
    public MyTextField(Boolean labelFloat, String unfocusColor, double width, double height, double x, double y, String holderText, String text, String styleClass,Pane apParent) {
        super(text);
        this.labelFloat = labelFloat;
        this.unfocusColor = unfocusColor;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.holderText = holderText;
        this.text = text;
        initView(labelFloat, unfocusColor, width, height, x, y, holderText, styleClass,apParent);
    }
    
    public void initView(Boolean labelFloat, String unfocusColor, double width, double height, double x, double y, String holderText, String styleClass,Pane apParent){
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setPromptText(holderText);
        this.setPrefSize(width, height);
        
        this.setLabelFloat(labelFloat);
        this.styleClass = styleClass;
        if (styleClass.length() != 0) {
            this.getStyleClass().setAll(styleClass);
        }else{
            this.getStyleClass().add("text-primary");
        }
        if(unfocusColor.length()==0){
            this.setFocusColor(Color.valueOf("#2196F3"));
            this.setUnFocusColor(Color.valueOf("#2196F3"));
        }else{
            this.setUnFocusColor(Color.valueOf(unfocusColor));
            this.setFocusColor(Color.valueOf(unfocusColor));
        }
        if(apParent!=null){
            apParent.getChildren().add(this);
        }
    }
    
    
    
}
