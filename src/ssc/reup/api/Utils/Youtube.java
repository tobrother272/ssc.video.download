/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
/**
 * @author inet
 */
public class Youtube {
    public static boolean uploadVideo(WebDriver driver,String video_uri) {
        try {
            ((JavascriptExecutor) driver).executeScript(" document.getElementById('upload-prompt-box').getElementsByTagName('input')[0].style.visibility = '' ");
            ((JavascriptExecutor) driver).executeScript(" document.getElementById('upload-prompt-box').getElementsByTagName('input')[0].style.height= '100px' ");
            ((JavascriptExecutor) driver).executeScript(" document.getElementById('upload-prompt-box').getElementsByTagName('input')[0].setAttribute('id','upfile') ");
            driver.findElement(By.id("upload-prompt-box")).findElements(By.tagName("input")).get(0).sendKeys(video_uri);  
            return true;
        } catch (Exception ex) {
            //ex.printStackTrace();
            return false;
        }
    }
}
