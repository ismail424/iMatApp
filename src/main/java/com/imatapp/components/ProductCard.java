package com.imatapp.components;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

public class ProductCard extends VBox {

    @FXML
    private ImageView productImage;

    @FXML
    private Text productTitle, productPrice, productPriceDecimal, productAmount, productUnit;

    @FXML
    private Button productAdd, productRemove, productFirstAdd;

    public ProductCard(Product product, IMatDataHandler dataHandler) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/product_card.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        productImage = (ImageView) fxmlLoader.getNamespace().get("productImage");
        productTitle = (Text) fxmlLoader.getNamespace().get("productTitle");
        productPrice = (Text) fxmlLoader.getNamespace().get("productPrice");
        productPriceDecimal = (Text) fxmlLoader.getNamespace().get("productPriceDecimal");
        productAmount = (Text) fxmlLoader.getNamespace().get("productAmount");
        productUnit = (Text) fxmlLoader.getNamespace().get("productUnit");
        productAdd = (Button) fxmlLoader.getNamespace().get("productAdd");
        productRemove = (Button) fxmlLoader.getNamespace().get("productRemove");
        productFirstAdd = (Button) fxmlLoader.getNamespace().get("productFirstAdd");

        productImage.setImage(dataHandler.getFXImage(product, 200, 200));
        productTitle.setText(product.getName());
        double price = product.getPrice();
        int intPrice = (int) price;
        int decimalPart = (int) ((price - intPrice) * 100);

        productPrice.setText(String.valueOf(intPrice));
        productPriceDecimal.setText(String.format("%02d", decimalPart));
        productUnit.setText(" " + product.getUnit());
    }
}
