package com.imatapp.components;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ProductCardSide  extends AnchorPane {

    @FXML
    private ImageView productImage;

    @FXML
    private Text productTitle, productPrice, productAmount, productPriceUnit;

    public ProductCardSide( String title, int price, int amount, String unit, Image image){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/product_card_side.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            productTitle.setText(title);
            productPriceUnit.setText("Pris:" + String.valueOf(price) + " " + unit);
            productAmount.setText("Antal: " + String.valueOf(amount)); 
            int totalPrice = price * amount;
            productPrice.setText(String.valueOf( totalPrice ) + " kr");
            productImage.setImage(image);
        } catch (IOException exception) {
            System.out.println("Error loading product_card_side.fxml");
            throw new RuntimeException(exception);
        }
    }
}
