/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static Utils.StringUtil.getPath;
/**
 *
 * @author tobrother272
 */
public class Property {
    Properties prop;
    OutputStream output;
    InputStream input = null;
    String path = "";
    public Property (){
            prop = new Properties();
            path=getPath();     
    }
    public void initWrite(){
        try {
            File file = new File(path+"\\config.properties");
            output = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Property.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void closeWrite(){
        try {
            prop.store(output, null);
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(Property.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void writePropety(String key , String value){
        prop.setProperty(key, value);
    }
    public String readPropety(String key){
        return prop.getProperty(key,"");
    }
    public void initReadPropety(){
        try {
                File file = new File(path+"\\config.properties");
                //input = getClass().getClassLoader().getResourceAsStream("config.properties");
                input = new FileInputStream(file);
		// load a properties file
		prop.load(input);

	} catch (IOException ex) {
		ex.printStackTrace();
	} 
    }
    public void closeReadPropety(){
        try {
            if(input!=null){
                 input.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Property.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
