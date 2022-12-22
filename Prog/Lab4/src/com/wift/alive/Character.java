package com.wift.alive;

import com.wift.events.Event;
import com.wift.utils.Entity;

public abstract class Character extends Alive implements CharacterInterface {
    public Character(String name, Emotions emotion, States... states) {
        super(name, emotion, states);
    }

    public Character(String name) {
        super(name);
    }

    @Override
    public void setEmotion(Emotions emotion) {
        super.setEmotion(emotion);
        System.out.printf("%s становиться %s\n", name, emotion);
    }

    public void poke(BodeParts what, BodeParts where) {
        System.out.println(name + " сунул " + what + " в " + where);
    }

    @Override
    public void hangDown(int n) {
        System.out.println(name + " свистнул " + (n > 0 ? n + " раз" : ""));
    }

    public void laugh() {
        System.out.println(name + " смеялся");
    }

    public void stop() {
        System.out.println(name + " остановился");
    }

    public void say(String message) {
        System.out.println(name + " сказал " + message);
    }

    public void decide(Event event) {
        System.out.println(name + " решил " + event);
    }
}
