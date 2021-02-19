/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.video;

import java.util.List;

/**
 *
 * @author PC
 */
public class YoutubeResult {

    private List<YoutubeApiVideo> arrVideo;
    private String nextPageToken;

    public List<YoutubeApiVideo> getArrVideo() {
        return arrVideo;
    }

    public void setArrVideo(List<YoutubeApiVideo> arrVideo) {
        this.arrVideo = arrVideo;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public YoutubeResult(List<YoutubeApiVideo> arrVideo, String nextPageToken) {
        this.arrVideo = arrVideo;
        this.nextPageToken = nextPageToken;
    }

}
