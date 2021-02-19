/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Setup;

import java.util.LinkedHashMap;
import java.util.Map;
import javafx.concurrent.Task;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import Setting.ToolSetting;
import api.ServiceAction;

/**
 *
 * @author simplesolution.co
 */
public class CheckProductExpired extends Task<Boolean> {
    @Override
    protected Boolean call() {
        try {
            updateMessage("Checking");
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("code",ToolSetting.getInstance().getToolCode());
            params.put("user_id", ToolSetting.getInstance().getUserId());
            String result = ServiceAction.getResultFromService("ProductExpiredApi/check", params);
            JSONObject jo = (JSONObject) new JSONParser().parse(result);
            if (jo!=null) {
                ToolSetting.getInstance().setDayLeft(Integer.parseInt(jo.get("dayleft").toString()));
                return true;
            }
        } catch (Exception e) {
        }
        updateMessage("Product Expired, Buy Now !");
        return false;
    }

    public void start(){
        Thread t=new Thread(this);
        t.setDaemon(true);
        t.start();
    }
    
    
}
