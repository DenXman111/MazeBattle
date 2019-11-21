package com.game.managers;

import java.util.ArrayList;

public class GameKeys {
    private static ArrayList<Integer> keys = new ArrayList<>();
    private static ArrayList<Integer> pkeys = new ArrayList<>();

    public static void update() {
        pkeys = new ArrayList<>(keys);
    }

    public static void setKey(Integer key) {
        keys.add(key);
    }

    public static void removeKey(Integer key) {
        keys.remove(key);
    }

    public static boolean isDown(Integer key) {
        return keys.contains(key);
    }

    public static boolean isPressed(Integer key) {
        return keys.contains(key) && !pkeys.contains(key);
    }
}
