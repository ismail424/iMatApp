package com.imatapp.components;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

public class ProductCard extends VBox {
    IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    @FXML
    private ImageView productImage;

    @FXML
    private Text productTitle, productPrice, productPriceDecimal, productAmount, productUnit;

    public ProductCard( Product product ){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/product_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            productImage.setImage(dataHandler.getFXImage(product, 200, 200));
            productTitle.setText(product.getName());


        } catch (IOException exception) {
            System.out.println("Error loading product_card_side.fxml");
            throw new RuntimeException(exception);
        }
    
    }

}
