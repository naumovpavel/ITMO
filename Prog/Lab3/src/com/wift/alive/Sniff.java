package com.wift.alive;

public class Sniff extends Character{

    public Sniff(String name) {
        super(name);
    }

    /**
     * Задает эмоцию и попискивает если это страх
     * */
    @Override
    public void setEmotion(Emotions emotion) {
        if(emotion == Emotions.SCARED) {
            System.out.format("%s попискивает от страха\n", getPadejName());
            this.emotion = emotion;
        } else {
            super.setEmotion(emotion);
        }
    }
}
