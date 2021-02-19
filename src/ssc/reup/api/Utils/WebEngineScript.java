/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.Utils;

import javafx.scene.web.WebEngine;

/**
 *
 * @author magictool
 */
public class WebEngineScript {
    public static String getTextContent(String xpath,WebEngine driver ){
        try {
            driver.executeScript("document.evaluate(\""+xpath+"\", document, null, 7, null).snapshotItem(0).textContent");
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
   
    public static String sendInputValue(String xpath,String value,WebEngine driver ){
        try {
            driver.executeScript("document.evaluate(\""+xpath+"\", document, null, 7, null).snapshotItem(0).value = \"" + value + "\"");
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
     public static String sendInputValue(String xpath,String value,int position ,WebEngine driver ){
        try {
            driver.executeScript("document.evaluate(\""+xpath+"\", document, null, 7, null).snapshotItem("+position+").value = \"" + value + "\"");
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public static String click(String xpath,int position,WebEngine driver){
        try {
            driver.executeScript("document.evaluate(\""+xpath+"\", document, null, 7, null).snapshotItem("+position+").click()");
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    public static String click(String xpath,WebEngine driver ){
        try {
            driver.executeScript("document.evaluate(\""+xpath+"\", document, null, 7, null).snapshotItem(0).click()");
            return "";
        } catch (Exception e) {
           e.printStackTrace();
            return e.getMessage();
        }
    }
    public String getAtrr(String xpath,String attr,WebEngine driver ){
        try {
            return (String)driver.executeScript("document.evaluate(\""+xpath+"\", document, null, 7, null).snapshotItem(0).getAttribute("+attr+")");
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public static String scrollToBottom(String xpath,String attr,WebEngine driver ){
        try {
            return (String)driver.executeScript("document.evaluate(\""+xpath+"\", document, null, 7, null).snapshotItem(0).getAttribute("+attr+")");
        } catch (Exception e) {
            return e.getMessage();
        }
    }
     public static String scrollTo(int scrollFrom ,int scrollTo,WebEngine driver ){
        try {
            return (String)driver.executeScript("window.scrollTo("+scrollFrom+","+scrollTo+");");
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    public static String getAtrr(String xpath,int positon,String attr,WebEngine driver ){
        try {
            return (String)driver.executeScript("document.evaluate(\""+xpath+"\", document, null, 7, null).snapshotItem("+positon+").getAttribute("+attr+")");
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public static void setHref(String xpath,int positon,String newHref,WebEngine driver ){
        try {
            driver.executeScript("document.evaluate(\""+xpath+"\", document, null, 7, null).snapshotItem("+positon+").setAttribute(\"href\",\""+newHref+"\")");
        } catch (Exception e) {
            e.getMessage();
        }
    }
     public static void removeClass(String xpath,int positon,String className,WebEngine driver ){
        try {
            driver.executeScript("document.evaluate(\""+xpath+"\", document, null, 7, null).snapshotItem("+positon+").classList.remove(\""+className+"\")");
        } catch (Exception e) {
            //e.getMessage();
        }
    }
    
    
    public static int getLengthElement(String xpath,WebEngine driver ){
       try {
            return (int)driver.executeScript("document.evaluate(\""+xpath+"\", document, null, 7, null).snapshotLength");
        } catch (Exception e) {
            return 0;
        } 
    }
    
}
    
