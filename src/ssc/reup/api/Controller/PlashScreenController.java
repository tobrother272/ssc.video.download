/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.Controller;

import com.jfoenix.controls.JFXTextArea;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import Utils.CMDUtils;
import static Utils.Constants.UPDATE_PATH;
import static ssc.reup.api.Utils.Graphics.addDragListeners;
import ssc.reup.api.task.PlashTask;

/**
 * @author inet
 */
public class PlashScreenController implements Initializable {

    Stage mainStage;

    // a timer allowing the tray icon to provide a periodic notification event.
    // format used to display the current time in a tray icon notification.
    private DateFormat timeFormat = SimpleDateFormat.getTimeInstance();

    Preferences pre;
    @FXML
    private Button lbPlashMessage;

    @FXML
    private Button btnUpdate;

    @FXML
    private StackPane spContent;

    @FXML
    private AnchorPane apMain;
    @FXML
    private JFXTextArea textAreaTest;
    @FXML
    private ImageView ivS1;
    @FXML
    private ImageView ivS2;
    @FXML
    private ImageView ivCo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        PlashTask plashTask = new PlashTask();
        File file = new File(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "S.png");
        Image image = new Image(file.toURI().toString());

        File file1 = new File(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "co.png");
        Image image1 = new Image(file1.toURI().toString());
        ivCo.setImage(image1);
        ivS1.setImage(image);
        ivS2.setImage(image);

        //ivCo.setLayoutY(lbPlashMessage.getLayoutY()-ivCo.getFitHeight()+20);
        //ivCo.setLayoutX(ivS1.getLayoutX() + ivS1.getFitWidth() / 2 + 10);
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(2), ivS1);
        translateTransition1.setFromY(-200);
        translateTransition1.setToY(0);
        translateTransition1.setFromX(100);
        translateTransition1.setToX(0);

        translateTransition1.setCycleCount(1);
        translateTransition1.play();

        TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(2), ivS2);
        translateTransition2.setFromY(200);
        translateTransition2.setToY(0);
        translateTransition2.setFromX(-100);
        translateTransition2.setToX(0);
        translateTransition2.play();

        //lbPlashMessage.setLayoutX(ivS1.getLayoutX());
        //lbPlashMessage.setLayoutY(ivS6.getLayoutY() + ivS6.getFitHeight()-lbPlashMessage.getPrefHeight()+10);
        btnUpdate.setPrefWidth(130);
        btnUpdate.setLayoutY(lbPlashMessage.getLayoutY() - lbPlashMessage.getPrefHeight() - 5);
        btnUpdate.setLayoutX(lbPlashMessage.getLayoutX() + lbPlashMessage.getPrefWidth() - btnUpdate.getPrefWidth());
        btnUpdate.getStyleClass().setAll("btn", "btn-primary", "btn-xs");
        btnUpdate.setText("Cập Nhật Bản Mới");

        translateTransition2.setOnFinished((e) -> {
            lbPlashMessage.setVisible(true);
            lbPlashMessage.setPrefWidth(0);
            TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), spContent);
            translateTransition3.setFromX(0);
            translateTransition3.setToX(-300);
            translateTransition3.play();
            Timer animTimer = new Timer();
            animTimer.scheduleAtFixedRate(new TimerTask() {
                int i = 0;

                @Override
                public void run() {
                    if (i < 100) {
                        lbPlashMessage.setPrefWidth(lbPlashMessage.getPrefWidth() + 6);
                    } else {
                        this.cancel();
                    }
                    i++;
                }

            }, 0, 10);
            lbPlashMessage.textProperty().bind(plashTask.messageProperty());
            Thread t = new Thread(plashTask);
            t.setDaemon(true);
            t.start();
        });
        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CMDUtils.runTask(UPDATE_PATH);
                Platform.exit();
                System.exit(0);
            }
        });
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), spContent);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);
        plashTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                if (plashTask.getValue()) {
                    fadeOut.play();
                } else {
                    if (plashTask.getCheckUpdate().equals("cập nhật")) {
                        btnUpdate.setVisible(true);
                    }
                    lbPlashMessage.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (plashTask.getCheckUpdate().contains("cập nhật")) {
                                fadeOut.play();
                            } else {
                                try {
                                    Runtime.getRuntime().exec("cmd /c start update.exe");
                                    Platform.exit();
                                    System.exit(0);
                                } catch (Exception e) {
                                }
                            }
                        }
                    });
                }
            }
        });
        fadeOut.setOnFinished((e) -> {
            try {
                Stage stageAccount = new Stage();
                stageAccount.initStyle(StageStyle.TRANSPARENT);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ssc/reup/api/Fxml/FXMLSetup.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                String uri = Paths.get(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "Styles.css").toUri().toString();
                root.getStylesheets().add(uri);
                addDragListeners(root);
                stageAccount.setScene(new Scene(root));
                stageAccount.show();
                Stage stage = (Stage) lbPlashMessage.getScene().getWindow();
                stage.close();
            } catch (Exception ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }
}
