/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent.View;

import FormComponent.SSCStatusCombobox;
import FormComponent.SSCStatusLabel;
import static Setting.ToolSetting.CHANGEIPMODE;
import static Setting.ToolSetting.THREADS;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import ssc.reup.api.task.CountRunTimeTask;

/**
 *
 * @author PC
 */
public abstract class SSCProcessView {
    public abstract void runEventBody();
    public  EventHandler<ActionEvent> runEvent(){
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                runEventBody();
            }
        };
    };
    public abstract void timerTaskBody();
    public  TimerTask timerTask(){
        return new TimerTask() {
            @Override
            public void run() {
                timerTaskBody();
            }
        };        
    };
    public abstract boolean stopRunningTaskBody();
    public StopRunningTask stopRunningTask(){
        return new StopRunningTask() {
            @Override
            public boolean mainThread() {
                return stopRunningTaskBody();
            }
        };     
    };
    private GridPane apStatus;

    public GridPane getApStatus() {
        return apStatus;
    }

    private GridPane apHorizonStatus;

    public GridPane getApHorizonStatus() {
        return apHorizonStatus;
    }

    SSCStatusLabel statusTime;
    SSCStatusLabel statusThread;
    SSCStatusLabel statusSuccess;
    SSCStatusLabel statusError;
    SSCStatusLabel statusFreeSpace;
    SSCStatusLabel statusCpu;
    SSCStatusCombobox statusRunThread;
    SSCStatusCombobox statusRunResetIpMode;
    private Button btRuButton;

    public Button getBtRuButton() {
        return btRuButton;
    }

    public void setBtRuButton(Button btRuButton) {
        this.btRuButton = btRuButton;
    }

    private int thread = 1;
    private int resetMode = 0;
    private int success = 0;
    private int error = 0;

    public void increaseSuccess() {
        this.success++;
    }

    public void increaseError() {
        this.error++;
    }

    public int getThread() {
        return thread;
    }

    public void setThread(int thread) {
        this.thread = thread;
    }

    public int getResetMode() {
        return resetMode;
    }

    public void setResetMode(int resetMode) {
        this.resetMode = resetMode;
    }

    public void goToRunReadyMode() {
        btRuButton.setText("  Thực Hiện ");
        GlyphsDude.setIcon(btRuButton, FontAwesomeIcons.valueOf("FAST_FORWARD"), "1.5em", ContentDisplay.LEFT);
        btRuButton.getStyleClass().setAll("btnRunTaskProcess");
        btRuButton.setOnAction(runEvent());
    }

    public void goToStopReadyMode() {
        btRuButton.setText("  Ngừng Ngay ");
        GlyphsDude.setIcon(btRuButton, FontAwesomeIcons.valueOf("STOP"), "1.5em", ContentDisplay.LEFT);
        btRuButton.getStyleClass().setAll("btnStopTaskProcess");
        btRuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stopTask();
                goToRunReadyMode();
                StopRunningTask st = stopRunningTask();
                btRuButton.disableProperty().bind(st.runningProperty());
                st.start();
            }
        });
    }
    public void initView(AnchorPane rootDialog, double x, double y, double w, double h) {
        apStatus = new GridPane();
        apStatus.setHgap(5);
        apStatus.setVgap(5);
        apStatus.setPrefSize(w, h);
        apStatus.setLayoutX(x);
        apStatus.getStyleClass().setAll("processPane");
        apStatus.setLayoutY(y);
        rootDialog.getChildren().add(apStatus);

        statusTime = new SSCStatusLabel("CLOCK_ALT", "Thời gian chạy", w / 2, 50);
        statusThread = new SSCStatusLabel("FORWARD", "Tổng luồng", w / 2, 50);
        statusSuccess = new SSCStatusLabel("CHECK", "Thành Công", w / 2, 50);
        statusError = new SSCStatusLabel("CLOSE", "Lỗi", w / 2, 50);
        statusFreeSpace = new SSCStatusLabel("DATABASE", "Dung lượng", w / 2, 50);
        statusRunThread = new SSCStatusCombobox("WINDOWS", "Số Luồng", THREADS, w / 2, 50);
        statusRunResetIpMode = new SSCStatusCombobox("WIFI", "Chế Độ Reset Ip", CHANGEIPMODE, w / 2, 50);
        btRuButton = new Button("  Thực Hiện");
        btRuButton.setPrefWidth((w / 2) - 20);
        btRuButton.getStyleClass().setAll("btnRunTaskProcess");
        GlyphsDude.setIcon(btRuButton, FontAwesomeIcons.valueOf("FAST_FORWARD"), "1.5em", ContentDisplay.LEFT);
        btRuButton.setOnAction(runEvent());
        apStatus.add(statusTime.getView(), 0, 0);
        apStatus.add(statusThread.getView(), 1, 0);
        apStatus.add(statusSuccess.getView(), 0, 1);
        apStatus.add(statusError.getView(), 1, 1);
        apStatus.add(statusFreeSpace.getView(), 0, 2);
        apStatus.add(statusRunThread.getView(), 1, 2);
        apStatus.add(btRuButton, 1, 3);
        //apStatus.getChildren().add(statusRunResetIpMode.getView());
        btRuButton.setOnAction(runEvent());
        statusRunThread.valueProperties().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                thread = newValue.intValue() + 1;
                updateProcess();
            }
        });
        statusRunResetIpMode.valueProperties().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                resetMode = newValue.intValue() + 1;
            }
        });

    }

    public void initHorizontalView(AnchorPane rootDialog, double x, double y, double w, double h) {
        apHorizonStatus = new GridPane();
        apHorizonStatus.setPrefSize(w, h);
        apHorizonStatus.setLayoutX(x);
        apHorizonStatus.getStyleClass().setAll("processPane");
        apHorizonStatus.setLayoutY(y);
        rootDialog.getChildren().add(apHorizonStatus);

        Double sizeChild = rootDialog.getPrefWidth() / 4;
        statusTime = new SSCStatusLabel("CLOCK_ALT", "Thời gian chạy", sizeChild, 50);
        statusThread = new SSCStatusLabel("FORWARD", "Tổng luồng", sizeChild, 50);
        statusSuccess = new SSCStatusLabel("CHECK", "Thành Công", sizeChild, 50);
        statusError = new SSCStatusLabel("CLOSE", "Lỗi", sizeChild, 50);
        statusFreeSpace = new SSCStatusLabel("DATABASE", "Dung lượng", sizeChild, 50);
        statusRunThread = new SSCStatusCombobox("WINDOWS", "Số Luồng", THREADS, sizeChild, 50);
        statusRunResetIpMode = new SSCStatusCombobox("WIFI", "Chế Độ Reset Ip", CHANGEIPMODE, sizeChild, 50);
        statusCpu = new SSCStatusLabel("DATABASE", "CPU/Ram", sizeChild, 50);

        btRuButton = new Button("  Thực Hiện");
        btRuButton.setPrefWidth(sizeChild - 20);
        btRuButton.getStyleClass().setAll("btnRunTaskProcess");
        GlyphsDude.setIcon(btRuButton, FontAwesomeIcons.valueOf("FAST_FORWARD"), "1.5em", ContentDisplay.LEFT);
        btRuButton.setOnAction(runEvent());
        apHorizonStatus.add(statusTime.getView(), 0, 0);
        apHorizonStatus.add(statusThread.getView(), 1, 0);
        apHorizonStatus.add(statusSuccess.getView(), 2, 0);
        apHorizonStatus.add(statusError.getView(), 3, 0);
        apHorizonStatus.add(statusFreeSpace.getView(), 0, 1);
        apHorizonStatus.add(statusRunThread.getView(), 1, 1);
        apHorizonStatus.add(statusCpu.getView(), 2, 1);
        apHorizonStatus.add(btRuButton, 3, 1);
        //apStatus.getChildren().add(statusRunResetIpMode.getView());

        statusRunThread.valueProperties().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                thread = newValue.intValue() + 1;
                updateProcess();
            }
        });
        statusRunResetIpMode.valueProperties().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                resetMode = newValue.intValue() + 1;
            }
        });

    }

    private String localRunPath = System.getProperty("user.dir");
    private int currentFreeSpace = 0;
    private CountRunTimeTask taskCountTime;
    private int currentRunningPosition = 0;
    private int countRunning = 0;

    public void startTask() {
        updateProcess();
        currentRunningPosition = 0;
        countRunning = 0;
        success = 0;
        error = 0;
        taskCountTime = new CountRunTimeTask();
        statusTime.textProperty().bind(taskCountTime.messageProperty());
        if (statusCpu != null) {
            statusCpu.textProperty().bind(taskCountTime.titleProperty());
        }
        taskCountTime.start();
        timer = new Timer();
        timer.schedule(timerTask(), 1000, 1000);
        goToStopReadyMode();
    }

    public void stopTask() {
        if (taskCountTime != null) {
            taskCountTime.stop();
            taskCountTime = null;
        }
        if (timer != null) {
            timer.cancel();
        }
        

    }

    public String getLocalRunPath() {
        return localRunPath;
    }

    public void setLocalRunPath(String localRunPath) {
        this.localRunPath = localRunPath;
    }

    public int getCurrentRunningPosition() {
        return currentRunningPosition;
    }

    public void increaseRunningPosition() {
        this.currentRunningPosition++;
    }

    public int getCountRunning() {
        return countRunning;
    }

    public void increaseRunning() {
        this.countRunning++;
    }

    public void decreaseRunning() {
        this.countRunning--;
    }

    public int getCurrentFreeSpace() {
        return currentFreeSpace;
    }

    public void setCurrentFreeSpace(int currentFreeSpace) {
        this.currentFreeSpace = currentFreeSpace;
    }
    Timer timer;

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void updateProcess() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                statusThread.setText(countRunning + "/" + thread + " Luồng");
                statusSuccess.setText(success + " ");
                statusError.setText(error + " ");
                if (localRunPath != null) {
                    currentFreeSpace = (int) (new File(localRunPath).getFreeSpace() / 1024 / 1024 / 1024);
                    statusFreeSpace.setText("" + currentFreeSpace + " GB");
                }
            }
        });
    }
}
