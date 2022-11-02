package com.wift.alive;

import com.wift.events.EventType;
import com.wift.utils.Enity;

import java.util.Arrays;
import java.util.Objects;

public abstract class Alive extends Enity implements AliveInterface{
    protected States[] states;
    protected Emotions emotion;
    public Alive(String name, String padejName, Emotions emotion, States... states) {
        super(name, padejName);
        this.emotion = emotion;
        this.states = states;
    }

    public Alive(String name, Emotions emotion, States... states) {
        this(name, name, emotion,states);
    }

    public Alive(String name) {
        this(name, name, null, null);
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
        StringBuilder out = new StringBuilder();
        if(emotion != null)
            out.append(emotion).append(" ");
        for(States state : states) {
            out.append(state).append(" ");
        }
        out.append(name);
        return out.toString();
    }
}
