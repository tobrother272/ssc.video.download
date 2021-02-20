/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainView.SubView;

import FormComponent.Dialog.SSCInputDialog;
import FormComponent.View.SSCChildView;
import FormComponent.SSCButtonChildTab;
import FormComponent.SSCTextField;
import FormComponent.View.FormUltils;
import FormComponent.View.SSCMessage;
import FormComponent.View.SSCProcessTaskView;
import Setting.ToolSetting;
import Utils.MyFileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import ssc.task.EventFinistTask;
import ssc.video.YoutubeApiVideo;
import ssc.video.task.ScanVideoTask;

/**
 * @author PC
 */
public class ChanelManagerView extends SSCChildView {

    public ChanelManagerView(Scene scene) {
        super(scene);
    }

    @Override
    public String getBreadScrumbTitle() {
        return "Download video";
    }

    @Override
    public String getBreadScrumbDescription() {
        return "Cài đặt scan và download video youtube";
    }

    @Override
    public String getLeftMenuIcon() {
        return "YOUTUBE";
    }

    @Override
    public int getTabIndex() {
        return 0;
    }
    private SSCProcessTaskView youtubeVideos;
    private AnchorPane apYoutubeVideo;
    private ObservableList<YoutubeApiVideo> arrVideos;
    private ObservableList<YoutubeApiVideo> arrVideoSelects;
    private SSCInputDialog scanDialog;
    private SSCTextField txtYoutubeKeyword;
    private SSCTextField txtVideoSave;
    private SSCTextField txtNumberVideoNeed;

    @Override
    public void initView(Scene scene) {
        arrVideos = FXCollections.observableArrayList();
        arrVideoSelects = FXCollections.observableArrayList();
        apYoutubeVideo = (AnchorPane) scene.lookup("#apYoutubeVideo");

       

        youtubeVideos = new SSCProcessTaskView(apYoutubeVideo, "Danh sách video", arrVideos, Arrays.asList()) {
            @Override
            public void runEventBody() {
                arrVideoSelects.clear();
                arrVideoSelects.addAll(youtubeVideos.getTv().getSelectionModel().getSelectedItems());
                if (arrVideoSelects.size() == 0) {
                    SSCMessage.showWarning(scene, "Chọn danh sách video cần download");
                    return;
                }
                arrVideos.setAll(arrVideoSelects);
                youtubeVideos.startTask();
            }

            @Override
            public void timerTaskBody() {
                if (getCountRunning() < getThread() && getCurrentRunningPosition() < arrVideos.size()) {
                    YoutubeApiVideo video = arrVideos.get(getCurrentRunningPosition());
                    increaseRunning();
                    increaseRunningPosition();
                    video.setPath(txtVideoSave.getText() + File.separator + video.getId() + ".mp4");
                    video.eventListener(new EventFinistTask() {
                        @Override
                        public void main() {
                            decreaseRunning();
                            if (new File(txtVideoSave.getText() + File.separator + video.getId() + ".mp4").exists()) {
                                increaseSuccess();
                            } else {
                                increaseError();
                            }
                        }
                    });
                    video.start();
                }
                if (getCountRunning() == 0 && getCurrentRunningPosition() >= arrVideos.size()) {
                    stopTask();
                }
            }

            @Override
            public boolean stopRunningTaskBody() {
                try {
                    for (YoutubeApiVideo video : arrVideos) {
                        video.stop();
                    }
                } catch (Exception ex) {

                }
                return true;
            }
        };
        YoutubeApiVideo.initTable(youtubeVideos.getTv(), arrVideos);
         youtubeVideos.getTv().setRowFactory(tv -> {
            TableRow<YoutubeApiVideo> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() == 2) {
                    YoutubeApiVideo account = (YoutubeApiVideo) row.getItem();
                    if (account == null) {
                        return;
                    }
                    if (event.getButton() == MouseButton.PRIMARY) {
                        ToolSetting.openUrl("https://www.youtube.com/watch?v="+account.getId());
                    }
                }
            });
            return row;
        });
        scanDialog = new SSCInputDialog(scene, 3, "Cài đặt quét", "Nhập từ khóa|id kênh|id Playlist để quét", "Quét ngay");
        txtYoutubeKeyword = new SSCTextField("Từ khóa", "Nhập từ khóa tìm kiếm , id kênh , id playlist", scanDialog.getForm().getFormElement(), "txtYoutubeKeyword", Arrays.asList("require"));
        txtVideoSave = new SSCTextField("Thư mục lưu", "Click đúp chọn thư mục", scanDialog.getForm().getFormElement(), "txtVideoSave", Arrays.asList("require"));
        txtVideoSave.enableSelectFolderMode();

        txtNumberVideoNeed = new SSCTextField("Số lượng video", "Nhập số video cần scan", scanDialog.getForm().getFormElement(), "txtNumberVideoNeed", Arrays.asList("require"));

        scanDialog.getForm().getSubmit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (txtYoutubeKeyword.isValidate() || txtNumberVideoNeed.isValidate() || txtVideoSave.isValidate()) {
                    return;
                }
                scanDialog.getForm().saveData();
                arrVideos.clear();
                ScanVideoTask svt = new ScanVideoTask(arrVideos, txtYoutubeKeyword.getText().trim().replaceAll(" ","+"), Integer.parseInt(txtNumberVideoNeed.getText().trim()));
                svt.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        scanDialog.hide();
                    }
                });
                scanDialog.getForm().getSubmit().disableProperty().bind(svt.runningProperty());
                svt.start();
            }
        });

    }

    @Override
    public void reloadView() {

    }

    @Override
    public List<SSCButtonChildTab> getListMenuButton() {
        List<SSCButtonChildTab> arrButton = new ArrayList<>();
        arrButton.add(new SSCButtonChildTab("Quét Video", "SEARCH", "Cài đặt quét video", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scanDialog.show();
            }
        }));
        return arrButton;

    }

}
