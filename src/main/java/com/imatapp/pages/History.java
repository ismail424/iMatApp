package com.imatapp.pages;


import com.imatapp.components.HistoryItem;
import com.imatapp.events.SwitchPageEvent;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class History extends AnchorPane {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    @FXML
    private Button GoBack;

    @FXML
    private VBox historyItems;

    @FXML
    public void initialize() { 

        GoBack.setOnAction(e -> {
            Event.fireEvent(GoBack, new SwitchPageEvent("Account"));  
        });

        // Get all the previous orders
        System.out.println(iMatDataHandler.getOrders());
        if (iMatDataHandler.getOrders().isEmpty()) {
            Text noOrders = new Text("Du har inga tidigare ordrar");
            noOrders.getStyleClass().add("no-orders");
            historyItems.getChildren().add(noOrders);
            return;
        }
        else{
            iMatDataHandler.getOrders().forEach(order -> {
                HistoryItem orderItem = new HistoryItem(order);
                historyItems.getChildren().add(orderItem);
            });
        }


    }
}
