package com.wift.utils;

public enum Direction {
    IN_ALL_DIRECTION("во все стороны"),
    STRAIGHT("прямо");

    private final String direction;

    Direction(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return getDirection();
    }
}
