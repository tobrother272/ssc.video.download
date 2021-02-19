/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author PC
 */
public class SSCStatusCombobox {
    private AnchorPane statusRoot;
    private Label lbTitle;
    private JFXComboBox lbValue;
    private Button btIcon;
    public SSCStatusCombobox(String icon,String title,ObservableList<String> arrOption,double w,double h){
        
        statusRoot=new AnchorPane();
        btIcon=new Button();
        statusRoot.setPrefSize(w, h);
        statusRoot.getChildren().add(btIcon);
        btIcon.setPrefSize(40, 40);
        btIcon.setLayoutX(10);
        btIcon.getStyleClass().setAll("statusIconRun");
        btIcon.setLayoutY(10);
        GlyphsDude.setIcon(btIcon,FontAwesomeIcons.valueOf(icon), "1.5em",ContentDisplay.CENTER);
        
        lbTitle=new Label(title);
        lbTitle.setPrefSize(w-50, 20);
        lbTitle.setLayoutX(50);
        lbTitle.setLayoutY(10);
        lbTitle.getStyleClass().setAll("lbStatusTitle");
        statusRoot.getChildren().add(lbTitle);
        
        lbValue=new JFXComboBox(arrOption);
        lbValue.setPrefSize(w-50, 20);
        lbValue.setLayoutX(50);
        lbValue.getSelectionModel().selectFirst();
        lbValue.setLayoutY(35);
        lbValue.getStyleClass().setAll("combo-box");
        statusRoot.getChildren().add(lbValue);
        
    }
    public SingleSelectionModel valueProperties(){
        return lbValue.getSelectionModel();
    }
    public AnchorPane getView(){
        return statusRoot;
    }

}
