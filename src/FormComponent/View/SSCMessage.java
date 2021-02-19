/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent.View;

import de.jensd.fx.glyphs.GlyphIcons;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author PC
 */
public class SSCMessage {

    public static void showError(Scene scene, String text) {
        show(scene, text, "error", FontAwesomeIcons.CLOSE);
    }

    public static void showSuccess(Scene scene, String text) {
        show(scene, text, "success", FontAwesomeIcons.CHECK_SQUARE_ALT);
    }

    public static void showWarning(Scene scene, String text) {
        show(scene, text, "warning", FontAwesomeIcons.WARNING);
    }

    public static void showSuccessInThread(Scene scene, String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                show(scene, text, "success", FontAwesomeIcons.CHECK_SQUARE_ALT);
            }
        });

    }

    public static void showWarningInThread(Scene scene, String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                show(scene, text, "warning", FontAwesomeIcons.WARNING);
            }
        });

    }

    private static void show(Scene scene, String text, String prefix, GlyphIcons icon) {
        AnchorPane apRoot = ((AnchorPane) scene.lookup("#messagePane"));
        apRoot.setVisible(true);
        AnchorPane apMessageRoot = new AnchorPane();
        apMessageRoot.setPrefSize(apRoot.getPrefWidth() / 2, 30);

        Button btnIcon = new Button();
        btnIcon.setLayoutX(10);
        btnIcon.setPrefSize(30, 30);
        apMessageRoot.getChildren().add(btnIcon);
        btnIcon.getStyleClass().setAll("icon-" + prefix);
        GlyphsDude.setIcon(btnIcon, icon, "1.5em", ContentDisplay.CENTER);

        Label lbMe = new Label(text);
        lbMe.setLayoutX(btnIcon.getLayoutX() + btnIcon.getPrefWidth());
        lbMe.setPrefHeight(30);
        lbMe.getStyleClass().setAll("lb-message-title");
        apMessageRoot.getChildren().add(lbMe);

        apMessageRoot.setLayoutX((apRoot.getPrefWidth() - apMessageRoot.getPrefWidth()) / 2);
        apRoot.getChildren().add(apMessageRoot);
        apMessageRoot.setLayoutY(-20);
        apMessageRoot.getStyleClass().setAll("message-" + prefix);
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(500), apMessageRoot);
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(1.0);
        fadeInTransition.play();
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), apMessageRoot);
        tt.setByY(-20);
        tt.setToY(35);
        tt.play();
        tt.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FadeTransition fadeInTransition = new FadeTransition(Duration.millis(1500), apMessageRoot);
                fadeInTransition.setFromValue(1.0);
                fadeInTransition.setToValue(0.9);
                fadeInTransition.play();
                fadeInTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        apRoot.getChildren().remove(apMessageRoot);
                        apRoot.setVisible(false);
                    }
                });
            }
        });
    }

}
