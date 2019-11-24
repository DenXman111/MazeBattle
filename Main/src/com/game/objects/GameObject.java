package com.game.objects;

public class GameObject {
    int x;
    int y;
    public GameObject(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
