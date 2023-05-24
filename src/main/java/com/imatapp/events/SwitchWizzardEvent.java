package com.imatapp.events;

import javafx.event.Event;
import javafx.event.EventType;

public class SwitchWizzardEvent extends Event {
    public static final EventType<SwitchWizzardEvent> SWITCH_WIZARD_EVENT = new EventType<>(Event.ANY, "SWITCH_WIZARD_EVENT");

    private final Boolean isEntering;

    public SwitchWizzardEvent(Boolean entering) {
        super(SWITCH_WIZARD_EVENT);
        this.isEntering = entering;
    }

    public Boolean isEntering() {
        return isEntering;
    }
}
