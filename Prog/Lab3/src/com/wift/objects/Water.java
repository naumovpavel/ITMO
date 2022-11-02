package com.wift.objects;

import com.wift.utils.Enity;

public class Water extends Enity{

    public Water() {
        super("вода", "воды");
    }

    public void speskOut(WaterType type, Enity object) {
        System.out.format("%s %s выплескиваются на %s\n", type, padejName, object);
    }

    public enum WaterType {
        FLOWS("потоки"),
        JETS("струи"),
        DEFAULT("");
        private final String type;

        WaterType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return getType();
        }
    }
}
