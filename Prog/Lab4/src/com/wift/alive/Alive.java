package com.wift.alive;

import com.wift.events.Event;
import com.wift.events.EventType;
import com.wift.places.Place;
import com.wift.utils.Entity;

import java.util.Arrays;
import java.util.Objects;

public abstract class Alive extends Entity implements AliveInterface{
    protected States[] states;
    protected Emotions emotion;
    protected Place place;
    public Alive(String name, Emotions emotion, States... states) {
        super(name);
        this.emotion = emotion;
        this.states = states;
    }

    public Alive(String name) {
        super(name);
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getFullName() {
        StringBuilder out = new StringBuilder();
        if(emotion != null)
            out.append(emotion).append(" ");
        if(states != null) {
            for (States state : states) {
                out.append(state).append(" ");
            }
        }
        out.append(name);
        return out.toString();
    }

    public void comeUp(Entity place) {
        System.out.println(name + " подошел к " + place);
    }
    public void stand(EventType type, Place place) {
        System.out.println(name +" " + type + " стоял(-и) " + place);
    }

    public void examine(Entity obj) {
        System.out.println(name + " разглядывал(-и) " + obj);
    }

    public void listen(Sounds obj) {
        System.out.println(name + " слушал(-и) " + obj);
    }
    public void doo(Event event) {
        System.out.println(name + " " + event);
    }
    public void hide(Entity obj) {
        System.out.println(name + " спрятался(-ись) с помощью " + obj);
    }

    public void scattered(Entity obj, Entity dir) {
        System.out.println(name + " расшвыривали " + obj + " над/в " + dir);
    }

    public void throwz(Entity obj, Entity dir) {
        System.out.println(name + " бросили " + obj + " над/в " + dir);
    }

    public void look(Entity dir) {
        System.out.println(name + " смотрел на/в " + dir);
    }

    public void takeOut(Entity obj) {
        System.out.println(name + " вынули " + obj);
    }

    public void roll(Entity obj, Place dir) {
        System.out.println(name + " покатили " + obj + " в " + dir);
    }
    public void stickOver(Entity obj) {
        System.out.println(name + " сгрудились вокруг " + obj);
    }

    public void hold(Entity obj) {
        System.out.println(name + " держали " + obj);
    }

    public void peepedOut(Entity obj) {
        System.out.println(name + " выглядывали из под " + obj);
    }

    public void waitt() {
        System.out.println(name + " ждали");
    }

    @Override
    public States[] getStates() {
        return states;
    }

    @Override
    public void setStates(States[] states) {
        this.states = states;
    }

    @Override
    public Emotions getEmotion() {
        return emotion;
    }

    @Override
    public void setEmotion(Emotions emotion) {
        this.emotion = emotion;
        System.out.println(name + " тперь " + emotion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Alive alive = (Alive) o;
        return Arrays.equals(states, alive.states) && emotion == alive.emotion;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), emotion);
        result = 31 * result + Arrays.hashCode(states);
        return result;
    }

    @Override
    public String toString() {
        return getName();
    }

}
