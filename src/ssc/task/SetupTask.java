/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.task;

import Utils.CMDUtils;
import java.io.File;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventDispatchChain;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONObject;
import org.sikuli.script.Screen;

/**
 *
 * @author PC
 */
public class SetupTask extends Task<Boolean> {

    private String setupTitle;
    private String path;
    private int status;
    private String downloadLink;
    private String destinationPath;
    private int checkMode;
    private boolean needInstall = false;

    public boolean isNeedInstall() {
        return needInstall;
    }

    public void setNeedInstall(boolean needInstall) {
        this.needInstall = needInstall;
    }

    public int getCheckMode() {
        return checkMode;
    }

    public void setCheckMode(int checkMode) {
        this.checkMode = checkMode;
    }

    public SetupTask(JSONObject oj, List<String> linkDownload) {
        this.setupTitle = oj.get("name").toString();
        this.path = oj.get("path").toString();
        needInstall = Boolean.parseBoolean(oj.get("need").toString());
        this.checkMode = Integer.parseInt(oj.get("checkMode").toString());
        this.destinationPath = oj.get("destinationPath").toString();
        if (oj.get("downloadLink").toString().length() == 0) {
            this.downloadLink = "";
        } else {
            for (String string : linkDownload) {
                if (string.contains(oj.get("downloadLink").toString())) {
                    this.downloadLink = string;
                }
            }
        }
    }

    /**
     * 0: check install file exist 1: check folder exit 2: check java version
     * 3:sikulix check command line
     *
     * @return
     */
    public static Boolean checkSikuli() {
        try {
            Boolean checkSikuli = false;
            try {
                new Screen().doubleClick();
                checkSikuli = true;
            } catch (Exception e) {
            }
            return checkSikuli;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean checkReady() {
        try {
            switch (this.checkMode) {
                case 0:
                    return new File(destinationPath).exists();
                case 1:
                    return new File(destinationPath).exists();
                case 2:
                    return System.getProperty("java.version").contains(destinationPath);
                case 3:
                    return checkSikuli();
                case 4:
                    return !CMDUtils.checkCmdExits(destinationPath, "is not recognized");
                default:
                    return false;

            }
        } catch (Exception e) {
        }
        return false;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getSetupTitle() {
        return setupTitle;
    }

    public void setSetupTitle(String setupTitle) {
        this.setupTitle = setupTitle;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public SetupTask(String setupTitle, String downloadLink, String path, int status, String destinationPath) {
        this.setupTitle = setupTitle;
        this.downloadLink = downloadLink;
        this.path = path;
        this.status = status;
        this.destinationPath = destinationPath;
    }

    @Override
    protected Boolean call() {
        try {
            updateTitle("Đang cài đặt " + setupTitle);
            if (checkMode != 4) {
                if (path.endsWith(".zip")) {
                    updateMessage("Đang giải nén ");
                    CMDUtils.extractZip(path, destinationPath);
                } else {
                    updateMessage("Hãy cài đặt phần mềm ");
                    CMDUtils.openAnotherApp(path);
                }
            }else{
                updateMessage("Đang cài đặt package "+setupTitle);
                CMDUtils.runCmd(path);
            }

            updateMessage("Hoàn thành");
        } catch (Exception e) {
        }
        return true;
    }

    public void start() {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }

    public static void initTable(TableView tv, ObservableList<SetupTask> data) {
        TableColumn<SetupTask, SetupTask> infoCol = new TableColumn("");
        infoCol.setResizable(false);
        infoCol.setPrefWidth(tv.getPrefWidth());
        infoCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        infoCol.setCellFactory(param -> new TableCell<SetupTask, SetupTask>() {
            private Label lbTitle;
            private Label lbMessage;

            @Override
            public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
                return super.buildEventDispatchChain(tail); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            protected void updateItem(SetupTask videoData, boolean empty) {
                super.updateItem(videoData, empty);
                if (videoData == null) {
                    setGraphic(null);
                    return;
                }
                try {
                    AnchorPane apRoot = new AnchorPane();
                    lbTitle = new Label(videoData.getSetupTitle());
                    lbTitle.setPrefSize(apRoot.getPrefWidth(), 30);
                    lbTitle.setLayoutX(10);
                    lbTitle.setLayoutY(5);
                    lbTitle.getStyleClass().setAll("lbSetUpTitle");
                    apRoot.getChildren().add(lbTitle);
                    lbMessage = new Label();
                    lbMessage.setPrefSize(apRoot.getPrefWidth(), 30);
                    lbMessage.setLayoutX(10);
                    lbMessage.setLayoutY(25);
                    apRoot.getChildren().add(lbMessage);
                    lbMessage.getStyleClass().setAll("lbProcessMessage");
                    lbMessage.textProperty().bind(videoData.messageProperty());
                    setGraphic(apRoot);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        tv.getColumns().addAll(infoCol);
        tv.setItems(data);
    }

}
