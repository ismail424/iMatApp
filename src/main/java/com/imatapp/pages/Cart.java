package com.imatapp.pages;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingCartListener;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.imatapp.components.CartItem;

public class Cart {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @FXML
    private Button goToCheckout;
    
    @FXML
    private Text cartTotalPrice;
    
    @FXML
    private VBox allItems;

    private int  amount = 0;

    @FXML
    public void initialize(){

        updateCart();
            
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        cartTotalPrice.setText(String.valueOf((int) shoppingCart.getTotal()) + " kr");

        // amount of products in cart
        this.amount = shoppingCart.getItems().size();

        shoppingCart.addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                Platform.runLater(() -> {
                    if ( shoppingCart.getItems().size() != amount){
                        updateCart();
                        amount = shoppingCart.getItems().size();
                    }
                    cartTotalPrice.setText(String.valueOf((int) shoppingCart.getTotal()) + " kr");
                });
            }
        });

        // TODO FIX THIS
        // goToCheckout.setOnAction(e -> {
        //     Event.fireEvent(goToCheckout, new SwitchPageEvent("Checkout"));
        // });
    }

    public void updateCart(){
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        List<ShoppingItem> cartItems = shoppingCart.getItems();
        allItems.getChildren().clear();
        loadCartItems(cartItems, iMatDataHandler);
    }


    public void loadCartItems(List<ShoppingItem> items, IMatDataHandler dataHandler) {
        for (ShoppingItem item : items) {
            CompletableFuture.runAsync(() -> {
                CartItem cartItem = new CartItem(item, dataHandler, this);
                Platform.runLater(() -> allItems.getChildren().add(cartItem));
            }, executorService);
        }
    }
}
