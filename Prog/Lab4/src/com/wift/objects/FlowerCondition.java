package com.wift.objects;

public enum FlowerCondition {
    SMELL_SWEET("благоухают");

    private final String state;

    FlowerCondition(String state) {
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
