/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.task;

import Setup.InitialingTool;
import static Utils.StringUtil.getJsonArrFromFile;
import java.io.File;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author simplesolution.co
 */
public class LoadSetupTask extends Task<List<SetupTask>> {
    private ObservableList<SetupTask> arrSetup;
    public LoadSetupTask(ObservableList<SetupTask> arrSetup) {
        this.arrSetup = arrSetup;
    }
    @Override
    protected List<SetupTask> call() {
        try {
            //node-v10.18.0-x64.msi
            //
            updateTitle("Đang kiểm tra phần mềm cần thiết");
            List<String> linkDownload = InitialingTool.getListFile();
            JSONArray arr=getJsonArrFromFile(System.getProperty("user.dir")+File.separator+"setup.json");
            for (Object object : arr) {
                JSONObject obj=(JSONObject)object;
                SetupTask sk=new SetupTask(obj, linkDownload);
                if(sk.isNeedInstall()){
                    updateMessage("Đang kiểm tra "+sk.getSetupTitle());
                    if(!sk.checkReady()){
                        arrSetup.add(sk);
                    }
                }
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrSetup;
    }
    public void start() {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }
}
