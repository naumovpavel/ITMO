package com.wift.events;

public enum EventType {
    DEFAULT(""),
    SUDDENLY("вдруг"),
    AGAIN("снова"),
    UNBEARABLE("невыносимое");
    private final String eventType;
    EventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    @Override
    public String toString() {
        return this.getEventType();
    }
}
