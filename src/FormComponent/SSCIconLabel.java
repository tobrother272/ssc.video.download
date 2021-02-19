/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;

/**
 *
 * @author PC
 */
public class SSCIconLabel {
    private String text;
    private String style;
    private String icon;
    private double w;
    private double h;
    private double x;
    private double y;
    public SSCIconLabel(String text, String style, String icon, double w, double h, double x, double y) {
        this.text = text;
        this.style = style;
        this.icon = icon;
        this.w = w;
        this.h = h;
        this.x = x;
        this.y = y;
    }
    
    public Label getView(){
        Label lb=new Label(text);
        lb.setPrefSize(w, h);
        lb.setLayoutX(x);
        lb.setLayoutY(y);
        lb.getStyleClass().setAll(style);
        GlyphsDude.setIcon(lb,FontAwesomeIcons.valueOf(icon), "1em",ContentDisplay.LEFT);
        return lb;
    }
}
