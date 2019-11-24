package com.game.mazebattle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.game.gamemap.GameMap;
import com.game.managers.GameInputProcessor;
import com.game.managers.GameKeys;
import com.game.managers.GameStateManager;

public class Game implements ApplicationListener {
    public static int WIDTH;
    public static int HEIGHT;

    public static GameMap gameMap = new GameMap(1, 32);

    public static OrthographicCamera camera;
    private GameStateManager gameStateManager;

    @Override
    public void create(){
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.translate((float) WIDTH / 2, (float) HEIGHT / 2);
        camera.update();

        Gdx.input.setInputProcessor(new GameInputProcessor());
        gameStateManager = new GameStateManager();
    }

    @Override
    public void render(){
        Gdx.gl.glClearColor( 0, (float)0.5, (float)0.33, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameStateManager.update(Gdx.graphics.getDeltaTime());
        gameStateManager.draw();

        if (GameKeys.isDown(Input.Keys.SPACE)) Gdx.gl.glClearColor(0, 1, 0, 1);
        if (GameKeys.isDown(Input.Keys.SPACE)) Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (GameKeys.isPressed(Input.Keys.SPACE)) Gdx.gl.glClearColor((float)0.5, 0, 1, 1);
        if (GameKeys.isPressed(Input.Keys.SPACE)) Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GameKeys.update();
    }

    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause(){}
    @Override
    public void resume(){}
    @Override
    public void dispose(){}
}
