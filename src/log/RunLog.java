/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package log;

import io.appium.java_client.android.AndroidDriver;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import static Utils.Constants.ERROR_FOLDER;
import static SQliteUtils.SQLIteHelper.executeQuery;
import SQliteUtils.SQLiteConnection;
import ssc.reup.api.Utils.SeleniumUtils;
import Utils.StringUtil;
import static Utils.StringUtil.getCurrentDateTime;
/**
 * @author inet
 */
public class RunLog {

    private SimpleIntegerProperty log_id;
    private SimpleIntegerProperty log_type;

    public SimpleIntegerProperty log_typeProperty() {
        return log_type;
    }

    public int getLog_type() {
        return log_type.get();
    }

    public void setLog_type(SimpleIntegerProperty log_type) {
        this.log_type = log_type;
    }
    private SimpleStringProperty log_title;
    private SimpleStringProperty log_date;
    private SimpleStringProperty log_content;

    public RunLog(String log_title, String log_date, String log_content) {
        this.log_title = new SimpleStringProperty(log_title);
        this.log_date = new SimpleStringProperty(log_date);
        this.log_content = new SimpleStringProperty(log_content);
    }

    public RunLog(int log_id, String log_title, String log_date, String log_content) {
        this.log_id = new SimpleIntegerProperty(log_id);
        this.log_title = new SimpleStringProperty(log_title);
        this.log_date = new SimpleStringProperty(log_date);
        this.log_content = new SimpleStringProperty(log_content);
    }

    public RunLog(int log_id, String log_title, String log_date, String log_content, int log_type) {
        this.log_id = new SimpleIntegerProperty(log_id);
        this.log_title = new SimpleStringProperty(log_title);
        this.log_date = new SimpleStringProperty(log_date);
        this.log_content = new SimpleStringProperty(log_content);
        this.log_type = new SimpleIntegerProperty(log_type);
    }

    public SimpleIntegerProperty log_idProperty() {
        return log_id;
    }

    public int getLog_id() {
        return log_id.get();
    }

    public void setLog_id(SimpleIntegerProperty log_id) {
        this.log_id = log_id;
    }

    public SimpleStringProperty log_titleProperty() {
        return log_title;
    }

    public String getLog_title() {
        return log_title.get();
    }

    public void setLog_title(String log_title) {
        this.log_title.set(log_title);
    }

    public SimpleStringProperty log_dateProperty() {
        return log_date;
    }

    public String getLog_date() {
        return log_date.get();
    }

    public void setLog_date(SimpleStringProperty log_date) {
        this.log_date = log_date;
    }

    public SimpleStringProperty getLog_content() {
        return log_content;
    }

    public void setLog_content(SimpleStringProperty log_content) {
        this.log_content = log_content;
    }

    public static void initTable(TableView tv, ObservableList<RunLog> data) {
        TableColumn<RunLog, String> log_titleCol = new TableColumn("Trạng thái");
        TableColumn<RunLog, String> log_dateCol = new TableColumn("Thời gian");
        log_titleCol.setCellValueFactory(new PropertyValueFactory<>("log_title"));
        log_titleCol.setStyle("-fx-alignment: CENTER_LEFT;");
        log_dateCol.setCellValueFactory(new PropertyValueFactory<>("log_date"));
        log_titleCol.setResizable(false);
        log_titleCol.setPrefWidth(tv.getPrefWidth()-100);
        log_dateCol.setResizable(false);
        log_dateCol.setPrefWidth(100);
        tv.getColumns().addAll(log_dateCol, log_titleCol);
        tv.setItems(data);
        PseudoClass highValuePseudoClass = PseudoClass.getPseudoClass("error-value");
        PseudoClass DefaultValuePseudoClass = PseudoClass.getPseudoClass("default-value");
        PseudoClass WarningValuePseudoClass = PseudoClass.getPseudoClass("warning-value");
        tv.setRowFactory(row -> new TableRow<RunLog>() {
            @Override
            public void updateItem(RunLog item, boolean empty) {
                super.updateItem(item, empty);
                if ((!empty)) {
                    pseudoClassStateChanged(highValuePseudoClass, (!empty) && item.getLog_type() == 0);
                    pseudoClassStateChanged(WarningValuePseudoClass, (!empty) && item.getLog_type() == 1);
                    pseudoClassStateChanged(DefaultValuePseudoClass, (!empty) && item.getLog_type() == 2);
                }
            }
        });
       
    }

