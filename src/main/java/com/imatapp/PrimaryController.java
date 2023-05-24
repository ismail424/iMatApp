package com.imatapp;


import java.io.IOException;

import com.imatapp.events.ShowPopupEvent;
import com.imatapp.events.SwitchPageEvent;
import com.imatapp.events.SwitchWizzardEvent;
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
    private AnchorPane packagesPane, allproductsPane, shoppingcartPane, accountPane, historyPane;

    @FXML
    private AnchorPane wizzardPane;

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
        
        mainStackPane.addEventHandler(Event.ANY, event -> {
            if (event instanceof ShowPopupEvent) {
                showPopup(((ShowPopupEvent) event).getAnchorPane());
            } else if (event instanceof SwitchPageEvent) {
                SwitchPageEvent switchPageEvent = (SwitchPageEvent) event;
                String destinationPage = switchPageEvent.getDestinationPage();
                if (destinationPage.equals("History")) {
                    switchPage(historyPane);
                } else if (destinationPage.equals("Account")) {
                    switchPage(accountPane);
                } else if (destinationPage.equals("Varukorg")) {
                    switchPage(shoppingcartPane);
                }
            } else if (event instanceof SwitchWizzardEvent) {
                SwitchWizzardEvent switchWizzardEvent = (SwitchWizzardEvent) event;
                boolean isEntering = switchWizzardEvent.isEntering();
                if (isEntering) {
                    enterWizzard();
                } else {
                    System.out.println("Exiting wizzard");
                    exitWizzard();
                }
            }
        });
        

        if (iMatDataHandler.getShoppingCart().getItems().size() > 0){
            shoppingCartItemsAmount.setText(String.valueOf(iMatDataHandler.getShoppingCart().getItems().size()));
        }

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
        
        FadeTransition ftShow = new FadeTransition( Duration.millis(500), popupContent);
        ftShow.setFromValue(0.0);
        ftShow.setToValue(1.0);
        ftShow.play();
        
        popupContent.toFront();
    }
    public void hidePopup(){
        allContent.toFront();
        popup.toBack();
        popup.setVisible(false);
    }

    public void enterWizzard(){
        
        wizzardPane.toFront();
        wizzardPane.setVisible(true);
        allContent.toBack();
        allContent.setVisible(false);
        popup.toBack();
        popup.setVisible(false);
        FadeTransition ftShow = new FadeTransition( Duration.millis(500), wizzardPane);
        ftShow.setFromValue(0.0);
        ftShow.setToValue(1.0);
        ftShow.play();
        
        wizzardPane.toFront();
    }

    public void exitWizzard(){
        allContent.toFront();
        allContent.setVisible(true);
        popup.toBack();
        popup.setVisible(false);
        wizzardPane.toBack();
        wizzardPane.setVisible(false);
        FadeTransition ftShow = new FadeTransition( Duration.millis(500), allContent);
        ftShow.setFromValue(0.0);
        ftShow.setToValue(1.0);
        ftShow.play();
        
        switchPage( shoppingcartPane );
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
