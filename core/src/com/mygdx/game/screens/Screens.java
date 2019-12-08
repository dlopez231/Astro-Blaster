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

    // Input for all touch events
    protected Vector3 input;

    // All screens uses the same ScreenManager
    protected ScreenManager sm;

    // Preferences to save scores in
    protected Preferences myPrefs;

    // ArrayList to store high scores
    protected ArrayList<Integer> highScores;

    // All screens will use the same button sound
    protected Sound buttonSound;

    // Initialize variables
    protected Screens(ScreenManager sm){

        this.sm = sm;
        camera = new OrthographicCamera();
        input = new Vector3();
        myPrefs = Gdx.app.getPreferences("Game Scores");
        highScores = new ArrayList<Integer>();
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("buttonSound.ogg"));

    }

    // This will be in charge of all input, you would call it in update
    protected abstract void handleInput();

    // Update constantly runs, it handles what most things happen in screen
    public abstract void update(float delta);

    // Put objects that needs to be drawn in render
    public abstract void render(SpriteBatch sb);

    // Always dispose textures, sounds, music, etc.
    public abstract void dispose();

    // Dispose the button sound
    protected void disposeSound(){

        buttonSound.dispose();
    }

}
