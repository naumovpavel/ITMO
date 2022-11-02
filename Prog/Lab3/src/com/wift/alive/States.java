package com.wift.alive;

public enum States {
    DEFAULT(""),
    WET("мокрый"),
    DISHEVELED("взъерошенный"),
    MAYBESMALLEST("наверно самый маленький на свете"),
    HALF_SIGHTED("подслеповато");

    private final String state;

    States(String state) {
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
