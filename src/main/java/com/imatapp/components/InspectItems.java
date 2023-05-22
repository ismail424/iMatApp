package com.imatapp.components;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

public class InspectItems extends AnchorPane {
    private IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    @FXML
    private VBox mainVbox;

   // Add constructor to accept Product[] and to create a AnchorPane with the correct items
    public InspectItems( Product[] products ){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/inspect_items.fxml")); 
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            for (Product product : products) {
                ProductCardSide productCardSide = new ProductCardSide(product.getName(), (int) product.getPrice(), 1, product.getUnit(),  iMatDataHandler.getFXImage(product));
                mainVbox.getChildren().add( productCardSide );
            }   
            mainVbox.setSpacing(6);



        } catch (IOException exception) {
            System.out.println("Error loading inspect_items.fxml");
            throw new RuntimeException(exception);
        }


    }
}
