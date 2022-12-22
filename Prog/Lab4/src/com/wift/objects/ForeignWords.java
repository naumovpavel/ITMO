package com.wift.objects;

import com.wift.utils.Entity;

public class ForeignWords extends Entity {

    public ForeignWords(String name) {
        super(name);
    }

    public void haveto(String what) {
        System.out.printf("%s пришлось %s\n", name, what);
    }

    public String find(String what, String where) {
        return String.format("искать %s на %s", what, where);
    }

    public void crawlOut(Entity obj) {
        System.out.println(name + " вылазили из " + obj);
    }

    public void sprawl(Entity where) {
        System.out.println(name + " расползлись на " + where);
    }
}
