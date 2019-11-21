package com.game.gamestates;

import com.game.managers.GameStateManager;

public class PlayState extends GameState {

    public PlayState(GameStateManager gameStateManager){
        super(gameStateManager);
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float dt) {
        //System.out.println("PLAY UPD");
    }

    @Override
    public void draw() {
        //System.out.println("PLAY DRAW");
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
