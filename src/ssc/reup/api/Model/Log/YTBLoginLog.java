/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.Model.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import static SQliteUtils.SQLIteHelper.executeQuery;
import static SQliteUtils.SQLIteHelper.getConnectionLog;

/**
 * @author inet
 */
public class YTBLoginLog {

    private String accountUsername;
    private String accountError;
    private String accountErrorDate;
    private int stt;

    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public String getAccountError() {
        return accountError;
    }

    public void setAccountError(String accountError) {
        this.accountError = accountError;
    }

    public String getAccountErrorDate() {
        return accountErrorDate;
    }

    public void setAccountErrorDate(String accountErrorDate) {
        this.accountErrorDate = accountErrorDate;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public YTBLoginLog(String accountUsername, String accountError, String accountErrorDate, int stt) {
        this.accountUsername = accountUsername;
        this.accountError = accountError;
        this.accountErrorDate = accountErrorDate;
        this.stt = stt;
    }

    public YTBLoginLog() {

    }

    public static void showTableYTBLog(AnchorPane apContainer, AnchorPane apRoot, TableView tvLoginLog, Button btLoginLogExit) {

        try {
            ObservableList<YTBLoginLog> arrData = FXCollections.observableArrayList();
            apRoot.setPrefSize(apContainer.getPrefWidth() / 2, apContainer.getPrefHeight() - 100);
            apRoot.getStyleClass().setAll("dialog");

            Label lbHeader = new Label("Lịch sử khởi tạo tài khoản");
            lbHeader.setPrefWidth(apRoot.getPrefWidth() - 20);
            lbHeader.setPrefHeight(50);
            lbHeader.setLayoutX(10);
            lbHeader.getStyleClass().setAll("lb-header");
            apRoot.getChildren().add(lbHeader);
            tvLoginLog.setItems(arrData);

            apRoot.setLayoutX((apContainer.getPrefWidth() - apRoot.getPrefWidth()) / 2);
            apRoot.setLayoutY(50);
            apRoot.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void initTable(TableView tv, ObservableList<YTBLoginLog> data) {
        TableColumn<YTBLoginLog, String> accountCol = new TableColumn("Tài khoản");
        TableColumn<YTBLoginLog, String> sttCol = new TableColumn("STT");
        TableColumn<YTBLoginLog, String> errorCol = new TableColumn("Lỗi");
        TableColumn<YTBLoginLog, String> dateCol = new TableColumn("Ngày");

        sttCol.setCellValueFactory(new PropertyValueFactory<>("stt"));
        sttCol.setResizable(false);
        sttCol.setPrefWidth(50);

        dateCol.setCellValueFactory(new PropertyValueFactory<>("accountErrorDate"));
        dateCol.setResizable(false);
        dateCol.setPrefWidth(100);

        accountCol.setCellValueFactory(new PropertyValueFactory<>("accountUsername"));
        accountCol.setResizable(false);
        accountCol.setPrefWidth(150);

        errorCol.setCellValueFactory(new PropertyValueFactory<>("accountError"));
        errorCol.setResizable(false);
        errorCol.setPrefWidth(tv.getPrefWidth() - 300);

        tv.getColumns().addAll(sttCol, dateCol, accountCol, errorCol);
        tv.setItems(data);
    }

    public static ObservableList<YTBLoginLog> getDatas() {
        ObservableList<YTBLoginLog> data = FXCollections.observableArrayList();
        String query = " Select * from ytb_login_log ";
        Statement stmt = null;
        ResultSet rs = null;
        Connection c = getConnectionLog();
        int stt = 1;
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                YTBLoginLog ytbLogin = new YTBLoginLog();
                ytbLogin.setStt(stt++);
                ytbLogin.setAccountUsername(rs.getString("account_username"));
                ytbLogin.setAccountError(rs.getString("account_error"));
                ytbLogin.setAccountErrorDate(rs.getString("account_error_date"));
                data.add(ytbLogin);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static boolean checkYTBLogExsits(String account_username) {
        String query = " Select * from ytb_login_log where account_username='"+account_username+"'";
        Statement stmt = null;
        ResultSet rs = null;
        Connection c = getConnectionLog();
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                rs.close();
                stmt.close();
                c.close();
                return true;
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void insertYTBLoginLog(String account_username, String account_error) {
        try {
            String datetime = (new SimpleDateFormat("dd/MM/yy").format(new Date()));
            String query ="";
            if(!checkYTBLogExsits(account_username)){
               query = "INSERT INTO ytb_login_log (account_username,account_error,account_error_date) values ('" + account_username + "','" + account_error + "','" + datetime + "')";
            }else{
               query = "UPDATE ytb_login_log set account_error='"+account_error+"' ,account_error_date='"+datetime+"' where account_username='"+account_username+"'"; 
            }
            if (executeQuery(query).length() == 0) {

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String removeYTBLog(String account_username) {
        String query = "DELETE from ytb_login_log where account_username='" + account_username + "'";
        return executeQuery(query);
    }

    public static String clearLog() {
        String query = "DELETE from ytb_login_log";
        return executeQuery(query);
    }
}
