package com.game.managers;

import com.badlogic.gdx.InputAdapter;

public class GameInputProcessor extends InputAdapter {

    @Override
    public boolean keyDown(int key) {
        GameKeys.setKey(key);
        return true;
    }

    @Override
    public boolean keyUp(int key){
        GameKeys.removeKey(key);
        return true;
    }
}
