package com.game.nativeclass;

public class Native {
    static {
        System.loadLibrary("native");
    }

    public native int getTable(int[] table, int size);
}
