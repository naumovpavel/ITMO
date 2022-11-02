package com.wift.alive;

interface AliveInterface {
    void setStates(States... states);
    void setEmotion(Emotions emotion);
    Emotions getEmotion();
    States[] getStates();
}
