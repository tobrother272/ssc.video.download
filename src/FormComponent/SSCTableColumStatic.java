/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import javafx.event.EventDispatchChain;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author PC
 */
public class SSCTableColumStatic {
     public static TableColumn getTableColInteger(String name,String data,int width,boolean resize){
        TableColumn<Integer,Integer> col = new TableColumn(name);
        col.setResizable(resize);
        col.setPrefWidth(width);
        col.setCellValueFactory(new PropertyValueFactory<>(data));
        col.setCellFactory(param -> new TableCell<Integer,Integer>() {
            private Label lb;

            {
                lb = new Label("n/a");
                setGraphic(lb);
            }

            @Override
            public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
                return super.buildEventDispatchChain(tail); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            protected void updateItem(Integer text, boolean empty) {
                super.updateItem(text, empty);
                if (text == null) {
                    setGraphic(null);
                    return;
                }
                try {
                    lb = new Label();
                    lb.setText("" + text);
                    lb.getStyleClass().addAll("labelTableValue");
                    setGraphic(lb);
                } catch (Exception e) {
                }
            }
        });
        return col;
    }
     public static TableColumn getTableColLong(String name,String data,int width,boolean resize){
        TableColumn<Long,Long> col = new TableColumn(name);
        col.setResizable(resize);
        col.setPrefWidth(width);
        col.setCellValueFactory(new PropertyValueFactory<>(data));
        col.setCellFactory(param -> new TableCell<Long,Long>() {
            private Label lb;

            {
                lb = new Label("n/a");
                setGraphic(lb);
            }

            @Override
            public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
                return super.buildEventDispatchChain(tail); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            protected void updateItem(Long text, boolean empty) {
                super.updateItem(text, empty);
                if (text == null) {
                    setGraphic(null);
                    return;
                }
                try {
                    lb = new Label();
                    lb.setText("" + text);
                    lb.getStyleClass().addAll("labelTableValue");
                    setGraphic(lb);
                } catch (Exception e) {
                }
            }
        });
        return col;
    }
    public static TableColumn getTableColString(String name,String data,int width,boolean resize){
        TableColumn<String,String> col = new TableColumn(name);
        col.setResizable(resize);
        col.setPrefWidth(width);
        col.setCellValueFactory(new PropertyValueFactory<>(data));
        col.setCellFactory(param -> new TableCell<String,String>() {
            private Label lb;

            {
                lb = new Label("n/a");
                setGraphic(lb);
            }

            @Override
            public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
                return super.buildEventDispatchChain(tail); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            protected void updateItem(String text, boolean empty) {
                super.updateItem(text, empty);
                if (text == null) {
                    setGraphic(null);
                    return;
                }
                try {
                    lb = new Label();
                    lb.setText("" + text);
                    lb.getStyleClass().addAll("labelTableValue");
                    setGraphic(lb);
                } catch (Exception e) {
                }
            }
        });
        return col;
    }
    
}
