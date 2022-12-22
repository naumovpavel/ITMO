package com.wift.alive;

import com.wift.events.Event;
import com.wift.places.Place;

public class Snusmumrik extends Character{
    protected Event habits;
    public Snusmumrik(String name, Emotions emotion, States... states) {
        super(name, emotion, states);
    }

    public void setHabits(Event habits) {
        this.habits = habits;
        System.out.println(name + " " + habits);
    }

    public Snusmumrik(String name) {
        super(name);
    }

    public void happy() {
        this.emotion = Emotions.HAPPY;
        System.out.println("сердце " + name + " так и подпрыгнуло от радости");
    }

    public void dontBack() {
        System.out.println(name + " не возвращался");
    }

    public void wentOnTrip() {
        this.place = new Place("где то в странствиях");
        System.out.println(name + " отправился в путешествие, делать какие-нибудь открытия");
        System.out.println("Скоро он разобьет на речном берегу палатку и совсем перестанет ночевать дома...");
        System.out.println(name + " вздохнул");
        this.emotion = Emotions.SAD;
        System.out.println("Ему было грустно, хотя тужить было не о чем");
    }
}
