package com.wift.events;

import java.util.Arrays;
import java.util.Objects;

public class Event implements Doable {
    protected String name;
    protected EventType[] types;

    public Event(String name, EventType... types) {
        this.name = name;
        this.types = types;
    }

    public Event(String name) {
        this.name = name;
    }

    @Override
    public void happend() {
        System.out.println(toString() + " произошло");
    }

    @Override
    public void goingOn() {
        System.out.println(toString() + " происходит");
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        if(types != null) {
            for (EventType type : types) {
                out.append(type).append(" ");
            }
        }
        out.append(name);
        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(name, event.name) && Arrays.equals(types, event.types);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(types);
        return result;
    }
}
