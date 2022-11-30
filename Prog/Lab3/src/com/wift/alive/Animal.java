package com.wift.alive;

import static java.lang.Math.max;

public abstract class Animal extends Alive{
    protected int feed = 0;
    protected long lastFeeded = 0;
    protected int health = 100;
    protected AnimalState animalState = AnimalState.WELL_FED;

    public Animal(String name, String padejName, Emotions emotion, States... states) {
        super(name, padejName, emotion, states);
        this.lastFeeded = System.currentTimeMillis();
    }

    public Animal(String name, Emotions emotion, States... states) {
        super(name, emotion, states);
    }

    public Animal(String name) {
        super(name);
    }

    public void feed(int grams) {
        long duration = System.currentTimeMillis() - this.lastFeeded;
        this.lastFeeded = System.currentTimeMillis();
        int deltaFeed = this.feed;
        this.feed -= duration / -10000;
        this.feed += grams;
        deltaFeed = this.feed - deltaFeed;
        this.health = max(0, this.health + deltaFeed / 10);
        if(this.health < 100) {
            this.animalState = AnimalState.HUNGRY;
        } else if(this.health < 150) {
            this.animalState = AnimalState.WELL_FED;
        } else if(this.health < 200) {
            this.animalState = AnimalState.OVERATE;
        } else {
            this.animalState = AnimalState.WELL_OVERATE;
        }
    }

    public AnimalState getAnimalState() {
        return animalState;
    }
}
