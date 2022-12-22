package com.wift.events;

public enum EventType {
    DEFAULT(""),
    SUDDENLY("вдруг"),
    UNHEARD("неслыханное"),
    INTO_SILENCE("в молчание"),
    NOTHING_SPECIAL("ничего особенного"),
    AGAIN("снова"),
    OFTEN("часто"),
    ALONE("один"),
    NOTLOUD("негромкий"),
    DEAD("мертвая"),
    FEW_SECONDS("несколько секунд"),
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
