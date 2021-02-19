/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Setup;
import api.SubService;
import javafx.concurrent.Task;
/**
 * @author magictool
 */
public class LoginTask extends Task<String>{
    private String userName;
    private String passWord;
    @Override
    protected String call() throws Exception {
        updateMessage("Checking ...");
        try {
            //SeleniumJSUtils.Sleep(3000);
            if(SubService.checkUserSuccess(userName, passWord)){
               return "";  
            }
            return "Sai tên đăng nhập hoặc mật khẩu";
        } catch (Exception e) {
            return "";
        }
    }
    public LoginTask(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }
    
    
    
    
    
}
