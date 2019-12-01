package com.game.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.game.exceptions.MapRangeOutException;
import com.game.exceptions.NoPortalHere;
import com.game.gamemap.GameMap;
import com.game.managers.GameKeys;
import com.game.managers.GameStateManager;
import com.game.mazebattle.Game;
import com.game.objects.Player;

import static com.game.mazebattle.Game.*;
import static java.lang.System.exit;

public class PlayState extends GameState {

    private GameMap gameMap;

    int k = 0;
    private Player player;
    private ShapeRenderer shapeRenderer;


    private BitmapFont font32;
    private BitmapFont font72;

    public PlayState(GameStateManager gameStateManager){
        super(gameStateManager);
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Amatic-Bold.ttf"));
        font32 = fontGenerator.generateFont(32);
        font72 = fontGenerator.generateFont(72);
        gameMap = new GameMap(1, 32, this, -1, -1, true);
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

    public GameMap getGameMap(){
        return gameMap;
    }

    public void setGameMap(GameMap map){
        gameMap = map;
    }

    private SpriteBatch spriteBatch;
    @Override
    public void init() {
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void update(float dt) {
        /*try {
            gameMap.getGeneratorThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //System.out.println(gameMap.isGenerated());
        if (!gameMap.isGenerated()) return;


        //moving
        if (GameKeys.isPressed(Input.Keys.UP)) player.tryUP(this);
        if (GameKeys.isPressed(Input.Keys.DOWN)) player.tryDOWN(this);
        if (GameKeys.isPressed(Input.Keys.LEFT)) player.tryLEFT(this);
        if (GameKeys.isPressed(Input.Keys.RIGHT)) player.tryRIGHT(this);

        //finish managing
        if (player.getX() == gameMap.getFinish().getX() && player.getY() == gameMap.getFinish().getY())
            gameMap.getFinish().stepOnFinish(this);

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
        //System.out.println(gameMap.isGenerated());
        if (!gameMap.isGenerated()) return;

        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        int curr = (HEIGHT - gameMap.getGlobalN()) / 2;
        int cageSize = gameMap.getGlobalN() / gameMap.getN();

        spriteBatch.setProjectionMatrix(Game.camera.combined);
        spriteBatch.begin();

        font72.draw(spriteBatch, "lvl: " + gameMap.getlvl(), 700, 550);

        shapeRenderer.setColor(Color.PINK);
        shapeRenderer.rect(720, 400, cageSize, cageSize);
        font32.draw(spriteBatch, "  - player", 730, 420);

        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(720, 320, cageSize, cageSize);
        font32.draw(spriteBatch, "  - portal", 730, 340);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(720, 240, cageSize, cageSize);
        font32.draw(spriteBatch, "  - finish", 730, 260);

        spriteBatch.end();

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
