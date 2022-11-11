package com.wift.alive;

interface AliveInterface {

    String getName();

    String getPadejName();
    void setStates(States... states);
    void setEmotion(Emotions emotion);
    Emotions getEmotion();
    States[] getStates();
}
