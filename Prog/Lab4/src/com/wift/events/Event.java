package com.wift.events;

import com.wift.alive.Alive;
import com.wift.alive.Emotions;
import com.wift.alive.States;
import com.wift.places.Place;

import java.util.Arrays;
import java.util.Objects;

public class Event implements Doable {
    protected String name;
    protected EventType[] types;
    protected Place place;

    public Event(String name, EventType... types) {
        this.name = name;
        this.types = types;
    }

    public Event(String name, Place place, EventType... types) {
        this.name = name;
        this.place = place;
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
    public void dontHappend() {
        System.out.println(toString() + " не произошло");
    }

    public void increase() {
        System.out.println(toString() + " возрастало");
    }

    static public void tellAboutEvent(Alive... alives) {
        StringBuilder out = new StringBuilder("об случившемся расказали ");
        for (Alive alive : alives) {
            out.append(alive).append(" ");
        }
        System.out.println(out);
    }

    static public void reacted(Emotions emotion, Alive... alives) {
        StringBuilder out = new StringBuilder();
        for (Alive alive : alives) {
            out.append(alive).append(" ");
        }
        out.append(emotion).append(" ");
        out.append(" отреагировали");
        System.out.println(out);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        if (place != null)
            out.append(place).append(" ");
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
