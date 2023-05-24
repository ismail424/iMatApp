package com.imatapp.components;

import java.io.IOException;

import com.imatapp.pages.Cart;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;
import se.chalmers.cse.dat216.project.Product;

public class CartItem extends HBox {

    private Cart parentController;
    private IMatDataHandler dataHandler;
    private Product product;
    private ShoppingItem shoppingItem;

    @FXML
    private ImageView itemImage;

    @FXML
    private Text itemTitle, itemUnitPrice, itemPrice, itemAmount;
    
    @FXML 
    private Button itemRemove, itemInc, itemDec;

    private Integer amount = 1;

    public CartItem( ShoppingItem shoppingItem , IMatDataHandler dataHandler, Cart parentController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/cart_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.parentController = parentController;
        this.shoppingItem = shoppingItem;
        this.product = shoppingItem.getProduct();
        this.dataHandler = dataHandler;
        itemImage.setImage(dataHandler.getFXImage(product, 200, 200));
        itemTitle.setText(product.getName());
        itemUnitPrice.setText(String.valueOf(product.getPrice()) + " " + product.getUnit());
        itemPrice.setText(String.valueOf((int) product.getPrice()) + " kr");

        // Get how many of this product is in the cart
        ShoppingCart shoppingCart = dataHandler.getShoppingCart();
        for (ShoppingItem item : shoppingCart.getItems()) {
            if (item.getProduct().equals(product)) {
                amount = (int) item.getAmount();
            }
        }

        itemAmount.setText(amount.toString());

        itemRemove.setOnAction(e -> {
            shoppingCart.removeProduct(product);
            System.out.println("Removed " + product.getName() + " from cart");
        });

        itemInc.setOnAction(e -> {
            amount++;
            shoppingItem.setAmount( amount );
            shoppingCart.fireShoppingCartChanged(shoppingItem, isCache());
            itemAmount.setText(String.valueOf(amount));
            System.out.println("Added " + product.getName() + " to cart");
        });

        itemDec.setOnAction(e -> {
            if (amount > 1) {
                amount--;
                shoppingItem.setAmount( amount );
                shoppingCart.fireShoppingCartChanged(shoppingItem, isCache());
                itemAmount.setText(String.valueOf(amount));
                System.out.println("Removed one of this " + product.getName() + " from cart");
            }else{
                shoppingCart.removeProduct(product);
                System.out.println("Removed " + product.getName() + " from cart");
            }
        });

        // make a listern for amount
        shoppingCart.addShoppingCartListener(e -> {
            try {
                if (e.getShoppingItem().equals(shoppingItem)) {
                    amount = (int) e.getShoppingItem().getAmount();
                    itemAmount.setText(String.valueOf(amount));
                }
            } catch (Exception _pass) {
                
                
                // TODO: handle exception

            }
        });
        
    }
}
