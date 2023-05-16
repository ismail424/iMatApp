package com.imatapp.pages;

import com.imatapp.components.PackageCard;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class Packages {

    public IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private Product[] dagesErbjudandeProducts = new Product[]{
        iMatDataHandler.getProduct(1),
        iMatDataHandler.getProduct(2),
        iMatDataHandler.getProduct(3),
    };
    private Product[] senasteOrderProducts = new Product[]{
        iMatDataHandler.getProduct(1),
        iMatDataHandler.getProduct(2),
        iMatDataHandler.getProduct(3),
    };

    private PackageCard dagensErbjudande, senasteOrder;

    @FXML
    private VBox dagensErbjudandeCard, senasteOrderCard;

    @FXML
    public void initialize() {
        

        dagensErbjudande = new PackageCard(this, dagensErbjudandeCard, "Dagens Erbjudande", 199, dagesErbjudandeProducts); 
        senasteOrder = new PackageCard(this, senasteOrderCard, "Senaste Beställning", 200, senasteOrderProducts); 


    }

}
