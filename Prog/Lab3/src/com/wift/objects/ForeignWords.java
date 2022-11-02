package com.wift.objects;

import com.wift.utils.Enity;

public class ForeignWords extends Enity {

    public ForeignWords(String name, String padejName) {
        super(name, padejName);
    }

    public void haveto(String what) {
        System.out.printf("%s пришлось %s\n", padejName, what);
    }

    public String find(String what, String where) {
        return String.format("искать %s на %s", what, where);
    }
}
