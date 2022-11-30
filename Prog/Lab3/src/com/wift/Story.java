package com.wift;

import com.wift.alive.*;
import com.wift.events.Event;
import com.wift.events.EventType;
import com.wift.objects.*;
import com.wift.utils.Direction;

public class Story {
    Event smth = new Event("что - то", EventType.SUDDENLY);
    Event waiting = new Event("ожидание", EventType.AGAIN, EventType.UNBEARABLE);
    Hat hat = new Hat();
    Water water = new Water();
    FrekenSnork frekenSnork = new FrekenSnork("Фрекен Снорк");
    MumiTroll mumiTroll = new MumiTroll("Муми-тролль");
    Sniff sniff = new Sniff("Снифф");
    Hedgehog hedgehog = new Hedgehog("ежик", null,States.MAYBESMALLEST, States.DISHEVELED, States.WET);
    ForeignWords foreignWords = new ForeignWords("иностранные слова", "иностранным словам");
    Furniture walls = new Furniture("стена", "стенах");
    Event rescue = new Event("спасение");

    public void run() {
        smth.happend();
        hat.drip(HatParts.BRIMS);
        hat.pour(HatParts.BRIMS);
        water.speskOut(Water.WaterType.FLOWS, new Furniture("ковер", "ковер"));
        foreignWords.haveto(foreignWords.find(rescue.toString(), walls.getPadejName()));
        waiting.goingOn();
        frekenSnork.scare(mumiTroll, BodeParts.CHEST);
        sniff.setEmotion(Emotions.SCARED);
        hat.showSmth(HatParts.BRIMS, hedgehog);
        hedgehog.wriggle(Direction.IN_ALL_DIRECTION);
        hedgehog.squint(States.HALF_SIGHTED);
        hedgehog.feed(1000);
        System.out.println(hedgehog.getAnimalState());
    }
}
