package com.wift.objects;

import com.wift.utils.Entity;

public class Water extends Entity {

    public Water() {
        super("вода");
    }

    public Water(String name) {
        super(name);
    }

    public void speskOut(WaterType type, Entity object) {
        System.out.format("%s %s выплескиваются на %s\n", type, name, object);
    }

    public void stopPour(WaterType type, Entity object) {
        System.out.format("%s %s перестала лить %s\n", type, name, object);
    }

    public void spreadOut(WaterType type, Entity object) {
        System.out.format("%s %s растеклась на  %s\n", type, name, object);
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
