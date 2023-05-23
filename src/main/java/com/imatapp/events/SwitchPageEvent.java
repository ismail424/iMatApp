package com.imatapp.events;

import javafx.event.Event;
import javafx.event.EventType;

public class SwitchPageEvent extends Event {
    public static final EventType<SwitchPageEvent> SWITCH_PAGE_EVENT = new EventType<>(Event.ANY, "SWITCH_PAGE_EVENT");

    private final String destinationPage;

    public SwitchPageEvent(String destinationPage) {
        super(SWITCH_PAGE_EVENT);
        this.destinationPage = destinationPage;
    }

    public String getDestinationPage() {
        return destinationPage;
    }
}
