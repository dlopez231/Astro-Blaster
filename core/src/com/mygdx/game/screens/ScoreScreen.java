package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.IntArray;
import com.mygdx.game.AstroBlaster;

public class ScoreScreen extends Screens {

    // Textures for scoreboard screen
    private Texture background;
    private String score_1;
    private String score_2;
    private String score_3;
    private String score_4;
    private String score_5;
    private BitmapFont scoreFont;

    public ScoreScreen(ScreenManager sm) {
        super(sm);

        camera.setToOrtho(false, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);

        background = new Texture("background2.png");

        // Get high scores and store them in string
        score_1 = "Score 1: " + myPrefs.getInteger("score_1");
        score_2 = "Score 2: " + myPrefs.getInteger("score_2");
        score_3 = "Score 3: " + myPrefs.getInteger("score_3");
        score_4 = "Score 4: " + myPrefs.getInteger("score_4");
        score_5 = "Score 5: " + myPrefs.getInteger("score_5");

        // Custom font
        scoreFont = new BitmapFont(Gdx.files.internal("myFont.fnt"), Gdx.files.internal("myFont.png"), false);
        scoreFont.getData().setScale(0.7f,0.7f);

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float delta) {
        camera.update();

    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(camera.combined);

        // Draw scores
        sb.begin();
        sb.draw(background, 0, 0, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);

        scoreFont.draw(sb, score_1, 20, 300);
        scoreFont.draw(sb, score_2, 20, 250);
        scoreFont.draw(sb, score_3, 20, 200);
        scoreFont.draw(sb, score_4, 20, 150);
        scoreFont.draw(sb, score_5, 20, 100);


        sb.end();

    }

    @Override
    public void dispose() {

    }




}