    public static boolean showTable(ObservableList<RunLog> data) {
        String query = " Select * from Log ";
        Statement stmt = null;
        ResultSet rs = null;
        Connection c = SQLiteConnection.getInstance().getConnection();
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                RunLog sscGmail = new RunLog(rs.getInt("log_id"), rs.getString("log_title"), rs.getString("log_date"), rs.getString("log_content"), rs.getInt("log_type"));
                data.add(sscGmail);
            }
            rs.close();
            stmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertRunLog(String subtitle, String title, Exception e, WebDriver driver, ObservableList<RunLog> dataLog) {
        try {
//String fileName = StringUtils.getCurrentTimeStamp() + ".jpg";
            String fileName = getCurrentDateTime("hhmmssddMMYYYY") + ".jpg";
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String datetime = (new SimpleDateFormat("HH:mm:ss - dd/MM/yy").format(new Date()));
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\ErrorImage\\" + fileName));
            dataLog.add(new RunLog(0, fileName + "#" + subtitle + " " + title, datetime, sw.toString(), 0));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean insertRunLog(String title, WebDriver driver, ObservableList<RunLog> dataLog) {
        try {
            String fileName = getCurrentDateTime("hhmmssddMMYYYY") + ".jpg";
            String datetime = (new SimpleDateFormat("HH:mm:ss - dd/MM/yy").format(new Date()));
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\ErrorImage\\" + fileName));
            dataLog.add(new RunLog(0, fileName + "#" + title, datetime, "", 0));
            return true;
        } catch (Exception ex) {
            Logger.getLogger(RunLog.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public static boolean insertRunLog(String title, Exception e, WebDriver driver, ObservableList<RunLog> dataLog) {
        //String fileName =getCurrentTimeStamp() + ".jpg";
        try {
            StringWriter sw = new StringWriter();
            String datetime = (new SimpleDateFormat("HH:mm:ss - dd/MM/yy").format(new Date()));
            e.printStackTrace(new PrintWriter(sw));
            //if (executeQuery(getConnectionLog(), query)) {
            dataLog.add(new RunLog(0, title, datetime, sw.toString(), 0));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean insertRunLog(String subtitle, String title, Exception e, ObservableList<RunLog> dataLog) {
        try {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String datetime = (new SimpleDateFormat("HH:mm:ss - dd/MM/yy").format(new Date()));
            RunLog sscLog =new RunLog(0, subtitle + " " + title + " " + sw.toString(), datetime, sw.toString(), 0);
            String query = "INSERT INTO LOG (log_title,log_date,log_content,log_type) values ('" + sscLog.getLog_title().replaceAll("'"," ") + "','" + sscLog.getLog_date() + "','" + sscLog.getLog_content().get().replaceAll("'"," ") + "'," + sscLog.getLog_type() + ")";
            //if (executeQuery(getConnectionLog(), query).length() == 0) {
                dataLog.add(sscLog);
            //}
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean insertRunLogWarning(String subtitle, String title, Exception e, ObservableList<RunLog> dataLog) {
        try {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String datetime = (new SimpleDateFormat("HH:mm:ss - dd/MM/yy").format(new Date()));
            dataLog.add(new RunLog(0, subtitle + " " + title, datetime, sw.toString(), 1));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean insertRunLogWarning(String subtitle, String title, String content, ObservableList<RunLog> dataLog) {
        try {
            String datetime = (new SimpleDateFormat("HH:mm:ss - dd/MM/yy").format(new Date()));
            RunLog sscLog = new RunLog(0, subtitle + " " + title, datetime, content, 1);
            String query = "INSERT INTO LOG (log_title,log_date,log_content,log_type) values ('" + sscLog.getLog_title().replaceAll("'"," ") + "','" + sscLog.getLog_date() + "','" + sscLog.getLog_content().get().replaceAll("'"," ") + "'," + sscLog.getLog_type() + ")";
            //if (executeQuery(getConnectionLog(), query).length() == 0) {
                dataLog.add(sscLog);
            //}
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static void screenshot(AndroidDriver driver, String fileName) {
        try {
            File srcFile = driver.getScreenshotAs(OutputType.FILE);
            File targetFile = new File(System.getProperty("user.dir") + "\\ErrorImage\\" + fileName);
            FileUtils.copyFile(srcFile, targetFile);
        } catch (Exception e) {
        }
    }


    public static boolean insertRunLogWithImage(String subtitle, String title, ObservableList<RunLog> dataLog, WebDriver driver) {
        try {
            String _datetime = StringUtil.getCurrentDateTime("ddMMyyhhmmss");
            String datetime = (new SimpleDateFormat("HH:mm:ss - dd/MM/yy").format(new Date()));
            dataLog.add(new RunLog(0, subtitle + " " + title, datetime, "", 0));
            SeleniumUtils.saveErrorImage(driver, ERROR_FOLDER + "\\" + _datetime + ".jpg");
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean insertRunLogWithImage(String subtitle, String title, ObservableList<RunLog> dataLog, WebDriver driver, Exception ex) {
        try {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String _datetime = StringUtil.getCurrentDateTime("ddMMyyhhmmss");
            String datetime = (new SimpleDateFormat("HH:mm:ss - dd/MM/yy").format(new Date()));
            dataLog.add(new RunLog(0, subtitle + " " + title, datetime, sw.toString(), 0));
            SeleniumUtils.saveErrorImage(driver, ERROR_FOLDER + "\\" + _datetime + ".jpg");
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean insertRunLogWithImage(String subtitle, String title, String content, ObservableList<RunLog> dataLog, WebDriver driver) {
        try {
            String datetime = StringUtil.getCurrentDateTime("ddMMyyhhmmss");
            dataLog.add(new RunLog(0, subtitle + " " + title, datetime, content, 0));
            SeleniumUtils.saveErrorImage(driver, ERROR_FOLDER + "\\" + datetime + ".jpg");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean insertRunLog(String subtitle, String title, String content, ObservableList<RunLog> dataLog) {
        try {
            String datetime = (new SimpleDateFormat("HH:mm:ss - dd/MM/yy").format(new Date()));
            RunLog sscLog = new RunLog(0, subtitle + " " + title, datetime, content, 0);
            String query = "INSERT INTO LOG (log_title,log_date,log_content,log_type) values ('" + sscLog.getLog_title().replaceAll("'"," ") + "','" + sscLog.getLog_date() + "','" + sscLog.getLog_content().get().replaceAll("'"," ") + "'," + sscLog.getLog_type() + ")";
            //if (executeQuery(getConnectionLog(), query).length() == 0) {
                dataLog.add(0,sscLog);
            //}
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean insertRunLogSuccess(String subtitle, String title, String content, ObservableList<RunLog> dataLog) {
        try {
            String datetime = (new SimpleDateFormat("HH:mm:ss - dd/MM/yy").format(new Date()));
            RunLog sscLog = new RunLog(0, subtitle + " " + title, datetime, content, 2);
            String query = "INSERT INTO LOG (log_title,log_date,log_content,log_type) values ('" + sscLog.getLog_title().replaceAll("'"," ") + "','" + sscLog.getLog_date() + "','" + sscLog.getLog_content().get().replaceAll("'"," ") + "'," + sscLog.getLog_type() + ")";
            //if (executeQuery(getConnectionLog(), query).length() == 0) {
                dataLog.add(0,sscLog);
            //}
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean insertRunLogCompleted(String subtitle, String title, String content, ObservableList<RunLog> dataLog) {
        try {
            String datetime = (new SimpleDateFormat("HH:mm:ss - dd/MM/yy").format(new Date()));
            dataLog.add(new RunLog(0, subtitle + " " + title, datetime, content, 2));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String clearLog() {
        String query = "DELETE from LOG";
        return executeQuery(query);
    }
}
