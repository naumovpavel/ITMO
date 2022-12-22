package com.wift.alive;

import com.wift.events.Event;
import com.wift.events.EventType;
import com.wift.places.Place;
import com.wift.utils.Entity;


public class They extends Alive{
    protected Alive[] members;

    public They(String name, Emotions emotion, States... states) {
        super(name, emotion, states);
    }

    public They(String name) {
        super(name);
    }

    public void setMembers(Alive... members) {
        this.members = members;
        StringBuilder out = new StringBuilder();
        for (Alive alive : members) {
            out.append(alive).append(" ");
        }
        name = out.toString();
    }

    public Alive[] getMembers() {
        return members;
    }

    public void continuee() {
        System.out.println(name + " продолжили");
    }

    public void muchLaugh() {
        System.out.println(name + "  ревели и катались от радости под столом");
    }

    public void decide(Event event) {
        System.out.println(name + " решил " + event);
    }

    @Override
    public String toString() {
        if(members == null) {
            return name;
        }
        StringBuilder out = new StringBuilder();
        for (Alive alive : members) {
            out.append(alive).append(" ");
        }
        return out.toString();
    }
}
