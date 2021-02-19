/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.facking;

import FormComponent.SSCCurrentProgress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import static SQliteUtils.SQLIteHelper.executeQuery;
import SQliteUtils.SQLiteConnection;
import Componant.MyButton;
import Componant.MyLabel;
import Componant.MyTextField;
import Utils.StringUtil;

/**
 * @author simpl
 */
public class Useragent {
    
    private String id;
    private String useragent;
    private int stt;
    
    public int getStt() {
        return stt;
    }
    
    public void setStt(int stt) {
        this.stt = stt;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUseragent() {
        return useragent;
    }
    
    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }
    
    public Useragent(int stt, String id, String useragent) {
        this.stt = stt;
        this.id = id;
        this.useragent = useragent;
    }
    
    public String insertData() {
        String query = "Insert into useragent(id,useragentString) values ('" + getId() + "','" + getUseragent() + "')";
        return executeQuery(query);
    }
    
    public String updateData() {
        String query = "UPDATE useragent set useragentString='" + getUseragent() + "' where id = '" + getId() + "'";
        return executeQuery(query);
    }
    
    public String deleteData() {
        String query = "delete from useragent where id = '" + getId() + "'";
        return executeQuery(query);
    }
    
    public static ObservableList<Useragent> getData() {
        ObservableList<Useragent> data = FXCollections.observableArrayList();
        String query = " Select * from useragent ";
        Statement stmt = null;
        ResultSet rs = null;
        Connection c = SQLiteConnection.getInstance().getConnection();
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                data.add(new Useragent(data.size() + 1, StringUtil.getStringFromRS("id", rs), StringUtil.getStringFromRS("useragentString", rs)));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    public static ObservableList<String> getDataForComboBox() {
        ObservableList<String> data = FXCollections.observableArrayList();
        String query = " Select * from useragent ";
        Statement stmt = null;
        ResultSet rs = null;
        Connection c = SQLiteConnection.getInstance().getConnection();
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                data.add(StringUtil.getStringFromRS("useragentString", rs));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    
    
    
    public static void initTable(TableView tvb, ObservableList<Useragent> data) {
        TableColumn<Useragent, Useragent> sttCol = new TableColumn("STT");
        sttCol.setResizable(false);
        sttCol.setPrefWidth(50);
        sttCol.setCellValueFactory(new PropertyValueFactory<>("stt"));
        TableColumn<Useragent, Useragent> noteCol = new TableColumn("Useragent");
        noteCol.setResizable(false);
        noteCol.setPrefWidth(tvb.getPrefWidth() - 50);
        noteCol.setCellValueFactory(new PropertyValueFactory<>("useragent"));
        tvb.getColumns().addAll(sttCol, noteCol);
        tvb.setItems(data);
        tvb.setRowFactory(tv -> {
            TableRow<Useragent> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.SECONDARY) && event.getClickCount() == 2) {
                    Useragent currentAccount = (Useragent) tvb.getSelectionModel().getSelectedItem();
                    if (currentAccount != null) {
                        if (currentAccount.deleteData().length() == 0) {
                            data.remove(currentAccount);
                        }
                    }
                }
            });
            return row;
        });
        tvb.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    
    public static void showUseragentSetting(AnchorPane apContainer) {
        AnchorPane apRoot = new AnchorPane();
        apRoot.setPrefSize(apContainer.getPrefWidth()/2,apContainer.getPrefHeight());
        apRoot.getStyleClass().setAll("dialog-full");
        apRoot.setLayoutX(0);
        apContainer.getChildren().add(apRoot);
        new MyLabel("Danh sách useragent", apRoot.getPrefWidth(), 30, 0, 0, "lb-header", apRoot);
        // row 1
        MyTextField textUseragent = new MyTextField(true, "", apRoot.getPrefWidth() - 20, 30, 10, 60, "Nhập useragent", "", "", apRoot);
        MyButton btInsert = new MyButton("Lưu", 53, 30, apRoot.getPrefWidth() - 63, 60, "Lưu", Arrays.asList("btn", "btn-xs", "btn-primary"), apRoot);
        ObservableList<Useragent> data = FXCollections.observableArrayList();
        btInsert.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (textUseragent.getText().length() == 0) {
                    SSCCurrentProgress.showError(btInsert.getScene(), "Nhập useragent trước");
                    textUseragent.requestFocus();
                    return;
                }
                Useragent useragent = new Useragent(data.size() + 1, StringUtil.getCurrentDateTime("ddMMyyhhmmss"), textUseragent.getText());
                if (useragent.insertData().length() == 0) {
                    data.add(useragent);
                    textUseragent.setText("");
                }
                
            }
        });
        TableView tvPhone = new TableView();
        tvPhone.getStyleClass().setAll("table-view-material");
        tvPhone.setPrefSize(apRoot.getPrefWidth() - 20, 520);
        tvPhone.setLayoutX(10);
        tvPhone.setLayoutY(textUseragent.getLayoutY() + textUseragent.getPrefHeight() + 10);
        data.addAll(getData());
        initTable(tvPhone, data);
        
        MyButton btChangeUseragent = new MyButton("", apRoot.getPrefWidth()-20, 30,10, tvPhone.getLayoutY() + tvPhone.getPrefHeight() + 10, "Đổi useragent", Arrays.asList("btn", "btn-xs", "btn-secondary"), apRoot);
        
        btChangeUseragent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
     
            }
        });
        
        MyButton btClose = new MyButton("", apRoot.getPrefWidth()-20, 30,10, btChangeUseragent.getLayoutY() + btChangeUseragent.getPrefHeight() + 10, "Thoát", Arrays.asList("btn", "btn-xs", "btn-danger"), apRoot);
        
        btClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                apContainer.getChildren().remove(apRoot);
            }
        });
        
        apRoot.getChildren().add(tvPhone);
    }
    
}
