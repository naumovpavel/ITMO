package com.wift.alive;

public enum Emotions {
    SCARED("испуган"),
    CALM("спокойный");

    private final String state;

    Emotions(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return getState();
    }
}
