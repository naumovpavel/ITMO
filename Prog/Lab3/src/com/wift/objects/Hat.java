package com.wift.objects;

import com.wift.utils.Enity;

import java.util.Objects;

public class Hat extends Enity {
    public Hat() {
        super("шляпа", "шляпы");
    }

    public Hat(String name, String padejName) {
        super(name, padejName);
    }

    public void drip(HatParts part) {
        System.out.format("С %s %s закапало\n", part, padejName);
    }

    public void pour(HatParts part) {
        System.out.format("С %s %s полилось\n", part, padejName);
    }

    public void showSmth(HatParts part, Enity object) {
        System.out.format("На %s %s показался %s\n", part, padejName, object);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Hat hat = (Hat) o;
        return padejName.equals(hat.padejName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), padejName);
    }
}
