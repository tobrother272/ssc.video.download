/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent.View;

import java.io.File;
import java.nio.file.Paths;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import log.RunLog;
import ssc.reup.api.Controller.FXMLLogController;
import static ssc.reup.api.Utils.Graphics.addDragListeners;

/**
 *
 * @author PC
 */
public class FormUltils {

    public static void showLogScreen(Class _class,ObservableList<RunLog> arrLog) {
        try {
            Stage mainStage = new Stage();
            mainStage.initStyle(StageStyle.TRANSPARENT);
            FXMLLoader fxmlLoader = new FXMLLoader(_class.getResource("/ssc/reup/api/Fxml/FXMLLog.fxml"));
            FXMLLogController controller = new FXMLLogController();
            fxmlLoader.setController(controller);
            controller.setArrLogs(arrLog);
            Parent root = (Parent) fxmlLoader.load();
            String uri = Paths.get(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "Styles.css").toUri().toString();
            root.getStylesheets().add(uri);
            addDragListeners(root);
            mainStage.setScene(new Scene(root));
            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
