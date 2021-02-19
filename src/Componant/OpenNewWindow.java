/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Componant;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import ssc.reup.api.Controller.FXMLDocumentController;
import static ssc.reup.api.Utils.Graphics.addDragListeners;

/**
 *
 * @author magictool
 */
public abstract class OpenNewWindow {
    private URL xmlUrl;
    private boolean tranparentMode;
    private String stylesFile;
    public OpenNewWindow(URL xmlUrl, boolean tranparentMode, String stylesFile) {
        this.xmlUrl = xmlUrl;
        this.tranparentMode = tranparentMode;
        this.stylesFile = stylesFile;
        main();
    }
    public void main() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(xmlUrl);
            Parent root = (Parent) fxmlLoader.load();
            String uri = Paths.get(stylesFile).toUri().toString();
            root.getStylesheets().add(uri);
            Stage stageContentTool = new Stage();
            if(tranparentMode){
                addDragListeners(root);
                stageContentTool.initStyle(StageStyle.TRANSPARENT);
            }
            stageContentTool.setScene(new Scene(root));
            stageContentTool.show();
            stageContentTool.setOnHiding(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                   hidingWindow();
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public abstract void hidingWindow();
}
