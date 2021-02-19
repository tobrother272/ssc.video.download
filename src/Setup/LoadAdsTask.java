/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Setup;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.concurrent.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import Setting.ToolSetting;
import api.ServiceAction;
/**
 * @author simplesolution.co
 */
public class LoadAdsTask extends Task<Boolean>{
    @Override
    protected Boolean call(){
        try {
            Map<String, Object> params = new LinkedHashMap<>();
            String result = ServiceAction.getResultFromService("ProductApi/"+ToolSetting.getInstance().getToolCode(),params);
            JSONObject jo = (JSONObject) new JSONParser().parse(result);
            if (jo!=null) {
                ToolSetting.getInstance().setDescription(((JSONObject)jo.get("product")).get("description").toString());
                JSONArray prices = (JSONArray)jo.get("prices");
                String priceString="";
                for (Object priceObj : prices) {
                    JSONObject price = (JSONObject)priceObj;
                    priceString=priceString+(price.get("day")+"="+price.get("price"))+",";
                }
                ToolSetting.getInstance().setArrPrices(priceString);   
                return true;
            }        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public void start(){
        Thread t=new Thread(this);
        t.setDaemon(true);
        t.start();
    }
}
