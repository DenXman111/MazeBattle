package com.game.gamestates;

import com.game.managers.GameStateManager;

public abstract class GameState {
    protected GameStateManager state;

    protected GameState(GameStateManager state){
        this.state = state;
        init();
    }

    public void changeStateTo(int state){
        this.state.setState(state);
    }
    public abstract void init();
    public abstract void update(float dt);
    public abstract void draw();
    public abstract void handleInput();
    public abstract void dispose();

}
