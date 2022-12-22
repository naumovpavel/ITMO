package com.wift.places;

import com.wift.alive.Alive;
import com.wift.utils.Entity;

public class Building extends Place {
    protected Room[] rooms;

    public Building(String name, Room... rooms) {
        super(name);
        this.rooms = rooms;
    }

    public Building(String name) {
        super(name);
    }

    static class Room extends Place {
        public Room(String name) {
            super(name);
        }

        @Override
        void add(Alive alive) {
            super.add(alive);
            inPlace.add(alive);
        }

        @Override
        void remove(Alive alive) {
            super.remove(alive);
            inPlace.remove(alive);
        }
    }
}
