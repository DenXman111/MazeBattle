package com.game.managers;

import com.game.gamestates.FinishedState;
import com.game.gamestates.GameState;
import com.game.gamestates.MenuState;
import com.game.gamestates.PlayState;

public class GameStateManager {
    private GameState gameState;
    public static final int MENU = 0;
    public static final int PLAY = 1;
    public static final int FINISHED = 2;

    public GameStateManager(){
        setState(MENU);
    }

    public void setState(int state){
        if (gameState != null) gameState.dispose();
        if (state == MENU){
            gameState = new MenuState(this);
        }
        if (state == PLAY){
            gameState = new PlayState(this);
        }
        if (state == FINISHED){
            gameState = new FinishedState(this);
        }
    }

    public void startGame(){
        setState(PLAY);
        //gameState = new PlayState(this);
    }

    public void update(float dt){
        gameState.update(dt);
    }

    public void draw() {
        gameState.draw();
    }


}
