/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componant;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;


/**
 *
 * @author magictool
 */
public class MyComboBox extends ComboBox {
    private double width;
    private double height;
    private double x;
    private double y;
    private String text;
    private List<String> styleClass;
    private Pane apParent;  
    public MyComboBox() {
        super();
    }
    public MyComboBox(ObservableList data) {
        super(data);
    }
    
    
    
    public MyComboBox( double width, double height, double x, double y, List<String> styleClass,Pane apParent) {
        super();
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        initView(width, height, x, y, styleClass,apParent);
    }
    public MyComboBox(double width, double height, double x, double y, List<String> styleClass,Pane apParent,ObservableList data) {
        super();
        setItems(data);
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        initView(width, height, x, y, styleClass,apParent);
    }
    public void initView( double width, double height, double x, double y, List<String> styleClass,Pane apParent){
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setPrefSize(width, height);
        if (styleClass.size() != 0) {
            this.getStyleClass().setAll(styleClass);
        }
        if(apParent!=null){
            apParent.getChildren().add(this);
        }
    }
}
