package com.wift.alive;

public enum Sounds {
    SCREAMS("вопли");
    private final String Sound;
    Sounds(String bodyPart) {
        Sound = bodyPart;
    }

    public String getSound() {
        return Sound;
    }

    @Override
    public String toString() {
        return this.getSound();
    }
}
