package com.game.mazebattle;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.nativeclass.Native;
import org.lwjgl.Sys;

public class Main {
    static public void main(String... args){
        int[] table = new int[2];
        table[0] = 69;
        new Native().getTable(table, 2);
        System.out.println(table[1]);
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "MazeBattle";
        cfg.width = 1640;
        cfg.height = 960;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(new Game(), cfg);
    }
}
