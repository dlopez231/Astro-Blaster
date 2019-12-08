package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.AstroBlaster;

// The leader board screen of the game
public class ScoreScreen extends Screens {

    // Textures for scoreboard screen
    private Texture background;
    private Texture scoreTitle;
    private String score_1;
    private String score_2;
    private String score_3;
    private String score_4;
    private String score_5;
    private BitmapFont scoreFont;
    private Texture homeButton;

    public ScoreScreen(ScreenManager sm) {

        super(sm);

        camera.setToOrtho(false, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);

        // Initialize textures
        background = new Texture("background.png");
        scoreTitle = new Texture("Scores Title.png");
        homeButton = new Texture("home.png");

        // Get high scores from the preferences and store them in string
        score_1 = "1:   " + myPrefs.getInteger("score_1");
        score_2 = "2:   " + myPrefs.getInteger("score_2");
        score_3 = "3:   " + myPrefs.getInteger("score_3");
        score_4 = "4:   " + myPrefs.getInteger("score_4");
        score_5 = "5:   " + myPrefs.getInteger("score_5");

        // Custom font
        scoreFont = new BitmapFont(Gdx.files.internal("myFont.fnt"), Gdx.files.internal("myFont.png"), false);
        scoreFont.getData().setScale(0.7f,0.7f);

    }



    @Override
    protected void handleInput() {

        if(Gdx.input.justTouched()){

            input.set(Gdx.input.getX(), Gdx.input.getY(), 0);

            camera.unproject(input);

            Rectangle homeButtonBounds = new Rectangle(720, 20, homeButton.getWidth(), homeButton.getHeight());

            if(homeButtonBounds.contains(input.x, input.y)){

                buttonSound.play(0.5f);
                sm.popScreen();

            }
        }
    }

    @Override
    public void update(float delta) {

        camera.update();
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(camera.combined);

        // Draw scores
        sb.begin();
        sb.draw(background, 0, 0, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);
        sb.draw(scoreTitle, AstroBlaster.WIDTH/2 - scoreTitle.getWidth()/2, 400);

        scoreFont.draw(sb, score_1, 330, 320);
        scoreFont.draw(sb, score_2, 330, 270);
        scoreFont.draw(sb, score_3, 330, 220);
        scoreFont.draw(sb, score_4, 330, 170);
        scoreFont.draw(sb, score_5, 330, 120);

        sb.draw(homeButton, 720, 20);

        sb.end();

    }

    @Override
    public void dispose(){

        // Dispose everything
        background.dispose();
        scoreTitle.dispose();
        scoreFont.dispose();
        homeButton.dispose();

    }
}
