package com.wift.utils;

import java.util.Objects;


/**
 * Базовый абстрактный класс для сущностей
 * */
public abstract class Enity {
    protected String name, padejName;

    public Enity(String name, String padejName) {
        this.name = name;
        this.padejName = padejName;
    }

    public String getPadejName() {
        return padejName;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enity enity = (Enity) o;
        return name.equals(enity.name) && padejName.equals(enity.padejName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, padejName);
    }
}
