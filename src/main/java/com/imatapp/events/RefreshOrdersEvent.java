package com.imatapp.events;

import javafx.event.Event;
import javafx.event.EventType;


public class RefreshOrdersEvent extends Event {
    public static final EventType<RefreshOrdersEvent> REFRESH_ORDERS =  new EventType<>(Event.ANY, "REFRESH_ORDERS");

    public RefreshOrdersEvent() {
        super(REFRESH_ORDERS);
    }
}