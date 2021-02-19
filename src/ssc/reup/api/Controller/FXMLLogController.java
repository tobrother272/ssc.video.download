/*
 * and open the template in the editor.
 */
package ssc.reup.api.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import log.RunLog;
/**
 * @author inet
 */
public class FXMLLogController implements Initializable {
        
    private ObservableList<RunLog> arrLogs;
    public ObservableList<RunLog> getArrLogs() {
        return arrLogs;
    }

    public void setArrLogs(ObservableList<RunLog> arrLogs) {
        this.arrLogs = arrLogs;
    }
    @FXML
    private TableView tvRunLog;
    @FXML
    private Label lbLogTitle;
    @FXML
    private Button btCloseLogList;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       RunLog.initTable(tvRunLog, arrLogs);
       btCloseLogList.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
              btCloseLogList.getScene().getWindow().hide();
           }
       });
    }

}
