
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import java.nio.file.Paths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;


/**
 * @author inet
 */
public class SscReupApi extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("SSC Account Manager");
        Parent root = FXMLLoader.load(getClass().getResource("/ssc/reup/api/Fxml/FXMLSplash.fxml"));
        String uri = Paths.get(System.getProperty("user.dir") + "\\assets\\Styles.css").toUri().toString();
        root.getStylesheets().add(uri);
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(null);
        stage.setScene(scene);
        stage.show();
        scene.getWindow().centerOnScreen();
    }

    /**
     * @param args the command line arguments
     */
    static void main(String[] args) {
        launch(args);
    }

}
