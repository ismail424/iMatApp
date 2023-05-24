package com.imatapp.components;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.Product;

public class CartItem extends HBox {

    IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    @FXML
    private ImageView itemImage;

    @FXML
    private Text ItemTitle, itemUnitPrice, itemPrice, itemAmount;
    
    @FXML 
    private Button itemRemove, itemInc, itemDec;


    public CartItem( Product product ){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/product_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Error loading product_card.fxml");
            e.printStackTrace();
        }
  

        itemImage.setImage(dataHandler.getFXImage(product, 200, 200));
        ItemTitle.setText(product.getName());
        itemUnitPrice.setText(String.valueOf(product.getPrice()) + " " + product.getUnit());
        itemPrice.setText(String.valueOf(product.getPrice()) + " " + product.getUnit());
        itemAmount.setText("1");

        itemRemove.setOnAction(e -> {
            ShoppingCart shoppingCart = dataHandler.getShoppingCart();
            shoppingCart.removeProduct(product);
            System.out.println("Removed " + product.getName() + " from cart");
        });

        itemInc.setOnAction(e -> {
            ShoppingCart shoppingCart = dataHandler.getShoppingCart();
            shoppingCart.addProduct(product);
            System.out.println("Added " + product.getName() + " to cart");
        });

        itemDec.setOnAction(e -> {
            ShoppingCart shoppingCart = dataHandler.getShoppingCart();
            shoppingCart.removeProduct(product);
            System.out.println("Removed " + product.getName() + " from cart");
        });
        
    }
}
