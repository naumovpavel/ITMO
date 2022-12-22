package com.wift.places;

import com.wift.alive.Alive;
import com.wift.utils.Entity;
import com.wift.utils.PlaceException;

import java.util.ArrayList;
import java.util.List;

public class Place extends Entity {
    ArrayList<Alive> inPlace = new ArrayList<>();
    public Place(String name) {
        super(name);
    }

    public Place(String name, Alive... alives) {
        super(name);
        for(Alive alive : alives) {
            inPlace.add(alive);
            alive.setPlace(this);
        }
    }

    void add(Alive alive) {
        inPlace.add(alive);
        alive.setPlace(this);
    }

    void remove(Alive alive) {
        if(alive.getPlace() != this) {
            throw new PlaceException("Данное существо находится не в этом месте, нельзя удалить", alive);
        } else {
            inPlace.remove(alive);
        }
    }
}
