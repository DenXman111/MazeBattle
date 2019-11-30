package com.game.gamemap;

import com.game.exceptions.MapRangeOutException;
import com.game.exceptions.NextMapException;
import com.game.exceptions.NoPortalHere;
import com.game.exceptions.NotGeneratedException;
import com.game.gamestates.PlayState;
import com.game.mazebattle.Game;
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
    private volatile Portal[] portals;
    private int portalN = 4;
    private int N;
    private PlayState playState;
    private volatile boolean generated;
    private Thread generatorThread;

    private int componentNum;
    private int distToFinish;
    private int portalNumHere;

    private volatile GameMap[] nextGameMaps;
    private boolean isCurrent = false;

    Random random =  new Random();

    //takes one of the next game maps and sets it as current map
    /*public GameMap(GameMap gameMap) throws NotGeneratedException {
        try {
            gameMap.getGeneratorThread().join();
            gameMap.generated = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Map constructor");
        if (!gameMap.generated) throw new NotGeneratedException();
        this.generated = true;
        this.N = gameMap.N;
        this.lvl = gameMap.lvl;
        this.playState = gameMap.playState;
        this.isCurrent = true;
        this.map = gameMap.map;
        this.finish = gameMap.finish;
        this.portals = gameMap.portals;
        this.nextGameMaps = new GameMap[portalN];
        generateNextMaps();
    }*/

    public GameMap(int lvl, int N, PlayState playState, int playerX, int playerY, boolean isCurrent){
        this.generated = false;
        this.N = N;
        this.lvl = lvl;
        this.playState = playState;
        this.isCurrent = isCurrent;
        portals = new Portal[portalN];
        nextGameMaps = new GameMap[portalN];
        componentNum = -1;
        portalNumHere = 0;
        distToFinish = -1;

        if (isCurrent) {
            fastNativeMapGeneration(N);
            generateNextMaps();
        } else {
            generatorThread = new Thread(() -> {
                nativeMapGeneration(N, playerX, playerY);
            });
            generatorThread.setDaemon(true);
            generatorThread.start();
        }
    }

    public Thread getGeneratorThread(){
        return generatorThread;
    }

    public int getGlobalN(){
        return globalSize;
    }

    public boolean isGenerated(){
        return generated;
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

    public int getCage(int i, int j) throws MapRangeOutException {
        if (i >= 0 && j >= 0 && i < N && j < N) return map[i][j];
        throw new MapRangeOutException();
    }

    public boolean isPortalHere(int x, int y){
//        System.out.println("Is portal in: " + x + " " + y);
        for (int i = 0; i < portalN; i++) if (portals[i].getX() == x && portals[i].getY() == y) return true;
        return false;
    }

    public Portal getPortalHere(int x, int y) throws NoPortalHere{
        if (!isPortalHere(x, y)) throw new NoPortalHere();
        for (int i = 0; i < portalN; i++) if (portals[i].getX() == x && portals[i].getY() == y) return portals[i];
        return null;
    }

    public GameMap gameMapChanger(GameMap map){
        this.isCurrent = false;
        map.isCurrent = true;
        map.generateNextMaps();
        return map;

    }

    public GameMap nextGameMapAtPortal(Portal portal) throws NextMapException{
        for (int i = 0; i < portalN; i++) if (portal == portals[i]) return nextGameMaps[i];
        throw new NextMapException();
    }

    private void generateNextMaps(){
        for (int i = 0; i < portalN; i++) nextGameMaps[i] = new GameMap(lvl + 1, N, playState, portals[i].getX(), portals[i].getY(), false);
        //for (int i = 0; i < portalN; i++) nextGameMaps[i] = new GameMap(this);
    }

    private void writeNativeResult(int[] tab){
        componentNum = tab[N * N];
        distToFinish = tab[N * N + 1];
        portalNumHere = tab[N * N + 2];

        int z = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = tab[i * N + j];
                if (map[i][j] == 2) portals[z++] = new Portal(i, j);
                if (map[i][j] == 3) finish = new Finish(i, j);
            }
        }
        generated = true;
    }

    private void fastNativeMapGeneration(int N) {
        map = new int[N][N];
        int[] tab = new int[N * N + 3];

        new Native().getTable(tab, N, portalN, -1, -1, -1, -1, -1);
        writeNativeResult(tab);
    }

    private void nativeMapGeneration(int N, int playerX, int playerY) {
        map = new int[N][N];
        int[] tab = new int[N * N + 3];
        //while (true) {
            new Native().getTable(tab, N, portalN, componentNum, distToFinish, portalNumHere, playerX, playerY);
        System.out.println("Got map8");

            writeNativeResult(tab);
        //}
    }

    private void generateAllRandomMap(int N){
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
