/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent.View;

import FormComponent.View.SSCMessage;
import com.jfoenix.controls.JFXProgressBar;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import FormComponent.SSCFilterComponent;

/**
 *
 * @author PC
 */
public abstract class SSCTableView {

    private TableView tv;
    private AnchorPane container;
    private TextField tbSearch;
    private Button btDelete;
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

    public abstract void deleteFunc();
    private ObservableList list;
    private HBox hboxFilter;

    public HBox getFilterContainer() {
        return hboxFilter;
    }

    public abstract void reloadData(String query);
    private List<SSCFilterComponent> arrFilter;
    public void setGroupFilterComponent(SSCFilterComponent groupFilter) {
        hboxFilter.getChildren().add(groupFilter.getView(120));
        arrFilter.add(groupFilter);
        groupFilter.getComboBox().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                String filterQuey = "";
                for (SSCFilterComponent filter : arrFilter) {
                    filterQuey = filterQuey + " " + filter.getCurrentValue();
                }
                reloadData(filterQuey);
            }
        });
    }

    public SSCTableView(AnchorPane container, String title, ObservableList l, List<SSCFilterComponent> arrFilter) {
        this.list = l;
        this.container = container;
        Label lbTitle = new Label(title);
        lbTitle.setPrefHeight(30);
        lbTitle.setLayoutX(10);
        lbTitle.setLayoutY(20);
        lbTitle.getStyleClass().add("lbTableTitle");
        container.getChildren().add(lbTitle);

        btDelete = new Button(" Xóa dòng ");
        btDelete.setPrefSize(85, 30);
        btDelete.getStyleClass().add("btnDelete");
        btDelete.setLayoutY(10);
        btDelete.setLayoutX(container.getPrefWidth() - btDelete.getPrefWidth() - 10);
        container.getChildren().add(btDelete);
        btDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tv.getSelectionModel().getSelectedItems().size() == 0) {
                    SSCMessage.showError(btDelete.getScene(), "Chọn danh sách cần xóa");
                    return;
                }
                deleteFunc();
                tv.getSelectionModel().clearSelection();
            }
        });

        tbSearch = new TextField();
        tbSearch.setPrefSize(container.getPrefWidth() / 3 - 10, 30);
        tbSearch.getStyleClass().add("txtInputSecondary");
        tbSearch.setLayoutY(10);
        tbSearch.setLayoutX(btDelete.getLayoutX() - tbSearch.getPrefWidth() - 10);
        container.getChildren().add(tbSearch);

        GlyphsDude.setIcon(btDelete, FontAwesomeIcons.valueOf("TRASH"), "1.2em", ContentDisplay.RIGHT);
        tv = new TableView();
        tv.setPrefSize(container.getPrefWidth() - 20, container.getPrefHeight() - 50);
        tv.setLayoutX(10);
        tv.getStyleClass().add("table-view-material");
        tv.setLayoutY(tbSearch.getLayoutY() + tbSearch.getPrefHeight() + 10);
        container.getChildren().add(tv);

        tv.getSelectionModel().getSelectedItems().addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change c) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lbTitle.setText(title + " - đã chọn :" + c.getList().size());
                    }
                });
            }
        });
        tbSearch.setPromptText("Tìm kiếm ...");
        tbSearch.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
                if (tbSearch.textProperty().get().isEmpty()) {
                    tv.setItems(list);
                    return;
                }
                ObservableList tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<Observable, ?>> cols = tv.getColumns();
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < cols.size(); j++) {
                        try {
                            TableColumn col = cols.get(j);
                            String cellValue = col.getCellData(list.get(i)).toString();
                            cellValue = cellValue.toLowerCase();
                            if (cellValue.contains(tbSearch.textProperty().get().toLowerCase())) {
                                tableItems.add(list.get(i));
                                break;
                            }
                        } catch (Exception e) {
                        }
                    }
                }
                tv.setItems(tableItems);
            }
        });

        hboxFilter = new HBox();
        hboxFilter.setPrefSize(container.getPrefWidth() - tbSearch.getLayoutX() - 10, 70);
        hboxFilter.setLayoutX(300);
        hboxFilter.setLayoutY(0);
        hboxFilter.getStyleClass().setAll("hbox");
        container.getChildren().add(hboxFilter);
        prLoading = new JFXProgressBar();
        prLoading.setVisible(false);
        prLoading.setPrefSize(tv.getPrefWidth(), 3);
        prLoading.setLayoutX(tv.getLayoutX());
        prLoading.setLayoutY(tv.getLayoutY() - 3);
        container.getChildren().add(prLoading);
        this.arrFilter=new ArrayList<>();
        this.arrFilter.addAll(arrFilter);   
        for (SSCFilterComponent filter : arrFilter) {
            hboxFilter.getChildren().add(filter.getView(120));
            filter.getComboBox().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    String filterQuey = "";
                    for (SSCFilterComponent filter : arrFilter) {
                        filterQuey = filterQuey + " " + filter.getCurrentValue();
                    }
                    reloadData(filterQuey);
                }
            });
        }

    }
}
