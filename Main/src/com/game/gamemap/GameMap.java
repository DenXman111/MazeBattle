package com.game.gamemap;

import com.game.exceptions.MapRangeOutException;
import com.game.exceptions.NoPortalHere;
import com.game.gamestates.PlayState;
import com.game.nativeclass.Native;
import com.game.objects.Finish;
import com.game.objects.Player;
import com.game.objects.Portal;

import java.util.Random;

public class GameMap {
    private int [][] map;
    private int lvl;
    private int globalSize = 512;
    private Finish finish;
    private Portal[] portals;
    private int portalN = 5;
    private int N;
    private PlayState playState;

    Random random =  new Random();

    public GameMap(int lvl, int N, PlayState playState, int playerX, int playerY){
        this.N = N;
        this.lvl = lvl;
        this.playState = playState;
        portals = new Portal[5];
        nativeMapGeneration(N, playerX, playerY);
    }

    public int getGlobalN(){
        return globalSize;
    }

    public int getlvl(){
        return lvl;
    }

    public int getN(){
        return N;
    }

    public Finish getFinish(){
        return finish;
    }

    public void playerGot(int x, int y){
        map[x][y] = 0;
    }

    public int getCage(int i, int j) throws MapRangeOutException {
        if (i >= 0 && j >= 0 && i < N && j < N) return map[i][j];
        throw new MapRangeOutException();
    }

    public boolean isPortalHere(int x, int y){
        for (int i = 0; i < portalN; i++) if (portals[i].getX() == x && portals[i].getY() == y) return true;
        return false;
    }

    public Portal getPortalHere(int x, int y) throws NoPortalHere{
        if (!isPortalHere(x, y)) throw new NoPortalHere();
        for (int i = 0; i < portalN; i++) if (portals[i].getX() == x && portals[i].getY() == y) return portals[i];
        return null;
    }

    private void nativeMapGeneration(int N, int playerX, int playerY) {
        map = new int[N][N];
        int[] tab = new int[N * N + 3];
        new Native().getTable(tab, N, portalN, playState.getComponentNum(), playState.getDistToFinish(), playState.getPortalNumHere(), playerX, playerY);
        playState.setComponentNum(tab[N * N]);
        playState.setDistToFinish(tab[N * N + 1]);
        playState.setPortalNumHere(tab[N * N + 2]);

        int z = 0;
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++) {
                map[i][j] = tab[i * N + j];
                if (map[i][j] == 2) portals[z++] = new Portal(i, j);
                if (map[i][j] == 3) finish = new Finish(i, j);
            }
        }
    }
    private void generateMap(int N){
        map = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) if (random.nextInt(7) < 2) map[i][j] = 1; else map[i][j] = 0;
        for (int i = 0; i < portalN; i++) {
            portals[i] = new Portal(random.nextInt(N), random.nextInt(N));
            map[portals[i].getX()][portals[i].getY()] = 2;
        }
        finish = new Finish(random.nextInt(N), random.nextInt(N));
        map[finish.getX()][finish.getY()] = 3;

        int x = random.nextInt(N), y = random.nextInt(N);
        while (map[x][y] != 0) {
            x = random.nextInt(N);
            y = random.nextInt(N);
        }
        map[x][y] = 4;
    }
}
