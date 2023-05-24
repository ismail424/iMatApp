package com.imatapp.components;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.Product;

public class ProductCard extends VBox {
    
    @FXML
    private ImageView productImage;

    @FXML
    private Text productTitle, productPrice, productAmount, productPriceUnit;

    public ProductCard( Product product ){
        
    }

}
