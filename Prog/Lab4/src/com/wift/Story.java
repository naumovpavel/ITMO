package com.wift;

import com.wift.alive.*;
import com.wift.events.Event;
import com.wift.events.EventType;
import com.wift.objects.*;
import com.wift.places.Building;
import com.wift.places.Place;
import com.wift.utils.CantOpenCloseException;
import com.wift.utils.Direction;
import com.wift.utils.Entity;

public class Story {
    Event smth = new Event("что - то", EventType.SUDDENLY);
    Event waiting = new Event("ожидание", EventType.AGAIN, EventType.UNBEARABLE);
    Hat hat = new Hat();
    Water water = new Water();
    FrekenSnork frekenSnork = new FrekenSnork("Фрекен Снорк");
    MumiTroll mumiTroll = new MumiTroll("Муми-тролль");
    MumiTroll mumiDad = new MumiTroll("Муми-папа");
    MumiTroll mumiMam = new MumiTroll("Муми-мама");
    They mumiParents = new They("они");
    Sniff sniff = new Sniff("Снифф");
    Snusmumrik snusmumrik = new Snusmumrik("Снусмумрик");
    Hemul hemul = new Hemul("Хемуль");
    Hedgehog hedgehog = new Hedgehog("ежик", null,States.MAYBESMALLEST, States.DISHEVELED, States.WET);
    ForeignWords foreignWords = new ForeignWords("иностранные слова");
    ForeignWordsDictionary foreignWordsDictionary = new ForeignWordsDictionary("словарь иностранных слов", foreignWords);
    Furniture walls = new Furniture("стена");
    Event rescue = new Event("спасение");
    They they = new They("Они");
    Jar jar = new Jar("банка", true);
    Antlion antlion = new Antlion("Муравьиный лев");
    Leaves wiltedLeaves = new Leaves("увядшие листья");
    Sand sand = new Sand("песок");
    Flowers flowers = new Flowers("цветы");
    Whistling whistling = new Whistling("свист");
    Sand fatLayerOfSand = new Sand("толстый слой песка");
    Building home = new Building("дом");
    They friends = new They("друзья") {
        @Override
        public void comeUp(Entity place) {
            System.out.println(name + " сбежались к " + place);
        }
    };
    Furniture table = new Furniture("стол");
    Ceiling ceiling = new Ceiling("потолок");
    They SMSH = new They("");

    {
        SMSH.setMembers(sniff, mumiTroll, snusmumrik, hemul);
        mumiParents.setMembers(mumiMam, mumiDad);
    }
    public void run() {
        they.scattered(sand, jar);
        try {
            jar.close(they);
        } catch (CantOpenCloseException e) {
            System.out.println(e.getMessage());
        }
        they.takeOut(jar);
        they.roll(jar, home);
        antlion.scream();
        fatLayerOfSand.drownOut(antlion);
        mumiTroll.comeUp(home);
        mumiTroll.poke(BodeParts.PAWS, BodeParts.MOUTH);
        mumiTroll.hangDown(3);
        new Event("событие", EventType.UNHEARD).happend();
        friends.comeUp(mumiTroll);
        friends.stickOver(jar);
        friends.stand(EventType.INTO_SILENCE, new Place(""));
        friends.examine(jar);
        friends.listen(Sounds.SCREAMS);
        sniff.rush(table);
        SMSH.hold(jar);
        try {
            jar.open(frekenSnork);
        } catch (CantOpenCloseException e) {
            System.out.println(e.getStackTrace());
        } finally {
            System.out.println("finally");
        }
        sand.whirlwinds.fails(antlion, hat);
        frekenSnork.coverUp(hat, foreignWordsDictionary);
        SMSH.comeUp(table);
        SMSH.hide(table);
        new Event("", EventType.NOTHING_SPECIAL).dontHappend();
        they.peepedOut(new Furniture("скатерть"));
        they.waitt();
        new Event("беспокойство").increase();
        hat.isSilence();
        foreignWordsDictionary.cringe();
        sniff.bite(hemul, BodeParts.FINGER);
        foreignWordsDictionary.cringe();
        foreignWordsDictionary.cringe();
        foreignWordsDictionary.whatPageLookLike(wiltedLeaves);
        foreignWords.crawlOut(foreignWordsDictionary);
        foreignWords.sprawl(new Furniture("пол"));

        smth.happend();
        hat.drip(HatParts.BRIMS);
        hat.pour(HatParts.BRIMS);
        water.speskOut(Water.WaterType.FLOWS, new Furniture("ковер"));
        foreignWords.haveto(foreignWords.find(rescue.toString(), walls.getName()));
        waiting.goingOn();
        frekenSnork.scare(mumiTroll, BodeParts.CHEST);
        sniff.setEmotion(Emotions.SCARED);
        hat.showSmth(HatParts.BRIMS, hedgehog);
        hedgehog.wriggle(Direction.IN_ALL_DIRECTION);
        hedgehog.squint(States.HALF_SIGHTED);


        new Event("тишина", EventType.DEAD, EventType.FEW_SECONDS).happend();
        snusmumrik.laugh();
        snusmumrik.stop();
        they.continuee();
        they.muchLaugh();
        hemul.notLaugh();
        hemul.examine(they);
        hemul.say("А ежик тем временем с торжественным и чуточку печальным видом прошествовал к выходу и спустился с крыльца.");
        water.stopPour(Water.WaterType.DEFAULT, hat);
        water.spreadOut(Water.WaterType.DEFAULT, new Furniture("пол Веранды"));
        ceiling.swarm(foreignWords);
        Event.tellAboutEvent(mumiDad, mumiMam);
        Event.reacted(Emotions.SERIOUSLY, mumiMam, mumiDad);
        mumiParents.decide(new Event("надо уничтожить шляпу Волшебника"));
        mumiParents.roll(hat, new River("река"));
        mumiParents.throwz(hat, water);
        snusmumrik.setEmotion(Emotions.NOTSLEEPY);
        snusmumrik.doo(new Event("лежал"));
        snusmumrik.look(new Place("июньская ночь"));
        flowers.setCondition(FlowerCondition.SMELL_SWEET);
        snusmumrik.dontBack();
        snusmumrik.setHabits(new Event("бродил со своей гормошкой", EventType.ALONE, EventType.OFTEN));
        new Event("Наверно").goingOn();
        snusmumrik.wentOnTrip();
        new Event("свист", new Place("под окошком"), EventType.NOTLOUD).happend();
        snusmumrik.happy();
        snusmumrik.comeUp(new Place("окно"));
        snusmumrik.look(new Place("наружу"));
        whistling.setState(States.SECRET);
        whistling.means();
        snusmumrik.stand(EventType.DEFAULT, new Place("Внизу у веревочной лестницы"));
        mumiTroll.shake(BodeParts.HEAD);
    }
}
