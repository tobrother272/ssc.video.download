/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.video;

import FormComponent.SSCTableColumStatic;
import Setting.ToolSetting;
import Utils.DownloadManagerUtils;
import api.ServiceAction;
import static api.SubService.getMd5;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventDispatchChain;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import ssc.task.LoadImageTask;
import ssc.task.TaskModel;

/**
 *
 * @author PC
 */
public class YoutubeApiVideo extends TaskModel {

    private String titleVideo;
    private String description;
    private String thumb;
    private String id;
    private int stt;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getTitleVideo() {
        return titleVideo;
    }

    public void setTitleVideo(String title) {
        this.titleVideo = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public YoutubeApiVideo(JSONObject opject) {
        titleVideo = ((JSONObject) opject.get("snippet")).get("title").toString();
        description = ((JSONObject) opject.get("snippet")).get("description").toString();
        thumb = ((JSONObject) ((JSONObject) ((JSONObject) opject.get("snippet")).get("thumbnails")).get("high")).get("url").toString();
        id = ((JSONObject) opject.get("id")).get("videoId").toString();
    }

    public static YoutubeResult getArrVideoFromItems(String jsonResultString) {
        List<YoutubeApiVideo> arr = new ArrayList<>();
        String nextPage = "";
        try {
            Object obj = new JSONParser().parse(jsonResultString);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray items = (JSONArray) jsonObject.get("items");
            Iterator i = items.iterator();
            while (i.hasNext()) {
                JSONObject video = (JSONObject) i.next();
                arr.add(new YoutubeApiVideo(video));
            }
            try {
                nextPage = jsonObject.get("nextPageToken").toString();
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new YoutubeResult(arr, nextPage);
    }

    public static List<YoutubeApiVideo> getArrayVideoFromSearchQuery(String query, int minVideo) {
        List<YoutubeApiVideo> arr = new ArrayList<>();
        String url = "";
        String nextPage = "";
        do {
            url = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=50&q=" + query + "&type=video"
                    + "&key=AIzaSyAStTN9Ldm9tS--vF8lCj_sZqErX-NoIqk&order=viewCount&videoDuration=long" + (nextPage.length() == 0 ? "" : "&pageToken=" + nextPage);
            String result = ServiceAction.getResultFromAnyAPIJson(url, null);
            YoutubeResult tr = getArrVideoFromItems(result);
            arr.addAll(tr.getArrVideo());
            nextPage = tr.getNextPageToken();
        } while (arr.size() < minVideo && nextPage.length() != 0);

        return arr;
    }

    public static void initTable(TableView tv, ObservableList<YoutubeApiVideo> arrVideos) {

        TableColumn<YoutubeApiVideo, YoutubeApiVideo> imageCol = new TableColumn("Hình");
        imageCol.setResizable(false);
        imageCol.setPrefWidth(100);
        imageCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        imageCol.setCellFactory(param -> new TableCell<YoutubeApiVideo, YoutubeApiVideo>() {
            private ImageView imageChannel;
            private AnchorPane imagePane;

            {
                imagePane = new AnchorPane();
                imagePane.getStyleClass().add("news-pane");
                imageChannel = new ImageView();
                imageChannel.getStyleClass().add("news-image");
                imageChannel.setFitHeight(50);
                imageChannel.setFitWidth(100);
                imagePane.getChildren().add(imageChannel);
                setGraphic(imagePane);
            }

            @Override
            public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
                return super.buildEventDispatchChain(tail); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            protected void updateItem(YoutubeApiVideo person, boolean empty) {
                super.updateItem(person, empty);
                if (person == null) {
                    setGraphic(null);
                    return;
                }
                try {
                    imagePane = new AnchorPane();
                    imagePane.getStyleClass().add("news-pane-image");
                    //imageChannel.getStyleClass().add("news-image");
                    imageChannel.setLayoutX(0);
                    imageChannel.setLayoutY(0);
                    imageChannel.setFitHeight(50);
                    imageChannel.setFitWidth(100);
                    imagePane.getChildren().add(imageChannel);
                    setGraphic(imagePane);
                    LoadImageTask lit = new LoadImageTask(person.getThumb());
                    lit.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            if (lit.getValue() != null) {
                                try {
                                    imageChannel.setImage(lit.getValue());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    Thread t = new Thread(lit);
                    t.setDaemon(true);
                    t.start();
                } catch (Exception e) {
                }
            }
        });

        TableColumn<YoutubeApiVideo, YoutubeApiVideo> infoCol = new TableColumn("Thông tin");
        infoCol.setResizable(false);
        infoCol.setPrefWidth(tv.getPrefWidth());
        infoCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        infoCol.setCellFactory(param -> new TableCell<YoutubeApiVideo, YoutubeApiVideo>() {
            private Label lbTitle;
            private Label lbMessage;

            @Override
            public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
                return super.buildEventDispatchChain(tail); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            protected void updateItem(YoutubeApiVideo videoData, boolean empty) {
                super.updateItem(videoData, empty);
                if (videoData == null) {
                    setGraphic(null);
                    return;
                }
                try {
                    AnchorPane apRoot = new AnchorPane();
                    lbTitle = new Label(videoData.getTitleVideo());
                    lbTitle.setPrefSize(apRoot.getPrefWidth(), 30);
                    lbTitle.setLayoutX(10);
                    lbTitle.setLayoutY(5);
                    lbTitle.getStyleClass().setAll("lbSetUpTitle");
                    apRoot.getChildren().add(lbTitle);
                    lbMessage = new Label();
                    lbMessage.setPrefSize(apRoot.getPrefWidth(), 30);
                    lbMessage.setLayoutX(10);
                    lbMessage.setLayoutY(25);
                    apRoot.getChildren().add(lbMessage);
                    lbMessage.getStyleClass().setAll("lbProcessMessage");
                    lbMessage.textProperty().bind(videoData.messageProperty());
                    setGraphic(apRoot);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        tv.getColumns().addAll(
                SSCTableColumStatic.getTableColInteger("stt", "stt", 70, false),
                imageCol,
                infoCol);
        tv.setItems(arrVideos);
        tv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        
    }
    public static String getLinkDownloadApi(String videoId, int fullHD) {
        try {
            try {
            Map<String, Object> params = new LinkedHashMap<>();
            String result = ServiceAction.getResultFromService("TiktokApi/ytbvideo/" + videoId+"/"+ToolSetting.getInstance().getToolCode(), params);
            
                System.out.println(""+result);
           
            
            JSONObject jo = (JSONObject) new JSONParser().parse(result);
            
            
            String videoLink720="";
            String videoLink480="";
            String videoLink360="";
            if (jo != null) {
                if (jo.get("status").toString().contains("success")) {
                        JSONArray items = (JSONArray) jo.get("data");
                        Iterator i = items.iterator();
                        while (i.hasNext()) {
                            JSONObject video = (JSONObject) i.next();
                            if(video.get("Quality").equals("720")){
                                videoLink720=video.get("link").toString();
                            }
                            if(video.get("Quality").equals("480")){
                                videoLink480=video.get("link").toString();
                            }
                            if(video.get("Quality").equals("360")){
                                videoLink360=video.get("link").toString();
                            }
                        } 
                    
                }
            }
            if(videoLink720.length()!=0){
                return videoLink720;
            }
            if(videoLink480.length()!=0){
                return videoLink480;
            }
            return videoLink360;
        } catch (Exception e) {
            e.printStackTrace();
        }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
    @Override
    public boolean main() {
        try {
            updateMessage("Đang lấy link download");
            String downloadUri = getLinkDownloadApi(getId(), 0);
            if (downloadUri.length() == 0) {
                updateMessage("Không lấy dc link download");
                return false;
            }
            updateMessage("Đang download video");
            DownloadManagerUtils.downloadFile(downloadUri, path, this);
            updateMessage("Hoàn thành");
        } catch (Exception e) {
        }
        return true;
    }
}
