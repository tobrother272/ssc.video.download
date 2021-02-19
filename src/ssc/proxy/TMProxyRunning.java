/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.proxy;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ssc.automation.selennium.SeleniumJSUtils;

/**
 *
 * @author PC
 */
public class TMProxyRunning {

    private String key;
    private SimpleStringProperty getting;
    private SimpleIntegerProperty count;
    private SimpleStringProperty nextChange;
    private SimpleStringProperty ip;
    private ProxyInfo proxyInfo;
    private long nextChangeLong;
    private SimpleIntegerProperty countSuccess;

    public SimpleIntegerProperty countSuccessProperty() {
        return countSuccess;
    }
     public int getCountSuccess() {
        return countSuccess.get();
    }
    public void setCountSuccess(int countSuccess) {
        this.countSuccess.set(countSuccess);
    }
    
    
    
    public long getNextChangeLong() {
        return nextChangeLong;
    }
    
    public void setNextChangeLong(long nextChangeLong) {
        this.nextChangeLong = nextChangeLong;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public SimpleStringProperty gettingProperty() {
        return getting;
    }

    public String getGetting() {
        return getting.get();
    }

    public void setGetting(String getting) {
        this.getting.set(getting);
    }
    
    public SimpleIntegerProperty countProperty() {
        return count;
    }
    
    public int getCount() {
        return count.get();
    }
    
    public void setCount(int count) {
        this.count.set(count);
    }
    
    public SimpleStringProperty nextChangeProperty() {
        return nextChange;
    }
    
    public String getNextChange() {
        return nextChange.get();
    }
    
    public void setNextChange(SimpleStringProperty nextChange) {
        this.nextChange = nextChange;
    }
    
    public SimpleStringProperty ipProperty() {
        return ip;
    }

    public String getIp() {
        return ip.get();
    }

    public void setIp(String ip) {
        this.ip.set(ip);
    }
    
    public ProxyInfo getProxyInfo() {
        return proxyInfo;
    }
    
    public void setProxyInfo(ProxyInfo proxyInfo) {
        this.proxyInfo = proxyInfo;
    }
    public TMProxyRunning(String key) {
        this.key = key;
        this.getting = new SimpleStringProperty("");
        this.count = new SimpleIntegerProperty(0);
        this.nextChange = new SimpleStringProperty("");
        this.countSuccess = new SimpleIntegerProperty(0);
        this.proxyInfo = null;
        this.ip = new SimpleStringProperty("");
    }

   int countError=0;    

    public int getCountError() {
        return countError;
    }

    public void setCountError(int countError) {
        this.countError = countError;
    }
    
    
   public static ProxyInfo getProxyInfo(ObservableList<TMProxyRunning> data){
       for (TMProxyRunning tMProxyRunning : data) {
           if(tMProxyRunning.getCount()<3&&!tMProxyRunning.getGetting().contains("getting")){
               return tMProxyRunning.getProxy();
           }
       }
       return null;
   }
    public ProxyInfo getProxy() {
        try {
            if (getGetting().contains("getting") ) {
                return null;
            }
            if(this.proxyInfo==null){
                setGetting("getting [ lấy proxy lần "+countError+"]");
                this.proxyInfo = GetTMProxy.GetTMProxy(key);
                if(this.proxyInfo!=null){
                    setIp(this.proxyInfo.getIp());
                    countError=0; 
                    setGetting("done");
                    return null;
                }else{
                    setGetting("getting [không lấy được proxy lần "+countError+"] chờ 10s");
                    SeleniumJSUtils.Sleep(10000);
                    countError++;
                }
                setGetting("done");
                return proxyInfo;
            }
        
            this.setCount(this.getCount() + 1);
            return this.proxyInfo;
        } catch (Exception e) {
        }
        return null;
    }
    public static void removeProxyRunning(ObservableList<TMProxyRunning> data, String ip) {
        for (TMProxyRunning tMProxyRunning : data) {
            if(tMProxyRunning.getIp().contains(ip)){
                tMProxyRunning.setCountSuccess(tMProxyRunning.getCountSuccess()+1);
                if(tMProxyRunning.getCountSuccess()>=3){
                    tMProxyRunning.proxyInfo=null;
                    tMProxyRunning.setCount(0);
                    tMProxyRunning.setCountSuccess(0);
                }
            }
        }
    }
     public static void removeProxyError(ObservableList<TMProxyRunning> data, String ip) {
        for (TMProxyRunning tMProxyRunning : data) {
            if(tMProxyRunning.getIp().contains(ip)){
                tMProxyRunning.setCount(tMProxyRunning.getCount()-1);
            }
        }
    }
    
    
    public static void initTable(TableView tv, ObservableList<TMProxyRunning> data) {
        
        TableColumn<TMProxyRunning, String> actionCol = new TableColumn("key");
        actionCol.setCellValueFactory(new PropertyValueFactory<>("key"));
        actionCol.setResizable(false);
        actionCol.setPrefWidth(300);
        
        TableColumn<TMProxyRunning, String> userNameCol = new TableColumn("Ip hiện tại");
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("ip"));
        userNameCol.setResizable(false);
        userNameCol.setPrefWidth(120);
        
        TableColumn<TMProxyRunning, String> ipCol = new TableColumn("Trạng thái");
        ipCol.setCellValueFactory(new PropertyValueFactory<>("getting"));
        ipCol.setResizable(false);
        ipCol.setPrefWidth(300);
        
        TableColumn<TMProxyRunning, Integer> titleCol = new TableColumn("số tài khoản");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        titleCol.setResizable(false);
        titleCol.setPrefWidth(100);
        
        TableColumn<TMProxyRunning, Integer> accountSuccessCol = new TableColumn("số tài khoản");
        accountSuccessCol.setCellValueFactory(new PropertyValueFactory<>("countSuccess"));
        accountSuccessCol.setResizable(false);
        accountSuccessCol.setPrefWidth(100);
        
        
        TableColumn<TMProxyRunning, Integer> timeCol = new TableColumn("Thời gian ");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("nextChange"));
        timeCol.setResizable(false);
        timeCol.setPrefWidth(150);
        
        tv.getColumns().addAll(actionCol, userNameCol, ipCol, titleCol,accountSuccessCol, timeCol);
        //tv.getColumns().addAll(sttCol, actionCol, userNameCol, ipCol, messageCol);

        tv.setItems(data);
    }
    

}
