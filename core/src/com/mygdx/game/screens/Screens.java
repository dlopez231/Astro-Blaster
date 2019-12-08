package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;

// Abstract class for all screens to use
public abstract class Screens {

    // Camera helps with game screen
    protected OrthographicCamera camera;

    protected Vector3 input;

    protected ScreenManager sm;

    // Preferences to save scores in
    protected Preferences myPrefs;

    // ArrayList to store high scores
    protected ArrayList<Integer> highScores;

    protected Sound buttonSound;

    protected Screens(ScreenManager sm){

        this.sm = sm;
        camera = new OrthographicCamera();
        input = new Vector3();
        myPrefs = Gdx.app.getPreferences("Game Scores");
        highScores = new ArrayList<Integer>();
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("buttonSound.ogg"));

    }

    protected abstract void handleInput();

    public abstract void update(float delta);

    public abstract void render(SpriteBatch sb);

    public abstract void dispose();

    protected void disposeSound(){

        buttonSound.dispose();
    }

}
