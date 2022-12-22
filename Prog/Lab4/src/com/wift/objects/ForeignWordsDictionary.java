package com.wift.objects;

import com.wift.utils.Entity;

public class ForeignWordsDictionary extends Entity {
    protected ForeignWords foreignWords;

    public ForeignWordsDictionary(String name, ForeignWords foreignWords) {
        super(name);
        this.foreignWords = foreignWords;
    }

    public ForeignWordsDictionary(String name) {
        super(name);
    }

    public void cringe() {
        System.out.println(name + " съеживается");
    }

    public void whatPageLookLike(Entity obj) {
        System.out.println("страницы " + name + " были похожи на " + obj);
    }
}
