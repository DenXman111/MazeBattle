package com.game.mazebattle;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    static public void main(String... args){
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "MazeBattle";
        cfg.width = 1640;
        cfg.height = 960;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(new Game(), cfg);
    }
}
