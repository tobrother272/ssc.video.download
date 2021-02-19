/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainView.SubView.Login;

import FormComponent.View.SSCLoginForm;
import FormComponent.View.SSCMessage;
import FormComponent.SSCPasswdField;
import FormComponent.SSCTextField;
import MainView.SubView.ChanelManagerView;
import Setting.ToolSetting;
import Setup.LoginTask;
import java.io.File;
import java.util.Arrays;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * @author PC
 */
public class LoginView {

    public void LoginView() {

    }

    private Button btnSignIn;
    private Button btnSignUp;
    private Button btnLoginPage;
    private Button btnViewPage;
    private Button btnPricePage;
    private AnchorPane apLogin;
    private AnchorPane paneContentContainer;
    private AnchorPane apInitAppViewContainer;
    private SSCLoginForm loginForm;
    private SSCTextField txtUsername;
    private SSCPasswdField txtPassword;
    private AnchorPane leftLoginPane;
    private ImageView imgLogin;

    public void initView(Scene scene) {
        this.paneContentContainer = ((AnchorPane) scene.lookup("#paneContentContainer"));
        this.apInitAppViewContainer = ((AnchorPane) scene.lookup("#apInitAppViewContainer"));
        leftLoginPane = ((AnchorPane) scene.lookup("#leftLoginPane"));
        apLogin = ((AnchorPane) scene.lookup("#apLogin"));
        Image image = new Image(new File("assets/login_bg.jpg").toURI().toString());
        //image.
        imgLogin = ((ImageView) scene.lookup("#imgLogin"));
        imgLogin.setImage(image);
        imgLogin.setPreserveRatio(false);
        paneContentContainer.setVisible(false);

        leftLoginPane.prefWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                imgLogin.setLayoutX(newValue.intValue());
                apLogin.setLayoutX(newValue.intValue() + 25);
            }
        });

        paneContentContainer.prefWidthProperty().bind((ToolSetting.getInstance().getApRootContainer().prefWidthProperty()));
        paneContentContainer.prefHeightProperty().bind((ToolSetting.getInstance().getApRootContainer().prefHeightProperty()));
        leftLoginPane.prefHeightProperty().bind((ToolSetting.getInstance().getApRootContainer().prefHeightProperty()));
        leftLoginPane.prefWidthProperty().bind((ToolSetting.getInstance().getApRootContainer().prefWidthProperty().subtract(imgLogin.getFitWidth())));
        imgLogin.fitHeightProperty().bind((ToolSetting.getInstance().getApRootContainer().prefHeightProperty()));
        loginForm = new SSCLoginForm(scene, "apLogin", "Đăng nhập");
        txtUsername = new SSCTextField("Tên đăng nhập", "Nhập tên đăng nhập ...", loginForm.getFormElement(), "txtUsername", Arrays.asList("require"));
        txtPassword = new SSCPasswdField("Mật khẩu", "Nhập mật khẩu ...", loginForm.getFormElement(), "txtPassword", Arrays.asList("require"));
        txtUsername.setText(ToolSetting.getInstance().getPre().get("txtUsername", ""));
        txtPassword.setText(ToolSetting.getInstance().getPre().get("txtPassword", ""));
        loginForm.getSubmit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (txtUsername.isValidate() || txtPassword.isValidate()) {
                    return;
                }

                LoginTask loginTask = new LoginTask(txtUsername.getText(), txtPassword.getText());
                loginForm.getSubmit().disableProperty().bind(loginTask.runningProperty());
                loginTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {
                        loginForm.getSubmit().disableProperty().unbind();
                        String loginMessage = loginTask.getValue();
                        if (loginMessage.length() == 0) {
                            loginForm.saveData();
                            apInitAppViewContainer.setVisible(false);
                            paneContentContainer.setVisible(true);
                        } else {
                            SSCMessage.showError(scene, loginMessage);
                            return;
                        }
                    }
                });
                Thread t1 = new Thread(loginTask);
                t1.setDaemon(true);
                t1.start();

            }
        });

    }
}
