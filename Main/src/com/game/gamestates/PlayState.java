package com.game.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.game.exceptions.MapRangeOutException;
import com.game.exceptions.NoPortalHere;
import com.game.gamemap.GameMap;
import com.game.managers.GameKeys;
import com.game.managers.GameStateManager;
import com.game.objects.Player;

import static com.game.mazebattle.Game.*;

public class PlayState extends GameState {
    private int componentNum = -1;
    private int distToFinish = -1;
    private int portalNumHere = -1;

    public GameMap gameMap;

    int k = 0;
    private Player player;
    private ShapeRenderer shapeRenderer;
    public PlayState(GameStateManager gameStateManager){
        super(gameStateManager);
        gameMap = new GameMap(1, 32, this, -1, -1);
        shapeRenderer = new ShapeRenderer();
        for (int i = 0; i < gameMap.getN(); i++)
            for (int j = 0; j < gameMap.getN(); j++) {
                try {
                    if (gameMap.getCage(i, j) == 4) {
                        player = new Player(i, j);
                        //gameMap.playerGot(i, j);
                    }
                } catch (MapRangeOutException e) {
                    e.printStackTrace();
                }
            }
    }

    public int getComponentNum(){
        return componentNum;
    }

    public int getDistToFinish(){
        return distToFinish;
    }

    public int getPortalNumHere(){
        return portalNumHere;
    }

    public void setComponentNum(int x){
        componentNum = x;
    }

    public void setDistToFinish(int x){
        distToFinish = x;
    }

    public void setPortalNumHere(int x){
        portalNumHere = x;
    }

    public void setGameMap(GameMap map){
        gameMap = map;
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float dt) {
        //moving
        if (GameKeys.isPressed(Input.Keys.UP)) player.tryUP(this);
        if (GameKeys.isPressed(Input.Keys.DOWN)) player.tryDOWN(this);
        if (GameKeys.isPressed(Input.Keys.LEFT)) player.tryLEFT(this);
        if (GameKeys.isPressed(Input.Keys.RIGHT)) player.tryRIGHT(this);

        //finish managing
        if (player.getX() == gameMap.getFinish().getX() && player.getY() == gameMap.getFinish().getY()) gameMap.getFinish().stepOnFinish(this);

        //portal managing
        if (gameMap.isPortalHere(player.getX(), player.getY())) {
            try {
                gameMap.getPortalHere(player.getX(), player.getY()).changeMap(gameMap, this, player.getX(), player.getY());
            } catch (NoPortalHere noPortalHere) {
                noPortalHere.printStackTrace();
            }
        }
    }

    @Override
    public void draw() {
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        int curr = (HEIGHT - gameMap.getGlobalN()) / 2;
        int cageSize = gameMap.getGlobalN() / gameMap.getN();
        for (int i = 0; i < gameMap.getN(); i++)
            for (int j = 0; j < gameMap.getN(); j++) {
                if (i == player.getX() && j == player.getY()) continue;
                int cageX = curr + i * cageSize;
                int cageY = curr + j * cageSize;
                int cage = 0;
                try {
                    cage = gameMap.getCage(i, j);
                } catch (MapRangeOutException e) {
                    continue;
                }
                if (cage == 0 || cage == 4) shapeRenderer.setColor(Color.LIGHT_GRAY);
                if (cage == 1) shapeRenderer.setColor(Color.BLACK);
                if (cage == 2) shapeRenderer.setColor(Color.YELLOW);
                if (cage == 3) shapeRenderer.setColor(Color.RED);
                shapeRenderer.rect(cageX, cageY, cageSize, cageSize);
            }
        int cageX = curr + player.getX() * cageSize;
        int cageY = curr + player.getY() * cageSize;
        shapeRenderer.setColor(Color.PINK);
        shapeRenderer.rect(cageX, cageY, cageSize, cageSize);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
