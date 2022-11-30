package com.wift.alive;

public class MumiTroll extends Character{
    public MumiTroll(String name, String padejName, Emotions emotion, States... states) {
        super(name, padejName, emotion, states);
    }
    public MumiTroll(String name, Emotions emotion, States... states) {
        super(name, emotion, states);
    }
    public MumiTroll(String name) {
        super(name);
    }

    public void beingMumiTroll() {

    }
}
