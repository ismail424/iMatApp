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

    private static final FXMLLoader fxmlLoader = new FXMLLoader();

    private Product product;
    private IMatDataHandler dataHandler;

    static {
        fxmlLoader.setControllerFactory(clazz -> clazz.newInstance());
        fxmlLoader.setLocation(ProductCard.class.getResource("/fxml/components/product_card.fxml"));
    }

    public ProductCard(Product product, IMatDataHandler dataHandler) {
        this.product = product;
        this.dataHandler = dataHandler;

        if (fxmlLoader.getNamespace() == null) {
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTr     ace();
            }
        }

        productImage.setImage(dataHandler.getFXImage(product, 200, 200));
        productTitle.setText(product.getName());
    }

    @FXML
    private void initialize() {
        double price = product.getPrice();
        int intPrice = (int) price;
        int decimalPart = (int) ((price - intPrice) * 100);

        productPrice.setText(String.valueOf(intPrice));
        productPriceDecimal.setText(String.format("%02d", decimalPart));
        productUnit.setText(" " + product.getUnit());
    }

}
