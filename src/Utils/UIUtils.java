/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import static Utils.Constants.DESKTOP_PATH;

/**
 * @author tobrother272
 */
public class UIUtils {

    public static void enablePane(Pane pane) {
        for (Node child : pane.getChildren()) {
            child.setDisable(true);
        }
    }

    public static void disablePane(Pane pane) {
        for (Node child : pane.getChildren()) {
            child.setDisable(false);
        }
    }

    public static void printLogToDesktop() {
        try {
            if (new File("log.txt").exists()) {
                PrintStream printStream = new PrintStream(new FileOutputStream("log.txt"));
                System.setOut(printStream);
                System.setErr(printStream);
            }
        } catch (Exception e) {
        }
    }

    public static <S> void addAutoScroll(final TableView<S> view) {
        if (view == null) {
            throw new NullPointerException();
        }

        view.getItems().addListener((ListChangeListener<S>) (c -> {
            c.next();
            final int size = view.getItems().size();
            if (size > 0) {
                view.scrollTo(size - 1);
            }
        }));
    }

    public static void showDialog(StackPane stackPane, String title, String content) {
        JFXDialogLayout contentLayout = new JFXDialogLayout();
        contentLayout.setHeading(new Text(title));
        Label lbContent = new Label(content);
        VBox vbox = new VBox();
        vbox.getChildren().add(lbContent);
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        BorderPane root = new BorderPane(scrollPane);
        lbContent.prefWidthProperty().bind(root.prefWidthProperty());
        lbContent.setWrapText(true);
        contentLayout.setBody(root);
        JFXDialog dialog = new JFXDialog(stackPane, contentLayout, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("Close");
        lbContent.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        lbContent.setVisible(false);
                        TextArea textarea = new TextArea(lbContent.getText());
                        textarea.setPrefHeight(lbContent.getHeight() + 10);
                        vbox.getChildren().add(textarea);
                        textarea.setOnKeyPressed(event -> {
                            System.out.println(event.getCode());
                            if (event.getCode().toString().equals("ENTER")) {
                                vbox.getChildren().remove(textarea);
                                lbContent.setVisible(true);
                            }
                        });
                    }
                }
            }
        });
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        contentLayout.setActions(button);
        dialog.show();
    }

    public static void showSelectingDialog(StackPane stackPane, String title) {
        JFXDialogLayout contentLayout = new JFXDialogLayout();
        contentLayout.setHeading(new Text(title));
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(0, 10, 0, 10));
        contentLayout.setBody(root);
        JFXDialog dialog = new JFXDialog(stackPane, contentLayout, JFXDialog.DialogTransition.CENTER);
        JFXButton btClose = new JFXButton("Close");
        JFXButton btNews = new JFXButton("Video tin tức");
        JFXButton btCustomMusic = new JFXButton("Video nhạc tổng hợp");
        root.add(btNews, 0, 0);
        root.add(btCustomMusic, 0, 1);
        btNews.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        btCustomMusic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        btClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        contentLayout.setActions(btClose);
        dialog.show();
    }

    public static void showDialog(String title, String header, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();
    }

}
