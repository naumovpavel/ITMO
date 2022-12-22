package com.wift.objects;

import com.wift.utils.Entity;

public class Ceiling extends Furniture{
    public Ceiling(String name) {
        super(name);
    }

    public void swarm(Entity obj) {
        System.out.println(name + " кишел " + obj);
    }
}
