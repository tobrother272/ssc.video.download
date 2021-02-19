/*
 * and open the template in the editor.
 */
package ssc.reup.api.Controller;

import FormComponent.View.SSCMessage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Setting.ToolSetting;
import Setup.CloseAppTask;
import com.jfoenix.controls.JFXProgressBar;
import java.io.File;
import java.nio.file.Paths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import static ssc.reup.api.Utils.Graphics.addDragListeners;
import ssc.task.LoadSetupTask;
import ssc.task.DownloadEnvTask;
import ssc.task.SetupTask;


/**
 * @author inet
 */
public class FXMLSetupController implements Initializable {

    @FXML
    private Button btnCloseAfter;
    @FXML
    private Button btCloseFromSetupTool;
    @FXML
    private Button btnNextSetupStep;
    @FXML
    private Button btnSetupNow;
    @FXML
    private AnchorPane apSetup;
    @FXML
    private JFXProgressBar pbChecking;
    @FXML
    private Label lbChecklingMessage;
    @FXML
    private TableView tvListSetup;
    @FXML
    private Label lbCheckingTitle;
    @FXML
    private Label lbCountCheckingEnv;
    @FXML
    private Label lbCurrentTaskChecking;
    @FXML
    private ImageView ivIconSetup;
    private ObservableList<SetupTask> arrSetup;

    public void bindSetup(SetupTask setupTask) {
        lbCheckingTitle.setText(setupTask.getSetupTitle());
        btnSetupNow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lbCheckingTitle.textProperty().bind(setupTask.titleProperty());
                lbCountCheckingEnv.textProperty().bind(setupTask.messageProperty());
                btnSetupNow.disableProperty().bind(setupTask.runningProperty());
                setupTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        lbCheckingTitle.textProperty().unbind();
                        lbCountCheckingEnv.textProperty().unbind();
                    }
                });
                setupTask.start();
            }
        });
        btnNextSetupStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (setupTask.checkReady()) {
                    arrSetup.remove(setupTask);
                    if (arrSetup.size() != 0) {
                        bindSetup(arrSetup.get(0));
                    } else {
                        try {
                            Stage mainStage = new Stage();
                            mainStage.initStyle(StageStyle.TRANSPARENT);
                            mainStage.setX(ToolSetting.getInstance().getPre().getInt("currentLoX", 0));
                            mainStage.setY(ToolSetting.getInstance().getPre().getInt("currentLoY", 0));
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ssc/reup/api/Fxml/FXMLMain.fxml"));
                            Parent root = (Parent) fxmlLoader.load();
                            String uri = Paths.get(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "Styles.css").toUri().toString();
                            root.getStylesheets().add(uri);
                            addDragListeners(root);
                            mainStage.setScene(new Scene(root));
                            mainStage.show();
                            Platform.setImplicitExit(false);
                            Stage stage = (Stage) btnNextSetupStep.getScene().getWindow();
                            stage.close();
                        } catch (Exception ex) {

                        }

                    }
                } else {
                    SSCMessage.showWarning(btnNextSetupStep.getScene(), "Bạn chưa hoàn thành cài đặt, thử lại");
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        apSetup.setVisible(true);
        arrSetup = FXCollections.observableArrayList();
        SetupTask.initTable(tvListSetup, arrSetup);
        LoadSetupTask load = new LoadSetupTask(arrSetup);
        lbCountCheckingEnv.textProperty().bind(load.messageProperty());
        lbCurrentTaskChecking.textProperty().bind(load.titleProperty());
        btnNextSetupStep.disableProperty().bind(load.runningProperty());
        ivIconSetup.setImage(new Image(new File(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "scan.gif").toURI().toString()));
        load.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                if (arrSetup.size() == 0) {
                    try {
                        Stage mainStage = new Stage();
                        mainStage.initStyle(StageStyle.TRANSPARENT);
                        mainStage.setX(ToolSetting.getInstance().getPre().getInt("currentLoX", 0));
                        mainStage.setY(ToolSetting.getInstance().getPre().getInt("currentLoY", 0));
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ssc/reup/api/Fxml/FXMLMain.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        String uri = Paths.get(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "Styles.css").toUri().toString();
                        root.getStylesheets().add(uri);
                        addDragListeners(root);
                        mainStage.setScene(new Scene(root));
                        mainStage.show();
                        Platform.setImplicitExit(false);
                        Stage stage = (Stage) btnNextSetupStep.getScene().getWindow();
                        stage.close();
                    } catch (Exception ex) {

                    }
                } else {
                    ivIconSetup.setImage(new Image(new File(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "downloading.gif").toURI().toString()));
                    DownloadEnvTask det = new DownloadEnvTask(arrSetup);
                    btnNextSetupStep.disableProperty().bind(det.runningProperty());
                    lbCurrentTaskChecking.textProperty().bind(det.titleProperty());
                    lbCountCheckingEnv.textProperty().bind(det.messageProperty());
                    det.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            lbCurrentTaskChecking.textProperty().unbind();
                            lbCurrentTaskChecking.setText("Hoàn thành cài đặt để chạy tool tối ưu nhất");
                            ivIconSetup.setImage(new Image(new File(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "setup.gif").toURI().toString()));
                            bindSetup(arrSetup.get(0));
                            btnSetupNow.setVisible(true);
                        }
                    });
                    det.start();
                }
            }
        });
        load.start();
        btCloseFromSetupTool.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ToolSetting.getInstance().getPre().putInt("currentLoX", (int) btnCloseAfter.getScene().getWindow().getX());
                ToolSetting.getInstance().getPre().putInt("currentLoY", (int) btnCloseAfter.getScene().getWindow().getY());
                CloseAppTask cat = new CloseAppTask();
                cat.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        Platform.exit();
                        System.exit(0);
                    }
                });
                Thread t = new Thread(cat);
                t.setDaemon(true);
                t.start();
            }
        });
        btnNextSetupStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        btnSetupNow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }

}
