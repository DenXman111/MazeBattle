package com.game.gamemap;

import com.game.exceptions.MapRangeOutException;

import java.util.Random;

public class GameMap {
    private int [][] map;
    private int lvl;
    private int globalN = 512;
    private int cageN;
    Random random =  new Random();

    public int getGlobalN(){
        return globalN;
    }

    public int getcageN(){
        return cageN;
    }
    public int getCage(int i, int j) throws MapRangeOutException {
        if (i >= 0 && j >= 0 && i <= cageN && j <= cageN) return map[i][j];
        throw new MapRangeOutException();
    }

    public GameMap(int lvl, int N){
        this.cageN = N;
        this.lvl = lvl;
        map = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) map[i][j] = random.nextInt(4);
    }
}
