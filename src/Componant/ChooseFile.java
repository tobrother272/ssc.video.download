/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componant;

import java.io.File;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Setting.ToolSetting;
import static Utils.Constants.DESKTOP_PATH;
import static Utils.Constants.DESKTOP_PATH_ONEDRIVE;

/**
 * @author magictool
 */
public class ChooseFile implements EventHandler<MouseEvent> {

    /*
        textThumbUrl.setOnMouseClicked(new ChooseFile(textThumbUrl, "Chọn file hình :", DESKTOP_PATH, new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter("PNG", "*.png"), new FileChooser.ExtensionFilter("JPG", "*.jpg")
        }));
     */ 
    public TextField eTextField;
    public String title;
    public FileChooser.ExtensionFilter[] arrFilter;
    private String namePreSave;

    public ChooseFile(TextField eTextField, String title, String startPath, FileChooser.ExtensionFilter[] arrFilter, String namePreSave) {
        this.eTextField = eTextField;
        this.title = title;
        this.startPath = startPath;
        this.arrFilter = arrFilter;
        this.namePreSave = namePreSave;
    }

    public ChooseFile(TextField eTextField, String title, String startPath) {
        this.eTextField = eTextField;
        this.title = title;
        this.startPath = startPath;
    }
    public String startPath;
    public boolean checkFirst = true;

    public void then() {

    }

    public void before() {

    }
    private File result;

    @Override
    public void handle(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                before();
                if (checkFirst) {
                    try {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle(title);
                        File initFile = null;
                        initFile = new File(ToolSetting.getInstance().getPre().get(namePreSave, DESKTOP_PATH));
                        if (!initFile.exists()) {
                            initFile = new File(DESKTOP_PATH).exists() ? new File(DESKTOP_PATH) : new File(DESKTOP_PATH_ONEDRIVE);
                        }
                        fileChooser.setInitialDirectory(initFile);
                        fileChooser.getExtensionFilters().addAll(arrFilter);
                        File selectedDirectory = fileChooser.showOpenDialog(new Stage());
                        if (selectedDirectory != null) {
                            result = selectedDirectory;
                            eTextField.setText(selectedDirectory.getAbsolutePath());
                            ToolSetting.getInstance().getPre().put(namePreSave, selectedDirectory.getParent());
                            then();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToolSetting.getInstance().getPre().put(namePreSave, DESKTOP_PATH);
                    }
                }
            }
        }
    }

    public File getResult() {
        return result;
    }

    public void setResult(File result) {
        this.result = result;
    }
}
