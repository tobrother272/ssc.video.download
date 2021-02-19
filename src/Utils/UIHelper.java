/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Setting.ToolSetting;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSpinner;
import java.awt.Desktop;
import java.net.URL;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ssc.task.LoadImageTask;

/**
 *
 * @author PC
 */
public class UIHelper {

    public static void showHelper(Scene scene, String title, String message, String image, String preString, String youtubeLink) {
        AnchorPane apRoot = ((AnchorPane) scene.lookup("#apHelperContainer"));
        ((Button) scene.lookup("#btCloseHelper")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                apRoot.setVisible(false);
                ToolSetting.getInstance().getPre().putBoolean(preString, !((JFXCheckBox) scene.lookup("#chSaveHelper")).isSelected());
            }
        });
        ((Button) scene.lookup("#btYoutubeHelp")).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (youtubeLink.length() != 0) {
                    try {
                        Desktop.getDesktop().browse(new URL(youtubeLink).toURI());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        if (ToolSetting.getInstance().getPre().getBoolean(preString, true)) {
            apRoot.setVisible(true);
            ImageView iv = ((ImageView) scene.lookup("#ivHelper"));
            iv.setFitWidth(1334);
            iv.setFitHeight(683);
            iv.setPreserveRatio(false);
            LoadImageTask loadImageTask = new LoadImageTask(image);
            ((JFXSpinner) scene.lookup("#spHelperLoading")).visibleProperty().bind(loadImageTask.runningProperty());
            loadImageTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    iv.setImage(loadImageTask.getValue());
                }
            });
            loadImageTask.start();
        }

    }
}
