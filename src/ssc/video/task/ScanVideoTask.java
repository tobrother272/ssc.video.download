/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.video.task;

import java.util.List;
import javafx.collections.ObservableList;
import ssc.task.TaskModel;
import ssc.video.YoutubeApiVideo;

/**
 *
 * @author PC
 */
public class ScanVideoTask extends TaskModel {

    private ObservableList<YoutubeApiVideo> arrVideos;
    private String keywork;
    private int numberVideo;
    public ScanVideoTask(ObservableList<YoutubeApiVideo> arrVideos, String keywork,int numberVideo) {
        this.arrVideos = arrVideos;
        this.keywork = keywork;
        this.numberVideo=numberVideo;
    }

    @Override
    public boolean main() {
        try {
            List<YoutubeApiVideo> data = YoutubeApiVideo.getArrayVideoFromSearchQuery(keywork.replaceAll(" ", "+"), numberVideo);
            for (YoutubeApiVideo youtubeApiVideo : data) {
                youtubeApiVideo.setStt(arrVideos.size()+1);
                arrVideos.add(youtubeApiVideo);
            }
        } catch (Exception e) {
        }
        return true;
    }

}
