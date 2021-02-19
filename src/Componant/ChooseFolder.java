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
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import static Utils.Constants.DESKTOP_PATH;
import static Utils.Constants.DESKTOP_PATH_ONEDRIVE;
import Utils.MyFileUtils;

/**
 *
 * @author magictool
 */
public class ChooseFolder implements EventHandler<MouseEvent> {

    public TextField eTextField;
    public String title;
    public String fileType = "";
    public String url;

    public ChooseFolder(TextField eTextField, String title, String startPath, String fileType) {
        this.eTextField = eTextField;
        this.title = title;
        this.startPath = startPath;
        this.fileType = fileType;
    }

    public ChooseFolder(TextField eTextField, String title, String startPath) {
        this.eTextField = eTextField;
        this.title = title;
        this.startPath = startPath;
    }
    public String startPath;
    public boolean checkFirst = true;

    public void then() {

    }

    ;
    public void before() {

    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                before();
                if (!(new File(startPath).exists())) {
                    startPath = new File(DESKTOP_PATH).exists() ? DESKTOP_PATH : DESKTOP_PATH_ONEDRIVE;
                }
                if (checkFirst) {
                    DirectoryChooser chooser = new DirectoryChooser();
                    chooser.setTitle(title);
                    chooser.setInitialDirectory(new File(startPath));
                    File selectedDirectory = chooser.showDialog(new Stage());
                    if (selectedDirectory != null) {
                        File list[] = selectedDirectory.listFiles();
                        for (File file : list) {
                            if (fileType.length() != 0 && !MyFileUtils.getFileExtension(file.getName()).equals(fileType)) {
                                return;
                            }
                        }
                        eTextField.setText(selectedDirectory.getAbsolutePath());
                        then();
                    }
                }
            }
        }
    }
}
