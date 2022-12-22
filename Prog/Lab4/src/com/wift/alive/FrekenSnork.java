package com.wift.alive;

import com.wift.utils.Entity;

public class FrekenSnork extends Character{
    public FrekenSnork(String name) {
        super(name);
    }

    /**
     * Задает эмоцию страха и прячет голову на какой-то части тела другого персонажа
     * */
    public void scare(Character character, BodeParts bodyPart) {
        System.out.format("%s прячет голову на %s %s\n", name, bodyPart, character.getName());
        this.emotion = Emotions.SCARED;
    }

    public void coverUp(Entity obj, Entity how) {
        System.out.println(name + " прикрыл " + obj + " с помощью " + how);
    }
}
