/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.task;

import static Utils.Constants.DATA.RESOURCE_DATA;
import Utils.DownloadManagerUtils;
import java.net.URL;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author PC
 */
public class DownloadEnvTask extends TaskModel{
    List<SetupTask> arrS;
    public DownloadEnvTask(List<SetupTask> arrS) {
        this.arrS = arrS;
    }
    public void updateMyMessage(String message){
        updateMessage(message);
    }
    public void updateMyProgress(double current,double total){
        updateProgress(current,total);
    }
    @Override
    protected Boolean call(){
        try {
            for (SetupTask setupTask : arrS) {
                if(setupTask.getDownloadLink().length()==0){
                    continue;
                }
                String down=setupTask.getDownloadLink().split("---")[0];
                updateTitle("Đang tải "+setupTask.getSetupTitle());
                String fileName = FilenameUtils.getName(new URL(down).getPath());
                DownloadManagerUtils.downloadFile(down, RESOURCE_DATA + "\\" + fileName, this, Integer.parseInt(setupTask.getDownloadLink().split("---")[1]),fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public void start(){
        Thread t=new Thread (this);
        t.setDaemon(true);
        t.start();
    }

    @Override
    public boolean main() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
