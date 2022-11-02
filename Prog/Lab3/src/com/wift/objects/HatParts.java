package com.wift.objects;

public enum HatParts {
    TOP("вверх"),
    DOWN("низ"),
    BRIMS("полей");

    private final String part;

    HatParts(String part) {
        this.part = part;
    }

    public String getPart() {
        return part;
    }

    @Override
    public String toString() {
        return getPart();
    }
}