package com.wift.alive;

import com.wift.events.EventType;
import com.wift.places.Place;
import com.wift.utils.Entity;

interface AliveInterface {

    String getName();
    void setStates(States... states);
    void setEmotion(Emotions emotion);
    void setPlace(Place place);
    Place getPlace();
    Emotions getEmotion();
    String getFullName();
    void comeUp(Entity place);
    public void examine(Entity obj);
    public void stand(EventType type, Place place);
    public void listen(Sounds obj);
    States[] getStates();
}
