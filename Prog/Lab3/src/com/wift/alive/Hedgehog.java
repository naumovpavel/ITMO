package com.wift.alive;

import com.wift.events.EventType;
import com.wift.utils.Direction;

public class Hedgehog extends Animal implements HedgehogInterface{
    public Hedgehog(String name, Emotions emotion, States... states) {
        super(name, emotion, states);
    }

    public void wriggle(Direction derction) {
        System.out.printf("%s поводил носом %s\n", this, derction);
    }

    public void squint(States how) {
        System.out.printf("%s %s щюрить %s\n", name, how, BodeParts.EYES);
    }
}
