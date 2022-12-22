package com.wift.alive;

public class MumiTroll extends Character{
    public MumiTroll(String name, Emotions emotion, States... states) {
        super(name, emotion, states);
    }
    public MumiTroll(String name) {
        super(name);
    }

    public void shake(BodeParts what) {
        System.out.println(name + " с жаром потрес " + what);
    }
}
