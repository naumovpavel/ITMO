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
    FrekenSnork FS = new FrekenSnork("Фрекен Снорк");
    MumiTroll MT = new MumiTroll("Муми-тролль");
    Sniff SN = new Sniff("Снифф");
    Hedgehog hedgehog = new Hedgehog("ежик", null,States.MAYBESMALLEST, States.DISHEVELED, States.WET);
    ForeignWords FW = new ForeignWords("иностранные слова", "иностранным словам");

    public void run() {
        smth.happend();
        hat.drip(HatParts.BRIMS);
        hat.pour(HatParts.BRIMS);
        water.speskOut(Water.WaterType.FLOWS, new Furniture("ковер", "ковер"));
        FW.haveto(FW.find(new Event("спасение").toString(), new Furniture("стена", "стенах").toString()));
        waiting.goingOn();
        FS.scare(MT, BodeParts.CHEST);
        SN.setEmotion(Emotions.SCARED);
        hat.showSmth(HatParts.BRIMS, hedgehog);
        hedgehog.wriggle(Direction.IN_ALL_DIRECTION);
        hedgehog.squint(States.HALF_SIGHTED);
    }
}
