



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.proxy;

import Utils.StringUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ssc.reup.api.Model.Log.YTBLoginLog;

/**
 *
 * @author PC
 */
public class TMKeyAndProxy {
    private String key;
    private SimpleStringProperty ip;
    private SimpleStringProperty account;
    private SimpleStringProperty time;
    private ProxyInfo proxyInfo;

    public SimpleStringProperty ipProperty() {
        return ip;
    }
     public String getIp() {
        return ip.get();
    }
    public void setIp(String ip) {
        this.ip.set(ip);
    }

    public SimpleStringProperty accountProperty() {
        return account;
    }
    public String getAccount() {
        return account.get();
    }
    public void setAccount(String account) {
        this.account.set(account);
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }
    public String getTime() {
        return time.get();
    }
    public void setTime(SimpleStringProperty time) {
        this.time = time;
    }
    
    
    
    
    
    private long timeNext;
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    
    public ProxyInfo getProxyInfo() {
        return GetTMProxy.GetTMProxy(key);
    }

    public void setProxyInfo(ProxyInfo proxyInfo) {
        this.proxyInfo = proxyInfo;
    }

    public long getTimeNext() {
        return timeNext;
    }

    public void setTimeNext(long timeNext) {
        this.timeNext = timeNext;
    }
    
    public void reloadTime(String ip){
        timeNext=System.currentTimeMillis()+(GetTMProxy.getTMProxyLastTime(key)*1000);
        time.set(StringUtil.getCurrentDateTimeFromLong("HH:mm", timeNext));
        setIp(ip);
    }
    
    public TMKeyAndProxy(String key) {
        this.key = key;
        this.time=new SimpleStringProperty("n/a");
        this.account=new SimpleStringProperty("");
        this.ip=new SimpleStringProperty("n/a");
        reloadTime("");
    }
    
    
    public static void initTable(TableView tv, ObservableList<TMKeyAndProxy> data) {
        TableColumn<YTBLoginLog, String> ipCol = new TableColumn("IP");
        TableColumn<YTBLoginLog, String> accountCol = new TableColumn("Tài khoản");
        TableColumn<YTBLoginLog, String> nextTimeCol = new TableColumn("Next");


        ipCol.setCellValueFactory(new PropertyValueFactory<>("ip"));
        ipCol.setResizable(false);
        ipCol.setPrefWidth(150);

        accountCol.setCellValueFactory(new PropertyValueFactory<>("account"));
        accountCol.setResizable(false);
        accountCol.setPrefWidth(200);

        nextTimeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        nextTimeCol.setResizable(false);
        nextTimeCol.setPrefWidth(100);

        tv.getColumns().addAll(ipCol, accountCol, nextTimeCol);
        tv.setItems(data);
    }
}
