/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.task;
import java.lang.management.ManagementFactory;
import javafx.concurrent.Task;
import ssc.automation.selennium.SeleniumJSUtils;
import com.sun.management.OperatingSystemMXBean;
/**
 * @author inet
 */

public class CountRunTimeTask extends Task<Void> {
    private static int[] printUsage() {
        int pro[]=new int[2];
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);
        pro[0]=(int)(osBean.getSystemCpuLoad()*100);
        long used=osBean.getTotalPhysicalMemorySize()-osBean.getFreePhysicalMemorySize();
        pro[1]=(int)((double)used/(double)osBean.getTotalPhysicalMemorySize() *100);
        return pro;
    }
    @Override
    protected Void call() throws Exception {
        int i=0;
        while (true) {
            int s =i%60;
            int m =i/60%60;       
            int h =i/60/60;
            updateMessage(h+" h "+m+" m "+s+" s");
            int res[]=printUsage();
            updateTitle(" "+res[0]+" % / "+res[1]+" %");
            SeleniumJSUtils.Sleep(1000);
            i++;
        }
    }
    Thread t;
    public void start(){
        t=new Thread(this);
        t.setDaemon(true);
        t.start();
    }
    public void stop(){
        t.stop();
    }
}
