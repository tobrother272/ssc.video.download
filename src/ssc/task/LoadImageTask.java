/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.task;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
/**
 * @author simpl
 */
public class LoadImageTask extends Task<Image>{
    private String imgUrl;
    public LoadImageTask(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    @Override
    protected Image call() throws Exception {
        try {
            Image image = new Image(imgUrl);
            return image;
        } catch (Exception e) {
        }
        return null;
    }
    public void start(){
        Thread t=new Thread(this);
        t.setDaemon(true);
        t.start();
    }
}
