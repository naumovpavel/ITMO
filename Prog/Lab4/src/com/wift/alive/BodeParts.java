package com.wift.alive;

public enum BodeParts {
    HEAD("голова"),
    CHEST("грудь"),
    LEG("нога"),
    HAND("рука"),
    VOICE("голос"),
    PAWS("лапы"),
    MOUTH("рот"),
    FINGER("палец"),
    EYES("глаза");
    private final String BodyPart;
    BodeParts(String bodyPart) {
        BodyPart = bodyPart;
    }

    public String getBodyPart() {
        return BodyPart;
    }

    @Override
    public String toString() {
        return this.getBodyPart();
    }
}
