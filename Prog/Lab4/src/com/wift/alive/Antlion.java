package com.wift.alive;

public class Antlion extends Alive{
    public Antlion(String name, Emotions emotion, States... states) {
        super(name, emotion, states);
    }

    public Antlion(String name) {
        super(name);
    }

    public void scream() {
        setEmotion(Emotions.ANGRY);
        System.out.println(name + " кричал и сыпал проклятиями");
    }
}
