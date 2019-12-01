package com.game.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.game.managers.GameKeys;
import com.game.managers.GameStateManager;
import com.game.mazebattle.Game;

public class FinishedState extends GameState{
    private SpriteBatch spriteBatch;
    private BitmapFont titleFont;
    private BitmapFont font;
    private final String title = "YOU WIN!!!";
    private final String item1 = "PRESS SPACE TO RESTART";


    public FinishedState(GameStateManager gameStateManager){
        super(gameStateManager);
    }

    @Override
    public void init() {
        spriteBatch = new SpriteBatch();
        FreeTypeFontGenerator titleFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Amatic-Bold.ttf"));
        titleFont = titleFontGenerator.generateFont(94);
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AmaticSC-Regular.ttf"));
        font = fontGenerator.generateFont(72);

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    private int ticks = 30;
    private int currTick = 0;
    private boolean red = true;

    @Override
    public void draw() {
        spriteBatch.setProjectionMatrix(Game.camera.combined);
        spriteBatch.begin();
        float width = 250;
        titleFont.draw(spriteBatch, title, (Game.WIDTH - width)/ 2, 500);

        if (currTick > ticks) {
            currTick = 0;
            if (red) red = false; else red = true;
            if (red) font.setColor(Color.RED); else font.setColor(Color.WHITE);

        }
        width = 420;
        font.draw(spriteBatch, item1, (Game.WIDTH - width)/ 2, 300);
        ++currTick;

        spriteBatch.end();
    }

    @Override
    public void handleInput() {
        if (GameKeys.isPressed(Input.Keys.SPACE)) state.restartGame();
    }

    @Override
    public void dispose() {

    }
}
