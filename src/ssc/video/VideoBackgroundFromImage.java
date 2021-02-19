/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.video;

import Ffmpeg.AudioRender;
import FormComponent.SSCCheckHoverThread;
import Utils.MyFileUtils;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import java.awt.Desktop;
import java.io.File;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventDispatchChain;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author PC
 */
public class VideoBackgroundFromImage extends MyTableItemTask {

    private String image;
    private int duration;
    private String folderSave;
    private int stt;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getFolderSave() {
        return folderSave;
    }

    public void setFolderSave(String folderSave) {
        this.folderSave = folderSave;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public VideoBackgroundFromImage(int stt, String image, int duration, String folderSave) {
        this.stt = stt;
        this.image = image;
        this.duration = duration;
        this.folderSave = folderSave;
    }

    @Override
    public boolean main() {
        try {
            MyFileUtils.createFolder(getFolder());
            String image6sVideo = AudioRender.makeVideoFromImage("Tạo video 6s", image, getFolder() + File.separator + "6s.mp4", this);
            if (image6sVideo.length() == 0) {
                updateMessage("Lỗi tạo video 6s");
            }
            String loopVideoPath = folderSave + File.separator + System.currentTimeMillis() + ".mp4";
            if (AudioRender.loopVideo("Kéo dài video " + duration + " m", image6sVideo, loopVideoPath, duration *60, this).length() != 0) {
                updateMessage("Hoàn thành");
                updateTitle("Hoàn thành");
            } else {
                updateMessage("Kéo dài video lỗi");
                updateTitle("Kéo dài video lỗi");
            }
            MyFileUtils.deleteFolder(new File(getFolder()));
        } catch (Exception e) {
        }
        return true;
    }

    public static void initTable(TableView tvb, ObservableList<VideoBackgroundFromImage> data) {
        TableColumn<VideoBackgroundFromImage, VideoBackgroundFromImage> sttCol = new TableColumn("STT");
        sttCol.setResizable(false);
        sttCol.setPrefWidth(100);
        sttCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        sttCol.setCellFactory(param -> new TableCell<VideoBackgroundFromImage, VideoBackgroundFromImage>() {
            private Label lb;

            {
                lb = new Label("n/a");
                setGraphic(lb);
            }

            @Override
            public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
                return super.buildEventDispatchChain(tail); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            protected void updateItem(VideoBackgroundFromImage person, boolean empty) {
                super.updateItem(person, empty);
                if (person == null) {
                    setGraphic(null);
                    return;
                }
                try {
                    lb = new Label();
                    lb.setText("" + person.getStt());
                    lb.getStyleClass().addAll("labelTableValue");
                    setGraphic(lb);
                } catch (Exception e) {
                }
            }
        });

        TableColumn<VideoBackgroundFromImage, VideoBackgroundFromImage> noteCol = new TableColumn("Thư mục tạm");
        noteCol.setResizable(false);
        noteCol.setPrefWidth(250);
        noteCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        noteCol.setCellFactory(param -> new TableCell<VideoBackgroundFromImage, VideoBackgroundFromImage>() {
            private Button lb;

            {
                lb = new Button("n/a");
                lb.setPrefWidth(getPrefWidth());
                setGraphic(lb);
            }

            @Override
            public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
                return super.buildEventDispatchChain(tail); //To change body of generated methods, choose Tools | Templates.
            }
            private SSCCheckHoverThread ss;
            private Thread t;

            @Override
            protected void updateItem(VideoBackgroundFromImage person, boolean empty) {
                super.updateItem(person, empty);
                if (person == null) {
                    setGraphic(null);
                    return;
                }

                try {
                    lb = new Button();
                    lb.setText("" + new File(person.getFolder()).getName());
                    lb.getStyleClass().addAll("btnTableValue");
                    GlyphsDude.setIcon(lb, FontAwesomeIcons.FOLDER_OPEN_ALT, "1em", ContentDisplay.LEFT);
                    setGraphic(lb);
                    lb.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                Desktop.getDesktop().open(new File(person.getFolder()));
                            } catch (Exception e) {
                            }
                        }
                    });
                    lb.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            try {
                                ss = new SSCCheckHoverThread();
                                ss.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                    @Override
                                    public void handle(WorkerStateEvent event) {
                                        ((AnchorPane) lb.getScene().lookup("#pnToolTip")).setVisible(true);
                                        ((Label) lb.getScene().lookup("#lbToolTip")).setText("Hành động : mở thư mục " + person.getFolder());
                                    }
                                });
                                t = new Thread(ss);
                                t.setDaemon(true);
                                t.start();
                            } catch (Exception e) {
                            }

                        }
                    });
                    lb.setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            try {
                                t.stop();
                                ss.cancel(true);
                                ((AnchorPane) lb.getScene().lookup("#pnToolTip")).setVisible(false);
                                ((Label) lb.getScene().lookup("#lbToolTip")).setText("");
                            } catch (Exception e) {
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        TableColumn<VideoBackgroundFromImage, VideoBackgroundFromImage> titleCol = new TableColumn("Tốc độ");
        titleCol.setResizable(false);
        titleCol.setPrefWidth(150);
        titleCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        titleCol.setCellFactory(param -> new TableCell<VideoBackgroundFromImage, VideoBackgroundFromImage>() {
            private Label lb;

            {
                lb = new Label("n/a");
                lb.setPrefWidth(getPrefWidth());
                setGraphic(lb);
            }

            @Override
            public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
                return super.buildEventDispatchChain(tail); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            protected void updateItem(VideoBackgroundFromImage person, boolean empty) {
                super.updateItem(person, empty);
                if (person == null) {
                    setGraphic(null);
                    return;
                }
                try {
                    lb = new Label();
                    lb.textProperty().bind(person.titleProperty());
                    lb.getStyleClass().addAll("labelTableValue");
                    setGraphic(lb);

                } catch (Exception e) {
                }
            }
        });

        TableColumn<VideoBackgroundFromImage, VideoBackgroundFromImage> messageCol = new TableColumn("Tiến trình");
        messageCol.setResizable(false);
        messageCol.setPrefWidth(450);
        messageCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        messageCol.setCellFactory(param -> new TableCell<VideoBackgroundFromImage, VideoBackgroundFromImage>() {
            private Label lb;

            {
                lb = new Label("n/a");
                lb.setPrefWidth(getPrefWidth());
                setGraphic(lb);
            }

            @Override
            public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
                return super.buildEventDispatchChain(tail); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            protected void updateItem(VideoBackgroundFromImage person, boolean empty) {
                super.updateItem(person, empty);
                if (person == null) {
                    setGraphic(null);
                    return;
                }
                try {
                    lb = new Label();
                    lb.textProperty().bind(person.messageProperty());
                    lb.getStyleClass().addAll("labelTableValue");
                    setGraphic(lb);

                } catch (Exception e) {
                }
            }
        });

        tvb.getColumns().addAll(sttCol, noteCol, titleCol, messageCol);
        tvb.setItems(data);
        tvb.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

}
