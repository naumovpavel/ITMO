package com.wift.alive;

public abstract class Character extends Alive {
    public Character(String name, String padejName, Emotions emotion, States... states) {
        super(name, padejName, emotion, states);
    }

    public Character(String name, Emotions emotion, States... states) {
        super(name, emotion, states);
    }

    public Character(String name) {
        super(name);
    }

    @Override
    public void setEmotion(Emotions emotion) {
        super.setEmotion(emotion);
        System.out.printf("%s становиться %s", name, emotion);
    }
}
