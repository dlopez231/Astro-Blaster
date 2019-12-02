package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

public abstract class Screens {

    protected OrthographicCamera camera;

    protected Vector3 input;

    protected ScreenManager sm;

    protected Preferences myPrefs;

    public ArrayList<Integer> highScores;

    protected Screens(ScreenManager sm){

        this.sm = sm;
        camera = new OrthographicCamera();
        input = new Vector3();
        myPrefs = Gdx.app.getPreferences("Game Scores");
        highScores = new ArrayList<Integer>();



    }

    protected abstract void handleInput();

    public abstract void update(float delta);

    public abstract void render(SpriteBatch sb);

    public abstract void dispose();

}
