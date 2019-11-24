package com.game.objects;

import com.game.exceptions.MapRangeOutException;

import static com.game.mazebattle.Game.gameMap;

public class Player extends GameObject {
    public Player(int x, int y) {
        super(x, y);
    }

    private boolean canGo(int x, int y){
        try {
            return gameMap.getCage(x, y) != 1;
        } catch (MapRangeOutException e) {
            return false;
        }
    }

    public void tryUP(){
        if (canGo(x, y + 1)) ++y;
    }

    public void tryDOWN(){
        if (canGo(x , y - 1)) --y;
    }

    public void tryLEFT(){
        if (canGo(x - 1, y)) --x;
    }

    public void tryRIGHT(){
        if (canGo(x + 1, y)) ++x;
    }
}
