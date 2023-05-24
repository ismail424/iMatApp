package com.imatapp.components;

import java.io.IOException;
import java.util.List;

import com.imatapp.events.ShowPopupEvent;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class HistoryItem extends BorderPane {
    
    @FXML
    private Text itemNmr, itemAmount, itemDate, itemPrice;
    
    @FXML
    private Button showDetail;

    private Product[] products;

    public HistoryItem( Order order ){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/history_item.fxml")); 
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("Error loading inspect_items.fxml");
            throw new RuntimeException(exception);
        }
        
        itemNmr.setText( "Beställning №"+ String.valueOf( order.getOrderNumber() ) );
        itemAmount.setText( String.valueOf( order.getItems().size() ) );
        itemDate.setText( order.getDate().toString() );
        int totalPrice = 0;
        for (int i = 0; i < order.getItems().size(); i++) {
            totalPrice += order.getItems().get(i).getProduct().getPrice();
        }
        itemPrice.setText( String.valueOf( totalPrice ) + " kr" );
        List<ShoppingItem> shoppingItems = order.getItems();

        Product[] orderProducts = new Product[ shoppingItems.size() ];
        for (int i = 0; i < shoppingItems.size(); i++) {
            orderProducts[i] = shoppingItems.get(i).getProduct();
        }
        this.products = orderProducts;
        showDetail.setOnAction( e -> {
            InspectItems inspectItems = new InspectItems( this.products );
            Event.fireEvent(this, new ShowPopupEvent(inspectItems));
        } );
    }
}
