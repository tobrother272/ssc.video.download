/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import javafx.concurrent.Task;
import ssc.automation.selennium.SeleniumJSUtils;

/**
 *
 * @author PC
 */
public class SSCCheckHoverThread extends Task<Boolean>{
    @Override
    protected Boolean call() {
        try {
            for (int i=0;i<3;i++) {
                SeleniumJSUtils.Sleep(500);
            }
        } catch (Exception e) {
        }
        return true;
    }
    
}
