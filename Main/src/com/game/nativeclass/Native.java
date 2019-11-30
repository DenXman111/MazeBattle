package com.game.nativeclass;

public class Native{
    static {
        System.loadLibrary("native");
    }

    public native int getTable(int[] table, int N, int portalN, int componentNumber, int distToFinish, int portalNumberHere, int playerX, int playerY);

}
