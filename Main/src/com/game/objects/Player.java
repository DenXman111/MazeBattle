package com.game.objects;

import com.game.exceptions.MapRangeOutException;
import com.game.gamestates.PlayState;

public class Player extends GameObject {
    public Player(int x, int y) {
        super(x, y);
    }

    private boolean canGo(int x, int y, PlayState playState){
        try {
            return playState.getGameMap().getCage(x, y) != 1;
        } catch (MapRangeOutException e) {
            return false;
        }
    }

    public void tryUP(PlayState playState){
        if (canGo(x, (y + 1) % playState.getGameMap().getN(), playState)) y = (y + 1) % playState.getGameMap().getN();
    }

    public void tryDOWN(PlayState playState){
        if (canGo(x , (y - 1 + playState.getGameMap().getN()) % playState.getGameMap().getN(), playState)) y = (y - 1 + playState.getGameMap().getN()) % playState.getGameMap().getN();
    }

    public void tryLEFT(PlayState playState){
        if (canGo((x - 1 + playState.getGameMap().getN()) % playState.getGameMap().getN(), y, playState)) x = (x - 1 + playState.getGameMap().getN()) % playState.getGameMap().getN();
    }

    public void tryRIGHT(PlayState playState){
        if (canGo((x + 1) % playState.getGameMap().getN(), y, playState)) x = (x + 1) % playState.getGameMap().getN();
    }
}
