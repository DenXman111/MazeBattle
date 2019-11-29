package com.game.objects;

import com.game.gamestates.GameState;
import com.game.managers.GameStateManager;

public class Finish extends GameObject{
    public Finish(int x, int y) {
        super(x, y);
    }

    public <T extends GameState> void stepOnFinish(T state){
        state.changeStateTo(GameStateManager.FINISHED);
    }
}
