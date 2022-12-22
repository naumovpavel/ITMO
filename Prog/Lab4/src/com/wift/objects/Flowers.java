package com.wift.objects;

import com.wift.utils.Entity;

public class Flowers extends Entity {
    protected FlowerCondition condition;
    public Flowers(String name) {
        super(name);
    }

    public Flowers(String name, FlowerCondition condition) {
        super(name);
        setCondition(condition);
    }

    public FlowerCondition getCondition() {
        return condition;
    }

    public void setCondition(FlowerCondition condition) {
        this.condition = condition;
        System.out.println(name + " " + condition);
    }
}
