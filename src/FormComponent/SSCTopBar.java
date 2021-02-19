/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import FormComponent.Dialog.SSCInputDialog;
import Setting.ToolSetting;
import Setup.CloseAppTask;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import static ssc.reup.api.Controller.FXMLDocumentController.operaigon;

/**
 * @author PC
 */
public class SSCTopBar {

    private HBox topBar;
    private SSCInputDialog dialogSetting;
    private SSCTextField txtResetModemfile;
    private SSCTextField txtResetModemTimeout;
    private SSCTextArea txtTMKeyList;
    //private SSCTextField txtFirefoxPath;
    private SSCTextField txtPhoneSMSApi;
    public void initDialogSetting(Scene s) {
        dialogSetting = new SSCInputDialog(s, 3, "Cài đặt", "Cài đặt thông số tool", "Lưu");
        txtResetModemfile = new SSCTextField("File reset modem", "Click đúp chọn file txt", dialogSetting.getForm().getFormElement(), "txtResetModemfile", Arrays.asList(""));
        txtResetModemfile.enableSelectFileMode(Arrays.asList("txt"));
        txtResetModemTimeout = new SSCTextField("Thời gian đổi ip tối đa", "Giây ...", dialogSetting.getForm().getFormElement(), "txtResetModemTimeout", Arrays.asList(""));
        //txtFirefoxPath = new SSCTextField("Đường dẫn firefox", "Click đúp chọn firefox", dialogSetting.getForm().getFormElement(), "txtFirefoxPath", Arrays.asList(""));
        txtPhoneSMSApi= new SSCTextField("Nhập key sms ", "rentcode | simthue", dialogSetting.getForm().getFormElement(), "txtPhoneSMSApi", Arrays.asList(""));
        txtTMKeyList = new SSCTextArea("Danh sách tmproxies", "Nhập mỗi tm một dòng", dialogSetting.getForm().getFormElement(), "txtTMKeyList", operaigon);
        dialogSetting.getForm().getSubmit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialogSetting.getForm().saveData();
                ToolSetting.getInstance().setTmKey(txtTMKeyList.getText());
                ToolSetting.getInstance().setKeyPhoneApi(txtPhoneSMSApi.getText());
                ToolSetting.getInstance().setResetModemScript(txtResetModemfile.getText());
                dialogSetting.hide();
            }
        });

    }
    public SSCTopBar(Scene scene) {
        topBar = (HBox) scene.lookup("#paneNavTopBar");
        initDialogSetting(scene);
    }

    public void initMenu() {
        List<SSCButtonMenu> arr = new ArrayList<>();
        arr.add(new SSCButtonMenu("CLOSE", "Đóng tool", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ToolSetting.getInstance().getPre().putInt("currentLoX", (int) topBar.getScene().getWindow().getX());
                ToolSetting.getInstance().getPre().putInt("currentLoY", (int) topBar.getScene().getWindow().getY());
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
        }));
        arr.add(new SSCButtonMenu("MINUS", "Thu nhỏ", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                   ((Stage)topBar.getScene().getWindow()).setIconified(true);
            }
        }));
        arr.add(new SSCButtonMenu("COG", "Cài đặt", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              dialogSetting.show();
            }
        }));
        for (SSCButtonMenu btnMenu : arr) {
            topBar.getChildren().add(btnMenu.getButton("topBarButton"));
        }
    }
}
