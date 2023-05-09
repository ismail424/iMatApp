package com.imatapp;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PrimaryController  {

    @FXML
    private Button packages_button, allproducts_button,shoppingcart_button,account_button;

    protected NavigationButton[] navigationButtons;

    @FXML
    private TextField searchbar;

    @FXML
    private Pane mainPane;

    @FXML
    public void initialize() {

        this.navigationButtons = new NavigationButton[]{         
            new NavigationButton("paketer",true, packages_button, this),
            new NavigationButton("alla_varor",true, allproducts_button, this),
            new NavigationButton("varukorg",true, shoppingcart_button, this),
            new NavigationButton("konto", false, account_button, this),
        };

    }   

    public void switchTab(  String page ) {
        System.out.println("Switching to " + page);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pages/"+ page +".fxml"));
            FadeTransition ft = new FadeTransition( Duration.millis(500),  mainPane);
            mainPane.getChildren().clear();
            mainPane.getChildren().add(loader.load());
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
        }
        catch (IOException e){ e.printStackTrace();}
    }

}
