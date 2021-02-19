/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import com.jfoenix.controls.JFXProgressBar;
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * @author magictool
 */
public class SSCCurrentProgress {
    

    public static JFXProgressBar getLoadingProgressBar(Scene scene){
         JFXProgressBar pb=((JFXProgressBar) scene.lookup("#pbLoading"));
         return pb;
    }
    
    public static AnchorPane getTaskMessage(Scene scene) {
        AnchorPane ap=((AnchorPane) scene.lookup("#apProgress"));
        ap.visibleProperty().unbind();
        //ap.getStyleClass().setAll("paneProgress");
        return ap;
    }
    public static Label getLabelMessage(Scene scene) {
        Label lb=((Label) scene.lookup("#lbProgress"));
        //lb.getStyleClass().add("lbMessageProgress");
        lb.textProperty().unbind();
        return lb;
    }
    public static Label getLabelTitle(Scene scene) {
        Label lb=((Label) scene.lookup("#lbTitleMesssage"));
        //lb.getStyleClass().add("lbMessageProgress");
        lb.textProperty().unbind();
        return lb;
    }
    public static void showError(Scene scene, String message) {
        AnchorPane apTaskMessage = ((AnchorPane) scene.lookup("#apTaskMessage"));
        apTaskMessage.visibleProperty().unbind();
        
        
        apTaskMessage.setVisible(true);
        //apTaskMessage.getStyleClass().setAll("paneMessageError");
        Label lbMainTaskMessage = ((Label) scene.lookup("#lbMainTaskMessage"));
        lbMainTaskMessage.textProperty().unbind();

        lbMainTaskMessage.setText(message);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), apTaskMessage);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.seconds(3));
        fadeOut.setCycleCount(1);
        fadeOut.setOnFinished((e) -> {
            //apTaskMessage.getStyleClass().setAll("message-panel");
            apTaskMessage.setOpacity(1);
            apTaskMessage.visibleProperty().unbind();
            apTaskMessage.setVisible(false);
//            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), apTaskMessage);
//            fadeIn.setFromValue(0);
//            fadeIn.setToValue(1);
//            fadeIn.setCycleCount(1);
//            fadeIn.setOnFinished((ex) -> {
//                  apTaskMessage.setVisible(false);
//            });
//            fadeIn.play();
        });
        fadeOut.play();
    }

    public static void showSuccess(Scene scene, String message) {
        AnchorPane apTaskMessage = ((AnchorPane) scene.lookup("#apTaskMessage"));
        apTaskMessage.setVisible(true);
        //apTaskMessage.getStyleClass().setAll("message-panel-success");
        Label lbMainTaskMessage = ((Label) scene.lookup("#lbMainTaskMessage"));
        lbMainTaskMessage.textProperty().unbind();
        lbMainTaskMessage.setText(message);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), apTaskMessage);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.seconds(3));
        fadeOut.setCycleCount(1);
        fadeOut.setOnFinished((e) -> {
            //apTaskMessage.getStyleClass().setAll("message-panel");
            apTaskMessage.setOpacity(1);
            apTaskMessage.visibleProperty().unbind();
            apTaskMessage.setVisible(false);
        });
        fadeOut.play();
    }

    public static void showWarning(Scene scene, String message) {
        AnchorPane apTaskMessage = ((AnchorPane) scene.lookup("#apTaskMessage"));
        apTaskMessage.setVisible(true);
        //apTaskMessage.getStyleClass().setAll("message-panel-warning");
        Label lbMainTaskMessage = ((Label) scene.lookup("#lbMainTaskMessage"));
        lbMainTaskMessage.textProperty().unbind();
        lbMainTaskMessage.setText(message);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), apTaskMessage);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.seconds(3));
        fadeOut.setCycleCount(1);
        fadeOut.setOnFinished((e) -> {
            //apTaskMessage.getStyleClass().setAll("message-panel");
            apTaskMessage.setOpacity(1);
            apTaskMessage.visibleProperty().unbind();
            apTaskMessage.setVisible(false);
        });
        fadeOut.play();
    }
}
