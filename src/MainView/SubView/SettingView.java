/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainView.SubView;

import FormComponent.SSCCurrentProgress;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Setting.ToolSetting;
import static Utils.Constants.DESKTOP_PATH;
import static Utils.Constants.DESKTOP_PATH_ONEDRIVE;

/**
 *
 * @author PC
 */
public class SettingView {

    public SettingView() {

    }
    private AnchorPane apSetting;
    private TextField txtOpenBrowserTimeout;
    private TextField txtModemAdapterName;
    private TextField txtPhoneAdapterName;
    private TextField txtPhoneKeyApi;
    private TextField txtChangeIpTimeout;
    private TextArea txtTMApiKey;
    private TextField txtFirefoxPathSetting;
    private TextField txtLoadPageTimeout;
    private TextField txtPlaneModeButtonLocation;
    private Button btnSaveSetting;
    public AnchorPane getApSetting() {
        return apSetting;
    }

    public void setApSetting(AnchorPane apSetting) {
        this.apSetting = apSetting;
    }

    public void show() {
        txtOpenBrowserTimeout.setText("" + ToolSetting.getInstance().getTimeOpenFirefox());
        txtLoadPageTimeout.setText("" + ToolSetting.getInstance().getPageLoadTimeout());
        //txtResetModemScript.setText(ToolSetting.getInstance().getResetModemScript());
        txtModemAdapterName.setText(ToolSetting.getInstance().getEthernetName());
        txtPhoneAdapterName.setText(ToolSetting.getInstance().getEthernet3GName());
        txtPlaneModeButtonLocation.setText(ToolSetting.getInstance().getLocationPlantMode());
        //txtTinsoftKey.setText(ToolSetting.getInstance().getTinsoftKey());
        txtChangeIpTimeout.setText("" + ToolSetting.getInstance().getTimeReset());
        txtTMApiKey.setText(ToolSetting.getInstance().getTmKey());
        txtPhoneKeyApi.setText(ToolSetting.getInstance().getKeyPhoneApi());
        //cbOperation.getSelectionModel().select(ToolSetting.getInstance().getPhoneOperation());
        txtFirefoxPathSetting.setText(ToolSetting.getInstance().getFirefoxPath());
    }

