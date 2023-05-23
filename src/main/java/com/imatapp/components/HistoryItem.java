package com.imatapp.components;

import java.io.IOException;

import com.imatapp.events.ShowPopupEvent;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;

public class HistoryItem extends BorderPane {
    
    @FXML
    private Text itemNmr, itemAmount, itemDate, itemPrice;
    

    public HistoryItem( Order order ){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/history_item.fxml")); 
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            itemNmr.setText( "Beställning №"+ String.valueOf( order.getOrderNumber() ) );
            itemAmount.setText( String.valueOf( order.getItems().size() ) );
            itemDate.setText( order.getDate().toString() );
            int totalPrice = 0;
            for (int i = 0; i < order.getItems().size(); i++) {
                totalPrice += order.getItems().get(i).getProduct().getPrice();
            }
            itemPrice.setText( String.valueOf( totalPrice ) + " kr" );
            InspectItems inspectItems = new InspectItems( order.getItems().toArray(new Product[order.getItems().size()]) );
            Event.fireEvent(this, new ShowPopupEvent(inspectItems));
        } catch (IOException exception) {
            System.out.println("Error loading inspect_items.fxml");
            throw new RuntimeException(exception);
        }
        
    }
}
