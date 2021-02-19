/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent.View;

import FormComponent.SSCButtonChildTab;
import Setting.ToolSetting;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author PC
 */
public abstract class SSCChildView {

    public Scene scene;

    public abstract String getBreadScrumbTitle();

    public abstract String getBreadScrumbDescription();

    public abstract String getLeftMenuIcon();

    public abstract int getTabIndex();

    public abstract void initView(Scene scene);

    public abstract void reloadView();

    /*
     List<SSCButtonChildTab> arrButton = new ArrayList<>();
        arrButton.add(new SSCButtonChildTab("License", "LOCK", "Đánh dấu video có bản quyền", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }})
        );
        return arrButton;
     */
    public abstract List<SSCButtonChildTab> getListMenuButton();

    public SSCChildView(Scene scene) {
        this.scene = scene;

        Button btn = new Button();

        GlyphsDude.setIcon(btn, FontAwesomeIcons.valueOf(getLeftMenuIcon()), "1.5em");
        ((VBox) this.scene.lookup("#vboxLeftNavBar")).getChildren().add(btn);
        btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((Label) scene.lookup("#lbBreadscrumbTitle")).setText(getBreadScrumbTitle());
                ((Label) scene.lookup("#lbBreadscrumbDescription")).setText(getBreadScrumbDescription());
            }
        });
        btn.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((Label) scene.lookup("#lbBreadscrumbTitle")).setText(ToolSetting.getInstance().getBreadScrumbTitle());
                ((Label) scene.lookup("#lbBreadscrumbDescription")).setText(ToolSetting.getInstance().getBreadScrumbDescription());
            }
        });
        clearSelectNavBar();
        ((TabPane) this.scene.lookup("#tabContentContainer")).getSelectionModel().select(getTabIndex());
        btn.getStyleClass().setAll("btnLeftMenuActive");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                reloadView();
                ((TabPane) scene.lookup("#tabContentContainer")).getSelectionModel().select(getTabIndex());
                ((Label) scene.lookup("#lbBreadscrumbTitle")).setText(getBreadScrumbTitle());
                ((Label) scene.lookup("#lbBreadscrumbDescription")).setText(getBreadScrumbDescription());
                ToolSetting.getInstance().setBreadScrumbTitle(getBreadScrumbTitle());
                ToolSetting.getInstance().setBreadScrumbDescription(getBreadScrumbDescription());
                refreshChildButtonBar();
                clearSelectNavBar();
                btn.getStyleClass().setAll("btnLeftMenuActive");
            }
        });
        initView(scene);
    }

    public void clearSelectNavBar() {
        for (Node btn : ((VBox) scene.lookup("#vboxLeftNavBar")).getChildren()) {
            btn.getStyleClass().setAll("btnLeftMenu");
        }
    }

    public void bindData() {
        refreshChildButtonBar();
        ((Label) scene.lookup("#lbBreadscrumbTitle")).setText(ToolSetting.getInstance().getBreadScrumbTitle());
        ((Label) scene.lookup("#lbBreadscrumbDescription")).setText(ToolSetting.getInstance().getBreadScrumbDescription());
        ToolSetting.getInstance().setBreadScrumbTitle(getBreadScrumbTitle());
        ToolSetting.getInstance().setBreadScrumbDescription(getBreadScrumbDescription());
    }

    public void refreshChildButtonBar() {
        ((HBox) scene.lookup("#vboxChildButtonGroup")).getChildren().clear();
        for (SSCButtonChildTab btnChild : getListMenuButton()) {
            ((HBox) scene.lookup("#vboxChildButtonGroup")).getChildren().add(btnChild.getButton("btnChildMenu"));
        }
    }
}
