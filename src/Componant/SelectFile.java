/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componant;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Setting.ToolSetting;
import static Utils.Constants.DESKTOP_PATH;


/**
 *
 * @author magictool
 */
public class SelectFile implements EventHandler<ActionEvent> {

    /*
        textThumbUrl.setOnMouseClicked(new ChooseFile(textThumbUrl, "Chọn file hình :", DESKTOP_PATH, new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter("PNG", "*.png"), new FileChooser.ExtensionFilter("JPG", "*.jpg")
        }));
    
        btAddImageOnBG.setOnAction(new SelectFile(btAddImageOnBG, "Chọn hình nền", DESKTOP_PATH, new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter("PNG", "*.png"), new FileChooser.ExtensionFilter("JPG", "*.jpg")}) {
            @Override
            public void then() {
                addTile(new ImageTile(), getResult().getAbsolutePath());
        }});
    
    
     */
    public Button eButton;
    public String title;
    public FileChooser.ExtensionFilter[] arrFilter;

    public SelectFile(Button eButton, String title, String startPath, FileChooser.ExtensionFilter[] arrFilter) {
        this.eButton = eButton;
        this.title = title;
        this.startPath = startPath;
        this.arrFilter = arrFilter;
    }

    public SelectFile(Button eButton, String title, String startPath) {
        this.eButton = eButton;
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

    public File getResult() {
        return result;
    }

    public void setResult(File result) {
        this.result = result;
    }
    @Override
    public void handle(ActionEvent event) {
        before();
        if (checkFirst) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(title);
            File initFile = null;
            try {
                initFile = new File(ToolSetting.getInstance().getPre().get(eButton.getId(), DESKTOP_PATH));
                if(!initFile.exists()){
                    initFile= new File(DESKTOP_PATH);
                }
            } catch (Exception e) {
                initFile = new File(DESKTOP_PATH);
            }
            fileChooser.setInitialDirectory(initFile);
            fileChooser.getExtensionFilters().addAll(arrFilter);
            File selectedDirectory = fileChooser.showOpenDialog(new Stage());
            if (selectedDirectory != null) {
                result=selectedDirectory;
                ToolSetting.getInstance().getPre().put(eButton.getId(), selectedDirectory.getParent());
                then();
            }
        }
    }
}
