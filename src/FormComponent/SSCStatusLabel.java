/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author PC
 */
public class SSCStatusLabel {
    private AnchorPane statusRoot;
    private Label lbTitle;
    private Label lbValue;
    private Button btIcon;
    public SSCStatusLabel(String icon,String title,double w,double h){
        
        statusRoot=new AnchorPane();
        btIcon=new Button();
        statusRoot.setPrefSize(w, h);
        statusRoot.getChildren().add(btIcon);
        btIcon.setPrefSize(40, 40);
        btIcon.setLayoutX(10);
        btIcon.getStyleClass().setAll("btn-dialog-close");
        btIcon.setLayoutY(10);
        GlyphsDude.setIcon(btIcon,FontAwesomeIcons.valueOf(icon), "1.5em",ContentDisplay.CENTER);
        
        lbTitle=new Label(title);
        lbTitle.setPrefSize(w-50, 20);
        lbTitle.setLayoutX(50);
        lbTitle.setLayoutY(10);
        lbTitle.getStyleClass().setAll("lbStatusTitle");
        statusRoot.getChildren().add(lbTitle);
        
        lbValue=new Label(title);
        lbValue.setPrefSize(w-50, 20);
        lbValue.setLayoutX(50);
        lbValue.setLayoutY(35);
        lbValue.getStyleClass().setAll("lbStatusValue");
        statusRoot.getChildren().add(lbValue);
        
    }
    public StringProperty textProperty(){
        return lbValue.textProperty();
    }
    public AnchorPane getView(){
        return statusRoot;
    }
    public void setText(String text){
       lbValue.setText(text);
    }
}
