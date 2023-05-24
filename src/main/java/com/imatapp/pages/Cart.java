package com.imatapp.pages;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import com.imatapp.components.CartItem;

public class Cart {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    @FXML
    private Button goToCheckout;
    
    @FXML
    private VBox allItems;

    @FXML
    public void initialize(){

        ShoppingCart cart = iMatDataHandler.getShoppingCart();
        cart.getItems().forEach(item -> {
            CartItem cartItem = new CartItem(item.getProduct());
            allItems.getChildren().add(cartItem);
        });


    }
}
