/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.Controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
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
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Setting.ToolSetting;
import static Utils.Constants.PRE.SETUP_READY;
import static ssc.reup.api.Utils.Graphics.addDragListeners;
import Setup.CheckReadyTask;

/**
 * @author inet
 */
public class SetupController implements Initializable {

    Preferences pre;
    @FXML
    private StackPane spContent;
    @FXML
    private Button btAppium;
    @FXML
    private Button btNodeJS;
    @FXML
    private Button btJDK;
    @FXML
    private Button btSDK;
    @FXML
    private Button btKOPlayer;
    @FXML
    private Button btSetup;

    @FXML
    private Button btExit;
    @FXML
    private Label lbSetupMessage;

    public static String SETUP_APPIUM = "appium";
    public static String SETUP_NODEJS = "Node js";
    public static String SETUP_JDK = "Java jdk";
    public static String SETUP_SDK = "Android sdk";
    public static String SETUP_KOPLAYER = "Device Koplayer";
    private String setupString = SETUP_APPIUM;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pre = ToolSetting.getInstance().getPre();
        String result = pre.get(SETUP_READY, "0 0 0 0 0");
        if (checkReady(result)) {
            startNextActivity();
        } else {
            if (lbSetupMessage.getText().contains(SETUP_APPIUM)) {
                setupString = SETUP_APPIUM;
            } else if (lbSetupMessage.getText().contains(SETUP_NODEJS)) {
                setupString = SETUP_NODEJS;
            } else if (lbSetupMessage.getText().contains(SETUP_JDK)) {
                setupString = SETUP_JDK;
            } else if (lbSetupMessage.getText().contains(SETUP_SDK)) {
                setupString = SETUP_SDK;
            } else {
                setupString = SETUP_KOPLAYER;
            }
        }
        btSetup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CheckReadyTask checkReadyTask = new CheckReadyTask(setupString);
                checkReadyTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {
                        lbSetupMessage.textProperty().unbind();
                        if (checkReady(checkReadyTask.getValue())) {
                            startNextActivity();
                        } else {
                            if (lbSetupMessage.getText().contains(SETUP_APPIUM)) {
                                setupString = SETUP_APPIUM;
                            } else if (lbSetupMessage.getText().contains(SETUP_NODEJS)) {
                                setupString = SETUP_NODEJS;
                            } else if (lbSetupMessage.getText().contains(SETUP_JDK)) {
                                setupString = SETUP_JDK;
                            } else if (lbSetupMessage.getText().contains(SETUP_SDK)) {
                                setupString = SETUP_SDK;
                            } else {
                                setupString = SETUP_KOPLAYER;
                            }
                        }
                    }
                });
                lbSetupMessage.textProperty().bind(checkReadyTask.messageProperty());
                btSetup.disableProperty().bind(checkReadyTask.runningProperty());
                Thread t = new Thread(checkReadyTask);
                t.setDaemon(true);
                t.start();
            }
        });
        btExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });

    }

    public void startNextActivity() {
        try {
            Stage stageAccount = new Stage();
            stageAccount.initStyle(StageStyle.TRANSPARENT);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ssc/reup/api/Fxml/FXMLMain.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            String uri = Paths.get(System.getProperty("user.dir") + "\\assets\\Styles.css").toUri().toString();
            root.getStylesheets().add(uri);
            addDragListeners(root);
            stageAccount.setScene(new Scene(root));
            stageAccount.show();
            Stage stage = (Stage) lbSetupMessage.getScene().getWindow();
            stage.close();
        } catch (IOException ex) {
            Logger.getLogger(SetupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkReady(String result) {
        String[] results = result.split(" ");
        System.out.println("results "+ result);
        if (!results[0].equals("1")) {
            btAppium.getStyleClass().setAll("icon-medium", "icon-unready");
            lbSetupMessage.setText("Chưa cài đat " + SETUP_APPIUM + " nhấn nút cài đặt để tiếp tục !");
            return false;
        } else {
            btAppium.getStyleClass().setAll("icon-medium", "icon-ready");
        }
        if (!results[1].equals("1")) {
            btNodeJS.getStyleClass().setAll("icon-medium", "icon-unready");
            lbSetupMessage.setText("Chưa cài đat " + SETUP_NODEJS + " nhấn nút cài đặt để tiếp tục !");
            return false;
        } else {
            btNodeJS.getStyleClass().setAll("icon-medium", "icon-ready");
        }
        if (!results[2].equals("1")) {
            btJDK.getStyleClass().setAll("icon-medium", "icon-unready");
            lbSetupMessage.setText("Chưa cài đat " + SETUP_JDK + " nhấn nút cài đặt để tiếp tục !");
            return false;
        } else {
            btJDK.getStyleClass().setAll("icon-medium", "icon-ready");
        }
        if (!results[3].equals("1")) {
            btSDK.getStyleClass().setAll("icon-medium", "icon-unready");
            lbSetupMessage.setText("Chưa cài đat " + SETUP_SDK + " nhấn nút cài đặt để tiếp tục !");
            return false;
        } else {
            btSDK.getStyleClass().setAll("icon-medium", "icon-ready");
        }
        if (!results[4].equals("1")) {
            btKOPlayer.getStyleClass().setAll("icon-medium", "icon-unready");
            lbSetupMessage.setText("Chưa cài đat " + SETUP_KOPLAYER + " nhấn nút cài đặt để tiếp tục !");
            return false;
        } else {
            btKOPlayer.getStyleClass().setAll("icon-medium", "icon-ready");
        }
        return true;
    }

}
