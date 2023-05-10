package com.imatapp;


import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class NavigationButton {
    PrimaryController parentController;
    AnchorPane pane;
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

  
}
