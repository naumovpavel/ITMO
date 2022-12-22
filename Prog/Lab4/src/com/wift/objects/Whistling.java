package com.wift.objects;

import com.wift.alive.States;
import com.wift.utils.Entity;

public class Whistling extends Entity {
    protected States state;
    public Whistling(String name) {
        super(name);
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    public void means() {
        System.out.println(name + " означал " + state);
    }
}
