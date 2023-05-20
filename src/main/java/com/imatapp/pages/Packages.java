package com.imatapp.pages;

import java.util.List;

import com.imatapp.components.PackageCard;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class Packages {

    private IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    private List<Order> orders = iMatDataHandler.getOrders();
    private Product[] dagesErbjudandeProducts = new Product[] {
        iMatDataHandler.getProduct(107),
        iMatDataHandler.getProduct(120),
        iMatDataHandler.getProduct(97),
        iMatDataHandler.getProduct(60),
        iMatDataHandler.getProduct(64),
        iMatDataHandler.getProduct(44),
        iMatDataHandler.getProduct(87),
        iMatDataHandler.getProduct(153),
        iMatDataHandler.getProduct(79),
        iMatDataHandler.getProduct(70),
        iMatDataHandler.getProduct(3),
    };


    private PackageCard dagensErbjudande, senasteOrder;

    @FXML
    private VBox dagensErbjudandeCard, senasteOrderCard;

    @FXML
    public void initialize() {
        
        int totaltDagensErbjudande = 0;
        for (Product product : dagesErbjudandeProducts) {
            totaltDagensErbjudande += product.getPrice();
        }
        
        dagensErbjudande = new PackageCard(dagensErbjudandeCard, "Dagens Erbjudande", totaltDagensErbjudande, dagesErbjudandeProducts); 
        
        if (orders.isEmpty() != true){
            System.out.println("Senaste order storlek -" + orders.get(0).getItems().size());
            Product[] senasteOrderProducts = new Product[orders.get(0).getItems().size()];
            orders.get(0).getItems().toArray(senasteOrderProducts);
            int i = 0;
            System.out.println("Senaste order produkter:" );
            for (Product product : senasteOrderProducts) {
                System.out.println(" - " + product.getName());
                senasteOrderProducts[i] = iMatDataHandler.getProduct(product.getProductId());
                i++;
            }
            int totalPriceSensate = 0;
            for (Product product : senasteOrderProducts) {
                totalPriceSensate += product.getPrice();
            }
            senasteOrder = new PackageCard(senasteOrderCard, "Senaste Beställning", totalPriceSensate, senasteOrderProducts); 
        }
        else{

            Product[] rekomenderasProducts = new Product[] {
                iMatDataHandler.getProduct(115),
                iMatDataHandler.getProduct(140),
                iMatDataHandler.getProduct(124),
                iMatDataHandler.getProduct(11),
                iMatDataHandler.getProduct(72),
                iMatDataHandler.getProduct(128),
                iMatDataHandler.getProduct(36),
                iMatDataHandler.getProduct(123),
            };
            int totaltRekomenderas = 0;
            for (Product product : rekomenderasProducts) {
                totaltRekomenderas += product.getPrice();
            }
            senasteOrder = new PackageCard(senasteOrderCard, "Rekommenderas", totaltRekomenderas, rekomenderasProducts );
        }


    }

}
