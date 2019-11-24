package com.game.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.game.exceptions.MapRangeOutException;
import com.game.managers.GameKeys;
import com.game.managers.GameStateManager;
import com.game.objects.Player;

import static com.game.mazebattle.Game.*;

public class PlayState extends GameState {
    int k = 0;
    private Player player;
    private ShapeRenderer shapeRenderer;
    public PlayState(GameStateManager gameStateManager){
        super(gameStateManager);
        shapeRenderer = new ShapeRenderer();
        player = new Player(16, 16);
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float dt) {
        if (GameKeys.isPressed(Input.Keys.UP)) player.tryUP();
        if (GameKeys.isPressed(Input.Keys.DOWN)) player.tryDOWN();
        if (GameKeys.isPressed(Input.Keys.LEFT)) player.tryLEFT();
        if (GameKeys.isPressed(Input.Keys.RIGHT)) player.tryRIGHT();
    }

    @Override
    public void draw() {
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        int curr = (HEIGHT - gameMap.getGlobalN()) / 2;
        int cageSize = gameMap.getGlobalN() / gameMap.getcageN();
        for (int i = 0; i < gameMap.getcageN(); i++)
            for (int j = 0; j < gameMap.getcageN(); j++) {
                if (i == player.getX() && j == player.getY()) continue;
                int cageX = curr + i * cageSize;
                int cageY = curr + j * cageSize;
                int cage = 0;
                try {
                    cage = gameMap.getCage(i, j);
                } catch (MapRangeOutException e) {
                    continue;
                }
                if (cage == 0) shapeRenderer.setColor(Color.LIGHT_GRAY);
                if (cage == 1) shapeRenderer.setColor(Color.BLACK);
                if (cage == 2) shapeRenderer.setColor(Color.YELLOW);
                if (cage == 3) shapeRenderer.setColor(Color.WHITE);
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
