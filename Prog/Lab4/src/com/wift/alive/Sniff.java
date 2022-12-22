package com.wift.alive;

import com.wift.utils.Entity;

public class Sniff extends Character{

    public Sniff(String name) {
        super(name);
    }

    public void rush(Entity obj) {
        System.out.println(name + " метнулся под/к " + obj);
    }

    public void bite(Alive who, BodeParts where) {
        System.out.println(name + " укусил " + who + " за " + where);
    }

    /**
     * Задает эмоцию и попискивает если это страх
     * */
    @Override
    public void setEmotion(Emotions emotion) {
        if(emotion == Emotions.SCARED) {
            System.out.format("%s попискивает от страха\n", getName());
            this.emotion = emotion;
        } else {
            super.setEmotion(emotion);
        }
    }
}
