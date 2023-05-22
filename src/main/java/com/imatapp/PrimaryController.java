package com.imatapp;


import java.io.IOException;

import com.imatapp.components.InspectItems;
import com.imatapp.events.ShowPopupEvent;
import com.imatapp.components.NavigationButton;
import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCartListener;


public class PrimaryController  {
    private IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    @FXML
    private Button packagesButton, allproductsButton, shoppingcartButton, accountButton, popupButton;

    public NavigationButton[] navigationButtons;

    @FXML
    private StackPane mainStackPane;

    @FXML 
    private Text shoppingCartItemsAmount;

    @FXML
    private  GridPane popup;

    @FXML
    private  BorderPane allContent;

    @FXML
    private AnchorPane packagesPane, allproductsPane, shoppingcartPane, accountPane;

    @FXML
    private  AnchorPane popupContent;

    private AnchorPane currentShowingPane;

    @FXML
    public void initialize() throws IOException {
        // Set up current showing pane
        currentShowingPane = packagesPane;

        this.navigationButtons = new NavigationButton[]{         
            new NavigationButton( packagesPane,true, packagesButton, this),
            new NavigationButton( allproductsPane,true, allproductsButton, this),
            new NavigationButton( shoppingcartPane,true, shoppingcartButton, this),
            new NavigationButton( accountPane, false, accountButton, this),
        };
     
        popupButton.setOnAction( e -> {
            hidePopup();
        });
        
        mainStackPane.addEventHandler(Event.ANY, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (event instanceof ShowPopupEvent) {
                    showPopup(((ShowPopupEvent) event).getAnchorPane());
                }
            }
        });

        iMatDataHandler.getShoppingCart().addShoppingCartListener( new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                shoppingCartItemsAmount.setText(String.valueOf(iMatDataHandler.getShoppingCart().getItems().size()));
            }
        });

    }   
    // any element can be accpeted as a parameter
    public void showPopup( AnchorPane  content ){
        popup.toFront();
        popup.setVisible(true);
        allContent.toBack();
        popupContent.getChildren().clear();
        popupContent.getChildren().add(content);
        // Ancher it to all sides
        AnchorPane.setTopAnchor(content, 0.0);
        AnchorPane.setBottomAnchor(content, 0.0);
        AnchorPane.setLeftAnchor(content, 0.0);
        AnchorPane.setRightAnchor(content, 0.0);
        
        popupContent.toFront();

    }

    public void hidePopup(){
        allContent.toFront();
        popup.toBack();
        popup.setVisible(false);
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
