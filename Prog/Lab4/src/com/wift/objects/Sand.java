package com.wift.objects;

import com.wift.utils.Entity;

public class Sand extends Entity {
    public Whirlwinds whirlwinds;
    public Sand(String name) {
        super(name);
        whirlwinds = new Whirlwinds("вихри песка");
    }

    public void drownOut(Entity obj) {
        System.out.println(name + " заглушил " + obj);
    }

    public class Whirlwinds {
        private Sand sand;
        protected String name;
        public Whirlwinds(String name) {
            sand = Sand.this;
            this.name = name;
        }

        public void fails(Entity obj, Entity where) {
            System.out.println(obj + " проваливается в " + where + " в " + name);
        }
    }
}
