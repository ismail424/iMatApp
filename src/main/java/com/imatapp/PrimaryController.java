package com.imatapp;


import com.imatapp.components.NavigationButton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class PrimaryController  {
    private IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    @FXML
    private Button packagesButton, allproductsButton, shoppingcartButton, accountButton;

    public NavigationButton[] navigationButtons;

    @FXML
    private StackPane mainStackPane;

    @FXML
    private TextField searchbar;

    @FXML
    private AnchorPane packagesPane, allproductsPane, shoppingcartPane, accountPane;

    private AnchorPane currentShowingPane;

    @FXML
    public void initialize() {
        // Set up current showing pane
        currentShowingPane = packagesPane;

        this.navigationButtons = new NavigationButton[]{         
            new NavigationButton( packagesPane,true, packagesButton, this),
            new NavigationButton( allproductsPane,true, allproductsButton, this),
            new NavigationButton( shoppingcartPane,true, shoppingcartButton, this),
            new NavigationButton( accountPane, false, accountButton, this),
        };
        

    }   

    public void switchPage( AnchorPane newPane){
        if (newPane == currentShowingPane){
            return;
        }
        // Hide current pane with Animation
        currentShowingPane.visibleProperty().setValue(false);
        currentShowingPane.toBack();

        // Set current pane
        currentShowingPane = newPane;
         
        // Show new pane with Animation
        FadeTransition ftShow = new FadeTransition( Duration.millis(500), currentShowingPane);
        currentShowingPane.visibleProperty().setValue(true);
        currentShowingPane.toFront();
        ftShow.setFromValue(0.0);
        ftShow.setToValue(1.0);
        ftShow.play();
    }
}
