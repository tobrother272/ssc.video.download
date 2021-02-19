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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
/**
 * @author PC
 */
public class SSCInputDialog {

    private SSCVForm form;

    public SSCVForm getForm() {
        return form;
    }

    public void setForm(SSCVForm form) {
        this.form = form;
    }
    private AnchorPane rootDialog;
    private AnchorPane apContainer;

    public AnchorPane getRootDialog() {
        return rootDialog;
    }

    public void setRootDialog(AnchorPane rootDialog) {
        this.rootDialog = rootDialog;
    }
    
    public SSCInputDialog(Scene scene, int rate, String title,String content, String buttonTitle) {
        this.apContainer = (AnchorPane) scene.lookup("#dialogPane");
        rootDialog = new AnchorPane();
        rootDialog.getStyleClass().setAll("paneDialog");
        rootDialog.setPrefHeight(apContainer.getPrefHeight() - 60);
        rootDialog.setPrefWidth(apContainer.getPrefWidth() / rate);
        rootDialog.setLayoutY(-60);
        rootDialog.setLayoutX((apContainer.getPrefWidth() - rootDialog.getPrefWidth()) / 2);
        apContainer.getChildren().add(rootDialog);
        rootDialog.setOpacity(0);
        rootDialog.setVisible(false);
        Label lbLabelHeader = new Label(title);
        lbLabelHeader.getStyleClass().setAll("lbBreadScrumbDialogTitle");
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
        lbLabelHeader.setLayoutX(35);
        lbLabelHeader.setLayoutY(20);
        lbLabelContent.setLayoutX(35);
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
        AnchorPane formRoot = new AnchorPane();
        formRoot.setLayoutY(lbLabelContent.getLayoutY()+lbLabelContent.getPrefHeight());
        formRoot.setPrefSize(rootDialog.getPrefWidth() - 40, rootDialog.getPrefHeight()-lbLabelContent.getLayoutY() - lbLabelContent.getPrefHeight() - 25);
        rootDialog.getChildren().add(formRoot);
        formRoot.setLayoutX(20);
        form = new SSCVForm(scene, formRoot, buttonTitle);

    }

    

    public void show() {
        this.apContainer.setVisible(true);
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
        tt.setByY(-60);
        tt.setToY(90);
        tt.play();
    }

    public void hide() {
        if (rootDialog.getOpacity() < 1) {
            this.apContainer.setVisible(false);
            return;
        }
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(300), rootDialog);
        fadeInTransition.setFromValue(1.0);
        fadeInTransition.setToValue(0.0);
        fadeInTransition.play();
        fadeInTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                apContainer.setVisible(false);
                rootDialog.setVisible(false);
            }
        });
        TranslateTransition tt = new TranslateTransition(Duration.millis(300), rootDialog);
        tt.setByY(90);
        tt.setToY(-60);
        tt.play();
    }
}
