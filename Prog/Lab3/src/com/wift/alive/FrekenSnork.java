package com.wift.alive;

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
}
