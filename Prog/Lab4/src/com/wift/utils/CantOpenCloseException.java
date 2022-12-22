package com.wift.utils;

public class CantOpenCloseException extends Exception{
    protected boolean isOpen;
    protected Entity entity;
    public CantOpenCloseException(String message, boolean isOpen, Entity entity) {
        super(message);
        this.isOpen = isOpen;
        this.entity = entity;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public Entity getEntity() {
        return entity;
    }
}
