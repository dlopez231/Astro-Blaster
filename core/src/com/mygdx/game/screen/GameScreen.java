package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MainGame;
import com.mygdx.game.camera.OrthoCamera;
import com.mygdx.game.entity.EntityManager;

public class GameScreen extends Screen{

    private OrthoCamera camera;
    private EntityManager entityManager;


    @Override
    public void create() {

        camera = new OrthoCamera();
        entityManager = new EntityManager(10, camera);

    }

    @Override
    public void update() {
        camera.update();
        entityManager.update();
    }

    @Override
    public void render(SpriteBatch sb) {

        Gdx.gl.glClearColor(0, 0, 1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        entityManager.render(sb);
        sb.end();

    }

    @Override
    public void resize(int width, int height) {
        camera.resize();

    }

    @Override
    public void dispose() {


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
