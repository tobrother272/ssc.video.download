/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.Utils;

import java.io.File;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/**
 *
 * @author inet
 */
public class EventUtils {

    public static void initTextFieldChooseFolderEvent(TextField tb, String title) {
        tb.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        DirectoryChooser chooser = new DirectoryChooser();
                        chooser.setTitle(title);
                        File selectedDirectory = chooser.showDialog(new Stage());
                        if (selectedDirectory != null) {
                            tb.setText(selectedDirectory.getAbsolutePath());
                        }
                    }
                }
            }
        });
    }

    public static void initTextFieldChooseFolderEvent(TextField tb, String start_path, String title) {
        tb.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        DirectoryChooser chooser = new DirectoryChooser();
                        chooser.setTitle(title);
                        chooser.setInitialDirectory(new File(start_path));
                        File selectedDirectory = chooser.showDialog(new Stage());
                        if (selectedDirectory != null) {
                            tb.setText(selectedDirectory.getAbsolutePath());
                        }
                    }
                }
            }
        });
    }

    public static void initTextFieldChooseFileEvent(TextField tb, String title) {
        tb.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle(title);
                        File selectedDirectory = fileChooser.showOpenDialog(new Stage());
                        if (selectedDirectory != null) {
                            tb.setText(selectedDirectory.getAbsolutePath());

                        }
                    }
                }
            }
        });
    }

    public static void initTextFieldChooseFileEvent(TextField tb, String start_path, String title, String expand_key, String expand_value) {
        tb.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle(title);
                        fileChooser.setInitialDirectory(new File(start_path));
                        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(expand_key, expand_value));
                        File selectedDirectory = fileChooser.showOpenDialog(new Stage());
                        if (selectedDirectory != null) {
                            tb.setText(selectedDirectory.getAbsolutePath());
                        }
                    }
                }
            }
        });
    }
  
}
