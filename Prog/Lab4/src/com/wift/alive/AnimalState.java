package com.wift.alive;

public enum AnimalState {
    HUNGRY("голодный"),
    WELL_FED("сытый"),
    OVERATE("обьелся"),
    WELL_OVERATE("конкретно так на кайфах");
    private final String state;

    AnimalState(String state) {
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
