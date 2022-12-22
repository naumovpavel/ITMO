package com.wift.alive;

public class Hemul extends Character{
    public Hemul(String name, Emotions emotion, States... states) {
        super(name, emotion, states);
    }

    public Hemul(String name) {
        super(name);
    }

    public void notLaugh() {
        System.out.println(name + " не смеялся");
    }
}
