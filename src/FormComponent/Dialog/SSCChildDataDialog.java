/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent.Dialog;
import FormComponent.View.SSCVForm;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
/**
 * @author PC
 */
public class SSCChildDataDialog {

    private SSCVForm form;

    public SSCVForm getForm() {
        return form;
    }

    public void setForm(SSCVForm form) {
        this.form = form;
    }
    private AnchorPane rootDialog;
    private AnchorPane apContainer;
    private TableView tvProgress;
    private TableView tvLog;
    private Button btHideLog;
    
    public TableView getTvLog() {
        return tvLog;
    }

    public void setTvLog(TableView tvLog) {
        this.tvLog = tvLog;
    }
    
    public TableView getTvProgress() {
        return tvProgress;
    }

    public void setTvProgress(TableView tvProgress) {
        this.tvProgress = tvProgress;
    }
    
    public SSCChildDataDialog(Scene scene,AnchorPane container,String title,String content, String buttonTitle) {
        this.apContainer = container;
        
        rootDialog = new AnchorPane();
        rootDialog.setPrefSize(apContainer.getPrefWidth()-40, apContainer.getPrefHeight()-80);
        rootDialog.getStyleClass().setAll("paneChildDialog");
        rootDialog.setLayoutY(-40);
        rootDialog.setLayoutX(20);
        apContainer.getChildren().add(rootDialog);
        rootDialog.setOpacity(0);
        rootDialog.setVisible(false);
        Label lbLabelHeader = new Label(title);
        lbLabelHeader.getStyleClass().setAll("lbBreadScrumb");
        lbLabelHeader.setPrefSize(rootDialog.getPrefWidth()-40, 30);
        rootDialog.getChildren().add(lbLabelHeader);
        
        Label lbLabelContent = new Label(content);
        lbLabelContent.getStyleClass().setAll("lbBreadScrumbDes");
        lbLabelContent.setPrefSize(rootDialog.getPrefWidth()-40, 30);
        rootDialog.getChildren().add(lbLabelContent);
        

        Button btnClose = new Button();
        btnClose.getStyleClass().setAll("btn-dialog-close");
        GlyphsDude.setIcon(btnClose, FontAwesomeIcons.CLOSE, "1.5em", ContentDisplay.RIGHT);
        rootDialog.getChildren().add(btnClose);
        btnClose.setLayoutX(rootDialog.getPrefWidth()-40);
        lbLabelHeader.setLayoutX(25);
        lbLabelHeader.setLayoutY(10);
        lbLabelContent.setLayoutX(25);
        lbLabelContent.setLayoutY(45);
        btnClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hide();
            }
        });
        rootDialog.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
    
        btHideLog=new Button("Ẩn lịch sử");
        btHideLog.getStyleClass().setAll("");
        btHideLog.setPrefSize(80, 30);
        btHideLog.setLayoutX(20);
        btHideLog.setLayoutY(lbLabelContent.getLayoutY()+lbLabelContent.getPrefHeight() + 20);
        rootDialog.getChildren().add(btHideLog);
     
        tvLog=new TableView();
        tvLog.setLayoutY(btHideLog.getLayoutY()+lbLabelContent.getPrefHeight() + 10);
        tvLog.setPrefSize(rootDialog.getPrefWidth() /3, rootDialog.getPrefHeight()-lbLabelContent.getLayoutY() - lbLabelContent.getPrefHeight() - 45);
        tvLog.setLayoutX(20);
        rootDialog.getChildren().add(tvLog);
        tvLog.setVisible(false);
        
        btHideLog.visibleProperty().bind(tvLog.visibleProperty());
        btHideLog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tvLog.setVisible(false);
            }
        });
           
        AnchorPane tableRoot = new AnchorPane();
        tableRoot.setLayoutY(lbLabelContent.getLayoutY()+lbLabelContent.getPrefHeight() + 20);
        tableRoot.setPrefSize(rootDialog.getPrefWidth()-40, (rootDialog.getPrefHeight() - tableRoot.getLayoutY())/2 );
        tableRoot.setLayoutY(lbLabelContent.getLayoutY()+lbLabelContent.getPrefHeight() + 20);
        tableRoot.setLayoutX(20);
      
        rootDialog.getChildren().add(tableRoot);
        tvProgress=new TableView();
        tvProgress.setPrefSize(tableRoot.getPrefWidth(), tableRoot.getPrefHeight());
        tvProgress.getStyleClass().setAll("table-view-material");
        tableRoot.getChildren().add(tvProgress);
        
        AnchorPane formRoot = new AnchorPane();
        formRoot.setLayoutY(tableRoot.getLayoutY()+tableRoot.getPrefHeight() + 20);
        formRoot.setPrefSize(rootDialog.getPrefWidth()-40, (rootDialog.getPrefHeight() - tableRoot.getLayoutY())/2 -30);
        rootDialog.getChildren().add(formRoot);
        formRoot.setLayoutX(20);
        form = new SSCVForm(scene, formRoot, buttonTitle);
        
        
        
        
    }

    

    public void show() {
        this.rootDialog.setVisible(true);
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(1000), rootDialog);
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(1.0);
        fadeInTransition.play();
        fadeInTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), rootDialog);
        tt.setByY(-40);
        tt.setToY(90);
        tt.play();
    }

    public void hide() {
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(300), rootDialog);
        fadeInTransition.setFromValue(1.0);
        fadeInTransition.setToValue(0.0);
        fadeInTransition.play();
        fadeInTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rootDialog.setVisible(false);
            }
        });
        TranslateTransition tt = new TranslateTransition(Duration.millis(300), rootDialog);
        tt.setByY(90);
        tt.setToY(-40);
        tt.play();
    }
}
