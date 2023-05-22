package com.imatapp.events;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.layout.AnchorPane;

public class ShowPopupEvent extends Event {
    public static final EventType<ShowPopupEvent> SHOW_POPUP =
            new EventType<>(Event.ANY, "SHOW_POPUP");

    private final AnchorPane anchorPane;

    public ShowPopupEvent(AnchorPane anchorPane) {
        super(SHOW_POPUP);
        this.anchorPane = anchorPane;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }
}
