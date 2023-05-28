package com.imatapp.pages;


import java.util.List;

import com.imatapp.components.HistoryItem;
import com.imatapp.events.RefreshOrdersEvent;
import com.imatapp.events.SwitchPageEvent;

import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;

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

        addEventHandler(RefreshOrdersEvent.REFRESH_ORDERS, this::handleRefreshOrdersEvent);
        displayOrders();
    }
    public void handleRefreshOrdersEvent(RefreshOrdersEvent event) {
        displayOrders();
    }

    private void displayOrders() {
        List<Order> orders = iMatDataHandler.getOrders();
        if (orders.isEmpty()) {
            Text noOrders = new Text("Du har inga tidigare ordrar");
            noOrders.getStyleClass().add("no-orders");
            historyItems.getChildren().add(noOrders);
            return;
        }
        orders.forEach(order -> {
            HistoryItem orderItem = new HistoryItem(order);
            historyItems.getChildren().add(orderItem);
        });
    }  
}
