/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainView.SubView;

import FormComponent.View.SSCChildView;
import FormComponent.SSCButtonChildTab;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

/**
 * @author PC
 */
public class ChannelInteractiveView extends SSCChildView {

    public ChannelInteractiveView(Scene scene) {
        super(scene);
    }

    @Override
    public String getBreadScrumbTitle() {
        return "Thực hiện nhiệm vụ";
    }

    @Override
    public String getBreadScrumbDescription() {
        return "Cài đặt và thực hiện nhiệm vụ";
    }

    @Override
    public String getLeftMenuIcon() {
        return "EYE";
    }

    @Override
    public int getTabIndex() {
        return 1;
    }

    @Override
    public void initView(Scene scene) {
     
    }

    @Override
    public void reloadView() {
      
    }

    @Override
    public List<SSCButtonChildTab> getListMenuButton() {
        List<SSCButtonChildTab> arrButton = new ArrayList<>();
        arrButton.add(new SSCButtonChildTab("Thêm nhiệm vụ", "PAPER_PLANE", "Thêm nhiệm vụ thực hiện", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
            }
        }));
        return arrButton;

    }

}
