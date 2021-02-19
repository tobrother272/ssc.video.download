/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componant;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


/**
 *
 * @author magictool
 */
public class MyLabel extends Label {
    private double width;
    private double height;
    private double x;
    private double y;
    private String text;
    private String styleClass;
    private Pane apParent;
    
    public MyLabel() {
        super();
    }
    public MyLabel(String text) {
        super(text);
    }
    public MyLabel(double width, double height, double x, double y,String styleClass,Pane apParent) {
        super();
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        initView(width, height, x, y, styleClass,apParent);
    }
     public MyLabel(String text,double width, double height, double x, double y,String styleClass,Pane apParent) {
        super(text);
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        initView(width, height, x, y, styleClass,apParent);
    }
    public MyLabel( double width, double height, double x, double y, String text, String styleClass,Pane apParent) {
        super(text);
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.text = text;
        initView(width, height, x, y, styleClass,apParent);
    }
    public void initView(double width, double height, double x, double y,String styleClass,Pane apParent){
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setPrefSize(width, height);
        this.styleClass = styleClass;
        if (styleClass.length() != 0) {
            this.getStyleClass().setAll(styleClass);
        }
        if(apParent!=null){
            apParent.getChildren().add(this);
        }
    }
    
    
    
}
