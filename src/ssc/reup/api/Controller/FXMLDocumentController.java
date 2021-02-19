/*
 * and open the template in the editor.
 */
package ssc.reup.api.Controller;

import FormComponent.SSCCurrentProgress;
import FormComponent.SSCTopBar;
import MainView.SubView.ChanelManagerView;
import MainView.SubView.ChannelInteractiveView;
import MainView.SubView.Login.LoginView;
import MainView.SubView.SettingView;
import Setup.CloseAppTask;
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
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Setting.ToolSetting;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ssc.reup.api.task.LoadLoginTask;

/**
 * @author inet
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane apInitAppViewContainer;
    @FXML
    private AnchorPane paneContentContainer;
    @FXML
    private AnchorPane apRootContainer;
    @FXML
    private AnchorPane apHeaderBar;
    @FXML
    private AnchorPane apChannelRootPane;
    @FXML
    private AnchorPane apChannelUploadPane;
    @FXML
    private AnchorPane dialogPane;
    @FXML
    private AnchorPane messagePane;
    @FXML
    private AnchorPane apProgress;
    @FXML
    private AnchorPane pnToolTip;
    @FXML
    private Label lbProgress;
    @FXML
    private Label lbTitleMesssage;

    @FXML
    private VBox vboxLeftNavBar;
    @FXML
    private HBox paneNavTopBar;
    @FXML
    private HBox vboxChildButtonGroup;
    @FXML
    private Button btnCloseAfter;
    @FXML
    private Button btGoToSimple;
    @FXML
    private Button btnTest;
    ///@FXML
    ///private ImageView imgLogin;
    @FXML
    private TabPane tabContentContainer;
    public static ObservableList<String> operaigon
            = FXCollections.observableArrayList(
                    "Tất cả",
                    "Viettel",
                    "Vinaphone",
                    "Mobile Phone",
                    "Vietnam Mobile",
                    "Cambodia"
            );
    private Stage mainStage;
    private LoginView loginView;
    private SettingView settingView;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //imgLogin.setImage(new Image(getClass().getResource("assets/login_bg.png").toString()));
        apHeaderBar.prefWidthProperty().bind(apRootContainer.prefWidthProperty());
        vboxLeftNavBar.prefHeightProperty().bind(apRootContainer.prefHeightProperty().subtract(apHeaderBar.getPrefHeight()));
        tabContentContainer.prefWidthProperty().bind(apRootContainer.prefWidthProperty());
        apChannelRootPane.prefWidthProperty().bind(apRootContainer.prefWidthProperty().subtract(vboxLeftNavBar.getPrefWidth()));
        apChannelUploadPane.prefWidthProperty().bind(apRootContainer.prefWidthProperty().subtract(vboxLeftNavBar.getPrefWidth()));
        apChannelRootPane.prefHeightProperty().bind(apRootContainer.prefHeightProperty().subtract(vboxChildButtonGroup.layoutYProperty()).subtract(vboxChildButtonGroup.prefHeightProperty()));
        apChannelUploadPane.prefHeightProperty().bind(apRootContainer.prefHeightProperty().subtract(vboxChildButtonGroup.layoutYProperty()).subtract(vboxChildButtonGroup.prefHeightProperty()));
        tabContentContainer.prefHeightProperty().bind(apRootContainer.prefHeightProperty().subtract(vboxChildButtonGroup.layoutYProperty()).subtract(vboxChildButtonGroup.prefHeightProperty()).add(30));
        dialogPane.prefWidthProperty().bind(apRootContainer.prefWidthProperty());
        dialogPane.prefHeightProperty().bind(apRootContainer.prefHeightProperty());
        apProgress.prefWidthProperty().bind(apRootContainer.prefWidthProperty());
        lbTitleMesssage.prefWidthProperty().bind(apRootContainer.prefWidthProperty());
        lbProgress.prefWidthProperty().bind(apRootContainer.prefWidthProperty());
        pnToolTip.prefWidthProperty().bind(apRootContainer.prefWidthProperty());
        apRootContainer.prefWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                paneNavTopBar.setLayoutX(newValue.intValue() - paneNavTopBar.getPrefWidth());
                vboxChildButtonGroup.setLayoutX(newValue.intValue() - vboxChildButtonGroup.getPrefWidth() - 20);
                messagePane.setLayoutX((newValue.intValue() - messagePane.getPrefWidth()) / 2);
            }
        });
        ToolSetting.getInstance().setApRootContainer(apRootContainer);
        ToolSetting.getInstance().refreshScreen();
        ToolSetting.getInstance().setToolReady(true);

        paneContentContainer.setVisible(true);
        apInitAppViewContainer.setVisible(true);
        loginView = new LoginView();
        settingView = new SettingView();
        LoadLoginTask load = new LoadLoginTask();
        load.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                loginView.initView(apInitAppViewContainer.getScene());
                SSCCurrentProgress.getLoadingProgressBar(apInitAppViewContainer.getScene()).setVisible(false);
                new SSCTopBar(apInitAppViewContainer.getScene()).initMenu();
                new ChanelManagerView(apInitAppViewContainer.getScene());
                new ChannelInteractiveView(apInitAppViewContainer.getScene());
            }
        });
        load.start();
        btnCloseAfter.setOnAction(new EventHandler<ActionEvent>() {
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
    }

}