    public void initView(Scene scene) {
        
        this.apSetting = ((AnchorPane) scene.lookup("#apSetting"));
        this.txtOpenBrowserTimeout = ((TextField) scene.lookup("#txtOpenBrowserTimeout"));
        this.txtModemAdapterName = ((TextField) scene.lookup("#txtModemAdapterName"));
        this.btnSaveSetting= ((Button) scene.lookup("#btnSaveSetting"));
        this.txtPhoneAdapterName = ((TextField) scene.lookup("#txtPhoneAdapterName"));
        this.txtPhoneKeyApi = ((TextField) scene.lookup("#txtPhoneKeyApi"));
        this.txtChangeIpTimeout = ((TextField) scene.lookup("#txtChangeIpTimeout"));
        this.txtFirefoxPathSetting = ((TextField) scene.lookup("#txtFirefoxPathSetting"));
        this.txtLoadPageTimeout = ((TextField) scene.lookup("#txtLoadPageTimeout"));
        this.txtPlaneModeButtonLocation = ((TextField) scene.lookup("#txtPlaneModeButtonLocation"));
        this.txtTMApiKey = ((TextArea) scene.lookup("#txtTMApiKey"));
        this.apSetting.setVisible(false);
        /*
        btnDumpFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GetPlaneLocationTask checkRealDeviceTask = new GetPlaneLocationTask(txtOnEplanMode.getText());
                btnDumpFile.disableProperty().bind(checkRealDeviceTask.runningProperty());
                checkRealDeviceTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {
                        if (checkRealDeviceTask.getValue() != null) {
                            if (checkRealDeviceTask.getValue().contains("Không thấy thiết bị")) {
                                ShowErrorMessage.showError(btnDumpFile.getScene(), "Không thấy thiết bị");
                                txtPlaneModeButtonLocation.setText(checkRealDeviceTask.getValue());
                            } else {
                                ShowErrorMessage.showSuccess(btnDumpFile.getScene(), "E plane mode  " + checkRealDeviceTask.getValue());
                                txtPlaneModeButtonLocation.setText(checkRealDeviceTask.getValue());
                            }
                        } else {
                            ShowErrorMessage.showError(btnDumpFile.getScene(), "Không thấy thiết bị");
                        }
                    }
                });
                checkRealDeviceTask.start();
            }
        });
        btnTestPlaneMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CheckRealDeviceTask checkRealDeviceTask = new CheckRealDeviceTask();
                btnTestPlaneMode.disableProperty().bind(checkRealDeviceTask.runningProperty());
                checkRealDeviceTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {
                        if (checkRealDeviceTask.getValue() != null) {
                            AdbUtils.clickToPoint(checkRealDeviceTask.getValue(), new String[]{txtPlaneModeButtonLocation.getText().split(",")[0], txtPlaneModeButtonLocation.getText().split(",")[1]});
                        }
                    }
                });
                checkRealDeviceTask.start();
            }
        });
        */
        txtFirefoxPathSetting.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                    try {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Tìm đường dẫn firefox.exe");
                        File initFile = null;
                        initFile = new File("C:\\Program Files\\Mozilla Firefox");
                        if (!initFile.exists()) {
                            initFile = new File("C:\\Program Files (x86)\\Mozilla Firefox");
                            if (!initFile.exists()) {
                                initFile = new File(DESKTOP_PATH).exists() ? new File(DESKTOP_PATH) : new File(DESKTOP_PATH_ONEDRIVE);
                            }
                        }
                        fileChooser.setInitialDirectory(initFile);
                        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter("EXE", "*.exe")});
                        File selectedDirectory = fileChooser.showOpenDialog(new Stage());
                        if (selectedDirectory != null) {
                            if (!selectedDirectory.getName().contains("firefox.exe")) {
                                SSCCurrentProgress.showError(txtFirefoxPathSetting.getScene(), "Select firefox.exe");
                                return;
                            }
                            txtFirefoxPathSetting.setText(selectedDirectory.getAbsolutePath());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToolSetting.getInstance().getPre().put("firefoxPathFolder", DESKTOP_PATH);
                    }
                }
            }
        });

        txtOpenBrowserTimeout.setText("" + ToolSetting.getInstance().getTimeOpenFirefox());
        txtChangeIpTimeout.setText("" + ToolSetting.getInstance().getTimeReset());
        txtPlaneModeButtonLocation.setText("" + ToolSetting.getInstance().getLocationPlantMode());
        txtModemAdapterName.setText("" + ToolSetting.getInstance().getEthernetName());
        txtPhoneAdapterName.setText("" + ToolSetting.getInstance().getEthernet3GName());
        //txtTinsoftKey.setText("" + ToolSetting.getInstance().getTinsoftKey());
        //txtResetModemScript.setText("" + ToolSetting.getInstance().getResetModemScript());

        btnSaveSetting.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //ToolSetting.getInstance().setResetModemScript(txtResetModemScript.getText());
                //ToolSetting.getInstance().setTinsoftKey(txtTinsoftKey.getText());
                ToolSetting.getInstance().setEthernetName(txtModemAdapterName.getText());
                ToolSetting.getInstance().setEthernet3GName(txtPhoneAdapterName.getText());
                ToolSetting.getInstance().setLocationPlantMode(txtPlaneModeButtonLocation.getText());
                ToolSetting.getInstance().setTmKey(txtTMApiKey.getText());
                ToolSetting.getInstance().setKeyPhoneApi(txtPhoneKeyApi.getText());
                //ToolSetting.getInstance().setPhoneOperation(cbOperation.getSelectionModel().getSelectedIndex());
                ToolSetting.getInstance().setFirefoxPath(txtFirefoxPathSetting.getText());
                try {
                    ToolSetting.getInstance().setTimeReset(Integer.parseInt(txtChangeIpTimeout.getText().trim()));
                } catch (Exception ex) {
                    ToolSetting.getInstance().setTimeReset(120);
                }
                try {
                    ToolSetting.getInstance().setTimeOpenFirefox(Integer.parseInt(txtOpenBrowserTimeout.getText().trim()));
                } catch (Exception ex) {
                    ToolSetting.getInstance().setTimeReset(120);
                }
                apSetting.setVisible(false);
            }
        });
        /*
        btnCheckTinsoftKey.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CheckProxyTinsoft checkProxyTinsoft = new CheckProxyTinsoft(ToolSetting.getInstance().getTinsoftKey());
                btnCheckTinsoftKey.disableProperty().bind(checkProxyTinsoft.runningProperty());
                checkProxyTinsoft.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        ShowErrorMessage.showSuccess(btnCheckTinsoftKey.getScene(), "Tinsoft còn : " + checkProxyTinsoft.getMessage() + " giờ");
                    }
                });
                checkProxyTinsoft.start();
            }
        });
        btnCheckTMKey.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CheckTMProxy checkProxyTinsoft = new CheckTMProxy(ToolSetting.getInstance().getTmKey());
                btnCheckTMKey.disableProperty().bind(checkProxyTinsoft.runningProperty());
                checkProxyTinsoft.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        ShowErrorMessage.showSuccess(btnCheckTinsoftKey.getScene(), "TM còn : " + checkProxyTinsoft.getMessage() + " giờ");
                    }
                });
                checkProxyTinsoft.start();
            }
        });
         */
    }
}
