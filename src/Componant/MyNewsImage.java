/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componant;

import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import Utils.MyFileUtils;

/**
 * @author magictool
 */
public class MyNewsImage extends ImageView {

    private String imageResult;
    private double width;
    private double heigth;
    private double x;
    private double y;
    private Pane apParent;

    public MyNewsImage(String imageResult, double width, double heigth, double x, double y, Pane apParent) {
        super(new File(imageResult).toURI().toString());
        this.imageResult = imageResult;
        this.width = width;
        this.heigth = heigth;
        this.x = x;
        this.y = y;
        this.apParent = apParent;
        initView(width, heigth, x, y, apParent);
    }

  

    public void initView(double width, double heigth, double x, double y, Pane apParent) {
        AnchorPane apImageBG= new AnchorPane();
       

        apImageBG.setPrefSize(width,heigth);
        apImageBG.getStyleClass().setAll("imgPane");
        apImageBG.setLayoutX(2);
        apImageBG.setLayoutY(2);
        this.setId("" + MyFileUtils.getFileName(imageResult));
        
        
        double rate = this.getImage().getWidth()/this.getImage().getHeight();
        double imgWidth = rate>1?width-4:( (heigth-4)*rate  );
        double imgHeight = rate<1?heigth-4:( (width-4)/rate );
        this.setFitHeight(imgHeight);
        this.setFitWidth(imgWidth);
        this.setLayoutX((width-imgWidth)/2);
        this.setLayoutY((heigth-imgHeight)/2);
        
        
        
        

//        this.setPreserveRatio(true);
        
        apImageBG.getChildren().add(this);
        apParent.getChildren().add(apImageBG);
    }

}
