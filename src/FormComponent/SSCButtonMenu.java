/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author PC
 */
public class SSCButtonMenu {
    private String tooltip;
    private EventHandler<ActionEvent> event;
    private String icon;
    public SSCButtonMenu(String icon, String tooltip, EventHandler<ActionEvent> event) {
        this.event = event;
        this.icon = icon;
        this.tooltip = tooltip;
    }
    Thread t;
    SSCCheckHoverThread ss;
    public Button getButton(String classStyle) {
        Button btn = new Button();
        btn.getStyleClass().setAll(classStyle);
        btn.setOnAction(event);
        btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ss = new SSCCheckHoverThread();
                ss.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        ((AnchorPane) btn.getScene().lookup("#pnToolTip")).setVisible(true);
                        ((Label) btn.getScene().lookup("#lbToolTip")).setText("Hành động : "+tooltip);
                    }
                });
                t = new Thread(ss);
                t.setDaemon(true);
                t.start();
            }
        });
        btn.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                t.stop();
                ss.cancel(true);
                ((AnchorPane) btn.getScene().lookup("#pnToolTip")).setVisible(false);
                ((Label) btn.getScene().lookup("#lbToolTip")).setText("");
            }
        });
        //btn.setTooltip(new Tooltip(tooltip));
        GlyphsDude.setIcon(btn, FontAwesomeIcons.valueOf(icon), "1.5em", ContentDisplay.CENTER);
        return btn;
    }
}
