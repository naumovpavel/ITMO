package com.wift.objects;

import com.wift.utils.Entity;

import java.util.Objects;

public class Hat extends Entity {
    protected boolean isSilence = true;
    public Hat() {
        super("шляпа");
    }

    public Hat(String name) {
        super(name);
    }

    public void drip(HatParts part) {
        System.out.format("С %s %s закапало\n", part, name);
    }

    public void pour(HatParts part) {
        System.out.format("С %s %s полилось\n", part, name);
    }

    public void showSmth(HatParts part, Entity object) {
        System.out.format("На %s %s показался %s\n", part, name, object);
    }

    public boolean isSilence() {
        System.out.println("в " + name + (isSilence ? " " : " не ") + "тихо");
        return isSilence;
    }

    public void setSilence(boolean silence) {
        isSilence = silence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Hat hat = (Hat) o;
        return name.equals(hat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
