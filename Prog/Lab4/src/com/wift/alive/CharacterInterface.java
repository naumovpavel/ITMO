package com.wift.alive;

public interface CharacterInterface extends AliveInterface{
    void poke(BodeParts what, BodeParts where);
    void hangDown(int n);
}
