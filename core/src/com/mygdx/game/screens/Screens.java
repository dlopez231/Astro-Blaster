package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class Screens {

    protected OrthographicCamera camera;

    protected Vector3 input;

    protected ScreenManager sm;

    protected Screens(ScreenManager sm){

        this.sm = sm;
        camera = new OrthographicCamera();
        input = new Vector3();

    }

    protected abstract void handleInput();

    public abstract void update(float delta);

    public abstract void render(SpriteBatch sb);

    public abstract void dispose();

}
