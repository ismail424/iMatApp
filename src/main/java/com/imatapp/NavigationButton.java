package com.imatapp;


import javafx.scene.control.Button;

public class NavigationButton {
    PrimaryController parentController;
    String page;
    Boolean isNavbar;
    Button button;

    public NavigationButton( String page, Boolean isNavbar, Button button , PrimaryController parentController){
        this.page = page;
        this.isNavbar = isNavbar;
        this.button = button;
        this.parentController = parentController;

        button.setOnAction( e -> {
            parentController.switchTab(page);
            for (NavigationButton navButton : parentController.navigationButtons) {
                navButton.button.getStyleClass().remove("navbar_active");
            }
            if (isNavbar){
                button.getStyleClass().add("navbar_active");
            }
        });
    }
}
