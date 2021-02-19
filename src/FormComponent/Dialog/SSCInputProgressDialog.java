/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent.Dialog;

import FormComponent.View.SSCProcessView;
import FormComponent.View.SSCVForm;
import FormComponent.View.StopRunningTask;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import java.util.Timer;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import ssc.socket.ToolSocket;

/**
 * @author PC
 */

public abstract class SSCInputProgressDialog extends SSCProcessView {

    private SSCVForm form;

    public SSCVForm getForm() {
        return form;
    }

    public void setForm(SSCVForm form) {
        this.form = form;
    }
    private AnchorPane rootDialog;
    private AnchorPane apContainer;
    private TableView tvProgress;
    private TableView tvLog;
    private Button btHideLog;

    public TableView getTvLog() {
        return tvLog;
    }

    public void setTvLog(TableView tvLog) {
        this.tvLog = tvLog;
    }

    public TableView getTvProgress() {
        return tvProgress;
    }

    public void setTvProgress(TableView tvProgress) {
        this.tvProgress = tvProgress;
    }
    private Button btnHideProcess;
    private AnchorPane header;

    public SSCInputProgressDialog(Scene scene, String title, String content, String buttonTitle) {
        this.apContainer = (AnchorPane) scene.lookup("#dialogPane");

        rootDialog = new AnchorPane();
        rootDialog.setPrefSize(apContainer.getPrefWidth() - 100, apContainer.getPrefHeight() - 60);
        rootDialog.getStyleClass().setAll("paneDialog");
        rootDialog.setLayoutY(-100);
        rootDialog.setLayoutX((apContainer.getPrefWidth() - rootDialog.getPrefWidth()) / 2);
        apContainer.getChildren().add(rootDialog);
        rootDialog.setOpacity(0);
        rootDialog.setVisible(false);

        header = new AnchorPane();
        header.setPrefSize(rootDialog.getPrefWidth(), 100);
        header.getStyleClass().setAll("paneDialogHeader");
        rootDialog.getChildren().add(header);

        Label lbLabelHeader = new Label(title);
        lbLabelHeader.getStyleClass().setAll("lbBreadScrumb");
        lbLabelHeader.setPrefSize(header.getPrefWidth() - 40, 30);
        header.getChildren().add(lbLabelHeader);

        Label lbLabelContent = new Label(content);
        lbLabelContent.getStyleClass().setAll("lbBreadScrumbDes");
        lbLabelContent.setPrefSize(header.getPrefWidth() - 40, 30);
        header.getChildren().add(lbLabelContent);

        Button btnClose = new Button();
        btnClose.getStyleClass().setAll("btn-dialog-close");
        GlyphsDude.setIcon(btnClose, FontAwesomeIcons.CLOSE, "1.5em", ContentDisplay.RIGHT);
        header.getChildren().add(btnClose);
        btnClose.setLayoutX(header.getPrefWidth() - 40);

        lbLabelHeader.setLayoutX(25);
        lbLabelHeader.setLayoutY(10);
        lbLabelContent.setLayoutX(25);
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

        btnHideProcess = new Button("");
        btnHideProcess.getStyleClass().setAll("btnInputDialogClose");
        btnHideProcess.setPrefSize(30, 30);
        btnHideProcess.setLayoutX(15);
        btnHideProcess.setLayoutY(header.getLayoutY() + header.getPrefHeight() + 10);
        GlyphsDude.setIcon(btnHideProcess, FontAwesomeIcons.EYE_SLASH, "1.5em", ContentDisplay.CENTER);
        rootDialog.getChildren().add(btnHideProcess);

        initView(rootDialog, 15, header.getLayoutY() + header.getPrefHeight() + 10, rootDialog.getPrefWidth() / 3, 340);

        AnchorPane formRoot = new AnchorPane();
        formRoot.setLayoutY(getApStatus().getLayoutY() + getApStatus().getPrefHeight() + 10);
        formRoot.setPrefSize(rootDialog.getPrefWidth() / 3, rootDialog.getPrefHeight() - getApStatus().getLayoutY() - getApStatus().getPrefHeight() + 20);
        rootDialog.getChildren().add(formRoot);
        formRoot.setLayoutX(15);
        form = new SSCVForm(scene, formRoot, buttonTitle);
        form.setStyle("formGroupProcess");
        btnHideProcess.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                formRoot.setVisible(!formRoot.isVisible());
            }
        });

        btHideLog = new Button("Ẩn lịch sử");
        btHideLog.getStyleClass().setAll("");
        btHideLog.setPrefSize(80, 30);
        btHideLog.setLayoutX(20);
        btHideLog.setLayoutY(header.getLayoutY() + header.getPrefHeight() + 20);
        rootDialog.getChildren().add(btHideLog);

        tvLog = new TableView();
        tvLog.setLayoutY(btHideLog.getLayoutY() + lbLabelContent.getPrefHeight() + 10);
        tvLog.setPrefSize(rootDialog.getPrefWidth() / 3, rootDialog.getPrefHeight() - lbLabelContent.getLayoutY() - lbLabelContent.getPrefHeight() - 45);
        tvLog.setLayoutX(20);
        rootDialog.getChildren().add(tvLog);
        tvLog.setVisible(false);

        btHideLog.visibleProperty().bind(tvLog.visibleProperty());
        btHideLog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tvLog.setVisible(false);
            }
        });
        AnchorPane tableRoot = new AnchorPane();
        tableRoot.setLayoutY(header.getLayoutY() + header.getPrefHeight() + 10);
        tableRoot.setPrefSize(((rootDialog.getPrefWidth() / 3 * 2) - 60), rootDialog.getPrefHeight() - header.getLayoutY() - header.getPrefHeight() - 45);
        tableRoot.setLayoutY(lbLabelContent.getLayoutY() + lbLabelContent.getPrefHeight() + 20);
        tableRoot.setLayoutX(formRoot.getLayoutX() + formRoot.getPrefWidth() + 20);
        rootDialog.getChildren().add(tableRoot);
        tvProgress = new TableView();
        tvProgress.setPrefSize(tableRoot.getPrefWidth(), tableRoot.getPrefHeight());
        tvProgress.getStyleClass().setAll("table-view-material-process");
        tableRoot.getChildren().add(tvProgress);
        form.getSubmit().setVisible(false);
    }

    public void show() {
        this.apContainer.setVisible(
                true);

        this.rootDialog.setVisible(
                true);
        setTimer(new Timer());
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(1000), rootDialog);

        fadeInTransition.setFromValue(
                0.0);
        fadeInTransition.setToValue(
                1.0);
        fadeInTransition.play();

        fadeInTransition.setOnFinished(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), rootDialog);

        tt.setByY(-100);
        tt.setToY(110);
        tt.play();
    }

    public void hide() {
        stopTask();
        goToRunReadyMode();
        StopRunningTask st = stopRunningTask();
        st.start();
        if (getTimer() != null) {
            getTimer().cancel();
        }
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
        tt.setByY(150);
        tt.setToY(-100);
        tt.play();
    }

}
