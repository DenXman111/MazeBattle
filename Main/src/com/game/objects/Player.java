package com.game.objects;

import com.game.exceptions.MapRangeOutException;
import com.game.gamestates.PlayState;

public class Player extends GameObject {
    public Player(int x, int y) {
        super(x, y);
    }

    private boolean canGo(int x, int y, PlayState playState){
        try {
            return playState.gameMap.getCage(x, y) != 1;
        } catch (MapRangeOutException e) {
            return false;
        }
    }

    public void tryUP(PlayState playState){
        if (canGo(x, y + 1, playState)) ++y;
    }

    public void tryDOWN(PlayState playState){
        if (canGo(x , y - 1, playState)) --y;
    }

    public void tryLEFT(PlayState playState){
        if (canGo(x - 1, y, playState)) --x;
    }

    public void tryRIGHT(PlayState playState){
        if (canGo(x + 1, y, playState)) ++x;
    }
}
