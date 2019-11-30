package com.game.objects;


import com.game.exceptions.NextMapException;
import com.game.gamemap.GameMap;
import com.game.gamestates.PlayState;

public class Portal extends GameObject {
    public Portal(int x, int y) {
        super(x, y);
    }

    public void changeMap(GameMap map, PlayState playState, int playerX, int playerY){
//        playState.setGameMap(new GameMap(map.getlvl() + 1, 32, playState, playerX, playerY));
        try {
            playState.setGameMap(map.gameMapChanger(map.nextGameMapAtPortal(this)));
            //playState.setGameMap(map.nextGameMapAtPortal(this));
        } catch (NextMapException e) {
            e.printStackTrace();
        }
    }
}
