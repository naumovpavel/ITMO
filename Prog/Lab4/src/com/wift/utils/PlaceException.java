package com.wift.utils;

import com.wift.alive.Alive;

public class PlaceException extends RuntimeException{
    protected Alive alive;
    public PlaceException(String message, Alive alive) {
        super(message);
        this.alive = alive;
    }
}
