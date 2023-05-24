package com.imatapp.pages;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.imatapp.components.ProductCard;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingCartListener;

public class AllProducts {

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    @FXML
    private Text allCategory;
    @FXML
    private FlowPane mainFlowPane;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    
    @FXML
    private TextField SearchBar;

    @FXML
    private VBox categoryList;

    @FXML
    public void initialize(){
   
        List<Product> allProducts = iMatDataHandler.getProducts();
        loadProductCards(allProducts, iMatDataHandler);

        ProductCategory[] categories = ProductCategory.values();
        for (ProductCategory category: categories){
            String SwedishName =  category.name();
            switch (SwedishName) {
                case "CABBAGE":
                    SwedishName = "Kål";
                    break;
                case "COLD_DRINKS":
                    SwedishName = "Kalla drycker";
                    break;
                case "DAIRIES":
                    SwedishName = "Mejeriprodukter";
                    break;
                case "EXOTIC_FRUIT":
                    SwedishName = "Exotiska frukter";
                    break;
                case "FISH":
                    SwedishName = "Fisk";
                    break;
                case "FRUIT":
                    SwedishName = "Frukt";
                    break;
                case "GRAPE_AND_BERRY":
                    SwedishName = "Bär";
                    break;
                case "HOT_DRINKS":
                    SwedishName = "Varma drycker";
                    break;
                case "MEAT":
                    SwedishName = "Kött";
                    break;
                case "MELONS":
                    SwedishName = "Meloner";
                    break;
                case "PASTA":
                    SwedishName = "Pasta";
                    break;
                case "POD":
                    SwedishName = "Baljväxter";
                    break;
                case "POTATO_RICE":
                    SwedishName = "Potatis och ris";
                    break;
                case "ROOT_VEGETABLE":
                    SwedishName = "Rotfrukter";
                    break;
                case "SWEET":
                    SwedishName = "Godis";
                    break;
                case "VEGETABLE_FRUIT":
                    SwedishName = "Grönsaksfrukt";
                    break;
                case "BERRY":
                    SwedishName = "Bär";
                    break;
                case "HERB":
                    SwedishName = "Örter";
                    break;
                case "CITRUS_FRUIT":
                    SwedishName = "Citrusfrukter";
                    break;
                case "FLOUR_SUGAR_SALT":
                    SwedishName = "Mjöl, socker, salt";
                    break;
                case "NUTS_AND_SEEDS":
                    SwedishName = "Nötter och frön";
                    break;
                case "BREAD":
                    SwedishName = "Bröd";
                    break;
            }
            Text categoryText = new Text( SwedishName );
            categoryText.getStyleClass().add("allproducts_categories_text");
            categoryText.setId(category.toString());
            categoryList.getChildren().add( categoryText );
        }


        // Add event listeners to all the category texts
        categoryList.getChildren().forEach(categoryText -> {
            categoryText.setOnMouseClicked(e -> {

                // Get the category from the category text
                ProductCategory category = ProductCategory.valueOf(categoryText.getId());
                // Get all the products in the category
                List<Product> productsInCategory = iMatDataHandler.getProducts(category);
                // Clear the mainFlowPane
                mainFlowPane.getChildren().clear();
                // Load the product cards for the products in the category
                loadProductCards(productsInCategory, iMatDataHandler);


                // Remove the active class from all the category texts
                categoryList.getChildren().forEach(text -> {
                    text.getStyleClass().remove("allproducts_categories_text_active");
                });
                
                // Add the active class to the clicked category text
                categoryText.getStyleClass().add("allproducts_categories_text_active");

            });
        });

        // Add event listener to the all category text
        allCategory.setOnMouseClicked(e -> {
            // Clear the mainFlowPane
            mainFlowPane.getChildren().clear();
            // Load the product cards for all the products
            loadProductCards(allProducts, iMatDataHandler);
            
            categoryList.getChildren().forEach(text -> {
                text.getStyleClass().remove("allproducts_categories_text_active");
            });
            allCategory.getStyleClass().add("allproducts_categories_text_active");
        });


        // Add event listener to the search bar
        SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            // Clear the mainFlowPane
            mainFlowPane.getChildren().clear();
            // Get all the products that match the search query
            List<Product> products = iMatDataHandler.findProducts(newValue);
            // Load the product cards for the products that match the search query
            loadProductCards(products, iMatDataHandler);
        });


    }

    public void loadProductCards(List<Product> products, IMatDataHandler dataHandler) {
        for (Product product : products) {
            CompletableFuture.runAsync(() -> {
                ProductCard productCard = new ProductCard(product, dataHandler);
                Platform.runLater(() -> mainFlowPane.getChildren().add(productCard));
            }, executorService);
        }
    }
}
