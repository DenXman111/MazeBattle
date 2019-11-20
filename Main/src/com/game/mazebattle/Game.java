package com.game.mazebattle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Game implements ApplicationListener {
    public static int WIDTH;
    public static int HEIGHT;

    public static OrthographicCamera camera;

    public void create(){
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.translate((float) WIDTH / 2, (float) HEIGHT / 2);
        camera.update();
    }

    public void render(){
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) Gdx.gl.glClearColor(0, 0, 1, 1);
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void resize(int width, int height) {}
    public void pause(){}
    public void resume(){}
    public void dispose(){}
}
