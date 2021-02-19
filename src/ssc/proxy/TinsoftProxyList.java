/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ssc.proxy.GetProxyTinSoft;
import ssc.proxy.ProxyInfo;
import Setting.ToolSetting;
/**
 * @author simpl
 */
public class TinsoftProxyList {

    public static TinsoftProxyList tinsoft_instance = null;
    private HashMap<String, List<String>> mapAccountRunningOfKey;
    private HashMap<String, List<String>> mapAccountSuccessOfKey;
    private HashMap<String, ProxyInfo> mapProxyByKey;
    private List<String> arrKey;
    private int maxAccounyPerKey;
    private int needTime=0;

    public int getNeedTime() {
        return needTime;
    }

    public void setNeedTime(int needTime) {
        this.needTime = needTime;
    }
    
    public ProxyInfo getProxyForAccount(String account_username) {
        for (Map.Entry<String, List<String>> entry : mapAccountRunningOfKey.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue().size() < maxAccounyPerKey) {
                if (mapProxyByKey.get(key) == null) {
                    needTime = GetProxyTinSoft.getTinsoftLastTime(ToolSetting.getInstance().getTinsoftKey());
                    if(needTime>0){
                        System.out.println("day ne");
                        return null;
                    }
                    ProxyInfo proxyInfo = GetProxyTinSoft.GetProxyTinSoftM(key);
                    if (proxyInfo == null) {
                        continue;
                    }
                    mapProxyByKey.replace(key, proxyInfo);
                }
                if(account_username.length()==0){
                    return mapProxyByKey.get(key);
                }
                entry.getValue().add(account_username);
                return mapProxyByKey.get(key);
            }
        }
        return null;
    }

    
    public void addProxyForAccount(String account_username,ProxyInfo pi) {
        String key = "";
        key=getKeyOfProxy(pi);
        if(key.length()==0){
            return;
        }
        mapAccountRunningOfKey.get(key).add(account_username);
    }
    
    
    private String getKeyOfProxy(ProxyInfo proxyInfo) {
        for (Map.Entry<String, ProxyInfo> entry : mapProxyByKey.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue() == proxyInfo) {
                return key;
            }
        }
        return "";
    }

    public void removeProxyByAccount(String account_username, ProxyInfo proxyInfo) {
        String key = getKeyOfProxy(proxyInfo);
        mapAccountSuccessOfKey.get(key).add(account_username);
        if (mapAccountSuccessOfKey.get(key).size() >= maxAccounyPerKey) {
            mapAccountRunningOfKey.get(key).clear();
            mapAccountSuccessOfKey.get(key).clear();
            mapProxyByKey.replace(key, null);
        }
    }

    public TinsoftProxyList() {
        mapAccountRunningOfKey = new HashMap<>();
        mapAccountSuccessOfKey = new HashMap<>();
        mapProxyByKey = new HashMap<>();
        maxAccounyPerKey = 3;
        arrKey =new ArrayList<>();
        arrKey.add(ToolSetting.getInstance().getTinsoftKey());
        
        System.out.println("arrKey "+arrKey.size());
        
        for (String string : arrKey) {
            List<String> arrRunning = new ArrayList<>();
            mapAccountRunningOfKey.put(string, arrRunning);
            List<String> arrSuccess = new ArrayList<>();
            mapAccountSuccessOfKey.put(string, arrSuccess);
            mapProxyByKey.put(string, null);
        }
    }

    public static TinsoftProxyList getInstance() {
        if (tinsoft_instance == null) {
            tinsoft_instance = new TinsoftProxyList();
        }
        return tinsoft_instance;
    }
}
