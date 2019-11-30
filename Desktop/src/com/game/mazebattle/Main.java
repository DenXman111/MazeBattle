package com.game.mazebattle;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.nativeclass.Native;
import org.lwjgl.Sys;

public class Main {
    static public void main(String... args){
        //int[] table = new int[2];
        //table[0] = 69;
        //new Native().getTable(table, 2);
        //System.out.println(table[0]);
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "PortalMaze";
        cfg.width = 980;
        cfg.height = 640;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(new Game(), cfg);
    }
}
