/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent.View;

import com.jfoenix.controls.JFXProgressBar;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import FormComponent.SSCFilterComponent;

/**
 *
 * @author PC
 */
public abstract class SSCProcessTaskView extends SSCProcessView{
    private TableView tv;
    private AnchorPane container;
    private JFXProgressBar prLoading;
    public JFXProgressBar getLoading() {
        return prLoading;
    }
    public TableView getTv() {
        return tv;
    }
    public void setTv(TableView tv) {
        this.tv = tv;
    }
    private ObservableList list;
    private HBox hboxFilter;
    public HBox getFilterContainer() {
        return hboxFilter;
    }
    private List<SSCFilterComponent> arrFilter;
    public SSCProcessTaskView(AnchorPane container, String title, ObservableList l, List<SSCFilterComponent> arrFilter) {

        this.list = l;
        this.container = container;
        initHorizontalView(container, 0, 0, container.getPrefWidth(), 150);
        tv = new TableView();
        tv.setPrefSize(container.getPrefWidth(),  container.getPrefHeight() -getApHorizonStatus().getPrefHeight()-10);
        tv.setLayoutX(0);
        tv.getStyleClass().add("table-view-material");
        tv.setLayoutY(160);
        container.getChildren().add(tv);
        prLoading = new JFXProgressBar();
        prLoading.setVisible(false);
        prLoading.setPrefSize(tv.getPrefWidth(), 3);
        prLoading.setLayoutX(tv.getLayoutX());
        prLoading.setLayoutY(tv.getLayoutY() - 3);
        container.getChildren().add(prLoading);
       

    }
}
