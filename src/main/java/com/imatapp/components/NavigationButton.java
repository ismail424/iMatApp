package com.imatapp.components;

import com.imatapp.PrimaryController;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class NavigationButton {
    PrimaryController parentController;
    public AnchorPane pane;
    Boolean isNavbar;
    Button button;

    public NavigationButton( AnchorPane pane, Boolean isNavbar, Button button , PrimaryController parentController){
        this.pane = pane;
        this.isNavbar = isNavbar;
        this.button = button;
        this.parentController = parentController;

        button.setOnAction( e -> {
            for (NavigationButton navButton : parentController.navigationButtons) {
                navButton.button.getStyleClass().remove("navbar_active");
            }
            if (isNavbar){
                button.getStyleClass().add("navbar_active");
            }
            parentController.switchPage( pane );
        });


    }
    public void setActive(){
        button.getStyleClass().add("navbar_active");
    }

    public void setInactive(){
        button.getStyleClass().remove("navbar_active");
    }
}
