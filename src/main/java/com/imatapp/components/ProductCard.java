package com.imatapp.components;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class ProductCard extends VBox {

    @FXML
    private ImageView productImage;

    @FXML
    private Text productTitle, productPrice, productPriceDecimal, productAmount, productUnit;
    
    @FXML
    private Button productAdd, productRemove, productFirstAdd;

    @FXML
    private StackPane buttons;

    @FXML
    private HBox normalButtons;

    private int amount = 0;
    private IMatDataHandler dataHandler;
    private Product product;

    public ProductCard(Product product, IMatDataHandler dataHandler) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/product_card.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }  

        this.dataHandler = dataHandler;
        this.product = product;
        this.amount = getAmount();

        ShoppingCart shoppingCart = dataHandler.getShoppingCart();
        productImage = (ImageView) fxmlLoader.getNamespace().get("productImage");
        productTitle = (Text) fxmlLoader.getNamespace().get("productTitle");
        productPrice = (Text) fxmlLoader.getNamespace().get("productPrice");
        productPriceDecimal = (Text) fxmlLoader.getNamespace().get("productPriceDecimal");
        productAmount = (Text) fxmlLoader.getNamespace().get("productAmount");
        productUnit = (Text) fxmlLoader.getNamespace().get("productUnit");
        productAdd = (Button) fxmlLoader.getNamespace().get("productAdd");
        productRemove = (Button) fxmlLoader.getNamespace().get("productRemove");
        productFirstAdd = (Button) fxmlLoader.getNamespace().get("productFirstAdd");
        buttons = (StackPane) fxmlLoader.getNamespace().get("buttons");
        normalButtons = (HBox) fxmlLoader.getNamespace().get("normalButtons");

        productImage.setImage(dataHandler.getFXImage(product, 200, 200));
        productTitle.setText(product.getName());
        double price = product.getPrice();
        int intPrice = (int) price;
        int decimalPart = (int) ((price - intPrice) * 100);
        productPrice.setText(String.valueOf(intPrice));
        productPriceDecimal.setText(String.format("%02d", decimalPart));
        productUnit.setText(" " + product.getUnit());
        
        if (amount > 0) {
            productAmount.setText(String.valueOf(amount));
            hideFirstButton();  
        } 

        productFirstAdd.setOnAction(e -> {
            amount = 1;
            productAmount.setText( String.valueOf(amount) );
            hideFirstButton();
            shoppingCart.addProduct(product);
        });

        productAdd.setOnAction(e -> {
            amount++;
            ShoppingItem shoppingItem = null;
            for (ShoppingItem item : shoppingCart.getItems()) {
                if (item.getProduct().equals(product)) {
                    shoppingItem = item;
                }
            }
            if (shoppingItem == null) {
                shoppingItem = new ShoppingItem(product, 0);
            }
            shoppingItem.setAmount(amount);
            shoppingCart.fireShoppingCartChanged(shoppingItem, isCache());
            productAmount.setText(String.valueOf(amount));
            System.out.println("Added " + product.getName() + " to cart");
            productAmount.setText( String.valueOf(amount) );
        });

        productRemove.setOnAction(e -> {
            amount--;
            ShoppingItem shoppingItem = null;
            for (ShoppingItem item : shoppingCart.getItems()) {
                if (item.getProduct().equals(product)) {
                    shoppingItem = item;
                }
            }
            if (amount <= 0) {
                shoppingCart.removeItem(shoppingItem);
                showFirstButton();

            } else {
                shoppingItem.setAmount(amount);
                shoppingCart.fireShoppingCartChanged(shoppingItem, isCache());
            }
            System.out.println("Removed " + product.getName() + " from cart");
        });

        shoppingCart.addShoppingCartListener(e -> {
            try{
                if (e.getShoppingItem().getProduct().equals(product)) {
                    amount = (int) e.getShoppingItem().getAmount();
                    productAmount.setText(String.valueOf(amount));
                    if (amount == 0) {
                        showFirstButton();
                    }
                }
            }
            catch( Exception ex ){
                showFirstButton();
            }
        });

    }

    public void hideFirstButton() {
        productFirstAdd.setVisible(false);
        normalButtons.setVisible(true);
        normalButtons.toFront();
    }

    public void showFirstButton() {
        productFirstAdd.setVisible(true);
        normalButtons.setVisible(false);
        normalButtons.toBack();
    }


    public int getAmount() {
        ShoppingCart shoppingCart =  dataHandler.getShoppingCart();
        for (ShoppingItem item : shoppingCart.getItems()) {
            if (item.getProduct().equals(product)) {
                return (int) item.getAmount();
            }
        }
        return 0;
    }
}
