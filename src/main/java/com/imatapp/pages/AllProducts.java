package com.imatapp.pages;

import java.util.List;

import com.imatapp.components.ProductCard;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;

public class AllProducts {

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    @FXML
    private Text berryCategory, breadCategory, cabbageCategory, citrus_fruitCategory, cold_drinksCategory, dairiesCategory, exotic_fruitCategory, fishCategory, flour_sugar_saltCategory, fruitCategory, herbCategory, hot_drinksCategory, meatCategory, melonsCategory, nuts_and_seedsCategory, pastaCategory, podCategory, potato_riceCategory, root_vegetableCategory, sweetCategory, vegetable_fruitCategory;
    @FXML
    private FlowPane mainFlowPane;

    @FXML
    public void initialize(){
        Text[] allCategories = new Text[] {
            berryCategory, breadCategory, cabbageCategory, citrus_fruitCategory, cold_drinksCategory, dairiesCategory, exotic_fruitCategory, fishCategory, flour_sugar_saltCategory, fruitCategory, herbCategory, hot_drinksCategory, meatCategory, melonsCategory, nuts_and_seedsCategory, pastaCategory, podCategory, potato_riceCategory, root_vegetableCategory, sweetCategory, vegetable_fruitCategory
        };

        List<Product> allProducts = iMatDataHandler.getProducts();
        allProducts.forEach(product -> {
            // print time benchmark
            long startTime = System.nanoTime();
            ProductCard productCard = new ProductCard(product, iMatDataHandler);
            mainFlowPane.getChildren().add(productCard);
            long endTime = System.nanoTime();
            System.out.println("Time to create product card: " + (endTime - startTime)/ 1000000 + "ms");
        });


        for(Text category : allCategories ){
    
            //remove all the active classes
            for (Text category2 : allCategories) {
                category2.getStyleClass().remove("allproducts_categories_text_active");
            }
            category.getStyleClass().add("allproducts_categories_text_active");
        }



    }


}
